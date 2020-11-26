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

    private val debug_characters : List<String> = listOf("一", "ハ")
    private val debug_meaning : List<String> = listOf("Ground", "Fins")
    private val debug_nameMnemonic :List<String> = listOf("This radical consists of a single, horizontal stroke. What's the biggest, single, horizontal stroke? That's the ground. Look at the ground, look at this radical, now look at the ground again. Kind of the same, right?", "Picture a fish. Now picture the fish a little worse, like a child's drawing of the fish. Now erase the fish's body and... you're left with two fins! Do you see these two fins? Yeah, you see them.")
    private val debug_examples :List<String> = listOf("Here is a glimpse of some of the kanji you will be learning that utilize Ground. Can you see where the radical fits in the kanji?", "Here is a glimpse of some of the kanji you will be learning that utilize Fins. Can you see where the radical fits in the kanji?")
    private var debug_idxPtr : Int =0
    private var debug_nameExamplePointer = "Name"

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
        nameMnemonicTV.text = "Name Mnemonic"
        textBlockTV.text = debug_nameMnemonic[0]

        nameTV.setOnClickListener{
            textBlockTV.text = debug_nameMnemonic[debug_idxPtr]

            debug_nameExamplePointer="Name"
        }

        examplesTV.setOnClickListener{
            openExamples()
        }
    }

    private fun openExamples() {
        textBlockTV.text = debug_examples[debug_idxPtr]
        nameMnemonicTV.text = "Kanji Examples"

        debug_nameExamplePointer="Examples"
    }

    private fun initLeftRightArrow(){
        leftArrowTV.setOnClickListener{
            debug_nameExamplePointer="Name"
            decrementIdx()
            nameMnemonicTV.text = "Name Mnemonic"
            charTV.text = debug_characters[debug_idxPtr]
            meaningTV.text = debug_meaning[debug_idxPtr]
            textBlockTV.text = debug_nameMnemonic[debug_idxPtr]

        }

        rightArrowTV.setOnClickListener{
            if(debug_nameExamplePointer == "Name") {
                debug_nameExamplePointer="Examples"
                openExamples()
            }
            else {
                debug_nameExamplePointer="Name"
                incrementIdx()
                textBlockTV.text = debug_nameMnemonic[debug_idxPtr]
                nameMnemonicTV.text = "Name Mnemonic"
            }
            charTV.text = debug_characters[debug_idxPtr]
            meaningTV.text = debug_meaning[debug_idxPtr]

        }
    }

    private fun decrementIdx() {
        if( debug_idxPtr == 0)
            debug_idxPtr = debug_characters.size-1
        else
            debug_idxPtr--
    }

    private fun incrementIdx() {
        if( debug_idxPtr == debug_characters.size-1)
            debug_idxPtr = 0
        else
            debug_idxPtr++
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