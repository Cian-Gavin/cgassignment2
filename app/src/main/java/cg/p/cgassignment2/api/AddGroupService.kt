package cg.p.cgassignment2.api

import cg.p.cgassignment2.models.ScoutGroupModels
import retrofit2.Call
import retrofit2.http.*


interface AddGroupService
{
    @GET("/addedGroup")
    fun findall(): Call<List<ScoutGroupModels>>

    @GET("/addedGroup/{email}")
    fun findall(@Path("email") email: String?)
            : Call<List<ScoutGroupModels>>

    @GET("/addedGroup/{email}/{id}")
    fun get(@Path("email") email: String?,
            @Path("id") id: String): Call<ScoutGroupModels>

    @DELETE("/addedGroup/{email}/{id}")
    fun delete(@Path("email") email: String?,
               @Path("id") id: String): Call<AddGroupWrapper>

    @POST("/addedGroup/{email}")
    fun post(@Path("email") email: String?,
             @Body donation: ScoutGroupModels)
            : Call<AddGroupWrapper>

    @PUT("/addedGroup/{email}/{id}")
    fun put(@Path("email") email: String?,
            @Path("id") id: String,
            @Body donation: ScoutGroupModels
    ): Call<AddGroupWrapper>
}