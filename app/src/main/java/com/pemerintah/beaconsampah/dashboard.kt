package com.pemerintah.beaconsampah

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ScrollView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_tambahtimbangan.view.*
import java.text.SimpleDateFormat
import java.util.*

class dashboard : AppCompatActivity() {
    var KEYDIVISI = "Key Divisi"
    var  KEYUSERNAME = "Key Username"
    var KEYID = "Key ID"
    var KEYPREF = "Key Preferences";
    lateinit var  preferences : SharedPreferences
    var act : dashboard? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        getSupportActionBar()!!.hide()
        act = this
        imageViewGarbageHome.requestFocus()
        scroll.fullScroll(ScrollView.FOCUS_UP);
        scroll.smoothScrollTo(0,0)
        preferences = getSharedPreferences(KEYPREF, Context.MODE_PRIVATE);
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
        TimbangMenu.setOnClickListener({
            customtimbang("xx", "x")
        })
        imageViewTimbang.setOnClickListener({
            customtimbang("xx", "x")
        })
        if(preferences.contains(KEYID))
        {
            if(preferences.getString("Key Divisi","").toString().toLowerCase().equals("normal") )
            {
                TimbangMenu.setVisibility(View.VISIBLE)
            }

            textViewLogin.setText("Logout")
            textViewNama.setText("Hello "+preferences.getString("Key Username","").toString() + ",")
        }
        textViewLogin.setOnClickListener({
            if(preferences.contains(KEYID))
            {
                logout()
            }
            else
            {
                var intent = Intent(act , login::class.java)
                finish()
                startActivity(intent)
            }

        })
    }
    fun customtimbang(nama:String, id:String)
    {
        var mDialogView = LayoutInflater.from(act).inflate(R.layout.activity_tambahtimbangan, null)
        val mBuilder = AlertDialog.Builder(this@dashboard)
            .setView(mDialogView)
        val  mAlertDialog = mBuilder.show()
        mDialogView.textViewNamaPembuangSampah.setText("Petugas $nama")
        var simpleDateFormat = SimpleDateFormat("dd MM yyyy")
        val currentDate = simpleDateFormat.format(Date()).toString()
        mDialogView.textViewTanggalPembuanganSampah.setText(currentDate.toString())

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
    fun logout()
    {
        val alertDialogBuilder = AlertDialog.Builder(
            this
        )
        alertDialogBuilder.setTitle("Logout Confirmation")
        alertDialogBuilder
            .setMessage("are you sure to logout??")
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->
                var preferences =getSharedPreferences("Key Preferences", Context.MODE_PRIVATE);


//                            pushnotif("Purchase Request Baru", "Ada Purchase Request Baru dari " + preferences.getString("Key Nama","").toString(), "chJVixvaQ1eYePCYz-8uGF:APA91bEJ4aBNGWIuKgrfm2WTkp7u2hyc3a7wpdaYAu8AyImnkSBnHzz_ppRPpE8CxgCYDZjCygf0qjB-3H5ZMt76YKK2iKalPtyHSxr8VZmK2Icst5gDg6xWvJ1Wg8Uwtp13XnGyt3Y9")
                var editor = preferences.edit();
                editor.clear();
                editor.apply();
                var intent = Intent(act, dashboard::class.java)
                finish()
                startActivity(intent)
                Toast.makeText(act,"You are logged out", Toast.LENGTH_SHORT).show()
            })
            .setNegativeButton("cancel", DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
            })
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}
