package coralis.led.animations

import coralis.led.Animation
import edu.wpi.first.wpilibj.AddressableLEDBuffer

class Rainbow : Animation {
    private var hue = 0

    override fun onStart() {
        hue = 0
    }

    override fun onUpdate(buffer: AddressableLEDBuffer, startingIndex: Int, endingIndex: Int) {
        for (i in startingIndex..endingIndex) {
            val hue = (this.hue + (i * 180 / (endingIndex - startingIndex))) % 180
            buffer.setHSV(i, hue, 255, 240)
        }

        hue += 1
        hue %= 180
    }
}