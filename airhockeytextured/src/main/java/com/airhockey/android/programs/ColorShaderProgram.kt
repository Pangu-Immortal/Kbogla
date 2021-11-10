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
import javax.microedition.khronos.opengles.GL10

class ColorShaderProgram(context: Context) : ShaderProgram(
    context, R.raw.simple_vertex_shader,
    R.raw.simple_fragment_shader
) {
    // Uniform locations
    private val uMatrixLocation: Int

    // Attribute locations
    val positionAttributeLocation: Int
    val colorAttributeLocation: Int
    fun setUniforms(matrix: FloatArray?) {
        // Pass the matrix into the shader program.
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0)
    }

    init {
        // Retrieve uniform locations for the shader program.
        uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX)
        // Retrieve attribute locations for the shader program.
        positionAttributeLocation =
            GLES20.glGetAttribLocation(program, A_POSITION)
        colorAttributeLocation =
            GLES20.glGetAttribLocation(program, A_COLOR)
    }
}