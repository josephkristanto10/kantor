package com.pemerintah.beaconsampah

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import kotlinx.android.synthetic.main.activity_splash.*

class splash : AppCompatActivity() {

    var act : splash? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        getSupportActionBar()!!.hide()
        act = this
        methodWithPermissions()
        buttonNexttoDashboard.setOnClickListener({
            var intent = Intent(act, dashboard::class.java)
            finish()
            startActivity(intent)
        })
    }
    fun methodWithPermissions() = runWithPermissions(Manifest.permission.CAMERA,  Manifest.permission.READ_EXTERNAL_STORAGE,  Manifest.permission.WRITE_EXTERNAL_STORAGE,  Manifest.permission.ACCESS_FINE_LOCATION,  Manifest.permission.ACCESS_COARSE_LOCATION) {

    }
}
