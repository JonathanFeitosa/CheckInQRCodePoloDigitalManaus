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
    private var contadorPalestra = 404


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        registrarContagem(Integer.parseInt(SharedPreferences.getIDPalestra(requireActivity())!!))
        return inflater.inflate(R.layout.qrcode_leitura, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        txtInfoMain.setText("Olá ${SharedPreferences.getOganizador(requireActivity())}! A Sala ${SharedPreferences.getSala(requireActivity())} está com - Participantes.")
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

            var registrarUsuario : RegistrarUsuario = RegistrarUsuario(Integer.parseInt(SharedPreferences.getIDPalestra(requireActivity())!!), "${rawResult.text}", "${SharedPreferences.getOganizador(requireActivity())}")
            registrarQRCode(registrarUsuario)

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

                    var registrarUsuario : RegistrarUsuario = RegistrarUsuario(Integer.parseInt(SharedPreferences.getIDPalestra(requireActivity())!!), "${qrcode}", "${SharedPreferences.getOganizador(requireActivity())}")
                    registrarQRCode(registrarUsuario)

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

        var subscribe = api.registrarQRCode(ru).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ success ->

                Log.i("Resultadojfs", "ID PALESTRA: ${ru.palestra}")
                Log.i("Resultadojfs", "Mensagem: ${success.msg}")
                Log.i("Resultadojfs", "Participante: ${success.participante}")

                val bundle = Bundle()
                bundle.putString("qrcode", ru.qrcode)
                bundle.putInt("contador", contadorPalestra)
                bundle.putString("nomecaboco", "404")
                findNavController().navigate(R.id.action_navigation_home_to_navigation_viewqrcode, bundle)

            }, { error ->
                var error2 = error
                var errorMessage = ApiErrorCredencial(error, "msg")

                if(errorMessage.message.equals("registradosucesso")) {

                    Log.i("Resultadojfs", "ID PALESTRA REGISTRADOSUCESSO: ${ru.palestra}")
                    Log.i("Resultadojfs", "qrcode REGISTRADOSUCESSO: ${ru.qrcode}")
                    Log.i("Resultadojfs", "contador_2 REGISTRADOSUCESSO: ${ru.qrcode}")
                    val bundle = Bundle()
                    bundle.putString("qrcode", ru.qrcode)
                    bundle.putString("nomecaboco", errorMessage.partipante)
                    bundle.putInt("contador", contadorPalestra+1)
                    findNavController().navigate(R.id.action_navigation_home_to_navigation_viewqrcode, bundle)



                }else {

                    Toast.makeText(requireActivity(), "Erro: ${errorMessage.message}", Toast.LENGTH_SHORT)
                        .show()
                    Log.i("Resultadojfs", "erro: ${errorMessage.message}")
                }

            })
        subscriptions.add(subscribe)
    }

    fun registrarContagem(palestra: Int) {

        var subscribe = api.getCountPalestra(palestra).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ success ->

                contadorPalestra = success.contador
                Log.i("Resultadojfs", "Acerto Contagem: ${contadorPalestra}")
                txtInfoMain.setText("Olá ${SharedPreferences.getOganizador(requireActivity())}! A Sala ${SharedPreferences.getSala(requireActivity())} está com ${contadorPalestra} Participantes.")

            }, { error ->
                Log.i("Resultadojfs", "Erro Contagem: ${error.localizedMessage}")
            })
        subscriptions.add(subscribe)
    }
}