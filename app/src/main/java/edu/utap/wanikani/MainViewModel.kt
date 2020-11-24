package edu.utap.wanikani

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.utap.wanikani.api.Repository
import edu.utap.wanikani.api.WanikaniApi
import edu.utap.wanikani.api.WanikaniVocabulary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val wanikaniApi = WanikaniApi.create()
    private val repo = Repository(wanikaniApi)
    private val wanikanivocab = MutableLiveData<List<WanikaniVocabulary>>()

    init {
       netRefresh()
    }

    fun netRefresh() {
        // XXX Write me.  This is where the network request is initiated.
        viewModelScope.launch( context = viewModelScope.coroutineContext + Dispatchers.IO) {
            wanikanivocab.postValue(repo.fetchVocab("1"))
        }
    }
    // XXX Another function is necessary
    fun observeWanikaniVocab() : LiveData<List<WanikaniVocabulary>> {
        return wanikanivocab
    }

}