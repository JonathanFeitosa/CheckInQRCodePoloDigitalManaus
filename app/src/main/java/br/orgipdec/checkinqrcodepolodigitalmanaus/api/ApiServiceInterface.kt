package br.orgipdec.checkinqrcodepolodigitalmanaus.api

import br.orgipdec.checkinqrcodepolodigitalmanaus.data.Constants
import br.orgipdec.checkinqrcodepolodigitalmanaus.model.ReturnAPIIPDEC
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiServiceInterface {

    @GET("schedule.json")
    fun getJSONAPI(): Observable<ReturnAPIIPDEC>

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