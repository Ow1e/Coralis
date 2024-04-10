package coralis.commands

import edu.wpi.first.wpilibj2.command.Command

/**
 * This class represents a blank command to be run once.
 *
 * @property onStart A lambda function that is executed when the command is started.
 * @property commandName The name of the command.
 *
 * @constructor Creates a new blank command with the specified start function and name.
 * */
class BlankCommand(private val onStart: () -> Unit, private val commandName : String) : Command() {
    init { name = commandName }

    /**
     * This function is called when the command is initialized.
     * It executes the onStart function.
     */
    override fun initialize() { onStart() }

    /**
     * This function checks if the command is finished.
     * @return false as the command is never finished until override.
     */
    override fun isFinished() = false

    /**
     * This function checks if the command runs when disabled.
     * @return true as the command runs even when disabled.
     */
    override fun runsWhenDisabled() = true
}