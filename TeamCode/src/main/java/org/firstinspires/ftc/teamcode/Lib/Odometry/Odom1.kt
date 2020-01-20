package org.firstinspires.ftc.teamcode.Lib.Odometry

import org.apache.commons.math3.linear.Array2DRowRealMatrix
import org.apache.commons.math3.linear.DecompositionSolver
import org.apache.commons.math3.linear.LUDecomposition
import org.apache.commons.math3.linear.MatrixUtils
import org.firstinspires.ftc.teamcode.Lib.Structs.Point
import org.firstinspires.ftc.teamcode.Lib.Structs.Pose2D
import org.firstinspires.ftc.teamcode.Lib.Util.fuzzyEquals
import org.firstinspires.ftc.teamcode.Lib.Util.limitAngle2
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin


// offsets = relative to center of robot +x = forward on robot
abstract class Odom1 constructor(val offsets: List<Pose2D>) {
    private var lastValues = emptyList<Double>()
    private var _poseEstimate = Pose2D()
    var poseEstimate: Pose2D
        get() = _poseEstimate
        set(value) {
            lastWheelPositions = emptyList()
            _poseEstimate = value
        }
    private var lastWheelPositions = emptyList<Double>()

    private val forwardSolver: DecompositionSolver

    init {
        require(offsets.size == 3) { "3 wheel positions must be provided" }

        val inverseMatrix = Array2DRowRealMatrix(3, 3)
        for (i in 0..2) {
            val orientationVector = Point(cos(offsets[i].heading), sin(offsets[i].heading))
            val positionVector = offsets[i] as Point
            inverseMatrix.setEntry(i, 0, orientationVector.x)
            inverseMatrix.setEntry(i, 1, orientationVector.y)
            inverseMatrix.setEntry(i, 2,
                    positionVector.x * orientationVector.y - positionVector.y * orientationVector.x)
        }

        forwardSolver = LUDecomposition(inverseMatrix).solver

        require(forwardSolver.isNonSingular) { "The specified configuration cannot support full localization" }
    }

    fun update() {
        val wheelPositions = getWheelPositions()
        if (lastWheelPositions.isNotEmpty()) {
            val wheelDeltas = wheelPositions
                    .zip(lastWheelPositions)
                    .map { it.first - it.second }
            val rawPoseDelta = forwardSolver.solve(MatrixUtils.createRealMatrix(
                    arrayOf(wheelDeltas.toDoubleArray())
            ).transpose())
            val robotPoseDelta = Pose2D(
                    rawPoseDelta.getEntry(0, 0),
                    rawPoseDelta.getEntry(1, 0),
                    rawPoseDelta.getEntry(2, 0)
            )
            _poseEstimate = relativeOdometryUpdate(_poseEstimate, robotPoseDelta)
        }
        lastWheelPositions = wheelPositions
    }

    fun relativeOdometryUpdate(fieldPose: Pose2D, robotPoseDelta: Pose2D): Pose2D {
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


    abstract fun getWheelPositions(): List<Double>
}