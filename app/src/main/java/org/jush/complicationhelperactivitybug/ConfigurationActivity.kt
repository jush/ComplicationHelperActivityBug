package org.jush.complicationhelperactivitybug

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.support.wearable.complications.ComplicationData
import android.support.wearable.complications.ComplicationHelperActivity.createProviderChooserHelperIntent
import android.support.wearable.complications.ComplicationProviderInfo
import android.support.wearable.complications.ProviderChooserIntent
import android.support.wearable.complications.ProviderInfoRetriever
import android.view.View

private const val REQUEST_CODE = 100

// This Activity is not really used in the project just added to trigger the compilation error:
// e: Supertypes of the following classes cannot be resolved. Please make sure you have the required dependencies in the classpath:
//    class android.support.wearable.complications.ComplicationHelperActivity, unresolved supertypes: android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback
class ConfigurationActivity : Activity() {

    private lateinit var providerInfoRetriever: ProviderInfoRetriever
    private lateinit var serviceClass: Class<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.CardText)
        super.onCreate(savedInstanceState)
    }

    fun onTapComplication(view: View) {
        launchComplicationHelperActivity()
    }

    fun onTapPreview(view: View) {
        finish()
    }

    private fun launchComplicationHelperActivity() {
        startActivityForResult(
            createProviderChooserHelperIntent(
                this,
                ComponentName(this, serviceClass),
                123,
                ComplicationData.TYPE_RANGED_VALUE,
                ComplicationData.TYPE_ICON,
                ComplicationData.TYPE_SHORT_TEXT,
                ComplicationData.TYPE_SMALL_IMAGE
            ),
            REQUEST_CODE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (intent != null && requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            val info =
                intent.getParcelableExtra<ComplicationProviderInfo>(ProviderChooserIntent.EXTRA_PROVIDER_INFO)
        }
    }

    override fun onDestroy() {
        providerInfoRetriever.release()
        super.onDestroy()
    }
}
