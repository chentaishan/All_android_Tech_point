package com.example.myapplication2

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import kotlin.random.Random

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    var directoryObserver: DirectoryObserver? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        directoryObserver = DirectoryObserver(cacheDir.absolutePath)
        directoryObserver!!.start(object : DirectoryObserver.IEventChangedListener {
            override fun onChange(path: String, event: DirectoryObserver.FileEvent) {
                Log.d(TAG, "onChange() called with: path = $path, event = $event")
            }
        })
        findViewById<Button>(R.id.btn_send).setOnClickListener {
            val file = File(cacheDir.absolutePath+ File.separator+"1/1.md")
            if (file.exists()){
                val text = Random(4343).toString()
                file.appendText(text,Charsets.UTF_8)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        directoryObserver?.stop()
    }
}