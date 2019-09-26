@file:Suppress("unused")

package dev.entao.kan.appbase.ex

import android.graphics.drawable.GradientDrawable


class ShapeRect() {
    val value: GradientDrawable = GradientDrawable()

    init {
        value.shape = GradientDrawable.RECTANGLE
    }

    constructor(fillColor: Int, corner: Int) : this() {
        this.fill(fillColor)
        this.corner(corner)
    }

    fun fill(color: Int): ShapeRect {
        value.setColor(color)
        return this
    }

    fun corner(corner: Int): ShapeRect {
        value.cornerRadius = corner.dpf
        return this
    }

    fun corners(topLeft: Int, topRight: Int, bottomRight: Int, bottomLeft: Int): ShapeRect {
        val f1 = topLeft.dpf
        val f2 = topRight.dpf
        val f3 = bottomRight.dpf
        val f4 = bottomLeft.dpf
        value.cornerRadii = floatArrayOf(f1, f1, f2, f2, f3, f3, f4, f4)
        return this
    }

    fun stroke(width: Int, color: Int): ShapeRect {
        value.setStroke(width.dp, color)
        return this
    }

    fun strokeDash(width: Int, color: Int, dashWidth: Float, dashGap: Float): ShapeRect {
        value.setStroke(width.dp, color, dashWidth.dpf, dashGap.dpf)
        return this
    }

    fun alpha(n: Int): ShapeRect {
        value.alpha = n
        return this
    }

    fun size(w: Int, h: Int = w): ShapeRect {
        value.setSize(w.dp, h.dp)
        return this
    }
}

class ShapeLine(width: Int, color: Int) {
    val value: GradientDrawable = GradientDrawable()

    init {
        value.shape = GradientDrawable.LINE
        this.stroke(width, color)
    }

    fun stroke(width: Int, color: Int): ShapeLine {
        value.setStroke(width.dp, color)
        return this
    }

    fun strokeDash(width: Int, color: Int, dashWidth: Float, dashGap: Float): ShapeLine {
        value.setStroke(width.dp, color, dashWidth.dpf, dashGap.dpf)
        return this
    }

    fun alpha(n: Int): ShapeLine {
        value.alpha = n
        return this
    }

    fun size(w: Int, h: Int = w): ShapeLine {
        value.setSize(w.dp, h.dp)
        return this
    }
}

class ShapeOval() {
    val value: GradientDrawable = GradientDrawable()

    init {
        value.shape = GradientDrawable.OVAL
    }

    fun fill(color: Int): ShapeOval {
        value.setColor(color)
        return this
    }

    fun stroke(width: Int, color: Int): ShapeOval {
        value.setStroke(width.dp, color)
        return this
    }

    fun strokeDash(width: Int, color: Int, dashWidth: Float, dashGap: Float): ShapeOval {
        value.setStroke(width.dp, color, dashWidth.dpf, dashGap.dpf)
        return this
    }

    fun alpha(n: Int): ShapeOval {
        value.alpha = n
        return this
    }

    fun size(w: Int, h: Int = w): ShapeOval {
        value.setSize(w.dp, h.dp)
        return this
    }
}