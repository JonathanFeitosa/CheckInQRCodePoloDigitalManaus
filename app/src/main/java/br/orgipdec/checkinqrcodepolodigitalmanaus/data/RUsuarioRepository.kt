package br.orgipdec.checkinqrcodepolodigitalmanaus.data

import androidx.room.Database
import androidx.room.RoomDatabase
import br.orgipdec.checkinqrcodepolodigitalmanaus.model.RegistrarUsuario


@Database(entities = [RegistrarUsuario::class], version = 1, exportSchema = false)
abstract class RUsuarioRepository : RoomDatabase() {

    abstract fun productDao(): RUsuarioDAO

}