package cg.p.cgassignment2.ui.Authentication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import cg.p.cgassignment2.firebase.FBAuthManager
import com.google.firebase.auth.FirebaseUser


class LoggedInViewModel(app: Application) : AndroidViewModel(app)
{
    var firebaseAuthManager : FBAuthManager = FBAuthManager(app)
    var liveFirebaseUser : MutableLiveData<FirebaseUser> = firebaseAuthManager.liveFirebaseUser
    var loggedOut : MutableLiveData<Boolean> = firebaseAuthManager.loggedOut


    fun logOut() {
        firebaseAuthManager.logOut()
    }
}