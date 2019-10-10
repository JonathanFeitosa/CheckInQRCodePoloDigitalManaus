package br.orgipdec.checkinqrcodepolodigitalmanaus

import android.os.Bundle
import android.util.Log
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView
import android.media.MediaPlayer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.orgipdec.checkinqrcodepolodigitalmanaus.data.Constants
import br.orgipdec.checkinqrcodepolodigitalmanaus.utils.DateTime
import kotlinx.android.synthetic.main.qrcode_leitura.*


/**
 * Created by
 * @name Anartz
 * @lastname Mugika
 * @data 2018/06/15
 */
class QRCodeActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {
   // private var mScannerView: ZXingScannerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.qrcode_leitura)

        QrCodeID!!.setResultHandler(this) // Register ourselves as a handler for scan results.
        QrCodeID!!.startCamera()         // Start camera


    }
    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        //       this.cleanResult()
    }

    override fun onBackPressed() { this.cleanResult() }

    private fun cleanResult() {
        try {
            QrCodeID!!.stopCamera() // Stop camera on pause
        } catch (e: Exception) {
            Log.e(Constants.ERROR, e.message)
        }

        /*
        val resultIntent = Intent()
        resultIntent.putExtra(Constants.BAR_CODE, "")
        setResult(2, resultIntent)
        finish() */
    }

    override fun handleResult(rawResult: Result) {

        Log.e(Constants.HANDLER, rawResult.text) // Prints scan results
        Log.e(Constants.HANDLER, rawResult.barcodeFormat.toString()) // Prints the scan format (qrcode)

        try {
            QrCodeID!!.stopCamera()

            //MediaPlayer.create(this, Constants.SCAN_OK_SOUND).start()

            Toast.makeText(this, "Selected : ${rawResult.text} - Data: ${DateTime.currentDataTime}", Toast.LENGTH_SHORT).show()

            println(Constants.STOP_CAMERA)

            // Stop camera on pause
        } catch (e: Exception) {
            Log.e(Constants.ERROR, e.message)
        }

    }
}