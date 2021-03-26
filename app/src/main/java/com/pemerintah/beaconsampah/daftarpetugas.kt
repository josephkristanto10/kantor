package com.pemerintah.beaconsampah

import Adapterpetugas
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams
import kotlinx.android.synthetic.main.activity_daftarpetugas.*
import kotlinx.android.synthetic.main.customdialogdetailpetugas.view.*
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList


class daftarpetugas : AppCompatActivity() {

    var arrpetugas = ArrayList<datapetugas>()
    var act : daftarpetugas? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftarpetugas)
        getSupportActionBar()!!.hide()
        act = this
        imageViewBackDaftarPetugas.setOnClickListener({
            var intent = Intent(act, dashboard::class.java)
            finish()
            startActivity(intent)
        })
        arrpetugas.clear()
        loaddata()
    }
    fun loaddata(){
        var statusindekshariini = 0
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_WEEK)
        when (day) {
            Calendar.SUNDAY -> {
                statusindekshariini = 6
            }
            Calendar.MONDAY -> {
                statusindekshariini = 0
            }
            Calendar.TUESDAY -> {
                statusindekshariini = 1
            }
            Calendar.WEDNESDAY -> {
                statusindekshariini = 2
            }
            Calendar.THURSDAY -> {
                statusindekshariini = 3
            }
            Calendar.FRIDAY -> {
                statusindekshariini = 4
            }
            Calendar.SATURDAY -> {
                statusindekshariini = 5
            }
        }
        var params = RequestParams()
        params.put("tipe", "listpetugas")
        var client = AsyncHttpClient()
        client.post(getString(R.string.server)+"petugas.php", params, object:
            AsyncHttpResponseHandler(){
            override fun onSuccess(res: String) {
                if(res.equals("none"))
                {
                    arrpetugas.clear()
                    Toast.makeText(act, "data petugas tidak ditemukan", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    arrpetugas.clear()
                    var jsonobj = JSONObject(res)
                    var jsonarr = jsonobj.getJSONArray("data")
                    for(i in 0 until jsonarr.length())
                    {
                        var jproduk = jsonarr.getJSONObject(i)
                        arrpetugas.add(datapetugas(jproduk.getString("idpetugas"), jproduk.getString("namapetugas"), jproduk.getString("handphonepetugas"), jproduk.getString("poolpetugas"), jproduk.getString("harisenin"), jproduk.getString("hariselasa"), jproduk.getString("harirabu"), jproduk.getString("harikamis"), jproduk.getString("harijumat"), jproduk.getString("harisabtu"), jproduk.getString("hariminggu")))
                    }
                }
                RecycleListPetugas.setHasFixedSize(true)
                RecycleListPetugas.layoutManager = LinearLayoutManager(act,
                    LinearLayoutManager.VERTICAL,false)

                val adapters = Adapterpetugas(act!!,arrpetugas, statusindekshariini)
                adapters.notifyDataSetChanged()

                RecycleListPetugas.setAdapter(adapters)
            }
        })

    }
    fun customdialogpetugas(nama:String, pool:String, hp:String, status:String)
    {
        var mDialogView = LayoutInflater.from(act).inflate(R.layout.customdialogdetailpetugas, null)
        val mBuilder = AlertDialog.Builder(this@daftarpetugas)
            .setView(mDialogView)
        val  mAlertDialog = mBuilder.show()
        mDialogView.textViewNamaPetugasDetail.setText("Petugas $nama")
        mDialogView.textViewPoolPetugasDetail.setText("Pool $pool")
        mDialogView.textViewHandphonePetugasDetail.setText("$hp")
        if(status.toLowerCase().equals("on"))
        {
            mDialogView.textViewStatusDetailPetugas.setText("OnDuty")
            mDialogView.textViewStatusDetailPetugas.background.setTint(Color.parseColor("#31C005"))
        }
        else
        {
            mDialogView.textViewStatusDetailPetugas.setText("Offline")
            mDialogView.textViewStatusDetailPetugas.background.setTint(Color.parseColor("#DF0707"))
        }
        mDialogView.imageViewBackDaftarPetugasDetail.setOnClickListener({
            mAlertDialog.dismiss()
        })
    }

    override fun onBackPressed() {
        var intent = Intent(act, dashboard::class.java)
        finish()
        startActivity(intent)
    }
}
