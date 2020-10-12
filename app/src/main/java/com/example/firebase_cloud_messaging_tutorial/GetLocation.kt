package com.example.firebase_cloud_messaging_tutorial

import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_get_location.*
import java.io.IOException
import java.util.*
import java.util.jar.Manifest

class GetLocation : AppCompatActivity() {

    var fusedLocationProviderClient: FusedLocationProviderClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_location)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        btn_getLocation.setOnClickListener {
            if(ActivityCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                getLocation()
            }else{
                ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 44)
            }
        }
    }

    fun getLocation(){
        fusedLocationProviderClient!!.lastLocation.addOnCompleteListener { task ->
            var location = task.result
            if(location != null){
                var geoCoder = Geocoder(this, Locale.getDefault())

                try {
                    val address: List<Address> = geoCoder.getFromLocation(location.latitude,location.longitude,1)

                    textview1.setText(Html.fromHtml("" + address.get(0).latitude))
                    textview2.setText("" + address.get(0).longitude)
                    textview3.setText("" + address.get(0).countryName)
                    textview4.setText("" + address.get(0).locality)

                }catch (e : IOException){
                    e.printStackTrace()
                }

            }else{

            }
        }
    }
}
