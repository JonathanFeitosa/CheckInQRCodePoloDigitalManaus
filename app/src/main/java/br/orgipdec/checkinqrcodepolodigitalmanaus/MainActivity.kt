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
import br.orgipdec.checkinqrcodepolodigitalmanaus.api.ApiServiceInterface
import br.orgipdec.checkinqrcodepolodigitalmanaus.model.Day
import br.orgipdec.checkinqrcodepolodigitalmanaus.model.ReturnAPIIPDEC
import br.orgipdec.checkinqrcodepolodigitalmanaus.model.Talk
import br.orgipdec.checkinqrcodepolodigitalmanaus.model.Trilha
import br.orgipdec.checkinqrcodepolodigitalmanaus.utils.SharedPreferences
import com.ogulcan.android.mvp.app.models.DetailsViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private val subscriptions = CompositeDisposable()
    private val ZXING_CAMERA_PERMISSION = 1
    private var mClss: Class<*>? = null
    private val api: ApiServiceInterface = ApiServiceInterface.create()


    private var listaDia : ArrayList<String> =  ArrayList<String>()

    private var listaSala : ArrayList<String> =  ArrayList<String>()

    private var listaPalestra : ArrayList<String> =  ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!SharedPreferences.checkInfo(this)) {  // Se for o primeiro Login

            SharedPreferences.setInfo(this, true)

            setContentView(R.layout.activity_main)

            pbCarregarTela.setVisibility(View.VISIBLE) // VISIBLE PROGRESSBAR
            carregarDia()

            spnDia!!.setOnItemSelectedListener(this)
            spnSala!!.setOnItemSelectedListener(this)

            btnCredenciar.setOnClickListener{

                pbCarregarTela.setVisibility(View.VISIBLE)
                // Setando Variáveis Globais
                SharedPreferences.setOrganizador(this, edtOrganizador.text.toString())
                SharedPreferences.setDia(this, listaDia.get(spnDia.getSelectedItemPosition()))
                SharedPreferences.setSala(this, listaSala.get(spnSala.getSelectedItemPosition()))
                SharedPreferences.setPalestra(this, listaPalestra.get(spnPalestra.getSelectedItemPosition()))

                launchActivity()
            }
        }
        else{ // SE NAO FOR O PRIMEIRO LOGIN

            val intent = Intent(this, HostQRCode::class.java)
            startActivity(intent)
            finish()

        }
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {

        when(arg0!!.id) {
            R.id.spnDia ->{
                pbCarregarTela.setVisibility(View.VISIBLE) // VISIBLE PROGRESSBAR
                carregarSala(listaDia.get(position))
            }
            R.id.spnSala -> {
                pbCarregarTela.setVisibility(View.VISIBLE) // VISIBLE PROGRESSBAR
                carregarPalestra(listaDia.get(spnDia.getSelectedItemPosition()), listaSala.get(position))
            }
        }
    }

    override fun onNothingSelected(arg0: AdapterView<*>) {

    }

    private fun launchActivity() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            pbCarregarTela.setVisibility(View.INVISIBLE)
        //   Caso Nao tenha aceito em nenhum momento o uso da camera
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.CAMERA), ZXING_CAMERA_PERMISSION)


        } else {
            // Caso ja aceito o uso da camera
            pbCarregarTela.setVisibility(View.INVISIBLE)
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
    fun carregarDia() {

        listaDia  =  ArrayList<String>()
        var subscribe = api.getJSONAPI().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list: ReturnAPIIPDEC? ->

                list!!.days.forEach { // Dias
                    listaDia!!.add(it.id)
                    }

                // Adapter do Dia !
                val aAdapterDia = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaDia!!.toList())
                aAdapterDia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spnDia!!.setAdapter(aAdapterDia)
                pbCarregarTela.setVisibility(View.INVISIBLE) // INVISIBLE PROGRESSBAR


            }, { error ->
                Log.i("Resultadojfs", "Erro: : ${error.localizedMessage}")
                pbCarregarTela.setVisibility(View.INVISIBLE) // INVISIBLE PROGRESSBAR
            })

        subscriptions.add(subscribe)
    }
    fun carregarSala(dia : String) {

        listaSala  =  ArrayList<String>()
        var subscribe = api.getJSONAPI().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list: ReturnAPIIPDEC? ->

                list!!.days.forEach {
                    if(it.id.equals(dia)) {
                        it.trilha.forEach {
                            // Salas
                            listaSala!!.add("${it.room} - ${it.title}")
                        }
                    }
                  }
                // Adapter da Sala !
                val aAdapteSala = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaSala!!.toList())
                aAdapteSala.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spnSala!!.setAdapter(aAdapteSala)
                pbCarregarTela.setVisibility(View.INVISIBLE) // INVISIBLE PROGRESSBAR
            }, { error ->
                Log.i("Resultadojfs", "Erro: + ${error.message}")
                pbCarregarTela.setVisibility(View.INVISIBLE) // INVISIBLE PROGRESSBAR
            })

        subscriptions.add(subscribe)
    }
    fun carregarPalestra(sala : String, trilha : String) {

        listaPalestra  =  ArrayList<String>()
        var subscribe = api.getJSONAPI().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list: ReturnAPIIPDEC? ->

                list!!.days.forEach {
                    if(it.id.equals(sala)) {
                        it.trilha.forEach {
                            if ("${it.room} - ${it.title}".equals(trilha)) {
                                it.talks.forEach {
                                    listaPalestra!!.add(it.title)
                                }
                            }
                        }
                    }
                }
                // Adapter da Palestra !
                val aAdaptePalestra = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaPalestra!!.toList())
                aAdaptePalestra.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spnPalestra!!.setAdapter(aAdaptePalestra)
                pbCarregarTela.setVisibility(View.INVISIBLE) // INVISIBLE PROGRESSBAR

            }, { error ->
                Log.i("Resultadojfs", "Erro: + ${error.message}")
                pbCarregarTela.setVisibility(View.INVISIBLE) // INVISIBLE PROGRESSBAR
            })

        subscriptions.add(subscribe)
    }
}
