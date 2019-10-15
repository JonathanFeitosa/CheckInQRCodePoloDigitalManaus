package br.orgipdec.checkinqrcodepolodigitalmanaus.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RegistrarUsuario {


    @PrimaryKey(autoGenerate = true)  var id: Long? = null
    @ColumnInfo(name = COLUMN_PALESTRA) var palestra: Int = 0
    @ColumnInfo(name = COLUMN_QRCODE) var qrcode: String? = null
    @ColumnInfo(name = COLUMN_RESPONSAVEL) var responsavel: String? = null


    constructor(palestra : Int, qrcode : String, responsavel: String){
        this.palestra = palestra
        this.qrcode = qrcode
        this.responsavel = responsavel

    }
    companion object {
        const val COLUMN_PALESTRA = "palestra"
        const val COLUMN_QRCODE = "qrcode"
        const val COLUMN_RESPONSAVEL = "responsavel"
    }

}
