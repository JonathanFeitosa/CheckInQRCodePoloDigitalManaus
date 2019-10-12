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
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import br.orgipdec.checkinqrcodepolodigitalmanaus.api.ApiErrorCredencial
import br.orgipdec.checkinqrcodepolodigitalmanaus.api.ApiServiceInterface
import br.orgipdec.checkinqrcodepolodigitalmanaus.model.RegistrarUsuario
import br.orgipdec.checkinqrcodepolodigitalmanaus.model.RegistrarUsuarioReturn
import br.orgipdec.checkinqrcodepolodigitalmanaus.model.ReturnAPIIPDEC
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.login_dialog.view.*


class QRCodeFragment : Fragment(), ZXingScannerView.ResultHandler {
    private var mScannerView: ZXingScannerView? = null
    private val api: ApiServiceInterface = ApiServiceInterface.create()
    private val subscriptions = CompositeDisposable()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.qrcode_leitura, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        txtInfoMain.setText("Olá ${SharedPreferences.getOganizador(requireActivity())}! A Sala ${SharedPreferences.getSala(requireActivity())} está com X Participantes.")
        txtOrganization.setText("Evento: ${SharedPreferences.getPalestra(requireActivity())}")
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

            var registrarUsuario : RegistrarUsuario = RegistrarUsuario(2, "${rawResult.text}", "${SharedPreferences.getOganizador(requireActivity())}")
            registrarQRCode(registrarUsuario)

         //   val bundle = Bundle()
         //   bundle.putString("qrcode", rawResult.text)
         //   bundle.putString("data", DateTime.currentDataTime)
        //    findNavController().navigate(R.id.action_navigation_home_to_navigation_viewqrcode, bundle)

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
                    .setTitle("Registrar QR Code")
                //show dialog
                val  mAlertDialog = mBuilder.show()
                mDialogView.dialogRegistrarBtn.setOnClickListener {
                    mAlertDialog.dismiss()

                    val qrcode = mDialogView.edtQRCode.text.toString()

                    var registrarUsuario : RegistrarUsuario = RegistrarUsuario(1, "${qrcode}", "${SharedPreferences.getOganizador(requireActivity())}")
                    registrarQRCode(registrarUsuario)

            //        val bundle = Bundle()
              //      bundle.putString("qrcode", qrcode)
          //          bundle.putString("data", DateTime.currentDataTime)
               //     findNavController().navigate(R.id.action_navigation_home_to_navigation_viewqrcode, bundle)

                }

                mDialogView.dialogCancelBtn.setOnClickListener {

                    mAlertDialog.dismiss()
                }

                true
            }
            R.id.alterarConfig -> {

                SharedPreferences.setInfo(requireActivity(), false)
                val intent = Intent(requireActivity(), MainActivity::class.java)
                startActivity(intent)

                true
            }
            else ->
                super.onOptionsItemSelected(item)
        })
    }

    fun registrarQRCode(ru : RegistrarUsuario) {

       // listaDia  =  ArrayList<String>()
        var subscribe = api.registrarQRCode(ru).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ success ->

                Log.i("Resultadojfs", "Nome: ${success.sucesso.participante.name}")
                Log.i("Resultadojfs", "Nome: ${success.sucesso.participante.lastname}")

                /*val errorMessage = ApiErrorCredencial(success., "message").message

                //   Conta tem que ser ativada para fazer Login
                if(errorMessage.equals("activeaccount")) {

                }
                if(success.jaregistrado == null){
                    Log.i("Resultadojfs", "entrou")
                }else{
                    Log.i("Resultadojfs", "nao entrou")
                }*/

            }, { error ->
                Log.i("Resultadojfs", "Erro: : ${error.localizedMessage}")
            })
        subscriptions.add(subscribe)
    }

}