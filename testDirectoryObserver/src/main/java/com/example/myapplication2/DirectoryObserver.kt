package com.example.myapplication2

import android.os.FileObserver
import android.util.Log
import java.io.File

private const val TAG = "DirectoryObserver"

public var fileObservers: MutableMap<String, DirectoryObserver.SubDirectory>? = null

class DirectoryObserver(private val directionPath: String) {
    private var _iEventChangedListener: IEventChangedListener? = null
    private fun loadRootPath() {
        fileObservers = HashMap<String, SubDirectory>()
        if (!directionPath.isNullOrEmpty()) {
            val rootFile = File(directionPath)
            val subDirectory = SubDirectory(
                rootFile.absolutePath,
                FileObserver.ALL_EVENTS,
                _iEventChangedListener!!
            )
            fileObservers?.set(rootFile.absolutePath, subDirectory)
            if (rootFile.exists()) {
                listDirectories(rootFile)
            }
        }
    }

    fun start(iEventChangedListener: IEventChangedListener) {
        _iEventChangedListener = iEventChangedListener

        loadRootPath()

        for (item in fileObservers!!.values) {
            Log.d(TAG, "start() called item=${item.file}")
            item.startWatching()
        }
    }

    fun stop() {
        for (item in fileObservers!!.values) {
            item.stopWatching()
        }
        fileObservers!!.clear()
    }

    private fun listDirectories(direction: File) {
        val listFiles = direction.listFiles()
        for (item in listFiles) {
            if (item.isDirectory) {
                Log.d(TAG, "listDirectories: item=$item")
                val subDirectory =
                    SubDirectory(
                        item.absolutePath,
                        FileObserver.ALL_EVENTS,
                        _iEventChangedListener!!
                    )
                fileObservers!![item.absolutePath] = subDirectory
                listDirectories(item)
            }
        }
    }


    enum class FileEvent {
        CREATE_FILE,
        CREATE_DIRECTORY,
        DELETE_FILE,
        DELETE_DIRECTORY,
        MODIFY_FILE
    }


    class SubDirectory(
        val file: String,
        mask: Int,
        private val iEventChangedListener: IEventChangedListener?
    ) : FileObserver(file, mask) {
        override fun onEvent(event: Int, path: String?) {
            addDirectory(file + File.separator + path)
            when (event) {
                MODIFY -> {
                    Log.d(TAG, "onEvent()   MODIFY file = ${file+File.separator+path}  path = $path,")
                    iEventChangedListener?.onChange(file+File.separator+path, FileEvent.MODIFY_FILE)
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
                    Log.d(TAG, "onEvent()   CREATE file = ${file+File.separator+path}  path = $path,")
                    iEventChangedListener?.onChange(file+File.separator+path, FileEvent.CREATE_FILE)
                }
                DELETE -> {
                    Log.d(TAG, "onEvent()   DELETE file = ${file+File.separator+path}  path = $path,")
                    iEventChangedListener?.onChange(file+File.separator+path, FileEvent.DELETE_FILE)
                }
                MOVE_SELF -> {
                    Log.d(TAG, "onEvent()   MOVE_SELF file = $file  path = $path,")

                }
                DELETE_SELF -> {
                    Log.d(TAG, "onEvent()   DELETE_SELF file = $file  path = $path,")
                    if (fileObservers!!.contains(file)) {
                        Log.d(TAG, "onEvent: remove-file")
                        fileObservers!!.remove(file)
                        iEventChangedListener?.onChange(file, FileEvent.DELETE_DIRECTORY)
                    }
                }
            }
        }

        private fun addDirectory(path: String) {
            Log.d(TAG, "addDirectory() called with: path = $path")
            val file = File(path)
            if (file.exists() && file.isDirectory && !fileObservers!!.containsKey(path)) {
                Log.d(TAG, "addDirectory: ${file.absolutePath}")
                fileObservers!![path] =
                    SubDirectory(path, FileObserver.ALL_EVENTS, iEventChangedListener)
                fileObservers!![path]!!.startWatching()
                iEventChangedListener?.onChange(file.absolutePath, FileEvent.CREATE_DIRECTORY)
            }
        }
    }

    interface IEventChangedListener {
        fun onChange(path: String, event: FileEvent)
    }

}