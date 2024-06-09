package coralis.signals

import edu.wpi.first.util.sendable.Sendable
import edu.wpi.first.util.sendable.SendableBuilder
import edu.wpi.first.wpilibj.Timer

class SignalSupplier<T: Enum<T>, V>(private val signal: Signal<T>, private val map: HashMap<T, () -> V?>) : Sendable {
    private val listeners = mutableListOf<(V) -> Unit>()
    private val productListeners = mutableListOf<(V) -> Unit>()
    private var lastProduct: V? = null
    private var lastTime = Timer.getFPGATimestamp()

    init {
        signal.addListener {
            val product = getSignalValue()
            if (product != null && product != lastProduct) {
                productListeners.forEach { listener -> listener(product) }
                lastProduct = product
            }
            if (product != null) {
                listeners.forEach { listener -> listener(product) }
                lastTime = Timer.getFPGATimestamp()
            }
        }
    }

    private fun getSignalValue(): V? {
        val signalFunction = map[signal.signal]
        return signalFunction?.invoke()
    }

    val value: V?
        get() = getSignalValue()

    fun addListener(listener: (V) -> Unit) {
        listeners.add(listener)
    }

    fun onNewProduct(listener: (V) -> Unit) {
        productListeners.add(listener)
    }

    override fun initSendable(builder: SendableBuilder?) {
        builder?.setSmartDashboardType("CoralisSignalSupplier")
        builder?.addStringProperty("CurrentSignal", { signal.uncleanSignal.toString() }, null)
        builder?.addStringProperty("CurrentValue", { getSignalValue().toString() }, null)
        builder?.addDoubleProperty("TimeSinceLastUpdate", { Timer.getFPGATimestamp() - lastTime }, null)
    }
}