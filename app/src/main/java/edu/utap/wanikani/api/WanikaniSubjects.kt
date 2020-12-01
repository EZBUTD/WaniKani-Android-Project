package edu.utap.wanikani.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.json.JSONArray

data class WanikaniSubjects  (
    @SerializedName("characters")
    val cha : String,
    @SerializedName("meaning_mnemonic")
    val meaning_mnemonic : String,
    @SerializedName("meanings")
    val meanings : MutableList<Any>,
    @SerializedName("reading_mnemonic")
    val reading_mnemonic : String,
    @SerializedName("id")
    var subject_id : Int
)

data class WanikaniAssignments  (
    @SerializedName("subject_id")
    var sub_id:Int,
    @SerializedName("meaning_mnemonic")
    val meaning_mnemonic : String,
    @SerializedName("meanings")
    val meanings : MutableList<Any>,
    @SerializedName("reading_mnemonic")
    val reading_mnemonic : String
)

data class WanikaniUser (
    @SerializedName("username")
    var username:String
)
