package br.orgipdec.checkinqrcodepolodigitalmanaus

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.wifi.hotspot2.pps.HomeSp
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private val ZXING_CAMERA_PERMISSION = 1
    private var mClss: Class<*>? = null

    private var listaSala = arrayOf(
        "Sala 01", "Sala 02", "Sala 03",
        "Sala 04" , "Sala 05" , "Sala 06" ,
        "Sala 07" , "Sala 08")

    private var listaPalestra =
        arrayOf(
            "iOT - Internet das Coisas",
            "Arduíno - Conceito Básico",
            "Indústria 4.0 - Conceitos Básicos")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        spnSala!!.setOnItemSelectedListener(this)
        spnPalestra!!.setOnItemSelectedListener(this)

        val aAdapterSala = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaSala)
        val aAdapterPalestra = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaPalestra)

        aAdapterSala.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        aAdapterPalestra.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spnSala!!.setAdapter(aAdapterSala)
        spnPalestra!!.setAdapter(aAdapterPalestra)

        btnCredenciar.setOnClickListener{
            launchActivity()
        }

    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {

        when(arg0!!.id) {
            R.id.spnSala ->
                Toast.makeText(this, "Selected : ${listaSala[position]}", Toast.LENGTH_SHORT).show()
            R.id.spnPalestra  ->
                Toast.makeText(this, "Selected : ${listaPalestra[position]}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onNothingSelected(arg0: AdapterView<*>) {

    }

    private fun launchActivity() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
        //   Caso Nao tenha aceito em nenhum momento o uso da camera
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.CAMERA), ZXING_CAMERA_PERMISSION)

        } else {
            // Caso ja aceito o uso da camera

            val intent = Intent(this, HostQRCode::class.java)
            startActivity(intent)
            finish()
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            ZXING_CAMERA_PERMISSION -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Após Aceitar Permissao
                    launchActivity()

                } else {
                    // Após Negar Permissao
                }
                return
            }
        }
    }
}
