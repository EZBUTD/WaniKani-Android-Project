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
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import edu.utap.wanikani.MainViewModel
import edu.utap.wanikani.R
import edu.utap.wanikani.api.WanikaniSubjects
import kotlinx.android.synthetic.main.fragment_review_quiz.*

class ReviewQuiz : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    //Is review tells whether to use the passed down subjects from lesson or fetch from the net
    private var isReview : Int =0

    private var currentIdx : Int =0
    private var tries : Int = 0
    //fragment tabs for the radicals section

    //Some hardcoded values just to see what my layout looks like.
//    private val debug_characters : List<String> = listOf("一", "ハ")
//    private val debug_answers : List<String> = listOf("Ground", "Fins")
    private var quiz_data= mutableListOf<WanikaniSubjects>()
    private var characters= mutableListOf<String>()
    private var answers= mutableListOf<String>()
    private var assignments_ids= HashMap<Int,Int>()

    private var questionDone : MutableList<Boolean> = arrayListOf()

    private fun initCharacters(){
        charTV.text=characters[0]

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
//        if (responseET.text.toString().toLowerCase() == debug_answers[currentIdx].toLowerCase()) {
        if (responseET.text.toString().toLowerCase().trim() == answers[currentIdx].toLowerCase()) {
            answerLay.setBackgroundColor(Color.GREEN)
            responseET.setBackgroundColor(Color.GREEN)
            questionDone[currentIdx] = true
            //Thread.sleep(2000)
            Log.d("XXXcheckanswer", "correct answer")

            if (lessonFinished()){
                //submit put requests to mark items as succesfully completed and move into review queue
                for(item in quiz_data){
                    var temp2=assignments_ids
                    var assignment_id_key=assignments_ids.filterValues { it==item.subject_id }.keys.iterator().next()
                    //check if key actually is right
                    var temp=assignment_id_key
                    viewModel.move_to_reviews(assignment_id_key)
                }
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
//        charTV.text = debug_characters[currentIdx]
        charTV.text=characters[currentIdx]
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
            //Use isReview to tell whether this is coming from a lesson frag and just needs to get the subjects from the lesson
            // or if this is from home frag and we need to fetch data across the network
            isReview = this.requireArguments().getInt(isReviewKey)

            //Use previous lesson's subjects
            if (isReview==1) {
                quiz_data = viewModel.get_quiz_data()
                for (i in quiz_data) {
                    answers.add(i.meanings[0].toString().split(",")[0].removePrefix("{meaning="))
                    characters.add(i.cha)
                }
                assignments_ids= viewModel.observeAssignment_ids().value!!

                for (i in answers.indices){
                    questionDone.add(false)
                    Log.d("XXXquizDone", "$i is set to false")
                }
                initCharacters()
            }
            //fetch the network for a review
            else {
                viewModel.netSubjectsReview()

                viewModel.observeAvailableReviewSubjects().observe(viewLifecycleOwner,
                        Observer {
                            if (it!= null){
                                for (i in it){
                                    //Log.d("XXXwessubjects", "subject char is: ${i.cha}")
                                    characters.add(i.cha)
                                    answers.add(i.meanings[0].toString().split(",")[0].removePrefix("{meaning="))

                                    questionDone.add(false)
                                }
                                initCharacters()
                            }
                        })

            }
        }

        initTitle()
        initAnswerCheck()
    }

    companion object {
        const val isReviewKey = "isReviewKey"
        fun newInstance(isReview: Int) : ReviewQuiz {
            val b = Bundle()
            b.putInt(isReviewKey, isReview)
            val frag = ReviewQuiz()
            frag.arguments = b
            return frag
        }
    }
}