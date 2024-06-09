package coralis.led.animations

import coralis.led.Animation
import edu.wpi.first.wpilibj.AddressableLEDBuffer

class Solid(private val color: Triple<Int, Int, Int> = Triple(255, 255, 255)) : Animation {
    override fun onStart() {} // Not needed

    override fun onUpdate(buffer: AddressableLEDBuffer, startingIndex: Int, endingIndex: Int) {
        for (i in startingIndex..endingIndex) {
            buffer.setRGB(i, color.first, color.second, color.third)
        }
    }

    override fun toString(): String {
        return "SOLID rgb(${color.first}, ${color.second}, ${color.third})"
    }
}