package com.pemerintah.beaconsampah

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams
import kotlinx.android.synthetic.main.activity_tambahlokasi.*

class tambahlokasi : AppCompatActivity(),  OnMapReadyCallback {

    var posisiuploadlat = ""
    var posisiuploadlong = ""
    private lateinit var map: GoogleMap
    var act : tambahlokasi? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambahlokasi)
        getSupportActionBar()!!.hide()
        act = this
        imageViewBackTambahLokasi.setOnClickListener({
            var intent = Intent(act, menutps::class.java)
            finish()
            startActivity(intent)
        })
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(act)
        buttonSubmitPosition.setOnClickListener({
            if(editTextNamaLokasiBaru.text.toString() != "")
            {
                if(editTextNamaLokasiBaru.text.length > 5)
                {
                    val alertDialogBuilder = AlertDialog.Builder(
                        this
                    )
                    alertDialogBuilder.setTitle("Submit Confirmation")
                    alertDialogBuilder
                        .setMessage("Are you sure to submit this position?")

                        .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->
                            uploaddata()
                        })
                        .setNegativeButton("cancel", DialogInterface.OnClickListener { dialog, id ->
                            dialog.cancel()
                        })
                    val alertDialog = alertDialogBuilder.create()
                    alertDialog.show()
                }
                else
                {
                    Toast.makeText(act, "Nama lokasi harus lebih dari 5 huruf", Toast.LENGTH_SHORT).show()
                }

            }
            else
            {
                Toast.makeText(act, "Nama lokasi harus disii", Toast.LENGTH_SHORT).show()
            }

        })
    }
    fun uploaddata()
    {
        var params = RequestParams()
        params.put("tipe", "tambahposisi")
        params.put("lat", posisiuploadlat)
        params.put("long", posisiuploadlong)
        params.put("nama", editTextNamaLokasiBaru.text.toString())
        var client = AsyncHttpClient()
        client.post(getString(R.string.server)+"marker.php", params,object:AsyncHttpResponseHandler(){
            override fun onSuccess(content: String) {
                if(content.equals("exists"))
                {
                    Toast.makeText(act, "Posisi sudah pernah diinputkan", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Toast.makeText(act, "Posisi sudah diinput", Toast.LENGTH_SHORT).show()
                    editTextNamaLokasiBaru.setText("")
                    var myposition = LatLng(-7.663640, 111.324669)
                    textViewKoordinatLatitudeLokasi.setText("-7.663640")
                    textViewKoordinatLongitude.setText("111.324669")
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(myposition, 16f))
                }
            }
        })
    }
    override fun onMapReady(p0: GoogleMap?) {
        map = p0!!
        map!!.setOnCameraMoveListener(object : GoogleMap.OnCameraMoveListener{
            override fun onCameraMove() {
                val midLatLng: LatLng = map.cameraPosition.target
                textViewKoordinatLatitudeLokasi.setText(midLatLng.latitude.toString())
                textViewKoordinatLongitude.setText(midLatLng.longitude.toString())
                posisiuploadlat = textViewKoordinatLatitudeLokasi.text.toString()
                posisiuploadlong = textViewKoordinatLongitude.text.toString()
            }
        })
        map!!.setOnCameraIdleListener {
            val midLatLng: LatLng = map.cameraPosition.target//map's center position latitude & longitude
            posisiuploadlat = midLatLng.latitude.toString()
            posisiuploadlong = midLatLng.longitude.toString()
        }
        var myposition = LatLng(-7.663640, 111.324669)
        textViewKoordinatLatitudeLokasi.setText("-7.663640")
        textViewKoordinatLongitude.setText("111.324669")
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(myposition, 16f))
    }

    override fun onBackPressed() {
        var intent = Intent(act , menutps::class.java)
        finish()
        startActivity(intent)
    }
}
