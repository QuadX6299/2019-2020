package org.firstinspires.ftc.teamcode.Robot.Sensors


abstract class Clock {

    companion object {
        @JvmStatic
        fun system() = object : Clock() {
            override fun seconds() = System.nanoTime() / 1e9
        }
    }
    abstract fun seconds(): Double
}