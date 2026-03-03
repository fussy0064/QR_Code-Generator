package com.fussy.qr_master

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.webkit.JavascriptInterface
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import java.io.OutputStream

class MainActivity : ComponentActivity() {

    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Make status bar and navigation bar match the app's dark purple theme
        val darkPurple = Color.parseColor("#0f0320")

        @Suppress("DEPRECATION")
        window.statusBarColor = darkPurple
        @Suppress("DEPRECATION")
        window.navigationBarColor = darkPurple

        // Full-screen immersive flags for a native app feel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }

        // Create and configure WebView
        webView = WebView(this).apply {
            setBackgroundColor(darkPurple)

            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                allowFileAccess = true
                allowContentAccess = true
                useWideViewPort = true
                loadWithOverviewMode = true
                setSupportZoom(false)
                builtInZoomControls = false
                displayZoomControls = false
                cacheMode = WebSettings.LOAD_DEFAULT
                mediaPlaybackRequiresUserGesture = false
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }

            webViewClient = WebViewClient()
            webChromeClient = WebChromeClient()


            // Allow file downloads (QR save functionality)
            setDownloadListener { _, _, _, _, _ -> }

            addJavascriptInterface(WebAppInterface(this@MainActivity), "Android")
        }

        setContentView(webView)

        // Load the HTML app from assets
        webView.loadUrl("file:///android_asset/www/index.html")

        // Handle back button: navigate within the app screens before exiting
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Try to go back within the WebView's screen navigation
                webView.evaluateJavascript(
                    """
                    (function() {
                        var result = document.getElementById('s-result');
                        var input = document.getElementById('s-input');
                        var home = document.getElementById('s-home');
                        if (result && result.classList.contains('active')) {
                            go('s-input');
                            return 'handled';
                        } else if (input && input.classList.contains('active')) {
                            go('s-home');
                            return 'handled';
                        }
                        return 'exit';
                    })()
                    """.trimIndent()
                ) { value ->
                    if (value.contains("exit")) {
                        // Let the system handle back (exit app)
                        isEnabled = false
                        onBackPressedDispatcher.onBackPressed()
                        isEnabled = true
                    }
                }
            }
        })
    }

    // Prevent WebView memory leaks
    override fun onDestroy() {
        webView.destroy()
        super.onDestroy()
    }

    inner class WebAppInterface(private val mContext: Context) {
        @JavascriptInterface
        fun saveImage(base64Data: String, fileName: String) {
            val uri = saveBase64ToGallery(base64Data, fileName)
            if (uri != null) {
                runOnUiThread { Toast.makeText(mContext, "QR code saved to Pictures!", Toast.LENGTH_SHORT).show() }
            } else {
                runOnUiThread { Toast.makeText(mContext, "Failed to save QR code.", Toast.LENGTH_SHORT).show() }
            }
        }

        @JavascriptInterface
        fun shareImage(base64Data: String, fileName: String) {
            val uri = saveBase64ToGallery(base64Data, fileName)
            if (uri != null) {
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "image/png"
                    putExtra(Intent.EXTRA_STREAM, uri)
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
                mContext.startActivity(Intent.createChooser(shareIntent, "Share QR Code"))
            } else {
                runOnUiThread { Toast.makeText(mContext, "Failed to prepare image for sharing.", Toast.LENGTH_SHORT).show() }
            }
        }

        private fun saveBase64ToGallery(base64Data: String, fileName: String): Uri? {
            try {
                val cleanBase64 = base64Data.replaceFirst("data:image/png;base64,", "")
                val decodedString = Base64.decode(cleanBase64, Base64.DEFAULT)
                val bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)

                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/QR_Master")
                    }
                }

                val resolver = mContext.contentResolver
                val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                uri?.let {
                    val outputStream = resolver.openOutputStream(it)
                    if (outputStream != null) {
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                        outputStream.close()
                    }
                }
                return uri
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
        }
    }
}