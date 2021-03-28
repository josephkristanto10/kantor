package com.pemerintah.beaconsampah

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class login : AppCompatActivity() {
    var act : login? = null
    var KEYDIVISI = "Key Divisi"
    var  KEYUSERNAME = "Key Username"
    var KEYID = "Key ID"
    var KEYPREF = "Key Preferences";
    lateinit var  preferences : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        getSupportActionBar()!!.hide()
        act = this
        preferences = getSharedPreferences(KEYPREF, Context.MODE_PRIVATE);
        buttonSubmit.setOnClickListener({
            var params = RequestParams()
            params.put("tipe", "login")
            params.put("username", editTextUsername.text.toString())
            params.put("password", editTextPassword.text.toString())
            var client = AsyncHttpClient()
            client.post(getString(R.string.server)+"login.php", params, object:AsyncHttpResponseHandler(){
                override fun onSuccess(content: String?) {
                    if(content.equals("none"))
                    {
                        Toast.makeText(act, "Data petugas tidak ditemukan", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        var json = JSONObject(content)
                        var jsonarr = json.getJSONArray("data")
                        for(i in 0 until jsonarr.length())
                        {
                            var jproduk = jsonarr.getJSONObject(0)
                            var editor = preferences.edit();
                            editor.putString(KEYID, jproduk.getString("idpetugas"));
                            editor.putString(KEYUSERNAME, jproduk.getString("username"))
                            editor.putString(KEYDIVISI, jproduk.getString("role"))
                            editor.apply();
                            Toast.makeText(act, "Login Sukses, Selamat Datang " + jproduk.getString("username"), Toast.LENGTH_SHORT).show()
                            var intent = Intent(act , dashboard::class.java)
                            finish()
                            startActivity(intent)
                        }


                    }
                }
            })
        })
    }

    override fun onBackPressed() {
        var intent = Intent(act , dashboard::class.java)
        finish()
        startActivity(intent)
    }
}
