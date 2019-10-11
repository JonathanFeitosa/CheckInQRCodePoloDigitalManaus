package br.orgipdec.checkinqrcodepolodigitalmanaus.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import br.orgipdec.checkinqrcodepolodigitalmanaus.R

class SharedPreferences {

    companion object {
        private val VIEW_INFO = "infoview"
        private val SET_PALESTRA = "infopalestra"
        private val SET_ORGANIZADOR = "infoorganizador"
        private val SET_SALA = "infosala"
        private val SET_DIA = "infodia"

        fun checkInfo(context: Context): Boolean {
            return context
                .getSharedPreferences(
                    context.getString(R.string.info_file_key),
                    Context.MODE_PRIVATE
                )
                .getBoolean(VIEW_INFO, false)
        }

        @SuppressLint("ApplySharedPref")
        fun setInfo(context: Context, state: Boolean) {
            val sharedPref = context
                .getSharedPreferences(
                    context.getString(R.string.info_file_key),
                    Context.MODE_PRIVATE
                )
            with(sharedPref.edit()) {
                putBoolean(VIEW_INFO, state)
                commit()
            }
        }

        fun getPalestra(context: Context) : String? {
            return context
                .getSharedPreferences(
                    context.getString(R.string.palestra_file_key),
                    Context.MODE_PRIVATE
                )
                .getString(SET_PALESTRA, null)
        }

        @SuppressLint("ApplySharedPref")
        fun setPalestra(context: Context, state: String?) {
            val sharedPref = context
                .getSharedPreferences(
                    context.getString(R.string.palestra_file_key),
                    Context.MODE_PRIVATE
                )
            with(sharedPref.edit()) {
                putString(SET_PALESTRA, state)
                commit()
            }
        }
        fun getSala(context: Context) : String? {
            return context
                .getSharedPreferences(
                    context.getString(R.string.sala_file_key),
                    Context.MODE_PRIVATE
                )
                .getString(SET_SALA, null)
        }

        @SuppressLint("ApplySharedPref")
        fun setSala(context: Context, state: String?) {
            val sharedPref = context
                .getSharedPreferences(
                    context.getString(R.string.sala_file_key),
                    Context.MODE_PRIVATE
                )
            with(sharedPref.edit()) {
                putString(SET_SALA, state)
                commit()
            }
        }
        fun getOganizador(context: Context) : String? {
            return context
                .getSharedPreferences(
                    context.getString(R.string.organizador_file_key),
                    Context.MODE_PRIVATE
                )
                .getString(SET_ORGANIZADOR, null)
        }

        @SuppressLint("ApplySharedPref")
        fun setOrganizador(context: Context, state: String?) {
            val sharedPref = context
                .getSharedPreferences(
                    context.getString(R.string.organizador_file_key),
                    Context.MODE_PRIVATE
                )
            with(sharedPref.edit()) {
                putString(SET_ORGANIZADOR, state)
                commit()
            }
        }
        fun getDia(context: Context) : String? {
            return context
                .getSharedPreferences(
                    context.getString(R.string.dia_file_key),
                    Context.MODE_PRIVATE
                )
                .getString(SET_DIA, null)
        }

        @SuppressLint("ApplySharedPref")
        fun setDia(context: Context, state: String?) {
            val sharedPref = context
                .getSharedPreferences(
                    context.getString(R.string.dia_file_key),
                    Context.MODE_PRIVATE
                )
            with(sharedPref.edit()) {
                putString(SET_DIA, state)
                commit()
            }
        }
    }
}