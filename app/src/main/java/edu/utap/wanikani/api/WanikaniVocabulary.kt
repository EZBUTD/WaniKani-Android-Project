package edu.utap.wanikani.api

import com.google.gson.annotations.SerializedName

data class WanikaniVocabulary  (
    //https://github.com/xiprox/WaniKani-for-Android/blob/master/WaniKani/src/tr/xip/wanikani/models/BaseItem.java

    @SerializedName("VOCABULARY")
    val vocabulary: String


)
