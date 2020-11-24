package edu.utap.wanikani.api

class Repository(private val wanikaniApi: WanikaniApi) {

    suspend fun fetchVocab(level : String) : List<WanikaniVocabulary>? {
        return wanikaniApi.api_call(level).results
    }
}