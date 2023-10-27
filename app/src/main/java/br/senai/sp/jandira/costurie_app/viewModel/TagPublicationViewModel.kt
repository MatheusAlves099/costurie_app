package br.senai.sp.jandira.costurie_app.viewModel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.senai.sp.jandira.costurie_app.model.TagGetPublicationResponse
import br.senai.sp.jandira.costurie_app.model.TagsResponse

class TagPublicationViewModel: ViewModel(){
    var tags: MutableList<TagGetPublicationResponse>? = mutableListOf()
}