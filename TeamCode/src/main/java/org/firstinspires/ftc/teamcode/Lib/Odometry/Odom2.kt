package org.firstinspires.ftc.teamcode.Lib.Odometry

import org.apache.commons.math3.linear.Array2DRowRealMatrix
import org.apache.commons.math3.linear.DecompositionSolver
import org.apache.commons.math3.linear.LUDecomposition
import org.apache.commons.math3.linear.MatrixUtils
import org.firstinspires.ftc.teamcode.Lib.Structs.Point
import org.firstinspires.ftc.teamcode.Lib.Structs.Pose2D
import org.firstinspires.ftc.teamcode.Lib.Util.fuzzyEquals
import org.firstinspires.ftc.teamcode.Lib.Util.limitAngle2
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin

abstract class Odom2(
        wheelPoses: List<Pose2D>
) {
    private var _poseEstimate = Pose2D()
    var poseEstimate: Pose2D
        get() = _poseEstimate
        set(value) {
            lastWheelPositions = emptyList()
            lastHeading = Double.NaN
            _poseEstimate = value
        }
    private var lastWheelPositions = emptyList<Double>()
    private var lastHeading = Double.NaN

    private val forwardSolver: DecompositionSolver

    init {
        require(wheelPoses.size == 2) { "2 wheel poses must be provided" }

        val inverseMatrix = Array2DRowRealMatrix(3, 3)
        for (i in 0..1) {
            val orientationVector = wheelPoses[i].headingVector()
            val positionVector = Point(wheelPoses[i].x, wheelPoses[i].y)
            inverseMatrix.setEntry(i, 0, orientationVector.x)
            inverseMatrix.setEntry(i, 1, orientationVector.y)
            inverseMatrix.setEntry(i, 2,
                    positionVector.x * orientationVector.y - positionVector.y * orientationVector.x)
        }
        inverseMatrix.setEntry(2, 2, 1.0)

        forwardSolver = LUDecomposition(inverseMatrix).solver

        require(forwardSolver.isNonSingular) { "The specified configuration cannot support full localization" }
    }

    fun update() {
        val wheelPositions = getWheelPositions()
        val heading = getHeading()
        if (lastWheelPositions.isNotEmpty()) {
            val wheelDeltas = wheelPositions
                    .zip(lastWheelPositions)
                    .map { it.first - it.second }
            val headingDelta = norm(heading - lastHeading)
            val rawPoseDelta = forwardSolver.solve(MatrixUtils.createRealMatrix(
                    arrayOf((wheelDeltas + headingDelta).toDoubleArray())
            ).transpose())
            val robotPoseDelta = Pose2D(
                    rawPoseDelta.getEntry(0, 0),
                    rawPoseDelta.getEntry(1, 0),
                    rawPoseDelta.getEntry(2, 0)
            )
            _poseEstimate = relativeOdometryUpdate(_poseEstimate, robotPoseDelta)
        }
        lastWheelPositions = wheelPositions
        lastHeading = heading
    }

    fun relativeOdometryUpdate(fieldPose: Pose2D, robotPoseDelta: Pose2D) : Pose2D {
        val dtheta = robotPoseDelta.heading
        val (sineTerm, cosTerm) = if (dtheta.fuzzyEquals(0.0, 10.0.pow(-6))) {
            1.0 - dtheta * dtheta / 6.0 to dtheta / 2.0
        } else {
            sin(dtheta) / dtheta to (1 - cos(dtheta)) / dtheta
        }

        val fieldPositionDelta = Point(
                sineTerm * robotPoseDelta.x - cosTerm * robotPoseDelta.y,
                cosTerm * robotPoseDelta.x + sineTerm * robotPoseDelta.y
        )

        val fieldPoseDelta = Pose2D(fieldPositionDelta.rotate(fieldPose.heading), robotPoseDelta.heading)

        return Pose2D(
                fieldPose.x + fieldPoseDelta.x,
                fieldPose.y + fieldPoseDelta.y,
                (fieldPose.heading + fieldPoseDelta.heading).limitAngle2()
        )
    }

    fun norm(angle: Double): Double {
        var modifiedAngle = angle % (2 * PI)

        modifiedAngle = (modifiedAngle + (2 * PI)) % (2 * PI)

        return modifiedAngle
    }


    /**
     * Returns the positions of the tracking wheels in the desired distance units (not encoder counts!)
     */
    abstract fun getWheelPositions(): List<Double>

    /**
     * Returns the heading of the robot (usually from a gyroscope or IMU).
     */
    abstract fun getHeading(): Double
}