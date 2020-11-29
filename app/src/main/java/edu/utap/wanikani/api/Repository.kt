package edu.utap.wanikani.api

import androidx.lifecycle.MutableLiveData


class Repository(private val wanikaniApi: WanikaniApi) {

    suspend fun fetchVocab(id: Int) : MutableLiveData<WanikaniSubjects> {
//        var temp=MutableLiveData<WanikaniSubjects>()
//        temp= MutableLiveData(wanikaniApi.single_character(id).data)
        var api_response=wanikaniApi.single_character(id)
        var answer=api_response.data
        answer.subject_id= (api_response.id)
        return MutableLiveData(answer)

//        return wanikaniApi.single_character(id).data
    }

    private fun unpackPosts(response: WanikaniApi.ListingData):  List<WanikaniAssignments> {
        // XXX Write me.
        var postlist= mutableListOf<WanikaniAssignments>()
        for(i in response.data){
            postlist.add(i.data)
        }
        return postlist
    }

    suspend fun fetchAssignments() : List<WanikaniAssignments> {
        return unpackPosts(wanikaniApi.get_assignments())
    }

//    private fun unpackPosts_assignments(response: WanikaniApi.ListingData):  List<Int> {
    private fun unpackPosts_assignments(response: WanikaniApi.ListingData):  HashMap<Int,Int> {
        // XXX Write me.
        var postlist= HashMap<Int,Int>()
        for(i in response.data){
//            postlist.add(i.assignment_id)
            postlist[i.id]=i.data.sub_id
        }
        return postlist
    }

    suspend fun fetchAssignments_ids() : HashMap<Int,Int> {
        return unpackPosts_assignments(wanikaniApi.get_assignments())
    }




    suspend fun start_assign(id: Int){
        wanikaniApi.start_assignment(id)
    }
}