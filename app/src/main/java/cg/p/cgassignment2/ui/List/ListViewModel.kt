package cg.p.cgassignment2.ui.List

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cg.p.cgassignment2.firebase.FBDatabaseManager

import cg.p.cgassignment2.models.ScoutGroupModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import timber.log.Timber

class ListViewModel : ViewModel() {

    private val groupList = MutableLiveData<List<ScoutGroupModels>>()

    val observableGroupList: LiveData<List<ScoutGroupModels>>
        get() = groupList

    var liveFirebaseUser = MutableLiveData<FirebaseUser>()

    init { load() }

    fun load() {
        try {
            FBDatabaseManager.findAll(liveFirebaseUser.value?.email!!, groupList)
            Timber.i("Report Load Success : ${groupList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Report Load Error : $e.message")
        }
    }

    fun delete(email: String, id: String) {
        try {
            FBDatabaseManager.delete(email,id)
            Timber.i("Report Delete Success")
        }
        catch (e: Exception) {
            Timber.i("Report Delete Error : $e.message")
        }
    }
}
