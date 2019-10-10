package br.orgipdec.checkinqrcodepolodigitalmanaus

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_main.*

class HostQRCode : AppCompatActivity(){

    override fun onCreate(savedInstanceState:Bundle ?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.qrcode_main)

            // Toolbar fragments integrate
        NavigationUI.setupActionBarWithNavController(
            this,
            Navigation.findNavController(this, R.id.navHostFragment)
        )

        }
    // Button Back toolbar
    override fun onSupportNavigateUp():Boolean {
        return Navigation.findNavController(this, R.id.navHostFragment).navigateUp()
    }

}
