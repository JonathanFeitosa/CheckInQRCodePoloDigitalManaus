package br.orgipdec.checkinqrcodepolodigitalmanaus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI

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