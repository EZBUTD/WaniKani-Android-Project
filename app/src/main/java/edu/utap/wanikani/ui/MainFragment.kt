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
            val lessonFragment = Lesson.newInstance()
            parentFragmentManager.beginTransaction()
                .add(R.id.main_frame, lessonFragment)
                .addToBackStack("backHome")
                .commit()
        }

        testBut.setOnClickListener{
            viewModel.netRefresh()
        }
    }

/*
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // XXX Write me.  viewModel should observe something
        // When it gets what it is observing, it should index into it
        // You might find the requireArguments() function useful
        // You should "turn off" the swipe refresh spinner.  You might
        // find the requireActivity() function useful for this.

        viewModel.observeWanikaniSubject().observe(viewLifecycleOwner,
            Observer {
                if (it != null) {
                    val subject = it[0].characters
                    Log.d("XXXFrag", "My subject character is ${subject}")
                } else{
                    Log.d("XXXFrag", "subject is null")

                }
            }
        )

        testBut.setOnClickListener{
            viewModel.netRefresh()
        }

    }
*/
    /*
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

     */
}