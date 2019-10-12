package br.orgipdec.checkinqrcodepolodigitalmanaus.api

import android.util.Log
import com.google.gson.JsonParser
import retrofit2.HttpException

class ApiErrorCredencial constructor(error: Throwable, campo : String) {
    var message = "Ocorreu um erro inesperado!"

    init {
        if (error is HttpException) {
            val errorJsonString = error.response()!!
                .errorBody()?.string()

            message = JsonParser().parse(errorJsonString)
                .asJsonObject["message"]
                .asString
        } else {
            this.message = error.message ?: this.message
        }
    }
}