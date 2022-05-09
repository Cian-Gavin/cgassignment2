package cg.p.cgassignment2.ui.Add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cg.p.cgassignment2.firebase.FBDatabaseManager
import cg.p.cgassignment2.models.ScoutGroupModels
import com.google.firebase.auth.FirebaseUser

class AddViewModel : ViewModel() {

    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addgroup(firebaseUser: MutableLiveData<FirebaseUser> ,group: ScoutGroupModels) {
        status.value = try {
            //ScoutGroupManager.create(group)
            FBDatabaseManager.create(firebaseUser,group)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}