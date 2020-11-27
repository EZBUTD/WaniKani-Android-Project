package edu.utap.wanikani.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import edu.utap.wanikani.MainViewModel
import edu.utap.wanikani.R
import kotlinx.android.synthetic.main.fragment_lesson.*

class Lesson : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private var currentIdx : Int =0
    //fragment tabs for the radicals section
    private var radicalTabsIdx : Int = 0
    private val radicalTabs : List<String> = listOf("Name", "Examples")
    private val radicalTabsTitles : List<String> = listOf("Name Mnemonic", "Kanji Examples")

    //Some hardcoded values just to see what my layout looks like.
    private val debug_characters : List<String> = listOf("一", "ハ")
    private val debug_meaning : List<String> = listOf("Ground", "Fins")
    private val debug_nameMnemonic :List<String> = listOf("This radical consists of a single, horizontal stroke. What's the biggest, single, horizontal stroke? That's the ground. Look at the ground, look at this radical, now look at the ground again. Kind of the same, right?", "Picture a fish. Now picture the fish a little worse, like a child's drawing of the fish. Now erase the fish's body and... you're left with two fins! Do you see these two fins? Yeah, you see them.")
    private val debug_examples :List<String> = listOf("Here is a glimpse of some of the kanji you will be learning that utilize Ground. Can you see where the radical fits in the kanji?", "Here is a glimpse of some of the kanji you will be learning that utilize Fins. Can you see where the radical fits in the kanji?")

    //I think you would need a lateinit var like this? I'm not sure
    // private lateinit var lessonCharacters : List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private fun initCharacters(){
        charTV.text = debug_characters[0]
    }

    private fun initMeaning(){
        meaningTV.text = debug_meaning[0]
    }

    private fun initNameMnemonic_Examples(){
        tabTitleTV.text = radicalTabsTitles[0]
//        textBlockTV.text = debug_nameMnemonic[0]
        textBlockTV.text=viewModel.observeWanikaniSubject().value?.meaning_mnemonic

        nameTV.setOnClickListener{
            radicalTabsIdx=0
            openTab(radicalTabsIdx)
        }

        examplesTV.setOnClickListener{
            radicalTabsIdx=1
            openTab(radicalTabsIdx)
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
            radicalTabsIdx=0
            openTab(radicalTabsIdx)

            charTV.text = debug_characters[currentIdx]
            meaningTV.text = debug_meaning[currentIdx]
        }

        rightArrowTV.setOnClickListener{
            if (radicalTabsIdx == radicalTabs.size-1) {
                incrementIdx()
                radicalTabsIdx=0
                openTab(radicalTabsIdx)
            } else {
                radicalTabsIdx++
                openTab(radicalTabsIdx)
            }

            charTV.text = debug_characters[currentIdx]
            meaningTV.text = debug_meaning[currentIdx]
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
        initCharacters()
        initMeaning()
        initNameMnemonic_Examples()
        initLeftRightArrow()
    }

    companion object {
        fun newInstance() : Lesson {
            return Lesson()
        }
    }
}