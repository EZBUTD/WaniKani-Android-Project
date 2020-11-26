package edu.utap.wanikani.api

class Repository(private val wanikaniApi: WanikaniApi) {

    suspend fun fetchVocab(level : Int) : WanikaniSubjects? {
        return wanikaniApi.api_call(level).data
    }
}