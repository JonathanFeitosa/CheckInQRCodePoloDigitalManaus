package br.orgipdec.checkinqrcodepolodigitalmanaus.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.orgipdec.checkinqrcodepolodigitalmanaus.model.RegistrarUsuario


@Dao
interface RUsuarioDAO {
    @Query("SELECT * from registrarusuario")
    fun getAll(): List<RegistrarUsuario>

    @Insert
    fun insert(ru: RegistrarUsuario)

    @Query("SELECT * FROM registrarusuario WHERE id = :idU")
    fun getRUsuarioById(idU: Long): RegistrarUsuario

    @Query("DELETE FROM registrarusuario WHERE id = :idU")
    fun deleteById(idU: Long): Int
}