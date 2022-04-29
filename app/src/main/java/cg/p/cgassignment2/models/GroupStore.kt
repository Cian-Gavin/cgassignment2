package cg.p.cgassignment2.models

interface GroupStore {
    fun findAll() : List<ScoutGroupModels>
    fun findById(id: Long) : ScoutGroupModels?
    fun create(donation: ScoutGroupModels)
}