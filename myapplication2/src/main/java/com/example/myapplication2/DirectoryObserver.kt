package com.example.myapplication2

import android.os.FileObserver
import android.util.Log
import java.io.File

private const val TAG = "DirectoryObserver"

class DirectoryObserver(private val directionPath: String) {
    private var fileObservers: MutableList<SubDirectory>? = null

    init {
        fileObservers = mutableListOf<SubDirectory>()
        if (!directionPath.isNullOrEmpty()) {
            val rootFile = File(directionPath)
            if (rootFile.exists()) {
                listDirectories(rootFile)
            }
        }
    }


    class SubDirectory(file: String, mask: Int) : FileObserver(file, mask) {
        override fun onEvent(event: Int, path: String?) {
            Log.d(TAG, "onEvent() called with: event = $event, path = $path")
        }
    }

    fun start() {
        val rootFile = File(directionPath)
        val subDirectory = SubDirectory(rootFile.absolutePath, FileObserver.ALL_EVENTS)
        fileObservers?.add(subDirectory)
        for (item in fileObservers!!) {
            Log.d(TAG, "start() called item=$item")
            item.startWatching()
        }
    }

    fun stop() {
        for (item in fileObservers!!) {
            item.stopWatching()
        }
    }

    private fun listDirectories(direction: File) {
        val listFiles = direction.listFiles()
        for (item in listFiles) {
            Log.d(TAG, "listDirectories: item=$item")
            if (item.isDirectory) {
                val subDirectory = SubDirectory(item.absolutePath, FileObserver.ALL_EVENTS)
                fileObservers?.add(subDirectory)
            }
        }
    }
}