package com.pemerintah.beaconsampah

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import kotlinx.android.synthetic.main.activity_splash.*
import com.github.javiersantos.appupdater.AppUpdater
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.net.Uri
import com.github.javiersantos.appupdater.enums.UpdateFrom


class splash : AppCompatActivity() {

    var act : splash? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        getSupportActionBar()!!.hide()
        act = this
        methodWithPermissions()
        update()
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
        buttonNexttoDashboard.setOnClickListener({
            var intent = Intent(act, dashboard::class.java)
            finish()
            startActivity(intent)
        })
    }
    fun update()
    {
        var test =  Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/josephkristanto10/kantor/tree/master"))
    }
    fun methodWithPermissions() = runWithPermissions(Manifest.permission.CAMERA,  Manifest.permission.READ_EXTERNAL_STORAGE,  Manifest.permission.WRITE_EXTERNAL_STORAGE,  Manifest.permission.ACCESS_FINE_LOCATION,  Manifest.permission.ACCESS_COARSE_LOCATION) {

    }
}
