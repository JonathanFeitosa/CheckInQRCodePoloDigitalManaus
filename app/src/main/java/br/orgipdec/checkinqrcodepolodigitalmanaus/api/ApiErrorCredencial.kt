package br.orgipdec.checkinqrcodepolodigitalmanaus.api

import android.util.Log
import com.google.gson.JsonParser
import retrofit2.HttpException

class ApiErrorCredencial constructor(error: Throwable, campo : String) {
    var message = "Ocorreu um erro inesperado!"
    var partipante = "404"

    init {
        if (error is HttpException) {
            var errorJsonString = error.response()!!
                .errorBody()?.string()

            Log.i("ResultadoJFS", "teste 1: $errorJsonString")


            if(errorJsonString!!.contains("Participante Registrado com Sucesso")) {
                this.message = "registradosucesso"

                partipante = JsonParser().parse(errorJsonString)
                    .asJsonObject["participante"]
                    .asString
            }
            else if(errorJsonString!!.contains("null")) {

                message = JsonParser().parse(errorJsonString)
                    .asJsonObject["msg"]
                    .asString
            }else
            {
                message = "Ocorreu um erro inesperado!"
            }

        } else {
            this.message = error.message ?: this.message
        }
    }
}