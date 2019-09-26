package dev.entao.kan.appbase.ex

import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.DrawableCompat


fun Drawable.limited(maxEdge: Int): Drawable {
    val h = this.intrinsicHeight
    val w = this.intrinsicWidth
    if (w > maxEdge || h > maxEdge) {
        if (w > h) {
            this.setBounds(0, 0, maxEdge.dp, (h * maxEdge / w).dp)
        } else {
            this.setBounds(0, 0, (w * maxEdge / h).dp, maxEdge.dp)
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
        return this.tinted(Colors.WHITE)
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