package edu.utap.wanikani.api

class Repository(private val wanikaniApi: WanikaniApi) {

    suspend fun fetchVocab(level : Int) : WanikaniSubjects? {
        return wanikaniApi.api_call(level).data
    }

    private fun unpackPosts(response:WanikaniApi.ListingResponse):  List<WanikaniAssignments> {
        // XXX Write me.
        var postlist= mutableListOf<WanikaniAssignments>()
        for(i in response.data.children){
            postlist.add(i.data)
        }
        var temp=postlist

        return postlist
    }

    suspend fun fetchAssignments() : List<WanikaniAssignments> {
        return unpackPosts(wanikaniApi.get_assignments())
    }

    suspend fun start_assign(id:Int){
        wanikaniApi.start_assignment(id)
    }
}