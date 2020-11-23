package edu.utap.wanikani.api

interface WanikaniApi {

    @GET("https://api.wanikani.com/v2/assignments.json")
    suspend fun  getAssignments() : ListingResponse
}