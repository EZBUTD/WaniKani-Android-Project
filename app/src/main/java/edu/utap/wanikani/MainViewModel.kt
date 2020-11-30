package edu.utap.wanikani

import android.util.Log
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
    private val subject_ids_for_review=MutableLiveData<List<WanikaniAssignments>>()
    private val assignments_ids=MutableLiveData<HashMap<Int,Int>>()
    private var subject_meanings_list = mutableListOf<WanikaniSubjects>()

    init {
       netRefresh()
    }

    fun netRefresh() {
        // XXX Write me.  This is where the network request is initiated.
        viewModelScope.launch( context = viewModelScope.coroutineContext + Dispatchers.IO) {
//            wanikanisubject.postValue(repo.fetchVocab(1))//need to figure out how to link the subject ID to this postvalue when we want to look something up...1=ground, 11=nine, for example.
            subject_ids.postValue(repo.fetchAssignments())
            subject_ids_for_review.postValue(repo.fetchAssignments_for_review())
            assignments_ids.postValue(repo.fetchAssignments_ids())
//            assignments.postValue(repo.fetchAssignments())

        }
    }
    fun launch_subject_data(subject_id:Int){
        viewModelScope.launch( context = viewModelScope.coroutineContext + Dispatchers.IO)
        {
            var temp=subject_id
            var temp2=repo.fetchVocab(subject_id).value
            Log.d("XXXsubjectdata", "launch subject data is $temp2")
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

    fun observeSubjects_for_review():LiveData<List<WanikaniAssignments>>{
        var temp=subject_ids_for_review
        return subject_ids_for_review
    }

    fun get_assignments_ids(){
        viewModelScope.launch( context = viewModelScope.coroutineContext + Dispatchers.IO)
        {
            assignments_ids.postValue(repo.fetchAssignments_ids())
        }
    }

    fun observeAssignment_ids():MutableLiveData<HashMap<Int,Int>>{
        var temp=assignments_ids
        return assignments_ids
    }

    fun store_for_lesson(data: MutableList<WanikaniSubjects>){
        subject_meanings_list=data
    }

    fun observe_quiz_data():MutableList<WanikaniSubjects>{
        return subject_meanings_list
    }

}