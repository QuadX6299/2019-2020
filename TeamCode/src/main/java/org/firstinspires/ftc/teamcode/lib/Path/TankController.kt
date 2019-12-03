package org.firstinspires.ftc.teamcode.lib.Path

import android.os.SystemClock
import com.qualcomm.robotcore.hardware.PIDCoefficients
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.lib.Extensions.fuzzyEquals
import kotlin.math.pow
import kotlin.math.sign

class TankController constructor(val co : PIDCoefficients, val kv : Double, val ka : Double, val clock: ElapsedTime){
    var esum = 0.0
    var lastTime = Double.NaN
    var target = 0.0
    var vprev = Double.NaN
    var eprev = 0.0

    private fun error(current : Double) : Double {
        return target - current
    }

    fun calc(v : Double) : Double {
        val timern = clock.seconds()
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
            val pid = co.p * e + co.d * deriv + kv * v + ka * a
            if (pid.fuzzyEquals(0.0, 1.0.pow(-6))) {
                0.0
            } else {
                pid
            }
        }
    }

    fun reset() {
        esum = 0.0
        eprev = 0.0
        lastTime = Double.NaN
    }
}