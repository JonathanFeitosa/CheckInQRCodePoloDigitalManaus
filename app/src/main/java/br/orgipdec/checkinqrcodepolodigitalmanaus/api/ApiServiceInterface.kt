package br.orgipdec.checkinqrcodepolodigitalmanaus.api

import br.orgipdec.checkinqrcodepolodigitalmanaus.data.Constants
import br.orgipdec.checkinqrcodepolodigitalmanaus.model.RegistrarUsuario
import br.orgipdec.checkinqrcodepolodigitalmanaus.model.RegistrarUsuarioReturn
import br.orgipdec.checkinqrcodepolodigitalmanaus.model.ReturnAPIIPDEC
import br.orgipdec.checkinqrcodepolodigitalmanaus.model.ReturnCredenciado
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiServiceInterface {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json; charset=utf-8"
    )
    @GET("acesso/schedules")
    fun getJSONAPI(): Observable<ReturnAPIIPDEC>

    @POST("acesso/registrar/")
    fun registrarQRCode(@Body info : RegistrarUsuario) : Observable<ReturnCredenciado>

    companion object Factory {
        fun create(): ApiServiceInterface {
            // Retrofit.Builder() : Classe responsável por construir um objeto do tipo Retrofit.
            val retrofit = retrofit2.Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                // API Gson para conversão de objetos
                .addConverterFactory(GsonConverterFactory.create())
                // URL da API
                .baseUrl(Constants.BASE_URL)
                // fINALIZAÇÃO DA CONFIGURAÇÃO BIULD.
                .build()

            return retrofit.create(ApiServiceInterface::class.java) // Single-Expression function kotlin
        }
    }
}