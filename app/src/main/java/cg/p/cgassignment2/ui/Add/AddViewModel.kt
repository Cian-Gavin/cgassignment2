package cg.p.cgassignment2.ui.Add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cg.p.cgassignment2.models.ScoutGroupMangerManager
import cg.p.cgassignment2.models.ScoutGroupModels

class AddViewModel : ViewModel() {

    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addgroup(group: ScoutGroupModels) {
        status.value = try {
            ScoutGroupMangerManager.create(group)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}