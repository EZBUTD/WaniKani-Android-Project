package edu.utap.wanikani.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import edu.utap.wanikani.MainViewModel
import edu.utap.wanikani.R
import kotlinx.android.synthetic.main.fragment_account_settings.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class AccountSettings : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun setUserName(myName :String){
        nameTV.text = myName
    }

    private fun setLevels(levels :String){
        lvlTV.text = levels
    }

    private fun setTimeRemaining(time :String){
        timeTV.text = time
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.netUser()
        viewModel.netPendingAssignments()

        viewModel.observeUsername().observe(viewLifecycleOwner,
            Observer {user->
                setUserName(user.username)
                setLevels(user.levels)
            })

        viewModel.observePendingAssignments().observe(viewLifecycleOwner,
            Observer {myAssignmentList->
                if(myAssignmentList!= null) {
                    Log.d("XXXlesson size:", "${myAssignmentList.size}")
                    if (myAssignmentList.size == 0) {
                        setTimeRemaining("No assignments pending. Please complete your current assignments to unlock new ones!")
                    } else {
                        var firstAssignmentTime :LocalDateTime = LocalDateTime.now()
                        val currentTime = LocalDateTime.now()
                        val formatter = DateTimeFormatter.ISO_INSTANT
                        val formattedCurrentTime = currentTime.format(formatter)

                        var ctr =0
                        for (assignment in myAssignmentList) {
                            val timeAvailable = LocalDateTime.parse(assignment.available_at, DateTimeFormatter.ISO_INSTANT)

                            if(ctr==0) {
                                firstAssignmentTime = timeAvailable
                            } else {
                                if (firstAssignmentTime.isAfter(timeAvailable)) {
                                    firstAssignmentTime = timeAvailable
                                }
                            }
                            ctr++
                            Log.d("XXXnextLessonTime", "next lesson available at: $timeAvailable current time is $formattedCurrentTime")
                        }
                        val timeUntilFirstAssignmentOpens = currentTime.toLocalTime().until(firstAssignmentTime.toLocalTime(), ChronoUnit.SECONDS)
                        assignment_timer.isCountDown = true
                        assignment_timer.base=timeUntilFirstAssignmentOpens
                        assignment_timer.start()
                    }
                } else {setTimeRemaining("No assignments pending. Please complete your current assignments to unlock new ones!")}
            })


        requireActivity().onBackPressedDispatcher.addCallback(this){
            parentFragmentManager.popBackStack()
        }
    }
    companion object {
        fun newInstance() : AccountSettings {
            val b = Bundle()
            val frag = AccountSettings()
            return frag
        }
    }
}