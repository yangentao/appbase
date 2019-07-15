package dev.entao.kan.appbase.files

import dev.entao.kan.appbase.App
import dev.entao.kan.json.YsonArray
import dev.entao.kan.json.YsonNull
import java.io.File
import java.io.FileNotFoundException

class FileList<T : Any>(filename: String, private val coder: ItemCoder) {

    private val file: File = File(App.files.app.filesDir, filename)
    var items: ArrayList<T> = ArrayList(256)

    init {
        load()
    }

    @Suppress("UNCHECKED_CAST")
    private fun load() {
        this.items.clear()
        try {
            val s = this.file.readText()
            val ya = YsonArray(s)
            val newSet = ArrayList<T>(ya.size)
            for (yv in ya) {
                if (yv !is YsonNull) {
                    val item = this.coder.decoder(yv) ?: continue
                    newSet.add(item as T)
                }
            }
            this.items = newSet
        } catch (ex: FileNotFoundException) {

        }
    }

    fun save() {
        val ya = YsonArray(this.items.size)
        for (item in this.items) {
            val yv = this.coder.encoder(item)
            ya.add(yv)
        }
        file.writeText(ya.yson())
    }

}