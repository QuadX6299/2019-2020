package org.firstinspires.ftc.teamcode.Lib.Hooks

import org.firstinspires.ftc.teamcode.Lib.Marker.Waypoint
import org.firstinspires.ftc.teamcode.Modules.Robot

interface Routine { }

interface Singular : Routine {
    fun run(arg: Robot)
}

interface AsyncLooping : Routine {
    fun loop(arg: Robot) : Boolean
}

interface PathBased : Routine {
    fun start(path: List<Waypoint>, arg: Robot)
}

interface TimeBased : Routine {
    fun queue(time: Long, arg: Robot)
}

interface BlockingLoop : Routine {
    fun blockLoop(arg: Robot) : Boolean
}

class SingleRoutine constructor(val action: (Robot) -> Unit) : Singular {
    var done = false
    override fun run(arg: Robot) {
        action(arg)
        done = true
    }
}

class AsyncLoopRoutine constructor(val action: (Robot) -> Boolean) : AsyncLooping {
    override fun loop(arg: Robot): Boolean {
        return action(arg)
    }
}

class BlockingLoopRoutine constructor(val action: (Robot) -> Boolean) : BlockingLoop {
    override fun blockLoop(arg: Robot): Boolean {
        return action(arg)
    }
}