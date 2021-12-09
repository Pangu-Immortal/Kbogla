/***
 * Excerpted from "OpenGL ES for Android",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material,
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose.
 * Visit http://www.pragmaticprogrammer.com/titles/kbogla for more book information.
 */
package com.airhockey.android.util

import android.opengl.GLES20
import android.util.Log

/**
 * Doc说明 (此类核心功能):
 * @date on 2021/11/10 17:21
 * +--------------------------------------------+
 * | @author qihao                              |
 * | @GitHub https://github.com/Pangu-Immortal  |
 * +--------------------------------------------+
 */
object ShaderHelper {
    private const val TAG = "ShaderHelper"

    /**
     * Loads and compiles a vertex shader, returning the OpenGL object ID.
     */
    fun compileVertexShader(shaderCode: String?): Int {
        return compileShader(GLES20.GL_VERTEX_SHADER, shaderCode)
    }

    /**
     * Loads and compiles a fragment shader, returning the OpenGL object ID.
     */
    fun compileFragmentShader(shaderCode: String?): Int {
        return compileShader(GLES20.GL_FRAGMENT_SHADER, shaderCode)
    }

    /**
     * Compiles a shader, returning the OpenGL object ID.
     */
    private fun compileShader(type: Int, shaderCode: String?): Int {
        // Create a new shader object.
        val shaderObjectId = GLES20.glCreateShader(type)
        if (shaderObjectId == 0) {
            if (LoggerConfig.ON) {
                Log.w(TAG, "Could not create new shader.")
            }
            return 0
        }

        // Pass in the shader source.
        GLES20.glShaderSource(shaderObjectId, shaderCode)

        // Compile the shader.
        GLES20.glCompileShader(shaderObjectId)

        // Get the compilation status.
        val compileStatus = IntArray(1)
        GLES20.glGetShaderiv(
            shaderObjectId, GLES20.GL_COMPILE_STATUS,
            compileStatus, 0
        )
        if (LoggerConfig.ON) {
            // Print the shader info log to the Android log output.
            Log.v(
                TAG, """
     Results of compiling source:
     $shaderCode
     :${GLES20.glGetShaderInfoLog(shaderObjectId)}
     """.trimIndent()
            )
        }

        // Verify the compile status.
        if (compileStatus[0] == 0) {
            // If it failed, delete the shader object.
            GLES20.glDeleteShader(shaderObjectId)
            if (LoggerConfig.ON) {
                Log.w(TAG, "Compilation of shader failed.")
            }
            return 0
        }

        // Return the shader object ID.
        return shaderObjectId
    }

    /**
     * Links a vertex shader and a fragment shader together into an OpenGL
     * program. Returns the OpenGL program object ID, or 0 if linking failed.
     */
    fun linkProgram(vertexShaderId: Int, fragmentShaderId: Int): Int {
        // Create a new program object.
        val programObjectId = GLES20.glCreateProgram()
        if (programObjectId == 0) {
            if (LoggerConfig.ON) {
                Log.w(TAG, "Could not create new program")
            }
            return 0
        }

        // Attach the vertex shader to the program.
        GLES20.glAttachShader(programObjectId, vertexShaderId)

        // Attach the fragment shader to the program.
        GLES20.glAttachShader(programObjectId, fragmentShaderId)

        // Link the two shaders together into a program.
        GLES20.glLinkProgram(programObjectId)

        // Get the link status.
        val linkStatus = IntArray(1)
        GLES20.glGetProgramiv(
            programObjectId, GLES20.GL_LINK_STATUS,
            linkStatus, 0
        )
        if (LoggerConfig.ON) {
            // Print the program info log to the Android log output.
            Log.v(
                TAG, """
     Results of linking program:
     ${GLES20.glGetProgramInfoLog(programObjectId)}
     """.trimIndent()
            )
        }

        // Verify the link status.
        if (linkStatus[0] == 0) {
            // If it failed, delete the program object.
            GLES20.glDeleteProgram(programObjectId)
            if (LoggerConfig.ON) {
                Log.w(TAG, "Linking of program failed.")
            }
            return 0
        }

        // Return the program object ID.
        return programObjectId
    }

    /**
     * Validates an OpenGL program. Should only be called when developing the
     * application.
     */
    fun validateProgram(programObjectId: Int): Boolean {
        GLES20.glValidateProgram(programObjectId)
        val validateStatus = IntArray(1)
        GLES20.glGetProgramiv(
            programObjectId, GLES20.GL_VALIDATE_STATUS,
            validateStatus, 0
        )
        Log.v(
            TAG, """
     Results of validating program: ${validateStatus[0]}
     Log:${GLES20.glGetProgramInfoLog(programObjectId)}
     """.trimIndent()
        )
        return validateStatus[0] != 0
    }
}