package com.example.sportapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import com.onesignal.OneSignal

class WebViewFragment : Fragment() {

    private val ONESIGNAL_APP_ID = "990a0314-3277-4cae-a56f-169f2f78b0c3"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val layout = inflater.inflate(R.layout.fragment_web_view, container, false)

        val web = layout.findViewById<WebView>(R.id.webViewGoogle)
        web.settings.javaScriptEnabled
        web.loadUrl("https://www.google.ru")

        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        activity?.let { OneSignal.initWithContext(it.applicationContext) };
        OneSignal.setAppId(ONESIGNAL_APP_ID);

        return layout
    }

}