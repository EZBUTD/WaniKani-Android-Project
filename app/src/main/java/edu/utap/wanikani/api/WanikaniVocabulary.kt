package edu.utap.wanikani.api

import com.google.gson.annotations.SerializedName

data class WanikaniVocabulary  (
    //https://github.com/baerrach/wanikani_exporter
    //https://github.com/baerrach/wanikani_exporter/blob/master/vocabulary.json
    //https://github.com/xiprox/WaniKani-for-Android/blob/master/WaniKani/src/tr/xip/wanikani/models/BaseItem.java
    // [{"character":"二","kana":"に","meaning":"two","level":1},{"character":"入る","kana":"はいる","meaning":"to enter","level":1},{"character":"八","kana":"はち","meaning":"eight","level":1}...]

    @SerializedName("character")
    val character : String,

    @SerializedName("meaning")
    val meaning : String

)
