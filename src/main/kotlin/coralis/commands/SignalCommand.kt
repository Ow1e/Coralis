package coralis.commands

import edu.wpi.first.wpilibj2.command.Command
import coralis.signals.Signal

/**
 * This class represents a command that sets a signal to a new state.
 *
 * @param T The type of the enum that represents the possible states of the signal.
 * @property signal The signal that will be set to a new state.
 * @property newState The new state that the signal will be set to.
 * @property actionName The name of the action.
 *
 * @constructor Creates a new SignalCommand with the specified signal, new state, and action name.
 */
class SignalCommand<T: Enum<T>>(private val signal: Signal<T>, private val newState: T, private val actionName : String) : Command() {
    init {
        name = actionName

        if (signal.protectDomain && signal.protector != null) {
            addRequirements(signal.protector)
        }
    }

    /**
     * This function is called when the command is initialized.
     * It sets the signal to the new state.
     */
    override fun initialize() {
        signal.signal = newState
    }

    /**
     * This function checks if the command is finished.
     * @return false as the command is never finished until overridden.
     */
    override fun isFinished() = false

    /**
     * This function checks if the command runs when disabled.
     * @return true as the command runs even when disabled.
     */
    override fun runsWhenDisabled() = true
}