package cg.p.cgassignment2.ui.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cg.p.cgassignment2.firebase.FBDatabaseManager
import cg.p.cgassignment2.models.ScoutGroupModels
import timber.log.Timber
import java.lang.Exception

class GroupInfoViewModel : ViewModel()
{
    private val addedGroups = MutableLiveData<ScoutGroupModels>()

    var observableGroups: LiveData<ScoutGroupModels>
        get() = addedGroups
        set(value) {addedGroups.value = value.value}


    fun getGroup(email:String, id: String) {
        try {
            FBDatabaseManager.findById(email, id, addedGroups)
            Timber.i("Detail GetGroup() Success : ${addedGroups.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Detail GetGroup() Error : $e.message")
        }
    }

    fun updateGroup(email:String, id: String,donation: ScoutGroupModels) {
        try {
            FBDatabaseManager.update(email, id, donation)
            Timber.i("Detail update() Success : $donation")
        }
        catch (e: Exception) {
            Timber.i("Detail update() Error : $e.message")
        }
    }

}