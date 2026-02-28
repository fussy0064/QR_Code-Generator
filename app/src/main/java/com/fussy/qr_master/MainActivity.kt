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
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

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
            setDownloadListener { url, _, contentDisposition, mimeType, _ ->
                // For data URIs (QR code saves), the JS handles downloads via anchor click
                // No additional handling needed
            }
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
}