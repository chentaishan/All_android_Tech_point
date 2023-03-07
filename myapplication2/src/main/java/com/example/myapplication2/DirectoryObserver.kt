package com.example.myapplication2

import android.os.FileObserver
import android.util.Log
import java.io.File

private const val TAG = "DirectoryObserver"

public var fileObservers: MutableMap<String, DirectoryObserver.SubDirectory>? = null

class DirectoryObserver(private val directionPath: String) {

    init {
        fileObservers = HashMap<String, SubDirectory>()
        if (!directionPath.isNullOrEmpty()) {
            val rootFile = File(directionPath)
            if (rootFile.exists()) {
                listDirectories(rootFile)
            }
        }
    }

    fun start() {
        val rootFile = File(directionPath)
        val subDirectory = SubDirectory(rootFile.absolutePath, FileObserver.ALL_EVENTS)
        fileObservers?.set(rootFile.absolutePath, subDirectory)
        for (item in fileObservers!!.values) {
            Log.d(TAG, "start() called item=${item.file}")
            item.startWatching()
        }
    }

    fun stop() {
        for (item in fileObservers!!.values) {
            item.stopWatching()
        }
    }

    private fun listDirectories(direction: File) {
        val listFiles = direction.listFiles()
        for (item in listFiles) {
            if (item.isDirectory) {
                Log.d(TAG, "listDirectories: item=$item")
                val subDirectory = SubDirectory(item.absolutePath, FileObserver.ALL_EVENTS)
                fileObservers!![item.absolutePath] = subDirectory
                listDirectories(item)
            }
        }
    }

    class SubDirectory(val file: String, mask: Int) : FileObserver(file, mask) {
        override fun onEvent(event: Int, path: String?) {
            Log.d(TAG, "onEvent()   event = $event  path = $file/$path -----")
            addDirectory(file + File.separator + path)
            when (event) {
//                (ACCESS or MODIFY or ATTRIB or CLOSE_WRITE
//                        or CLOSE_NOWRITE or OPEN or MOVED_FROM or MOVED_TO or DELETE or CREATE
//                        or DELETE_SELF or MOVE_SELF)
                MODIFY -> {
                    Log.d(TAG, "onEvent()   MODIFY file = $file  path = $path,")
                }
                ACCESS -> {
                    Log.d(TAG, "onEvent()   ACCESS file = $file  path = $path,")
                }
                ATTRIB -> {
                    Log.d(TAG, "onEvent()   ATTRIB file = $file  path = $path,")
                }
                CLOSE_WRITE -> {
                    Log.d(TAG, "onEvent()   CLOSE_WRITE file = $file  path = $path,")
                }
                CLOSE_NOWRITE -> {
                    Log.d(TAG, "onEvent()   CLOSE_NOWRITE file = $file  path = $path,")
                }
                OPEN -> {
                    Log.d(TAG, "onEvent()   OPEN file = $file  path = $path,")
                }

                MOVED_FROM -> {
                    Log.d(TAG, "onEvent()   MOVED_FROM file = $file  path = $path,")
                }
                MOVED_TO -> {
                    Log.d(TAG, "onEvent()   MOVED_TO file = $file  path = $path,")
                }
                CREATE -> {
                    Log.d(TAG, "onEvent()   CREATE file = $file  path = $path,")
                }
                DELETE -> {
                    Log.d(TAG, "onEvent()   DELETE file = $file  path = $path,")
                }
                MOVE_SELF -> {
                    Log.d(TAG, "onEvent()   MOVE_SELF file = $file  path = $path,")
                }
                DELETE_SELF -> {
                    Log.d(TAG, "onEvent()   DELETE_SELF file = $file  path = $path,")
                    if (fileObservers!!.contains(file)) {
                        fileObservers!!.remove(file)
                    }
                }
            }
        }

        private fun addDirectory(path: String) {
            val file = File(path)
            if (file.exists() && file.isDirectory) {
                fileObservers!![path] = SubDirectory(path, ALL_EVENTS)
                fileObservers!![path]!!.startWatching()
            }
        }
    }

}