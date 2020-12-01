package edu.utap.wanikani.api

import android.text.SpannableString
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import okhttp3.*
import okhttp3.internal.connection.ConnectInterceptor.intercept
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import retrofit2.http.Headers
import java.io.IOException
import java.lang.reflect.Type


interface WanikaniApi {

    // username: EChenAP password: 123456AP Access Token: ffef2121-13e6-409a-bd8d-78437dc4338e
    //@GET("https://api.wanikani.com/v2/assignments.json")
    //suspend fun  getAssignments() : ListingResponse

    @Headers("Authorization: Bearer ffef2121-13e6-409a-bd8d-78437dc4338e")
    @GET("subjects/?")
    suspend fun api_call(@Query("level") level: Int) : List<WanikaniResponse>

    @Headers("Authorization: Bearer ffef2121-13e6-409a-bd8d-78437dc4338e")
    @GET("user")
    suspend fun getUser() : WanikaniUserResponse

    @Headers("Authorization: Bearer ffef2121-13e6-409a-bd8d-78437dc4338e")
//    @GET("subjects/1?types=radical,kanji")
    @GET("subjects/{id}")
    suspend fun single_character(@Path("id") id: Int) : WanikaniResponse

    @Headers("Authorization: Bearer ffef2121-13e6-409a-bd8d-78437dc4338e")
    @PUT("assignments/{id}/start")//example of moving a lesson into reviews once local session is done.
    suspend fun start_assignment(@Path("id") id:Int)

    @Headers("Authorization: Bearer ffef2121-13e6-409a-bd8d-78437dc4338e")
    @GET("assignments?immediately_available_for_lessons") //this is to filter on available for lessons.
    suspend fun get_assignments_for_lesson(): ListingData

    @Headers("Authorization: Bearer ffef2121-13e6-409a-bd8d-78437dc4338e")
    @GET("assignments?immediately_available_for_review") //this is to filter on available for lessons.
    suspend fun get_assignments_for_review(): ListingData

    @Headers("Authorization: Bearer ffef2121-13e6-409a-bd8d-78437dc4338e")
    @GET("assignments") //Checks when the next available assignment is
    suspend fun get_assignments_available_after(@Query("available_after") time: String): ListingData

    @Headers("Authorization: Bearer ffef2121-13e6-409a-bd8d-78437dc4338e")
    @GET("subjects?") //this is to filter on available for lessons.
    suspend fun get_subjects(@Query("ids", encoded = true) ids: String): ListingData

    @Headers("Authorization: Bearer ffef2121-13e6-409a-bd8d-78437dc4338e")
    @GET("subjects?") //this is to filter on available for lessons.
    suspend fun get_subjects_from_ids(@Query("ids", encoded = true) ids: String): WanikaniSubjectsResponse


    class ListingData(
        val data: List<WaniKaniChildrenResponse>
    )
    data class WaniKaniChildrenResponse(val data:WanikaniAssignments, val id: Int)

    data class WanikaniSubjectsResponse(val data:List<WanikaniResponse>)

    data class WanikaniResponse(val data: WanikaniSubjects, val id:Int)

    data class WanikaniUserResponse(val data: WanikaniUser)

    class SpannableDeserializer : JsonDeserializer<SpannableString> {
        // @Throws(JsonParseException::class)
        override fun deserialize(
            json: JsonElement,
            typeOfT: Type,
            context: JsonDeserializationContext
        ): SpannableString {
            return SpannableString(json.asString)
        }
    }


    /*
    class ListingResponse(val data: ListingData)

    class ListingData(
            val children: List<RedditChildrenResponse>,
            val after: String?,
            val before: String?
    )
    data class RedditChildrenResponse(val data: RedditPost)
*/
//    companion object {
//        // Leave this as a simple, base URL.  That way, we can have many different
//        // functions (above) that access different "paths" on this server
//        // https://square.github.io/okhttp/4.x/okhttp/okhttp3/-http-url/
//        var url = HttpUrl.Builder()
//            .scheme("https")
//            .host("api.wanikani.com")
//            .build()
//
//        // Public create function that ties together building the base
//        // URL and the private create function that initializes Retrofit
//        fun create(): WanikaniApi = create(url)
//        private fun create(httpUrl: HttpUrl): WanikaniApi {
//            val client = OkHttpClient.Builder()
//                .addInterceptor(HttpLoggingInterceptor().apply {
//                    // Enable basic HTTP logging to help with debugging.
//                    //this.level = HttpLoggingInterceptor.Level.BASIC
//                    this.level = HttpLoggingInterceptor.Level.BODY
//                })
//                //.addInterceptor(BasicAuthInterceptor())       //Not sure why this doesn't work
//                .build()
//
//                return Retrofit.Builder()
//                //.baseUrl(httpUrl)
//                .client(client)
//                    .baseUrl("https://api.wanikani.com/v2/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//                .create(WanikaniApi::class.java)
//        }
//    }
    companion object {
        // Tell Gson to use our SpannableString deserializer
        private fun buildGsonConverterFactory(): GsonConverterFactory {
            val gsonBuilder = GsonBuilder().registerTypeAdapter(
                SpannableString::class.java, SpannableDeserializer()
            )
            return GsonConverterFactory.create(gsonBuilder.create())
        }
        // Keep the base URL simple
        //private const val BASE_URL = "https://www.reddit.com/"
        var httpurl = HttpUrl.Builder()
            .scheme("https")
            .host("api.wanikani.com")
            .build()
        fun create(): WanikaniApi = create(httpurl)
        private fun create(httpUrl: HttpUrl): WanikaniApi {
            val client = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    // Enable basic HTTP logging to help with debugging.
                    this.level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
            return Retrofit.Builder()
                .baseUrl("https://api.wanikani.com/v2/")
                .client(client)
                .addConverterFactory(buildGsonConverterFactory())
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