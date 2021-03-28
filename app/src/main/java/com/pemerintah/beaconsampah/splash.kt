package com.pemerintah.beaconsampah

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import kotlinx.android.synthetic.main.activity_splash.*
import com.github.javiersantos.appupdater.AppUpdater
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.net.Uri
import com.github.javiersantos.appupdater.enums.UpdateFrom
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.github.javiersantos.appupdater.enums.AppUpdaterError
import com.github.javiersantos.appupdater.objects.Update
import com.github.javiersantos.appupdater.AppUpdaterUtils
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.github.javiersantos.appupdater.enums.Display
import com.livinglifetechway.quickpermissions_kotlin.util.QuickPermissionsOptions
import com.livinglifetechway.quickpermissions_kotlin.util.QuickPermissionsRequest


class splash : AppCompatActivity() {

    var act : splash? = null
    val quickPermissionsOption = QuickPermissionsOptions(
        handleRationale = false,
        rationaleMessage = "Custom rational message",
        permanentlyDeniedMessage = "Custom permanently denied message",
        rationaleMethod = { req -> rationaleCallback(req) },
        permanentDeniedMethod = { req -> permissionsPermanentlyDenied(req) }
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        getSupportActionBar()!!.hide()
        act = this
        methodRequiresPermissions()
//        update()
//        val appUpdater = AppUpdater(this)
//            .setTitleOnUpdateAvailable("Update available")
//            .setContentOnUpdateAvailable("Check out the latest version available of my app!")
//            .setTitleOnUpdateNotAvailable("Update not available")
//            .setContentOnUpdateNotAvailable("No update available. Check for updates again later!")
//            .setButtonUpdate("Update now?")
//            .setButtonUpdateClickListener(update())
//
//        .setButtonDoNotShowAgain("Huh, not interested")
//
//            .setCancelable(false) // Dialog could not be dismissab
//            .start()
//         AppUpdater(this)
//            .setUpdateFrom(UpdateFrom.GITHUB)
//            .setGitHubUserAndRepo("josephkristanto10", "kantor")
//            .start()
//        AppUpdater(this)
//            .setDisplay(Display.SNACKBAR)
//            .setTitleOnUpdateAvailable("Update available")
//            .setContentOnUpdateAvailable("Check out the latest version available of my app!")
//            .setTitleOnUpdateNotAvailable("Update not available")
//            .setContentOnUpdateNotAvailable("No update available. Check for updates again later!")
//            .setButtonUpdate("Update now?")
//            .setButtonUpdateClickListener(object : DialogInterface,
//                DialogInterface.OnClickListener {
//                override fun dismiss() {
//                }
//
//                override fun cancel() {
//                }
//
//                override fun onClick(dialog: DialogInterface?, which: Int) {
//                    update()
//                }
//
//            })
//            .showAppUpdated(true)
//            .start()

//        startActivity(
//            Intent(
//                Intent.ACTION_VIEW,
//                Uri.parse("https://github.com/josephkristanto10/kantor")
//            )
//        )


        buttonNexttoDashboard.setOnClickListener({
            var intent = Intent(act, dashboard::class.java)
            finish()
            startActivity(intent)
        })
    }
    fun update()
    {
        var test =  Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/josephkristanto10/kantor"))
    }
    fun methodRequiresPermissions() = runWithPermissions(Manifest.permission.CAMERA,  Manifest.permission.READ_EXTERNAL_STORAGE,  Manifest.permission.WRITE_EXTERNAL_STORAGE,  Manifest.permission.ACCESS_FINE_LOCATION,  Manifest.permission.ACCESS_COARSE_LOCATION) {
        Toast.makeText(this, "Cal and microphone permissions granted", Toast.LENGTH_LONG).show()
    }
    private fun methodRequiresPermissions(quickPermissionsOptions: QuickPermissionsOptions) = runWithPermissions(Manifest.permission.WRITE_CALENDAR, Manifest.permission.RECORD_AUDIO, options = quickPermissionsOptions) {
        Log.d("test", "methodRequiresPermissions: Cal and microphone permission granted")

        val toast = Toast.makeText(this, "Cal and microphone permission granted", Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }
    private fun rationaleCallback(req: QuickPermissionsRequest) {
        // this will be called when permission is denied once or more time. Handle it your way
        AlertDialog.Builder(this)
            .setTitle("Permissions Denied")
            .setMessage("This is the custom rationale dialog. Please allow us to proceed " + "asking for permissions again, or cancel to end the permission flow.")
            .setPositiveButton("Go Ahead") { dialog, which -> req.proceed() }
            .setNegativeButton("cancel") { dialog, which -> req.cancel() }
            .setCancelable(false)
            .show()
    }
    private fun permissionsPermanentlyDenied(req: QuickPermissionsRequest) {
        // this will be called when some/all permissions required by the method are permanently
        // denied. Handle it your way.
        AlertDialog.Builder(this)
            .setTitle("Permissions Denied")
            .setMessage("This is the custom permissions permanently denied dialog. " +
                    "Please open app settings to open app settings for allowing permissions, " +
                    "or cancel to end the permission flow.")
            .setPositiveButton("App Settings") { dialog, which -> req.openAppSettings() }
            .setNegativeButton("Cancel") { dialog, which -> req.cancel() }
            .setCancelable(false)
            .show()
    }

}
