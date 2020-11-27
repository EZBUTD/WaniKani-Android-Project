package edu.utap.wanikani

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.utap.wanikani.api.Repository
import edu.utap.wanikani.api.WanikaniApi
import edu.utap.wanikani.api.WanikaniAssignments
import edu.utap.wanikani.api.WanikaniSubjects
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val wanikaniApi = WanikaniApi.create()
    private val repo = Repository(wanikaniApi)
    private val wanikanisubject = MutableLiveData<WanikaniSubjects>()
    private val assignments=MutableLiveData<WanikaniAssignments>()

    init {
       netRefresh()
    }

    fun netRefresh() {
        // XXX Write me.  This is where the network request is initiated.
        viewModelScope.launch( context = viewModelScope.coroutineContext + Dispatchers.IO) {
            wanikanisubject.postValue(repo.fetchVocab(1))
//            assignments.postValue(repo.fetchAssignments())

        }
    }
    // XXX Another function is necessary
    fun observeWanikaniSubject() : LiveData<WanikaniSubjects> {
        return wanikanisubject
    }

    fun move_to_reviews(id:Int)
    {
//        try{
        viewModelScope.launch( context = viewModelScope.coroutineContext + Dispatchers.IO)
        {
            repo.start_assign(id)
//        }}
//        catch (e: Exception){
//
//        }

        }

    }


//    fun observeAssignments():LiveData<WanikaniAssignments>{
//        return assignments
//    }

}