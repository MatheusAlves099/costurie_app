package br.senai.sp.jandira.costurie_app.models_private

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_user")
data class User(
    @PrimaryKey val id: Long = 0,
    val nome: String = "",
    val token: String = "",
    val email: String = "",
    val senha: String = "",
    val foto: String = "",
    val idEndereco: Int = 0,
    val bairro: String = "",
    val cidade: String = "",
    val ufEstado: String = "",
    val idChat: String = ""
)
