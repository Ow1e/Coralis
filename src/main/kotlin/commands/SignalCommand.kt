package commands

import edu.wpi.first.wpilibj2.command.Command
import signals.Signal

class SignalCommand<T: Enum<T>>(private val signal: Signal<T>, private val newState: T, actionName : String) : Command() {
    init {
        name = actionName
    }

    override fun initialize() {
        signal.signal = newState
    }

    override fun isFinished() = false

    override fun runsWhenDisabled() = true
}