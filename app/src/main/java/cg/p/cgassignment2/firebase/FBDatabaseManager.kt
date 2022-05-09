package cg.p.cgassignment2.firebase

import androidx.lifecycle.MutableLiveData
import cg.p.cgassignment2.models.ScoutGroupModels
import cg.p.cgassignment2.models.ScoutGroupStore
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import timber.log.Timber

object FBDatabaseManager : ScoutGroupStore {

    var database: DatabaseReference = FirebaseDatabase.getInstance("https://cgassignment2-47097-default-rtdb.europe-west1.firebasedatabase.app/").getReference()
    override fun findAll(groupList: MutableLiveData<List<ScoutGroupModels>>) {
        TODO("Not yet implemented")
    }

    override fun findAll(email: String, groupList: MutableLiveData<List<ScoutGroupModels>>)
    {
        database.child("user-donations").child(email)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Timber.i("Firebase Scout app error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val localList = ArrayList<ScoutGroupModels>()
                    val children = snapshot.children
                    children.forEach {
                        val groups = it.getValue(ScoutGroupModels::class.java)
                        localList.add(groups!!)
                    }
                    database.child("GroupsaddedByUser").child(email)
                        .removeEventListener(this)

                    groupList.value = localList
                }
            })
    }

    override fun findById(email: String, id: String, scoutGroupModels: MutableLiveData<ScoutGroupModels>)
    {
        database.child("GroupsaddedByUser").child(id)
            .child(id).get().addOnSuccessListener {
                scoutGroupModels.value = it.getValue(ScoutGroupModels::class.java)
                Timber.i("firebase Got value ${it.value}")
            }.addOnFailureListener{
                Timber.e("firebase Error getting data $it")
            }
    }

    override fun create(firebaseUser: MutableLiveData<FirebaseUser>, scoutGroupModels: ScoutGroupModels)
    {
        Timber.i("Firebase DB Reference : $database")

        val uid = firebaseUser.value!!.uid
        val key = database.child("AllScoutGroupsAdded").push().key
        if (key == null) {
            Timber.i("Firebase Error : Key Empty")
            return
        }
        scoutGroupModels._id = key
        val donationValues = scoutGroupModels.toMap()

        val childAdd = HashMap<String, Any>()
        childAdd["/AllScoutGroupsAdded/$key"] = donationValues
        childAdd["/GroupsaddedByUser/$uid/$key"] = donationValues

        database.updateChildren(childAdd)
    }

    override fun delete(email: String, id: String)
    {
        val childDelete : MutableMap<String, Any?> = HashMap()
        childDelete["/AllScoutGroupsAdded/$email"] = null
        childDelete["/GroupsaddedByUser/$email/$id"] = null

        database.updateChildren(childDelete)
    }

    override fun update(email: String, id: String, scoutGroupModels: ScoutGroupModels)
    {
        val donationValues = scoutGroupModels.toMap()

        val childUpdate : MutableMap<String, Any?> = HashMap()
        childUpdate["AllScoutGroupsAdded/$id"] = donationValues
        childUpdate["GroupsaddedByUser/$email/$id"] = donationValues

        database.updateChildren(childUpdate)
    }


}