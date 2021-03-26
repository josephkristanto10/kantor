package com.pemerintah.beaconsampah

import Adaptermarker
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams
import kotlinx.android.synthetic.main.activity_daftarlokasipembuangan.*
import org.json.JSONObject

class daftarlokasipembuangan : AppCompatActivity() {

    var arrmarker = ArrayList<datamarker>()
    var act : daftarlokasipembuangan? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftarlokasipembuangan)
        getSupportActionBar()!!.hide()
        act = this
        arrmarker.clear()
        loaddata()
        constraintLayoutMapView.setOnClickListener({
            var intent = Intent(act, daftarpembuangansampahmapview::class.java)
            finish()
            startActivity(intent)
        })
        imageViewBackDaftarPembuanganSampah.setOnClickListener({
            var intent = Intent(act, menutps::class.java)
            finish()
            startActivity(intent)
        })
    }

    fun loaddata()
    {
        var params = RequestParams()
        params.put("tipe", "listmarkertong")
        var client = AsyncHttpClient()
        client.post(getString(R.string.server)+"marker.php", params, object:
            AsyncHttpResponseHandler(){
            override fun onSuccess(res: String) {
                if(res.equals("none"))
                {
                    arrmarker.clear()
                    Toast.makeText(act, "data marker tidak ditemukan", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    arrmarker.clear()
                    var jsonobj = JSONObject(res)
                    var jsonarr = jsonobj.getJSONArray("data")
                    for(i in 0 until jsonarr.length())
                    {
                        var jproduk = jsonarr.getJSONObject(i)
                        arrmarker.add(datamarker(jproduk.getString("idmarker"), jproduk.getString("namamarker"), jproduk.getString("latitude"), jproduk.getString("longitude")))
                    }
                }
                RecycleListMarker.setHasFixedSize(true)
                RecycleListMarker.layoutManager = LinearLayoutManager(act,
                    LinearLayoutManager.VERTICAL,false)

                val adapters = Adaptermarker(act!!,arrmarker)
                adapters.notifyDataSetChanged()

                RecycleListMarker.setAdapter(adapters)
            }
        })
    }

    override fun onBackPressed() {
        var intent = Intent(act , menutps::class.java)
        finish()
        startActivity(intent)
    }
}
