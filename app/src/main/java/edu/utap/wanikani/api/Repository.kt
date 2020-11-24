package edu.utap.wanikani.api

class Repository(private val wanikaniApi: WanikaniApi) {

    suspend fun fetchVocab(level : Int) : List<WanikaniSubjects>? {
        return wanikaniApi.api_call(level).results
    }
}