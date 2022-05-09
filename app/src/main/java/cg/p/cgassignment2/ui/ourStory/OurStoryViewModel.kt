package cg.p.cgassignment2.ui.ourStory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class OurStoryViewModel
{
    private val _text = MutableLiveData<String>().apply {
        value = ""
    }
    val text: LiveData<String> = _text
}