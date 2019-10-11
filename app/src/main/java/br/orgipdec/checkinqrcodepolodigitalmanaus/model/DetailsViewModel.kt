package com.ogulcan.android.mvp.app.models

import br.orgipdec.checkinqrcodepolodigitalmanaus.model.ReturnAPIIPDEC
import com.google.gson.Gson

/**
 * Created by ogulcan on 08/02/2018.
 */
data class DetailsViewModel(val posts: List<ReturnAPIIPDEC>) {
    fun toJson(): String {
        return Gson().toJson(this)
}
}