package dev.entao.kan.appbase.ex

import android.graphics.Color
import dev.entao.kan.appbase.App
import dev.entao.kan.base.Hex

object Colors {
    val BLACK = 0xFF000000.argb
    val DKGRAY = 0xFF444444.argb
    val GRAY = 0xFF888888.argb
    val LTGRAY = 0xFFCCCCCC.argb
    val WHITE = 0xFFFFFFFF.argb
    val RED = 0xFFFF0000.argb
    val GREEN = 0xFF00FF00.argb
    val BLUE = 0xFF0000FF.argb
    val YELLOW = 0xFFFFFF00.argb
    val CYAN = 0xFF00FFFF.argb
    val MAGENTA = 0xFFFF00FF.argb
    val TRANSPARENT = 0

    val TRANS = Color.TRANSPARENT
    var LightGray = Color.LTGRAY
    var GrayMajor = 0xFFDDDDDD.argb
    var Disabled = 0xFFCCCCCC.argb
    val BlueMajor = 0xFF4990E2.argb


    var GreenMajor = 0xFF5ABA39.argb
    var RedMajor = 0xFFD83B31.argb
    var OrangeMajor = 0xFFF5A623.argb
    var CyanMajor = 0xFF50E3C2.argb
    var PinkMajor = 0xFFFD6691.argb


    var Warning = 0xFFF3A407.argb


    val Theme: Int get() = App.themeColor

    var PageGray = 0xFFDDDDDD.argb
    var Title = Color.WHITE
    var Fade = 0xFFFF8800.argb
    var Unselected = 0xFFAAAAAA.argb

    var TextColorMajor = 0xFF333333.argb
    var TextColorMid = 0xFF666666.argb
    var TextColorMinor = 0xFF888888.argb
    var TextColor = TextColorMajor
    var TextColorUnselected = 0xFF999999.argb

    var EditFocus = 0xFF38C4B0.argb


    var Risk = 0xFFCF2C40.argb
    var Safe = 0xFF199055.argb
    var Progress = 0xFF00C864.argb

    var LineGray = Color.LTGRAY


    object Green {
        var Light = 0xFF54792A.argb
        var Dark = 0xFF54792A.argb
    }

    fun color(value: String): Int {
        return colorParse(value)
    }

    fun rgb(r: Int, g: Int, b: Int): Int {
        return Color.rgb(r, g, b)
    }

    //0xff8800 --> "#ff8800"
    fun toStringColor(color: Int): String {
        val a = Color.alpha(color)
        val r = Color.red(color)
        val g = Color.green(color)
        val b = Color.blue(color)
        if (a == 0xff) {
            return "#" + Hex.encodeByte(r) + Hex.encodeByte(g) + Hex.encodeByte(b)
        }
        return "#" + Hex.encodeByte(a) + Hex.encodeByte(r) + Hex.encodeByte(g) + Hex.encodeByte(b)
    }


}


