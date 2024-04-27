package coralis.led.animations

import coralis.led.Animation
import edu.wpi.first.wpilibj.AddressableLEDBuffer

class Rainbow(
    private val brightness: Int = 240,
    private val hueChange: Int = 3
) : Animation {
    private var hue = 0

    override fun onStart() {
        hue = 0
    }

    override fun onUpdate(buffer: AddressableLEDBuffer, startingIndex: Int, endingIndex: Int) {
        for (i in startingIndex..endingIndex) {
            val hue = (this.hue + (i * 180 / (endingIndex - startingIndex))) % 180
            buffer.setHSV(i, hue, 255, brightness)
        }

        hue += hueChange
        hue %= 180
    }
}