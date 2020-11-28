package edu.utap.wanikani.api

import androidx.lifecycle.MutableLiveData


class Repository(private val wanikaniApi: WanikaniApi) {

    suspend fun fetchVocab(id: Int) : MutableLiveData<WanikaniSubjects> {
        var temp=MutableLiveData<WanikaniSubjects>()
        temp= MutableLiveData(wanikaniApi.single_character(id).data)
        return temp

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

    private fun unpackPosts_assignments(response: WanikaniApi.ListingData):  List<Int> {
        // XXX Write me.
        var postlist= mutableListOf<Int>()
        for(i in response.data){
            postlist.add(i.id)
        }
        return postlist
    }

    suspend fun fetchAssignments_ids() : List<Int> {
        return unpackPosts_assignments(wanikaniApi.get_assignments())
    }




    suspend fun start_assign(id: Int){
        wanikaniApi.start_assignment(id)
    }
}