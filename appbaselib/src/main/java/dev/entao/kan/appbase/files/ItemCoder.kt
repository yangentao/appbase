package dev.entao.kan.appbase.files

import dev.entao.kan.json.Yson
import dev.entao.kan.json.YsonValue
import kotlin.reflect.KClass

open class ItemCoder(private val itemCls: KClass<*>) {

    open fun encoder(item: Any): YsonValue {
        return Yson.toYson(item)
    }

    open fun decoder(yv: YsonValue): Any? {
        return Yson.toModelClass(yv, itemCls)
    }
}