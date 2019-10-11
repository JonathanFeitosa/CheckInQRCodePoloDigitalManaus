package br.orgipdec.checkinqrcodepolodigitalmanaus.model

import com.google.gson.annotations.SerializedName

data class RegistrarUsuario(
    val palestra: Int,
    val qrcode: String,
    val responsavel: String
)

data class RegistrarUsuarioReturn(
    @SerializedName("contador") val contador: Int,
    @SerializedName("palestra") val palestra: Int)