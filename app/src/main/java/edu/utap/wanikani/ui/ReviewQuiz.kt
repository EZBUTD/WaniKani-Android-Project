package edu.utap.wanikani.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import edu.utap.wanikani.MainViewModel
import edu.utap.wanikani.R
import kotlinx.android.synthetic.main.fragment_review_quiz.*

class ReviewQuiz : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    //Use myTypeId to tell if we want to display stuff for 0: radical, 1: kanji, 2: vocabulary
    private var myTypeId : Int =0

    private var currentIdx : Int =0
    private var tries : Int = 0
    //fragment tabs for the radicals section

    //Some hardcoded values just to see what my layout looks like.
    private val debug_characters : List<String> = listOf("一", "ハ")
    private val debug_answers : List<String> = listOf("Ground", "Fins")

    //private lateinit var questionDone : MutableList<Boolean>
    private var questionDone : MutableList<Boolean> = arrayListOf()

    private fun initCharacters(){
        charTV.text = debug_characters[0]
    }

    private fun initTitle(){
        nameTypeTV.text = "Radical Name"
    }

    private fun initAnswerCheck(){
        answerArrow.setOnClickListener{
            checkAnswer()
        }
    }

    private fun checkAnswer() {
        if (responseET.text.toString().toLowerCase() == debug_answers[currentIdx].toLowerCase()) {
            answerLay.setBackgroundColor(Color.GREEN)
            responseET.setBackgroundColor(Color.GREEN)
            questionDone[currentIdx] = true
            //Thread.sleep(2000)
            Log.d("XXXcheckanswer", "correct answer")

            if (lessonFinished()){
                parentFragmentManager.popBackStack()
                parentFragmentManager.popBackStack()
            } else
                nextQuestion()

        } else {
            if (tries == 1) {
                nextQuestion()
            } else {
                answerLay.setBackgroundColor(Color.RED)
                responseET.setBackgroundColor(Color.RED)
                //Thread.sleep(2000)
                tries++
            }
        }
    }

    private fun nextQuestion() {
        tries = 0

        var nextIdx = questionDone.subList(currentIdx+1, questionDone.size).indexOf(false)
        Log.d("XXXnextQuestion0", "$nextIdx is the next idx")
        if(nextIdx == -1) {
            nextIdx = questionDone.indexOf(false)
        } else {
            //Accounts for the offset introduced by taking the sublist
            nextIdx+= currentIdx+1
        }

        Log.d("XXXnextQuestion1", "$nextIdx is the next idx")

        currentIdx = nextIdx
        responseET.setBackgroundColor(Color.WHITE)
        answerLay.setBackgroundColor(Color.WHITE)
        charTV.text = debug_characters[currentIdx]
        responseET.text.clear()
    }

    private fun lessonFinished() :Boolean {
        return !questionDone.contains(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_review_quiz, container, false)

        requireActivity().onBackPressedDispatcher.addCallback(this){
            parentFragmentManager.popBackStack()
        }


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (this.arguments != null) {
            myTypeId = this.requireArguments().getInt(Lesson.typeIdKey)
        }
        Log.d("XXXtypeid", "$myTypeId")
        for (i in debug_answers.indices){
            //questionDone[i] = false
            questionDone.add(false)
            Log.d("XXXquizDone", "$i is set to false")
        }
        initCharacters()
        initTitle()
        initAnswerCheck()
    }

    companion object {
        const val typeIdKey = "typeIdKey"
        fun newInstance(typeId: Int) : ReviewQuiz {
            val b = Bundle()
            b.putInt(typeIdKey, typeId)
            val frag = ReviewQuiz()
            frag.arguments = b
            return frag
        }
    }
}