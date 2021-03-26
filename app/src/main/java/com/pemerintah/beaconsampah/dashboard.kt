package com.pemerintah.beaconsampah

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ScrollView
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_dashboard.*

class dashboard : AppCompatActivity() {

    var act : dashboard? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        getSupportActionBar()!!.hide()
        act = this
        imageViewGarbageHome.requestFocus()
        scroll.fullScroll(ScrollView.FOCUS_UP);
        scroll.smoothScrollTo(0,0)
        TPSMenu.setOnClickListener({
            var intent = Intent(act , menutps::class.java)
            finish()
            startActivity(intent)
        })
        PetugasMenu.setOnClickListener({
            var intent = Intent(act , daftarpetugas::class.java)
            finish()
            startActivity(intent)
        })

    }

    override fun onBackPressed() {
        val alertDialogBuilder = AlertDialog.Builder(
            this
        )
        alertDialogBuilder.setTitle("Exit Confirmation")
        alertDialogBuilder
            .setMessage("Are you sure to quit from our apps?")

            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->
               finish()
            })
            .setNegativeButton("cancel", DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
            })
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}
