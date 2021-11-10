/***
 * Excerpted from "OpenGL ES for Android",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material,
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose.
 * Visit http://www.pragmaticprogrammer.com/titles/kbogla for more book information.
 */
package com.airhockey.android

import android.app.Activity
import android.app.ActivityManager
import android.opengl.GLSurfaceView
import android.os.Build
import android.os.Bundle
import android.widget.Toast

/**
 * Doc说明 (此类核心功能):
 * @date on 2021/11/10 17:18
 * +------------------------------------+
 * | @author qihao                      |
 * | @GitHub https://github.com/yugu88  |
 * +------------------------------------+
 */
class AirHockeyActivity : Activity() {
    /**
     * 持有对我们的 GLSurfaceView 的引用
     */
    private var glSurfaceView: GLSurfaceView? = null
    private var rendererSet = false
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        glSurfaceView = GLSurfaceView(this)

        // 检查系统是否支持 OpenGL ES 2.0。
        val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val configurationInfo = activityManager
            .deviceConfigurationInfo

        /**
         * 尽管最新的模拟器支持 OpenGL ES 2.0，但它有一个错误，
         * 它没有设置 reqGlEsVersion，因此上述检查不起作用。
         * 下面将检测应用程序是否在模拟器上运行，并假设它支持 OpenGL ES 2.0。
         */
        val supportsEs2 = (configurationInfo.reqGlEsVersion >= 0x20000
                || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
                && (Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86"))))
        rendererSet = if (supportsEs2) {
            // 请求 OpenGL ES 2.0 兼容上下文。
            glSurfaceView!!.setEGLContextClientVersion(2)

            // 分配我们的 renderer.
            glSurfaceView!!.setRenderer(AirHockeyRenderer(this))
            true
        } else {
            /**
             * 如果您想同时支持 ES 1 和 ES 2，您可以在此处创建与 OpenGL ES 1.x 兼容的渲染器。
             * 由于我们没有做任何事情，如果设备不支持 OpenGL ES 2.0，应用程序将崩溃。
             * 如果我们在市场上发布，我们还应该在 AndroidManifest.xml 中
             * 添加以下内容： <uses-feature android:glEsVersion="0x00020000" android:required="true" >
             * 这对那些不支持 OpenGL 的设备隐藏我们的应用程序ES 2.0。
             */
            Toast.makeText(
                this, "This device does not support OpenGL ES 2.0.",
                Toast.LENGTH_LONG
            ).show()
            return
        }
        setContentView(glSurfaceView)
    }

    override fun onPause() {
        super.onPause()
        if (rendererSet) {
            glSurfaceView!!.onPause()
        }
    }

    override fun onResume() {
        super.onResume()
        if (rendererSet) {
            glSurfaceView!!.onResume()
        }
    }
}