package com.rakeshgangwar.newsapplication.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.ProgressBar
import androidx.fragment.app.DialogFragment
import com.rakeshgangwar.newsapplication.R

class WebViewFragment : DialogFragment() {

    private var url: String? = null

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_web_view, container, false)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme)

        val progressBar = view.findViewById<ProgressBar>(R.id.progress_bar_read_more)

        val args = arguments
        if (args != null) {
            url = args.getString("url")
        }
        val webView = view.findViewById<WebView>(R.id.web_view)
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        webView.settings.allowUniversalAccessFromFileURLs = true
        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        webView.settings.javaScriptEnabled = true
        webView.settings.setAppCacheEnabled(true)
        webView.settings.domStorageEnabled = true
        webView.settings.cacheMode = WebSettings.LOAD_DEFAULT
        webView.settings.allowFileAccess = true
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                if (newProgress == 100) {
                    progressBar.visibility = ProgressBar.GONE
                    return
                }
                if (progressBar.visibility != View.VISIBLE)
                    progressBar.visibility = View.VISIBLE
                progressBar.progress = newProgress
            }
        }
        webView.loadUrl(url)
        return view
    }
}
