package io.dkozak.inference.activity.activityinference

import android.util.Log
import java.io.PrintWriter
import java.io.StringWriter

private val TAG = ExceptionHandler::class.java.simpleName

class ExceptionHandler : java.lang.Thread.UncaughtExceptionHandler {

    override fun uncaughtException(thread: Thread, exception: Throwable) {
        val stackTrace = StringWriter()
        exception.printStackTrace(PrintWriter(stackTrace))

        val errorReport = StringBuilder()
        errorReport.append(stackTrace.toString())

        Log.e(TAG, errorReport.toString())

        android.os.Process.killProcess(android.os.Process.myPid())
        System.exit(10)
    }
}



