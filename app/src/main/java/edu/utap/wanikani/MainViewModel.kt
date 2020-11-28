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
    private var wanikanisubject = MutableLiveData<WanikaniSubjects>()
    private val subject_ids=MutableLiveData<List<WanikaniAssignments>>()
    private val assignments_ids=MutableLiveData<List<Int>>()

    init {
       netRefresh()
    }

    fun netRefresh() {
        // XXX Write me.  This is where the network request is initiated.
        viewModelScope.launch( context = viewModelScope.coroutineContext + Dispatchers.IO) {
//            wanikanisubject.postValue(repo.fetchVocab(1))//need to figure out how to link the subject ID to this postvalue when we want to look something up...1=ground, 11=nine, for example.
            subject_ids.postValue(repo.fetchAssignments())
            assignments_ids.postValue(repo.fetchAssignments_ids())
//            assignments.postValue(repo.fetchAssignments())

        }
    }
    fun launch_subject_data(subject_id:Int){
        viewModelScope.launch( context = viewModelScope.coroutineContext + Dispatchers.IO)
        {
            var temp=subject_id
            var temp2=repo.fetchVocab(subject_id).value
            wanikanisubject.postValue(temp2)
        }
    }

    fun create_list_subject_data(subject_ids:MutableLiveData<List<WanikaniAssignments>>){
//        for x in subject_ids{
//
//        }
    }

    // XXX Another function is necessary
    fun observeWanikaniSubject() : MutableLiveData<WanikaniSubjects> {
        var temp=wanikanisubject
        return wanikanisubject
    }

    fun move_to_reviews(id:Int)
    {
//        try{
        viewModelScope.launch( context = viewModelScope.coroutineContext + Dispatchers.IO)
        {
            repo.start_assign(id)//this should be assignment id
        }
    }

    fun get_assignments(){
        viewModelScope.launch( context = viewModelScope.coroutineContext + Dispatchers.IO)
        {
            subject_ids.postValue(repo.fetchAssignments())
        }
    }


    fun observeSubjects():LiveData<List<WanikaniAssignments>>{
        var temp=subject_ids
        return subject_ids
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