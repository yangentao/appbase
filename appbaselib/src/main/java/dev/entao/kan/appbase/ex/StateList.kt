@file:Suppress("unused")

package dev.entao.kan.appbase.ex

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable


enum class ViewState(val value: Int) {
    Selected(android.R.attr.state_selected), Unselected(-android.R.attr.state_selected),
    Pressed(android.R.attr.state_pressed), Unpressed(-android.R.attr.state_pressed),
    Enabled(android.R.attr.state_enabled), Disabled(-android.R.attr.state_enabled),
    Checked(android.R.attr.state_checked), Unchecked(-android.R.attr.state_checked),
    Focused(android.R.attr.state_focused), Unfocused(-android.R.attr.state_focused)
}
typealias VState = ViewState


object StateList {


    fun color(normalColor: Int, vararg ls: Pair<VState, Int>): ColorStateList {
        val count = ls.size
        val a = Array<IntArray>(count + 1) {
            if (it < count) {
                intArrayOf(ls[it].first.value)
            } else {
                IntArray(0)
            }
        }
        val b = IntArray(count + 1) {
            if (it < count) {
                ls[it].second
            } else {
                normalColor
            }
        }
        return ColorStateList(a, b)
    }


    fun drawable(normal: Drawable, vararg ls: Pair<VState, Drawable>): StateListDrawable {
        val ld = StateListDrawable()
        for (p in ls) {
            ld.addState(intArrayOf(p.first.value), p.second)
        }
        ld.addState(IntArray(0), normal)
        return ld
    }

    fun colorDrawable(normal: Int, vararg ls: Pair<VState, Int>): StateListDrawable {
        val ld = StateListDrawable()
        for (p in ls) {
            ld.addState(
                intArrayOf(p.first.value),
                android.graphics.drawable.ColorDrawable(p.second)
            )
        }
        ld.addState(IntArray(0), android.graphics.drawable.ColorDrawable(normal))
        return ld
    }

    fun lightColor(normal: Int, light: Int): ColorStateList {
        return this.color(normal, VState.Pressed to light, VState.Selected to light, VState.Focused to light)
    }

    fun lightDrawable(normal: Drawable, light: Drawable): StateListDrawable {
        return this.drawable(normal, VState.Pressed to light, VState.Selected to light, VState.Focused to light)
    }

    fun lightColorDrawable(normal: Int, light: Int): StateListDrawable {
        return this.colorDrawable(normal, VState.Pressed to light, VState.Selected to light, VState.Focused to light)
    }
}


fun StateList.colors(normalColor: Int, block: StateMaker<Int>.() -> Unit): ColorStateList {
    val m = StateMaker<Int>()
    m.block()
    return this.color(normalColor, *m.array)
}

fun StateList.drawables(normal: Drawable, block: StateMaker<Drawable>.() -> Unit): StateListDrawable {
    val m = StateMaker<Drawable>()
    m.block()
    return this.drawable(normal, *m.array)
}

fun StateList.colorDrawables(normal: Int, block: StateMaker<Int>.() -> Unit): StateListDrawable {
    val m = StateMaker<Int>()
    m.block()
    return this.colorDrawable(normal, *m.array)
}


class StateMaker<T> {
    private val ls: ArrayList<Pair<VState, T>> = ArrayList()

    val array: Array<Pair<VState, T>>
        get() {
            return ls.toTypedArray()
        }

    fun selected(v: T): StateMaker<T> {
        ls += VState.Selected to v
        return this
    }

    fun unselected(v: T): StateMaker<T> {
        ls += VState.Unselected to v
        return this
    }

    fun pressed(v: T): StateMaker<T> {
        ls += VState.Pressed to v
        return this
    }

    fun unpressed(v: T): StateMaker<T> {
        ls += VState.Unpressed to v
        return this
    }

    fun enabled(v: T): StateMaker<T> {
        ls += VState.Enabled to v
        return this
    }

    fun disabled(v: T): StateMaker<T> {
        ls += VState.Disabled to v
        return this
    }

    fun checked(v: T): StateMaker<T> {
        ls += VState.Checked to v
        return this
    }

    fun unchecked(v: T): StateMaker<T> {
        ls += VState.Unchecked to v
        return this
    }

    fun focused(v: T): StateMaker<T> {
        ls += VState.Focused to v
        return this
    }

    fun unfocused(v: T): StateMaker<T> {
        ls += VState.Unfocused to v
        return this
    }
}