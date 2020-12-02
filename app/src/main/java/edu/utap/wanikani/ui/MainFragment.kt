package edu.utap.wanikani.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import edu.utap.wanikani.MainViewModel
import edu.utap.wanikani.R
import edu.utap.wanikani.api.WanikaniApi
import kotlinx.android.synthetic.main.main_fragment.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment(R.layout.main_fragment) {
    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    private val viewModel: MainViewModel by activityViewModels()

    private fun setUserName(myName: String){
        nameTV.text = myName
    }

    private fun setReviewsTV(n_reviews: Int){
        myReviewTV.text = n_reviews.toString() + " reviews available"
    }

    private fun setLessonsTV(n_lessons: Int){
        myLessonsTV.text = n_lessons.toString() + " lessons available"
    }

    private fun initReviewObserver() {
        viewModel.observeAvailableReviewSubjectsId().observe(viewLifecycleOwner,
            Observer { myReviewList ->
                if (myReviewList != null) {
                    setReviewsTV(myReviewList.size)
                } else {
                    setReviewsTV(0)
                }
            })
    }

    private fun initLessonObserver() {
        viewModel.observeAvailableLessonSubjectsId().observe(viewLifecycleOwner,
            Observer { myLessonList ->
                if (myLessonList != null) {
                    setLessonsTV(myLessonList.size)
                    Log.d("XXXlesson size:", "$myLessonList")
                } else {
                    setLessonsTV(0)
                }
            })
    }

    private fun initNameObserver() {
        viewModel.observeUsername().observe(viewLifecycleOwner,
            Observer { user ->
                setUserName(user.username)
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.main_fragment, container, false)

        viewModel.observeWanikaniSubject().observe(viewLifecycleOwner,
            Observer {
                if (it != null) {
                    val subject = it.cha
                    Log.d("XXXFrag", "My subject character is ${subject}")
                } else {
                    Log.d("XXXFrag", "subject is null?")

                }
            }
        )

        //val testButton = (activity as AppCompatActivity).findViewById<TextView>(R.id.testBut)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initNameObserver()
        initReviewObserver()
        initLessonObserver()

        startBut.setOnClickListener{
            val lessonFragment = Lesson.newInstance(0)
            parentFragmentManager.beginTransaction()
                .add(R.id.main_frame, lessonFragment)
                .addToBackStack("backHome")
                .commit()
        }

        reviewBut.setOnClickListener{
//            val reviewFragment = Review.newInstance(0)
            //Use isQuiz=0 to indicate this is not a quiz
            val reviewFragment=ReviewQuiz.newInstance(0)
            parentFragmentManager.beginTransaction()
                    .add(R.id.main_frame, reviewFragment)
                    .addToBackStack("backHome")
                    .commit()
        }

        accountBut.setOnClickListener{
//            val reviewFragment = Review.newInstance(0)
            //Use isQuiz=0 to indicate this is not a quiz
            val accountFragment=AccountSettings.newInstance()
            parentFragmentManager.beginTransaction()
                .add(R.id.main_frame, accountFragment)
                .addToBackStack("backHome")
                .commit()
        }

        testBut.setOnClickListener{
//            viewModel.netRefresh()
//            viewModel.move_to_reviews(206307847)
//            viewModel.get_assignments_ids()
//            myprogresstextTV.setText(viewModel.observeAssignment_ids().value?.get(0).toString())
//            myprogresstextTV.setText(viewModel.observeAssignment_ids().value?.get(1).toString())
            //.removeSurrounding("[", "]")
//            viewModel.netSubjectsLesson()
//
//            viewModel.observeAvailableLessonSubjects().observe(viewLifecycleOwner,
//                    Observer {
//                        if (it!= null){
//                            for (i in it){
//                                Log.d("XXXwessubjects", "subject char is: ${i.cha}")
//                            }
//                        }
//                    })
            class NestedJSON_single internal constructor(val assignment_id: String, val incorrect_meaning_answers: String, val incorrect_reading_answers: String)
            class NestedJSON internal constructor(val review: NestedJSON_single)
            viewModel.create_review(WanikaniApi.NestedJSON(WanikaniApi.NestedJSON_single(assignment_id = "206307845",incorrect_meaning_answers = "0",incorrect_reading_answers = "0")))
            /*
            val data= "1,2,3"
            val data2 = listOf(1,2,3)
            val data3 = data2.joinToString(separator = ",")
            val new_data = arrayListOf(1.toString(), 2.toString(), 3.toString())
            var new_data2 = new_data.joinToString(separator = ",")
            Log.d("XXXcsvtest", "${new_data2}")
            Log.d("XXXcsvtest", "${data3}")
            viewModel.get_subject_data(new_data2)
            viewModel.observeSubject_data()

             */
        }
    }


}