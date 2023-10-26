package br.senai.sp.jandira.costurie_app.repository

import br.senai.sp.jandira.costurie_app.model.AnexoResponse
import br.senai.sp.jandira.costurie_app.model.BaseResponsePublication
import br.senai.sp.jandira.costurie_app.model.BaseResponseTag
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

    suspend fun getAllPublications(): Response<BaseResponsePublication> {

        return apiService.getAllPublications()
    }

}