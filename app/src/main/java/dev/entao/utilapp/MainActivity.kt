package dev.entao.utilapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.entao.kan.appbase.App

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        println(App.files.ex.filesDir.absolutePath)
    }


}
