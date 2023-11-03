package br.senai.sp.jandira.costurie_app.viewModel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.senai.sp.jandira.costurie_app.model.TagResponse
import br.senai.sp.jandira.costurie_app.model.TagsResponse

class UserViewModel2: ViewModel() {
    var id_usuario: Int? = 0
    var nome: String = ""
    var descricao: String = ""
    var nome_de_usuario: String = ""
    var email: String = ""
    var foto: Uri? = null
    var id_localizacao: Int? = 0
    val estados: MutableLiveData<List<String>> = MutableLiveData()
    val cidades: MutableLiveData<List<String>> = MutableLiveData()
    val bairros: MutableLiveData<List<String>> = MutableLiveData()
    var tags: MutableList<TagResponse>? = mutableListOf()

    private val _profileEditSuccess = MutableLiveData<Boolean>()
    val profileEditSuccess: LiveData<Boolean>
        get() = _profileEditSuccess

    fun setProfileEditSuccess(success: Boolean) {
        _profileEditSuccess.value = success
    }
}