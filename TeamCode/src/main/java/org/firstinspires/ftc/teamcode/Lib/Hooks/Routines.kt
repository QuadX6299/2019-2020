package org.firstinspires.ftc.teamcode.Lib.Hooks

typealias Subroutine<T> = (T) -> Unit

interface Routine {

}

interface Singular : Routine {
    fun run(arg: Int)
}

interface AsyncLooping : Routine {
    fun loop(arg: Int) : Boolean
}

interface PathBased : Routine {
    fun start(path: Double, arg: Int)
}

interface TimeBased : Routine {
    fun queue(time: Long, arg: Int)
}

interface BlockingLoop : Routine {
    fun blockLoop(arg: Int) : Boolean
}

class SingleRoutine constructor(val action: (Int) -> Unit) : Singular {
    override fun run(arg: Int) {
        action(arg)
    }
}

class AsyncLoopRoutine constructor(val action: (Int) -> Boolean) : AsyncLooping {
    override fun loop(arg: Int): Boolean {
        return action(arg)
    }
}

class BlockingLoopRoutine constructor(val action: (Int) -> Boolean) : BlockingLoop {
    override fun blockLoop(arg: Int): Boolean {
        return action(arg)
    }
}