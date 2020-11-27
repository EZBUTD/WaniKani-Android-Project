package edu.utap.wanikani.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.replace
import edu.utap.wanikani.MainViewModel
import edu.utap.wanikani.R
import kotlinx.android.synthetic.main.fragment_lesson.*

class Lesson : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    //Use myTypeId to tell if we want to display stuff for 0: radical, 1: kanji, 2: vocabulary
    private var myTypeId : Int =0

    private var currentIdx : Int =0
    //fragment tabs for the radicals section
    private var tabCount : Int = 0
    private var currentTabIdx : Int = 0
    private val radicalTabsTitles : List<String> = listOf("Name Mnemonic", "Kanji Examples")

    //Some hardcoded values just to see what my layout looks like.
    private val debug_characters : List<String> = listOf("一", "ハ")
    private val debug_meaning : List<String> = listOf("Ground", "Fins")
    private val debug_nameMnemonic :List<String> = listOf("This radical consists of a single, horizontal stroke. What's the biggest, single, horizontal stroke? That's the ground. Look at the ground, look at this radical, now look at the ground again. Kind of the same, right?", "Picture a fish. Now picture the fish a little worse, like a child's drawing of the fish. Now erase the fish's body and... you're left with two fins! Do you see these two fins? Yeah, you see them.")
    private val debug_examples :List<String> = listOf("Here is a glimpse of some of the kanji you will be learning that utilize Ground. Can you see where the radical fits in the kanji?", "Here is a glimpse of some of the kanji you will be learning that utilize Fins. Can you see where the radical fits in the kanji?")

    //I think you would need a lateinit var like this? I'm not sure
    // private lateinit var lessonCharacters : List<String>

    //private lateinit var lessonDone : MutableList<Boolean>          //Use this to check that User has gone through each tab in the lesson
    private var lessonDone : MutableList<Boolean> = arrayListOf()          //Use this to check that User has gone through each tab in the lesson

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private fun initCharacters(){
        charTV.text = debug_characters[0]
    }

    private fun initMeaning(){
        meaningTV.text = debug_meaning[0]
    }

    private fun initTabs(){
        kanjiTab1TV.visibility = View.INVISIBLE
        kanjiTab2TV.visibility = View.INVISIBLE
        kanjiTab3TV.visibility = View.INVISIBLE
        kanjiTab4TV.visibility = View.INVISIBLE

        tabTitleTV.text = radicalTabsTitles[0]
//        textBlockTV.text = debug_nameMnemonic[0]
        textBlockTV.text=viewModel.observeWanikaniSubject().value?.meaning_mnemonic

        radTab1TV.setOnClickListener{
            currentTabIdx=0
            openTab(currentTabIdx)
        }

        radTab2TV.setOnClickListener{
            currentTabIdx=1
            openTab(currentTabIdx)
        }
    }

    private fun initQuiz(){
        quizBut.isClickable=false
        quizBut.isEnabled=false

        quizBut.setOnClickListener{
            val quizFragment = ReviewQuiz.newInstance(myTypeId)
            parentFragmentManager.beginTransaction()
                    .replace(R.id.main_frame, quizFragment)
                    .addToBackStack("backHome")
                    .commit()
        }
    }
    
    private fun checkIfLessonDone() : Boolean {
        if ( lessonDone.contains(false))
            return false
        else {
            //XXX probably need to use the API to return some call back to the servers here
            quizBut.isClickable=true
            quizBut.isEnabled=true
            quizBut.setBackgroundColor(Color.GREEN)
            Toast.makeText(context, "Congratulations, you have finished the lesson and can now start the quiz", Toast.LENGTH_SHORT).show()
            return true
        }
    }

    private fun openTab(tabIdx: Int) {
        tabTitleTV.text = radicalTabsTitles[tabIdx]

        if (tabIdx == 0){
            textBlockTV.text = debug_nameMnemonic[currentIdx]
        } else {
            textBlockTV.text = debug_examples[currentIdx]
        }
    }

    private fun initLeftRightArrow(){
        leftArrowTV.setOnClickListener{
            decrementIdx()
            currentTabIdx=0
            openTab(currentTabIdx)

            charTV.text = debug_characters[currentIdx]
            meaningTV.text = debug_meaning[currentIdx]
            lessonDone[currentIdx*tabCount]=true
        }

        rightArrowTV.setOnClickListener{
            if (currentTabIdx == radicalTabsTitles.size-1) {
                incrementIdx()
                currentTabIdx=0
                openTab(currentTabIdx)
            } else {
                currentTabIdx++
                openTab(currentTabIdx)
            }

            charTV.text = debug_characters[currentIdx]
            meaningTV.text = debug_meaning[currentIdx]
            val lessonDoneIdx = currentIdx*tabCount+currentTabIdx
            lessonDone[lessonDoneIdx]=true
            Log.d("XXXlessonDone", "$lessonDoneIdx is set to true")
            checkIfLessonDone()
        }
    }

    private fun decrementIdx() {
        if( currentIdx == 0)
            currentIdx = debug_characters.size-1
        else
            currentIdx--
    }

    private fun incrementIdx() {
        if( currentIdx == debug_characters.size-1)
            currentIdx = 0
        else
            currentIdx++
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_lesson, container, false)

        requireActivity().onBackPressedDispatcher.addCallback(this){
            parentFragmentManager.popBackStack()
        }
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (this.arguments != null) {
            myTypeId = this.requireArguments().getInt(typeIdKey)
        }
        // This tells us how many tabs there are based on whether this lesson is type: Radicals, Kanji, Vocabulary
        when (myTypeId){
            0->tabCount=2
            1->tabCount=4
            2->tabCount=3
            else->tabCount=0
        }

        //I used a 2d array that's been flattened to 1d
        for (i in 0 until debug_characters.size * tabCount) {
            lessonDone.add(false)
            Log.d("XXXlessonDone", "$i is set to false")
        }
        lessonDone[0] = true

        Log.d("XXXtypeid", "$myTypeId")
        initCharacters()
        initMeaning()
        initTabs()
        initQuiz()
        initLeftRightArrow()
    }

    companion object {
        const val typeIdKey = "typeIdKey"
        fun newInstance(typeId: Int) : Lesson {
            val b = Bundle()
            b.putInt(typeIdKey, typeId)
            val frag = Lesson()
            frag.arguments = b
            return frag
        }
    }
}