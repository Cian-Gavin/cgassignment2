package cg.p.cgassignment2.ui.List

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cg.p.cgassignment2.models.ScoutGroupMangerManager
import cg.p.cgassignment2.models.ScoutGroupModels

class ListViewModel : ViewModel() {

    private val groupList = MutableLiveData<List<ScoutGroupModels>>()

    val observableGroupList: LiveData<List<ScoutGroupModels>>
        get() = groupList

    init {
        load()
    }

    fun load() {
        groupList.value = ScoutGroupMangerManager.findAll()
    }
}
