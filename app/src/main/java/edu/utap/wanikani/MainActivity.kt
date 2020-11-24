package edu.utap.wanikani

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import edu.utap.wanikani.ui.MainFragment

class MainActivity : AppCompatActivity() {

    private val viewModel // XXX need to initialize the viewmodel (from an activity)
            : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            // XXX Write me: add fragments to layout, swipeRefresh
            supportFragmentManager.beginTransaction()
                .replace(R.id.q1, MainFragment.newInstance("blah"))
                .commit()

            viewModel.netRefresh()
        }
    }
}