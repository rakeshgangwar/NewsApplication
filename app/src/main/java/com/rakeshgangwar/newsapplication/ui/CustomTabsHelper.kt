package com.rakeshgangwar.newsapplication.ui

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION
import androidx.fragment.app.FragmentManager
import java.util.*

class CustomTabsHelper(context: Context, fragmentManager: FragmentManager) {

    private var context: Context? = context
    private var url: String? = null
    private var fragmentManager: FragmentManager? = fragmentManager

    fun openCustomTab(url: String) {
        this.url = url
        openCustomTab()
    }

    private fun openCustomTab() {
        val packageName = getPackageNameToUse(context)

        if (packageName == null) {
            openUrlInWebView()
        } else {

            val builder = CustomTabsIntent.Builder()
            builder.setShowTitle(true)
            builder.enableUrlBarHiding()
            try {
                val customTabsIntent = builder.build()
                customTabsIntent.intent.setPackage(packageName)
                customTabsIntent.intent.data = Uri.parse(url)
                (context as Activity).startActivityForResult(customTabsIntent.intent, REQUEST_CODE_CUSTOM_TAB)
            } catch (e: ActivityNotFoundException) {
                openUrlInWebView()
            }

        }
    }

    private fun openUrlInWebView() {
        val fragment = WebViewFragment()
        val args = Bundle()
        args.putString("url", url)
        fragment.arguments = args
        if (fragmentManager != null)
            fragment.show(fragmentManager!!, "ReadMoreDialogFragment")
    }

    companion object {

        private const val TAG = "CustomTabsHelper"

        const val REQUEST_CODE_CUSTOM_TAB = 3000
        private const val STABLE_PACKAGE = "com.android.chrome"
        private const val BETA_PACKAGE = "com.chrome.beta"
        private const val DEV_PACKAGE = "com.chrome.dev"
        private const val LOCAL_PACKAGE = "com.google.android.apps.chrome"
        private var sPackageNameToUse: String? = null

        private fun getPackageNameToUse(context: Context?): String? {
            if (sPackageNameToUse != null) return sPackageNameToUse

            val pm = context!!.packageManager
            // Get default VIEW intent handler.
            val activityIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.example.com"))
            val defaultViewHandlerInfo = pm.resolveActivity(activityIntent, 0)
            var defaultViewHandlerPackageName: String? = null
            if (defaultViewHandlerInfo != null) {
                defaultViewHandlerPackageName = defaultViewHandlerInfo.activityInfo.packageName
            }

            val resolvedActivityList = pm.queryIntentActivities(activityIntent, 0)
            val packagesSupportingCustomTabs = ArrayList<String>()
            for (info in resolvedActivityList) {
                val serviceIntent = Intent()
                serviceIntent.action = ACTION_CUSTOM_TABS_CONNECTION
                serviceIntent.setPackage(info.activityInfo.packageName)
                if (pm.resolveService(serviceIntent, 0) != null) {
                    packagesSupportingCustomTabs.add(info.activityInfo.packageName)
                }
            }

            if (packagesSupportingCustomTabs.isEmpty()) {
                sPackageNameToUse = null
            } else if (packagesSupportingCustomTabs.size == 1) {
                sPackageNameToUse = packagesSupportingCustomTabs[0]
            } else if (!TextUtils.isEmpty(defaultViewHandlerPackageName)
                && !hasSpecializedHandlerIntents(context, activityIntent)
                && packagesSupportingCustomTabs.contains(defaultViewHandlerPackageName)
            ) {
                sPackageNameToUse = defaultViewHandlerPackageName
            } else if (packagesSupportingCustomTabs.contains(STABLE_PACKAGE)) {
                sPackageNameToUse = STABLE_PACKAGE
            } else if (packagesSupportingCustomTabs.contains(BETA_PACKAGE)) {
                sPackageNameToUse = BETA_PACKAGE
            } else if (packagesSupportingCustomTabs.contains(DEV_PACKAGE)) {
                sPackageNameToUse = DEV_PACKAGE
            } else if (packagesSupportingCustomTabs.contains(LOCAL_PACKAGE)) {
                sPackageNameToUse = LOCAL_PACKAGE
            }
            return sPackageNameToUse
        }

        private fun hasSpecializedHandlerIntents(context: Context?, intent: Intent): Boolean {
            try {
                val pm = context!!.packageManager
                val handlers = pm.queryIntentActivities(
                    intent,
                    PackageManager.GET_RESOLVED_FILTER
                )
                if (handlers == null || handlers.size == 0) {
                    return false
                }
                for (resolveInfo in handlers) {
                    val filter = resolveInfo.filter ?: continue
                    if (filter.countDataAuthorities() == 0 || filter.countDataPaths() == 0) continue
                    if (resolveInfo.activityInfo == null) continue
                    return true
                }
            } catch (e: RuntimeException) {
                Log.e(TAG, "Runtime exception while getting specialized handlers")
            }

            return false
        }
    }
}
