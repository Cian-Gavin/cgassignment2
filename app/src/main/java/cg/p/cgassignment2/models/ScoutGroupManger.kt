package cg.p.cgassignment2.models

//import timber.log.Timber

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

object ScoutGroupMangerManager : GroupStore {

    private val Groups = ArrayList<ScoutGroupModels>()

    override fun findAll(): List<ScoutGroupModels> {
        return Groups
    }

    override fun findById(id:Long) : ScoutGroupModels? {
        val foundGroup: ScoutGroupModels? = Groups.find { it.id == id }
        return foundGroup
    }

    override fun create(groupModels: ScoutGroupModels) {
        groupModels.id = getId()
        Groups.add(groupModels)
        //logAll()
    }
/*
    fun logAll() {
        Timber.v("** Donations List **")
        Groups.forEach { Timber.v("Donate ${it}") }
    }

 */
}