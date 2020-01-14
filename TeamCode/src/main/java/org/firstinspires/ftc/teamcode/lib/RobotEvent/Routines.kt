package org.firstinspires.ftc.teamcode.lib.RobotEvent

import org.firstinspires.ftc.teamcode.Robot.NDriveTrain4Mecanum
import org.firstinspires.ftc.teamcode.Robot.NRobot
import org.firstinspires.ftc.teamcode.lib.Coords.Point

class Routines {

    interface Routine {}

    class Singular(private val runOnce: (NRobot) -> Unit) : Routine

    class PathBased(private val runOnce: (Point, NRobot) -> Unit) : Routine

    class Repeating(private val loop: (NRobot) -> Boolean) : Routine
}