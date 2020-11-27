package edu.utap.wanikani.api

class Repository(private val wanikaniApi: WanikaniApi) {

    suspend fun fetchVocab(level : Int) : WanikaniSubjects? {
        return wanikaniApi.api_call(level).data
    }
    suspend fun fetchAssignments() : List<WanikaniAssignments> {
        return wanikaniApi.assignment_call()
    }

    suspend fun start_assign(id:Int){
        wanikaniApi.start_assignment(id)
    }
}