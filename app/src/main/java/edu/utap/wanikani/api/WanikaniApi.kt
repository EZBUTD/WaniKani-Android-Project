package edu.utap.wanikani.api

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call;
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET;
import retrofit2.http.Headers
import retrofit2.http.Path;
import retrofit2.http.Query

interface WanikaniApi {

    // username: EChenAP password: 123456AP Access Token: ffef2121-13e6-409a-bd8d-78437dc4338e
    //@GET("https://api.wanikani.com/v2/assignments.json")
    //suspend fun  getAssignments() : ListingResponse

    //@Headers("Authorization: Bearer ffef2121-13e6-409a-bd8d-78437dc4338e")
    //@GET("https://api.wanikani.com/v2/assignments.json")
    //suspend fun api_call(@Query("<param>") value: String) : WanikaniResponse

    @Headers("Authorization: Bearer ffef2121-13e6-409a-bd8d-78437dc4338e")
    @GET("v2/vocabulary.json")
    suspend fun api_call(@Query("level") value: String) : WanikaniResponse

    data class WanikaniResponse(val results: List<WanikaniVocabulary>)

    companion object {
        // Leave this as a simple, base URL.  That way, we can have many different
        // functions (above) that access different "paths" on this server
        // https://square.github.io/okhttp/4.x/okhttp/okhttp3/-http-url/
        var url = HttpUrl.Builder()
            .scheme("https")
            .host("api.wanikani.com")
            .build()

        // Public create function that ties together building the base
        // URL and the private create function that initializes Retrofit
        fun create(): WanikaniApi = create(url)
        private fun create(httpUrl: HttpUrl): WanikaniApi {
            val client = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    this.level = HttpLoggingInterceptor.Level.BASIC
                })
                .build()
            return Retrofit.Builder()
                .baseUrl(httpUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WanikaniApi::class.java)
        }
    }
    //Found the below from: https://github.com/xiprox/WaniKani-for-Android/blob/master/WaniKani/src/tr/xip/wanikani/client/WaniKaniService.java
    // (Converted from Java to Kotlin by android studios)
    /*
    @GET("{api_key}/user-information")
    fun getUser(@Path("api_key") api_key: String?): Call<Request<User?>?>?

    @GET("{api_key}/study-queue")
    fun getStudyQueue(@Path("api_key") api_key: String?): Call<Request<StudyQueue?>?>?

    @GET("{api_key}/level-progression")
    fun getLevelProgression(@Path("api_key") api_key: String?): Call<Request<LevelProgression?>?>?

    @GET("{api_key}/srs-distribution")
    fun getSRSDistribution(@Path("api_key") api_key: String?): Call<Request<SRSDistribution?>?>?

    @GET("{api_key}/recent-unlocks/{limit}")
    fun getRecentUnlocksList(
        @Path("api_key") api_key: String?,
        @Path("limit") limit: Int
    ): Call<Request<RecentUnlocksList?>?>?

    @GET("{api_key}/critical-items/{percentage}")
    fun getCriticalItemsList(
        @Path("api_key") api_key: String?,
        @Path("percentage") percentage: Int
    ): Call<Request<CriticalItemsList?>?>?

    @GET("{api_key}/radicals/{level}")
    fun getRadicalsList(
        @Path("api_key") api_key: String?,
        @Path("level") level: String?
    ): Call<Request<RadicalsList?>?>? // We use a string to handle the level argument as the API accepts comma-delimited level argument


    @GET("{api_key}/kanji/{level}")
    fun getKanjiList(
        @Path("api_key") api_key: String?,
        @Path("level") level: String?
    ): Call<Request<KanjiList?>?>? // We use a string to handle the level argument as the API accepts comma-delimited level argument


    @GET("{api_key}/vocabulary/{level}")
    fun getVocabularyList(
        @Path("api_key") api_key: String?,
        @Path("level") level: String?
    ): Call<Request<VocabularyList?>?>? // We use a string to handle the level argument as the API accepts comma-delimited level argument
*/

}