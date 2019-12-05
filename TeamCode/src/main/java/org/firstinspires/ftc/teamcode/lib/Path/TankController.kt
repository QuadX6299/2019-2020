package org.firstinspires.ftc.teamcode.lib.Path

import android.os.SystemClock
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.PIDCoefficients
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.Robot.Sensors.Clock
import org.firstinspires.ftc.teamcode.lib.Extensions.fuzzyEquals
import kotlin.math.pow
import kotlin.math.sign

class TankController constructor(val co : PIDCoefficients, val kv : Double, val ka : Double, val clock: Clock = Clock.system(), val Op : OpMode){
    var esum = 0.0
    var lastTime = Double.NaN
    var target = 0.0
    var vprev = Double.NaN
    var eprev = 0.0

    private fun error(current : Double) : Double {
        return target - current
    }

    fun calc(v : Double) : Double {
        val timern = clock.seconds()*1000
        val e = error(v)
        return if (lastTime.isNaN()) {
            eprev = e
            lastTime = timern
            0.0
        } else {
            val dterm = timern - lastTime
            esum += .5 * (e + eprev) * dterm
            val deriv = (e - eprev) / dterm
            val a = if (vprev.isNaN()) {
                v / dterm
            } else {
                (v - vprev) / dterm
            }

            eprev = e
            lastTime = timern
            vprev = v
            val pid = (co.p * e) + co.d * deriv + kv * v + ka * a
            return pid
        }
    }

    fun reset() {
        esum = 0.0
        eprev = 0.0
        lastTime = Double.NaN
    }
}