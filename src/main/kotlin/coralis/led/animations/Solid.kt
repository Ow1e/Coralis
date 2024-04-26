package coralis.led.animations

import coralis.led.Animation
import edu.wpi.first.wpilibj.AddressableLEDBuffer

class Solid(
    private val red: Int = 255,
    private val green: Int = 255,
    private val blue: Int = 255
) : Animation {
    override fun onStart() {} // Not needed

    override fun onUpdate(buffer: AddressableLEDBuffer, startingIndex: Int, endingIndex: Int) {
        for (i in startingIndex..endingIndex) {
            buffer.setRGB(i, red, green, blue)
        }
    }
}