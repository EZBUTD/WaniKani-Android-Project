package edu.utap.wanikani

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.utap.wanikani.api.Repository
import edu.utap.wanikani.api.WanikaniApi
import edu.utap.wanikani.api.WanikaniSubjects
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val wanikaniApi = WanikaniApi.create()
    private val repo = Repository(wanikaniApi)
    private val wanikanisubject = MutableLiveData<List<WanikaniSubjects>>()

    init {
       netRefresh()
    }

    fun netRefresh() {
        // XXX Write me.  This is where the network request is initiated.
        viewModelScope.launch( context = viewModelScope.coroutineContext + Dispatchers.IO) {
            wanikanisubject.postValue(repo.fetchVocab(1))
        }
    }
    // XXX Another function is necessary
    fun observeWanikaniSubject() : LiveData<List<WanikaniSubjects>> {
        return wanikanisubject
    }

}