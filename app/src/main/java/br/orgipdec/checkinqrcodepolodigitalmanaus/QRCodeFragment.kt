package br.orgipdec.checkinqrcodepolodigitalmanaus

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.*
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import br.orgipdec.checkinqrcodepolodigitalmanaus.data.Constants
import br.orgipdec.checkinqrcodepolodigitalmanaus.utils.DateTime
import br.orgipdec.checkinqrcodepolodigitalmanaus.utils.SharedPreferences
import kotlinx.android.synthetic.main.qrcode_leitura.*
import android.text.InputType
import android.R.attr.inputType
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.login_dialog.view.*


class QRCodeFragment : Fragment(), ZXingScannerView.ResultHandler {
    private var mScannerView: ZXingScannerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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

            MediaPlayer.create(requireActivity(), R.raw.sound_successful).start()

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menuqrcode, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return (when(item.itemId) {
            R.id.cadNumber -> {

                val mDialogView = LayoutInflater.from(requireActivity()).inflate(R.layout.login_dialog, null)
                //AlertDialogBuilder
                val mBuilder = AlertDialog.Builder(requireActivity())
                    .setView(mDialogView)
                    .setTitle("Login Form")
                //show dialog
                val  mAlertDialog = mBuilder.show()
                //login button click of custom layout
                mDialogView.dialogLoginBtn.setOnClickListener {
                    //dismiss dialog
                    mAlertDialog.dismiss()
                    //get text from EditTexts of custom layout
               //     val name = mDialogView.dialogNameEt.text.toString()
          //          val email = mDialogView.dialogEmailEt.text.toString()
                    val password = mDialogView.dialogPasswEt.text.toString()
                    //set the input text in TextView
                  //  mainInfoTv.setText("Name:"+ name +"\nEmail: "+ email +"\nPassword: "+ password)
                }
                //cancel button click of custom layout
                mDialogView.dialogCancelBtn.setOnClickListener {
                    //dismiss dialog
                    mAlertDialog.dismiss()
                }

                true
            }
            R.id.alterarConfig -> {

                SharedPreferences.setInfo(requireActivity(), false)
                val intent = Intent(requireActivity(), MainActivity::class.java)
                startActivity(intent)
              //  finish()

                true
            }
            else ->
                super.onOptionsItemSelected(item)
        })
    }

}