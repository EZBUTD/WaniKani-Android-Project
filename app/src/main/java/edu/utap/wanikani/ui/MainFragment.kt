package edu.utap.wanikani.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import edu.utap.wanikani.MainViewModel
import edu.utap.wanikani.R
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
    // TODO: Rename and change types of parameters
    private var param1: String? = null

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }

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
                } else{
                    Log.d("XXXFrag", "subject is null?")

                }
            }
        )

        //val testButton = (activity as AppCompatActivity).findViewById<TextView>(R.id.testBut)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        startBut.setOnClickListener{
            val lessonFragment = Lesson.newInstance(0)
            parentFragmentManager.beginTransaction()
                .add(R.id.main_frame, lessonFragment)
                .addToBackStack("backHome")
                .commit()
        }

        reviewBut.setOnClickListener{
//            val reviewFragment = Review.newInstance(0)
            val reviewFragment=Review_quiz_mod.newInstance(0)
            parentFragmentManager.beginTransaction()
                    .add(R.id.main_frame, reviewFragment)
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
            viewModel.netSubjects()

            viewModel.observeAvailableLessonSubjects().observe(viewLifecycleOwner,
                    Observer {
                        if (it!= null){
                            for (i in it){
                                Log.d("XXXwessubjects", "subject char is: ${i.cha}")
                            }
                        }
                    })





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