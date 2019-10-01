package dev.entao.kan.appbase.ex

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.DrawableCompat

fun Drawable.sizeW(newWidth: Int): Drawable {
    val h = this.intrinsicHeight
    val w = this.intrinsicWidth
    if (newWidth == 0 || w == 0 || w == newWidth) {
        return this
    }
    this.setBounds(0, 0, newWidth.dp, h * newWidth.dp / w)
    return this
}

fun Drawable.sizeH(newHeight: Int): Drawable {
    val h = this.intrinsicHeight
    val w = this.intrinsicWidth
    if (newHeight == 0 || h == 0 || h == newHeight) {
        return this
    }
    this.setBounds(0, 0, w * newHeight.dp / h, newHeight.dp)
    return this
}

fun Drawable.limited(maxEdge: Int): Drawable {
    val h = this.intrinsicHeight
    val w = this.intrinsicWidth
    if (w > maxEdge || h > maxEdge) {
        val a = maxEdge.dp
        if (w > h) {
            this.setBounds(0, 0, a, h * a / w)
        } else {
            this.setBounds(0, 0, w * a / h, a)
        }
    }
    return this
}

fun Drawable.sized(w: Int, h: Int = w): Drawable {
    this.setBounds(0, 0, w.dp, h.dp)
    return this
}

val Drawable.tintedWhite: Drawable
    get() {
        return this.tinted(Color.WHITE)
    }

fun Drawable.tinted(color: Int): Drawable {
    val dc = DrawableCompat.wrap(this.mutate())
    DrawableCompat.setTint(dc, color)
    return dc
}

fun Drawable.tinted(color: Int, light: Int): Drawable {
    val dc = DrawableCompat.wrap(this.mutate())
    DrawableCompat.setTintList(dc, StateList.lightColor(color, light))
    return dc
}