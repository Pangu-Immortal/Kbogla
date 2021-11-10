/***
 * Excerpted from "OpenGL ES for Android",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material,
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose.
 * Visit http://www.pragmaticprogrammer.com/titles/kbogla for more book information.
 */
package com.airhockey.android.programs

import android.opengl.GLES20
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.opengl.GLUtils
import android.content.res.Resources.NotFoundException
import com.airhockey.android.R
import android.app.Activity
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.widget.Toast
import com.airhockey.android.util.ShaderHelper
import com.airhockey.android.util.TextResourceReader
import javax.microedition.khronos.opengles.GL10

abstract class ShaderProgram protected constructor(
    context: Context, vertexShaderResourceId: Int,
    fragmentShaderResourceId: Int
) {
    // Shader program
    protected val program: Int
    fun useProgram() {
        // Set the current OpenGL shader program to this program.
        GLES20.glUseProgram(program)
    }

    companion object {
        // Uniform constants
        const val U_MATRIX = "u_Matrix"
        const val U_TEXTURE_UNIT = "u_TextureUnit"

        // Attribute constants
        const val A_POSITION = "a_Position"
        const val A_COLOR = "a_Color"
        const val A_TEXTURE_COORDINATES = "a_TextureCoordinates"
    }

    init {
        // Compile the shaders and link the program.
        program = ShaderHelper.buildProgram(
            TextResourceReader.readTextFileFromResource(
                context, vertexShaderResourceId
            ),
            TextResourceReader.readTextFileFromResource(
                context, fragmentShaderResourceId
            )
        )
    }
}