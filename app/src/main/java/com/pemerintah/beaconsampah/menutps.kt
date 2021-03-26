package com.pemerintah.beaconsampah

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_menutps.*

class menutps : AppCompatActivity() {

    var act : menutps? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menutps)
        getSupportActionBar()!!.hide()
        act = this
        linearLayoutMenuDaftarLokasi.setOnClickListener({
            var intent = Intent(act, daftarlokasipembuangan::class.java)
            finish()
            startActivity(intent)
        })
        linearLayoutTambahLokasi.setOnClickListener({
            var intent = Intent(act, tambahlokasi::class.java)
            finish()
            startActivity(intent)
        })
        imageViewBackMenuTps.setOnClickListener({
            var intent = Intent(act, dashboard::class.java)
            finish()
            startActivity(intent)
        })
    }

    override fun onBackPressed() {
        var intent = Intent(act , dashboard::class.java)
        finish()
        startActivity(intent)
    }
}
