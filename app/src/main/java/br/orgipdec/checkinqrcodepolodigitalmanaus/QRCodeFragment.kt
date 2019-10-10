package br.orgipdec.checkinqrcodepolodigitalmanaus

import android.os.Bundle
import android.util.Log
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import br.orgipdec.checkinqrcodepolodigitalmanaus.data.Constants
import br.orgipdec.checkinqrcodepolodigitalmanaus.utils.DateTime
import kotlinx.android.synthetic.main.qrcode_leitura.*

class QRCodeFragment : Fragment(), ZXingScannerView.ResultHandler {
    private var mScannerView: ZXingScannerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        return inflater.inflate(R.layout.qrcode_leitura, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mScannerView = view.findViewById(R.id.QrCodeID)
        mScannerView!!.setResultHandler(this) // Register ourselves as a handler for scan results.
        mScannerView!!.startCamera()         // Start camera

    }

    override fun onPause() {
        super.onPause()
        this.cleanResult()
    }

    private fun cleanResult() {
        try {
            mScannerView!!.stopCamera() // Stop camera on pause
        } catch (e: Exception) {
            Log.e(Constants.ERROR, e.message)
        }
    }
    override fun handleResult(rawResult: Result) {

        Log.e(Constants.HANDLER, rawResult.text) // Prints scan results
        Log.e(Constants.HANDLER, rawResult.barcodeFormat.toString()) // Prints the scan format (qrcode)

        try {
            QrCodeID!!.stopCamera()

            //MediaPlayer.create(this, Constants.SCAN_OK_SOUND).start()

            val bundle = Bundle()
            bundle.putString("qrcode", rawResult.text)
            bundle.putString("data", DateTime.currentDataTime)
            findNavController().navigate(R.id.action_navigation_home_to_navigation_viewqrcode, bundle)

            println(Constants.STOP_CAMERA)

            // Stop camera on pause
        } catch (e: Exception) {
            Log.e(Constants.ERROR, e.message)
        }

    }
}