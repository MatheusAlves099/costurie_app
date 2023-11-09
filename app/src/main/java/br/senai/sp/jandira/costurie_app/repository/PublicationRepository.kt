package br.senai.sp.jandira.costurie_app.repository

import android.util.Log
import br.senai.sp.jandira.costurie_app.model.AnexoResponse
import br.senai.sp.jandira.costurie_app.model.BaseResponseGivePoint
import br.senai.sp.jandira.costurie_app.model.BaseResponseIdPublication
import br.senai.sp.jandira.costurie_app.model.BaseResponsePopularPublication
import br.senai.sp.jandira.costurie_app.model.BaseResponsePublication
import br.senai.sp.jandira.costurie_app.model.BaseResponseTag
import br.senai.sp.jandira.costurie_app.model.GivePointResponse
import br.senai.sp.jandira.costurie_app.model.PublicationGetResponse
import br.senai.sp.jandira.costurie_app.model.PublicationResponse
import br.senai.sp.jandira.costurie_app.model.TagResponseId
import br.senai.sp.jandira.costurie_app.service.PublicationService
import br.senai.sp.jandira.costurie_app.service.RetrofitFactory
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Response

class PublicationRepository {
    private val apiService = RetrofitFactory.getInstance().create(PublicationService::class.java)

    suspend fun createPublication(
        id_usuario: Int,
        token: String,
        titulo: String,
        descricao: String,
        tags: MutableList<TagResponseId>,
        anexos: List<AnexoResponse>
    ): Response<PublicationResponse> {
        val requestBody = JsonObject().apply {
            addProperty("id_usuario", id_usuario)
            addProperty("titulo", titulo)
            addProperty("descricao", descricao)
            val tagsArray = JsonArray()
            if (tags != null) {
                for (tag in tags) {
                    val tagObject = JsonObject().apply {
                        addProperty("id_tag", tag.id)
                    }
                    tagsArray.add(tagObject)
                }
            }
            add("tags", tagsArray)
            val anexosArray = JsonArray()
            if (anexos != null) {
                for (anexo in anexos) {
                    Log.i("anexos", "createPublication: ${anexos}")
                    Log.i("anexo", "createPublication: ${anexo}")
                    val anexoObject = JsonObject().apply {
                        addProperty("conteudo", anexo.conteudo)
                    }
                    anexosArray.add(anexoObject)
                }
            }
            add("anexos", anexosArray)
        }

        return apiService.createPublication(requestBody, token)
    }

    suspend fun getAllPublications(token: String): Response<BaseResponsePublication> {

        return apiService.getAllPublications(token)
    }

    suspend fun getPopularPublication(token: String): Response<BaseResponsePopularPublication> {

        return apiService.getPupularPublication(token)
    }


    suspend fun getPublicationById(token: String, id: Int): Response<BaseResponseIdPublication> {

        return apiService.getPublicationById(token, id)
    }

    suspend fun deletePublication(token: String, id: Int): Response<JsonObject> {
        return apiService.deletePublicationById(token, id)
    }

    suspend fun givePoint(
        token: String,
        id_usuario: Int,
        id_publicacao: Int
    ): Response<GivePointResponse> {
        val requestBody = JsonObject().apply {
            addProperty("id_usuario", id_usuario)
            addProperty("id_publicacao", id_publicacao)
        }

        return apiService.givePoint(token, requestBody)
    }

    suspend fun updatePublication(
        id_publicacao: Int,
        id_usuario: Int,
        token: String,
        titulo: String,
        descricao: String,
        tags: MutableList<TagResponseId>,
        anexos: List<AnexoResponse>
    ): Response<JsonObject> {
        val requestBody = JsonObject().apply {
            addProperty("id_publicacao", id_publicacao)
            addProperty("id_usuario", id_usuario)
            addProperty("titulo", titulo)
            addProperty("descricao", descricao)
            val tagsArray = JsonArray()
            if (tags != null) {
                for (tag in tags) {
                    val tagObject = JsonObject().apply {
                        addProperty("id_tag", tag.id)
                    }
                    tagsArray.add(tagObject)
                }
            }
            add("tags", tagsArray)
            val anexosArray = JsonArray()
            if (anexos != null) {
                for (anexo in anexos) {
                    val anexoObject = JsonObject().apply {
                        addProperty("conteudo", anexo.conteudo)
                    }
                    anexosArray.add(anexoObject)
                }
            }
            add("anexos", anexosArray)
        }

        return apiService.updatePublication(token, requestBody)
    }
}