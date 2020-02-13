package org.firstinspires.ftc.teamcode.Lib.Hooks

import android.os.Handler
import android.os.Looper
import org.firstinspires.ftc.teamcode.Lib.Marker.Waypoint
import org.firstinspires.ftc.teamcode.Lib.Structs.Pose2D
import org.firstinspires.ftc.teamcode.Lib.Util.wrap
import org.firstinspires.ftc.teamcode.Modules.Robot
import kotlin.math.abs

interface Routine { }

interface Static : Routine {
    fun exec()
    fun isDone() : Boolean
    fun isRun() : Boolean
}

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

class StaticRoutine constructor(val action: () -> Unit) : Static {
    var done = false
    var run = false
    override fun exec() {
        action()
        run = true
    }

    override fun isDone(): Boolean {
        return done
    }

    override fun isRun(): Boolean {
        return run
    }
}

class SingleRoutine constructor(val action: (Robot) -> Unit) : Singular {
    var done = false
    override fun run(arg: Robot) {
        action(arg)
        done = true
    }
}

class DelayedRoutine constructor(val action: () -> Unit, val delay: Long) : Static {
    var done = false
    var run = false
    override fun exec() {
        if (run) { return }
        action()
        run = true
        Handler(Looper.getMainLooper()).postDelayed({
            done = true
        }, delay)
    }

    override fun isDone(): Boolean {
        return done
    }

    override fun isRun(): Boolean {
        return run
    }
}

class ArrivalRoutine constructor(val action: () -> Unit, val delay: Long, val tolS : Double) : Static {
    var done = false
    var run = false
    var close = false
    fun update(rloc: Pose2D, target: Waypoint) {
        if (abs(rloc distance target) < tolS) {
            close = true
        }
    }

    override fun exec() {
        if (run) return
        if (close) {
            action()
            run = true
            Handler(Looper.getMainLooper()).postDelayed({
                done = true
            }, delay)
        }
    }

    override fun isDone() : Boolean {
        return done
    }

    override fun isRun(): Boolean {
        return run
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