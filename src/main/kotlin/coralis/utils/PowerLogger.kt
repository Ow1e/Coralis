package coralis.utils

import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.wpilibj.Notifier
import edu.wpi.first.wpilibj.PowerDistribution

class PowerLogger(
    canID: Int = 0,
    powerType : PowerDistribution.ModuleType = PowerDistribution.ModuleType.kRev,
    private val overCurrentWarningChannelThreshold: Double = 100.0,
    private val overCurrentWarningTotalThreshold: Double = 200.0,
    private val overTemperatureWarningThreshold: Double = 60.0
) {
    private val pdh : PowerDistribution = PowerDistribution(canID, powerType)
    private val notifier = Notifier(this::log)
    private val ntInstance = NetworkTableInstance.getDefault()
    private val ntTable = ntInstance.getTable("Coralis").getSubTable("Power")

    private val channels = if (powerType == PowerDistribution.ModuleType.kRev) 23 else 15

    init {
        notifier.setName("RevPowerLogger")
        notifier.startPeriodic(0.1)
    }

    private fun log() {
        ntTable.getEntry("Voltage").setDouble(pdh.voltage)
        ntTable.getEntry("TotalCurrent").setDouble(pdh.totalCurrent)
        ntTable.getEntry("TotalPower").setDouble(pdh.totalPower)
        ntTable.getEntry("TotalEnergy").setDouble(pdh.totalEnergy)
        ntTable.getEntry("Temperature").setDouble(pdh.temperature)

        for (i in 0..<channels) {
            val current = pdh.getCurrent(i)
            val isFaulted = pdh.faults.getBreakerFault(i)

            if (current > overCurrentWarningChannelThreshold) {
                println("[CORALIS] WARNING: Overcurrent detected on channel $i")
            }

            if (isFaulted) {
                println("[CORALIS] WARNING: Breaker fault detected on channel $i")
            }

            val channelTable = ntTable.getSubTable("Channel$i")
            channelTable.getEntry("Current").setDouble(current)
            channelTable.getEntry("Tripped").setBoolean(isFaulted)
        }

        if (pdh.totalCurrent > overCurrentWarningTotalThreshold) {
            println("[CORALIS] WARNING: Overcurrent detected on total current")
        }

        if (pdh.temperature > overTemperatureWarningThreshold) {
            println("[CORALIS] WARNING: Power Overtemperature detected")
        }

        if (pdh.faults.HardwareFault) {
            println("[CORALIS] WARNING: Power Hardware fault detected")
        }

        if (pdh.faults.Brownout) {
            println("[CORALIS] WARNING: Power Brownout fault detected")
        }

        if (pdh.faults.CanWarning) {
            println("[CORALIS] WARNING: Power CAN warning detected, check network status and DS")
        }
    }
}