package coralis.signals

import edu.wpi.first.util.sendable.Sendable
import edu.wpi.first.util.sendable.SendableBuilder
import edu.wpi.first.wpilibj.Timer

/**
 * A signals.SignalSupplier class that maps signals to values.
 * It listens to a signals.Signal and when the signals.Signal changes, it invokes the corresponding function from the map to get the new value.
 *
 * @param T The type of the enum that the signals.Signal uses.
 * @param V The type of the value that the signals.SignalSupplier provides.
 * @param signal The signals.Signal that the signals.SignalSupplier listens to.
 * @param map The map that associates each signal with a function that provides a value.
 */
class SignalSupplier<T: Enum<T>, V>(private val signal: Signal<T>, private val map: HashMap<T, () -> V?>) : Sendable {
    private val listeners = mutableListOf<(V) -> Unit>()
    private var lastTime = Timer.getFPGATimestamp()

    init {
        signal.addListener {
            val product = getSignalValue()
            if (product != null) {
                listeners.forEach { listener -> listener(product) }
                lastTime = Timer.getFPGATimestamp()
            }
        }
    }

    /**
     * Gets the value associated with the current signal.
     * It invokes the function associated with the current signal in the map.
     *
     * @return The value associated with the current signal, or null if there is no function associated with the current signal.
     */
    private fun getSignalValue(): V? {
        val signalFunction = map[signal.signal]
        return signalFunction?.invoke()
    }

    // The value associated with the current signal.
    val value: V?
        get() = getSignalValue()

    /**
     * Adds a listener to the signals.SignalSupplier.
     * The listener is a function that takes a value of type V and returns Unit.
     * It is triggered whenever the signal changes.
     *
     * @param listener The listener to add.
     */
    fun addListener(listener: (V) -> Unit) {
        listeners.add(listener)
    }

    override fun initSendable(builder: SendableBuilder?) {
        builder?.setSmartDashboardType("CoralisSignalSupplier")
        builder?.addStringProperty("CurrentSignal", { signal.signal.toString() }, null)
        builder?.addStringProperty("CurrentValue", { getSignalValue().toString() }, null)
        builder?.addDoubleProperty("TimeSinceLastUpdate", { Timer.getFPGATimestamp() - lastTime }, null)
    }
}