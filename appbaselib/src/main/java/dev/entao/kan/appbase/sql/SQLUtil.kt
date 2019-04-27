@file:Suppress("unused")

package dev.entao.kan.appbase.sql

import dev.entao.kan.base.Prop
import dev.entao.kan.base.nameClass
import dev.entao.kan.base.nameProp
import dev.entao.kan.json.*
import kotlin.reflect.KClass

/**
 * Created by entaoyang@163.com on 2018-07-19.
 */

val Prop.s: String get() = this.nameProp
val KClass<*>.sqlName: String
	get() {
		return "`" + this.nameClass + "`"
	}



operator fun StringBuilder.plusAssign(s: String) {
	this.append(s)
}

operator fun StringBuilder.plusAssign(ch: Char) {
	this.append(ch)
}

operator fun StringBuilder.plus(s: String): StringBuilder {
	this.append(s)
	return this
}

operator fun StringBuilder.plus(ch: Char): StringBuilder {
	this.append(ch)
	return this
}

fun stringAnyMapToYson(map: Map<String, Any?>): YsonObject {
	val yo = YsonObject()
	map.forEach {
		yo.any(it.key, it.value)
	}
	return yo
}

fun ysonToMap(yo: YsonObject, map: MutableMap<String, Any?>) {
	yo.forEach {
		val v = it.value
		val vv: Any? = when (v) {
			is YsonNull -> null
			is YsonString -> v.data
			is YsonNum -> v.data
			is YsonObject -> v
			is YsonArray -> v
			else -> v
		}
		map[it.key] = vv
	}
}



