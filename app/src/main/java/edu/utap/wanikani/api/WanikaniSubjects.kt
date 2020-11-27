package edu.utap.wanikani.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.json.JSONArray

data class WanikaniSubjects  (
    //OLD API:
    //https://github.com/baerrach/wanikani_exporter
    //https://github.com/baerrach/wanikani_exporter/blob/master/vocabulary.json
    //https://github.com/xiprox/WaniKani-for-Android/blob/master/WaniKani/src/tr/xip/wanikani/models/BaseItem.java
    // NEW API:
    //https://github.com/mrothberg/Kakumei-for-WaniKani/blob/master/WaniKani/src/main/java/com/mrothberg/kakumei/client/WaniKaniApiV2.java
    //https://github.com/mrothberg/Kakumei-for-WaniKani/blob/master/WaniKani/src/main/java/com/mrothberg/kakumei/client/WaniKaniServiceV2.java
    // [{"character":"二","kana":"に","meaning":"two","level":1},{"character":"入る","kana":"はいる","meaning":"to enter","level":1},{"character":"八","kana":"はち","meaning":"eight","level":1}...]

    //curl "https://api.wanikani.com/v2/subjects?types=radical,kanji"  -H "Authorization: Bearer ffef2121-13e6-409a-bd8d-78437dc4338e"
//,{"id":1000,"object":"kanji","url":"https://api.wanikani.com/v2/subjects/1000","data_updated_at":"2020-05-27T16:43:48.760990Z","data":{"created_at":"2012-10-30T19:49:28.123286Z","level":17,"slug":"σà╡","hidden_at":null,"document_url":"https://www.wanikani.com/kanji/%E5%85%B5","characters":"σà╡","meanings":[{"meaning":"Soldier","primary":true,"accepted_answer":true}],"auxiliary_meanings":[],"readings":[{"type":"onyomi","primary":true,"reading":"πü╕πüä","accepted_answer":true},{"type":"onyomi","primary":true,"reading":"πü▓πéçπüå","accepted_answer":true}],"component_subject_ids":[115,1,2],"amalgamation_subject_ids":[3960,3961,3962,3963,4347,5211,5872,5990,6485,7194,7546,8132,8183],"visually_similar_subject_ids":[1855,1888,1376],"meaning_mnemonic":"If you set a fish on the \u003cradical\u003eground\u003c/radical\u003e and chop its \u003cradical\u003efins\u003c/radical\u003e off with an \u003cradical\u003eaxe\u003c/radical\u003e, youΓÇÖre probably a \u003ckanji\u003esoldier\u003c/kanji\u003e. Only soldiers have access to axes, and you like to eat fish, but first you have to set it on the ground to chop its fins off.","meaning_hint":"Imagine trying to carve a fish with an axe. Probably not an easy thing to do. If youΓÇÖre a trained axe soldier, youΓÇÖd probably have an easier time with it. Picture yourself chopping off fins as an expert axe soldier.","reading_mnemonic":"Uh-oh, it looks like this axe \u003ckanji\u003esoldier\u003c/kanji\u003e was so excited about chopping the fins off his fish that he didn't think about how he was going to cook them. The only thing nearby to light on fire is a bale of \u003creading\u003ehay\u003c/reading\u003e (\u003cja\u003eπü╕πüä\u003c/ja\u003e). Well, it's not the best thing, but at least it will get these fish cooked.","reading_hint":"Imagine this soldier lighting a bale of hay on fire to cook his fish. The hay burns really fast, but it should be enough.","lesson_position":15,"spaced_repetition_system_id":1}}

        @SerializedName("characters")
        val cha : String,
        /*
        @SerializedName("id")
        val id : Int

         */

//    @SerializedName("characters")
//    val characters : String,
//
    @SerializedName("meaning_mnemonic")
    val meaning_mnemonic : String,

    @SerializedName("reading_mnemonic")
    val reading_mnemonic : String



)

data class WanikaniAssignments  (
//        @SerializedName("id") //this is one layer up
//        val id:Int,
        @SerializedName("subject_id")
        val sub_id:Int
)
