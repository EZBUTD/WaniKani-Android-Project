package edu.utap.wanikani.api

import android.text.SpannableString
import com.google.gson.GsonBuilder
import edu.utap.wanikani.MainActivity

class Repository(private val wanikaniApi: WanikaniApi) {

    suspend fun fetchVocab(level : Int) : WanikaniSubjects? {
        return wanikaniApi.api_call(level).data
    }

    private fun unpackPosts(response:WanikaniApi.ListingData):  List<WanikaniAssignments> {
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

    private fun unpackPosts_assignments(response:WanikaniApi.ListingData):  List<Int> {
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




    suspend fun start_assign(id:Int){
        wanikaniApi.start_assignment(id)
    }
}