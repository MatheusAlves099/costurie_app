package br.senai.sp.jandira.costurie_app.repository

import android.net.Uri
import android.util.Log
import br.senai.sp.jandira.costurie_app.model.TagResponseId
import br.senai.sp.jandira.costurie_app.model.UserGetIDResponse
import br.senai.sp.jandira.costurie_app.model.UserGetResponse
import br.senai.sp.jandira.costurie_app.model.UserResponse
import br.senai.sp.jandira.costurie_app.model.UserTagsResponse
import br.senai.sp.jandira.costurie_app.service.RetrofitFactory
import br.senai.sp.jandira.costurie_app.service.UserService
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Response

class UserRepository {
    private val apiService = RetrofitFactory.getInstance().create(UserService::class.java)

    suspend fun getUser(id: Int, token: String): Response<UserGetIDResponse> {
        return apiService.getUser(id, token)
    }

    suspend fun updateUser(
        id: Int,
        token: String,
        id_localizacao: Int,
        cidade: String,
        estado: String,
        bairro: String,
        nome: String,
        descricao: String,
        foto: String?,
        nome_de_usuario: String,
        tags: List<TagResponseId>
    ): Response<UserResponse> {
        val requestBody = JsonObject().apply {
            addProperty("id_usuario", id)
            addProperty("id_localizacao", id_localizacao)
            addProperty("cidade", cidade)
            addProperty("estado", estado)
            addProperty("bairro", bairro)
            addProperty("nome", nome)
            addProperty("descricao", descricao)
            if (foto != null) {
                // Converte o Uri em uma String
                addProperty("foto", foto.toString())
            }
            addProperty("nome_de_usuario", nome_de_usuario)
            val tagsArray = JsonArray()
            if (tags.isNotEmpty()) {
                for (tag in tags) {
                    val tagObject = JsonObject().apply {
                        addProperty("id_tag", tag.id)
                    }
                    tagsArray.add(tagObject)
                }
            }
            add("tags", tagsArray)
        }
        return apiService.updateUser(requestBody, token)
    }

    suspend fun updateUserNamePicDesc(
        id: Int,
        nome: String,
        descricao: String,
        foto: String?,
    ): Response<UserResponse> {
        val requestBody = JsonObject().apply {
            addProperty("id", id)
            addProperty("nome", nome)
            addProperty("descricao", descricao)
            if (foto != null) {
                // Converte o Uri em uma String
                addProperty("foto", foto.toString())
            }
        }
        return apiService.updateUserNamePicDesc(requestBody)
    }

    suspend fun updateLocation(
        id: Int,
        token: String,
        cidade: String,
        estado: String,
        bairro: String
    ): Response<UserResponse> {
        Log.i("location", "updateLocation: ${token}")
        Log.i("location", "updateLocation: ${id}")
        val requestBody = JsonObject().apply {
            addProperty("id_usuario", id)
            addProperty("cidade", cidade)
            addProperty("estado", estado)
            addProperty("bairro", bairro)
        }
        return apiService.updateLocation(requestBody, token)
    }

    suspend fun updateUserTags(
        id: Int,
        token: String,
        tags: MutableList<TagResponseId>
    ): Response<UserTagsResponse> {
        val requestBody = JsonObject().apply {
            addProperty("id_usuario", id)
            val tagsArray = JsonArray()
            if (tags != null) {
                for (tag in tags) {
                    val tagObject = JsonObject().apply {
                        addProperty("id", tag.id)
                    }
                    tagsArray.add(tagObject)
                }
            }
            add("tags", tagsArray)
        }
        return apiService.updateUserTags(requestBody, token)
    }

    suspend fun updateEmail(
        id_usuario: Int,
        token: String,
        email: String
    ): Response<JsonObject> {
        val requestBody = JsonObject().apply {
            addProperty("id_usuario", id_usuario)
            addProperty("email", email)
        }
        return apiService.updateEmail(requestBody, token)
    }

    suspend fun updatePassword(
        id_usuario: Int,
        token: String,
        senha: String
    ): Response<JsonObject> {
        val requestBody = JsonObject().apply {
            addProperty("id_usuario", id_usuario)
            addProperty("senha", senha)
        }
        return apiService.updatePassword(requestBody, token)
    }

}