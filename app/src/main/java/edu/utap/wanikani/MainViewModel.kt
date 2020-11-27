package edu.utap.wanikani

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.utap.wanikani.api.Repository
import edu.utap.wanikani.api.WanikaniApi
import edu.utap.wanikani.api.WanikaniAssignments
import edu.utap.wanikani.api.WanikaniSubjects
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val wanikaniApi = WanikaniApi.create()
    private val repo = Repository(wanikaniApi)
    private val wanikanisubject = MutableLiveData<WanikaniSubjects>()
    private val assignments=MutableLiveData<List<WanikaniAssignments>>()
    private val assignments_ids=MutableLiveData<List<Int>>()

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
        }

    }

    fun get_assignments(){
        viewModelScope.launch( context = viewModelScope.coroutineContext + Dispatchers.IO)
        {
            assignments.postValue(repo.fetchAssignments())
        }
    }


    fun observeAssignments():LiveData<List<WanikaniAssignments>>{
        var temp=assignments
        return assignments
    }

    fun get_assignments_ids(){
        viewModelScope.launch( context = viewModelScope.coroutineContext + Dispatchers.IO)
        {
            assignments_ids.postValue(repo.fetchAssignments_ids())
        }
    }

    fun observeAssignment_ids():LiveData<List<Int>>{
        var temp=assignments_ids
        return assignments_ids
    }

}