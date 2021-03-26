package com.pemerintah.beaconsampah

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.schibstedspain.leku.LocationPicker
import com.schibstedspain.leku.LocationPickerActivity
import com.schibstedspain.leku.locale.SearchZoneRect
import com.schibstedspain.leku.tracker.LocationPickerTracker
import com.schibstedspain.leku.tracker.TrackEvents
import kotlinx.android.synthetic.main.activity_daftarpembuangansampahmapview.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.graphics.Bitmap
import android.provider.MediaStore.Images.Media.getBitmap
import android.graphics.drawable.BitmapDrawable
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.provider.Settings
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams
import org.json.JSONObject


private const val MAP_BUTTON_REQUEST_CODE = 1

class daftarpembuangansampahmapview : AppCompatActivity(), OnMapReadyCallback {
    lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var map: GoogleMap
    private lateinit var myposition: LatLng
    private lateinit var monas: LatLng
    var arrmarker = ArrayList<datamarker>()
    var myposisilat = 0.0
    var myposisilang = 0.0
    var act : daftarpembuangansampahmapview? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftarpembuangansampahmapview)
        getSupportActionBar()!!.hide()
        act = this
        arrmarker.clear()
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        loaddata()
        imageViewRefreshMap.setOnClickListener({
            loaddata()
            Toast.makeText(act, "Silahkan tunggu, data marker segera tersedia", Toast.LENGTH_SHORT).show()
        })
        imageViewBackMapView.setOnClickListener({
            var intent = Intent(act, daftarlokasipembuangan::class.java)
            finish()
            startActivity(intent)
        })

    }
    fun loaddata()
    {
        var params = RequestParams()
        params.put("tipe", "listmarkertong")
        var client = AsyncHttpClient()
        client.post(getString(R.string.server)+"marker.php", params, object:AsyncHttpResponseHandler(){
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
                getLastLocation()
            }
        })
    }
    override fun onMapReady(p0: GoogleMap?) {
        map = p0!!
        val height = 100
        val width = 100
        val icon = resources.getDrawable(R.drawable.pin) as BitmapDrawable
        val b = icon.bitmap
        val smallMarker = Bitmap.createScaledBitmap(b, width, height, false)
//        Toast.makeText(act, myposisilat.toString(),Toast.LENGTH_SHORT).show()
        myposition = LatLng(myposisilat, myposisilang)
        val markerFkip = MarkerOptions()
            .position(myposition)
            .title("My Location")
            .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
        map.addMarker(markerFkip)
        for(i in 0 until arrmarker.size)
        {
            val heights = 50
            val widths = 50
            val icons = resources.getDrawable(R.drawable.redbutton) as BitmapDrawable
            val bs = icons.bitmap
            val smallMarkers = Bitmap.createScaledBitmap(bs, widths, heights, false)
            var posisitong = LatLng(arrmarker[i].latmarker.toDouble(), arrmarker[i].longmarker.toDouble())
            val markertong = MarkerOptions()
                .position(posisitong)
                .title("Garbage Location")
                .icon(BitmapDescriptorFactory.fromBitmap(smallMarkers))

            map.addMarker(markertong)
        }

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(myposition, 14f))
    }
    private fun initializeLocationPickerTracker() {
        LocationPicker.setTracker(MyPickerTracker(this))
    }

    private class MyPickerTracker(private val context: Context) : LocationPickerTracker {
        override fun onEventTracked(event: TrackEvents) {
            Toast.makeText(context, "Event: " + event.eventName, Toast.LENGTH_SHORT).show()
        }
    }
    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {

                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
//                        findViewById<TextView>(R.id.latTextView).text = location.latitude.toString()
//                        findViewById<TextView>(R.id.lonTextView).text = location.longitude.toString()
                        myposisilang = location.longitude
                        myposisilat = location.latitude
                        val mapFragment = supportFragmentManager
                            .findFragmentById(R.id.map) as SupportMapFragment
                        mapFragment.getMapAsync(act)
                    }
                }
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient!!.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location = locationResult.lastLocation
            myposisilang = mLastLocation.longitude
            myposisilat = mLastLocation.latitude


        }
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            42
        )
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == 42) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
    }

    override fun onBackPressed() {
        var intent = Intent(act , daftarlokasipembuangan::class.java)
        finish()
        startActivity(intent)
    }
}
