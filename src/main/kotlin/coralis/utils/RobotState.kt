package coralis.utils

import edu.wpi.first.wpilibj.RobotState

object RobotState {
    enum class State {
        DISABLED,
        AUTONOMOUS,
        TELEOP,
        TEST,
        ESTOPED
    }

    val robotState: State
        get() {
            return if (RobotState.isEStopped()) State.ESTOPED
            else if (RobotState.isDisabled()) State.DISABLED
            else if (RobotState.isTest()) State.TEST
            else if (RobotState.isAutonomous()) State.AUTONOMOUS
            else if (RobotState.isTeleop()) State.TELEOP
            else State.DISABLED
        }
}