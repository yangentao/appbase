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