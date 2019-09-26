package dev.entao.kan.appbase.ex

import dev.entao.kan.appbase.App

val Int.dp: Int get() = App.dp2px(this)
val Int.sp: Int get() = App.sp2px(this.toFloat())

val Int.dpf: Float get() = App.dp2pxF(this.toFloat())
val Int.spf: Float get() = App.sp2pxF(this.toFloat())

val Float.dpf: Float get() = App.dp2pxF(this)
val Float.spf: Float get() = App.sp2pxF(this)

fun dp(n: Int): Int {
    return n.dp
}

fun sp(n: Int): Int {
    return n.sp
}

fun px2dp(px: Int): Int {
    return App.px2dp(px)
}