package cg.p.cgassignment2.models

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser

interface ScoutGroupStore {
    fun findAll(groupList:
                MutableLiveData<List<ScoutGroupModels>>
    )
    fun findAll(email:String,
                groupList:
                MutableLiveData<List<ScoutGroupModels>>
    )
    fun findById(email:String, id: String,
                 scoutGroupModels: MutableLiveData<ScoutGroupModels>
    )
    fun create(firebaseUser: MutableLiveData<FirebaseUser>, scoutGroupModels: ScoutGroupModels)
    fun delete(email:String,id: String)
    fun update(email:String,id: String,scoutGroupModels: ScoutGroupModels)
}