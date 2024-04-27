package coralis.utils

import edu.wpi.first.wpilibj.RobotState

class RobotState {
    enum class State {
        DISABLED,
        AUTONOMOUS,
        TELEOP,
        TEST,
        ESTOPED
    }

    val robotState: State
        get() {
            if (RobotState.isEStopped()) return State.ESTOPED
            else if (RobotState.isDisabled()) return State.DISABLED
            else if (RobotState.isAutonomous()) return State.AUTONOMOUS
            else if (RobotState.isTeleop()) return State.TELEOP
            else if (RobotState.isTest()) return State.TEST
            else return State.DISABLED
        }
}