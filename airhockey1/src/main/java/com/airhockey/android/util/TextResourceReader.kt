/***
 * Excerpted from "OpenGL ES for Android",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material,
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose.
 * Visit http://www.pragmaticprogrammer.com/titles/kbogla for more book information.
 */
package com.airhockey.android.util

import android.content.Context
import android.content.res.Resources.NotFoundException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/**
 * Doc说明 (此类核心功能):
 * @date on 2021/11/10 17:18
 * +--------------------------------------------+
 * | @author qihao                              |
 * | @GitHub https://github.com/Pangu-Immortal  |
 * +--------------------------------------------+
 */
object TextResourceReader {
    /**
     * Reads in text from a resource file and returns a String containing the
     * text.
     */
    fun readTextFileFromResource(
        context: Context?,
        resourceId: Int
    ): String {
        val body = StringBuilder()
        try {
            val inputStream = context!!.resources.openRawResource(resourceId)
            val inputStreamReader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            var nextLine: String?
            while (bufferedReader.readLine().also { nextLine = it } != null) {
                body.append(nextLine)
                body.append('\n')
            }
        } catch (e: IOException) {
            throw RuntimeException(
                "Could not open resource: $resourceId", e
            )
        } catch (nfe: NotFoundException) {
            throw RuntimeException("Resource not found: $resourceId", nfe)
        }
        return body.toString()
    }
}