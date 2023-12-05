package com.sti.nalirelative
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {
    @FormUrlEncoded
    @POST("login") // Replace with your actual login endpoint
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<ApiResponse>

    @FormUrlEncoded
    @POST("register") // Replace with your actual registration endpoint
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("roleId") roleId: Int, // Make sure to add this field if needed
        @Field("mobile") mobile: String,
        @Field("isAdmin") isAdmin: Int, // Make sure to add this field if needed
        @Field("createdBy") createdBy: Int, // Make sure to add this field if needed
        @Field("createdDtm") createdDtm: String // Add the createdDtm field as a String
    ): Call<ApiResponse>


    @FormUrlEncoded
    @POST("addrelative") // Replace with your actual registration endpoint
    fun addrelative(
        @Field("device_id") device_id: String,
        @Field("relative_id") relative_id: String,

    ): Call<ApiResponse>

    @FormUrlEncoded
    @POST("registerdevice") // Replace with your actual registration endpoint
    fun device(
        @Field("device_id") device_id: String,
        @Field("user_id") user_id: String,
    ): Call<ApiResponse>

    @FormUrlEncoded
    @POST("information") // Replace with your actual registration endpoint
    fun information(
        @Field("user_id") user_id: String,
        @Field("emer_contact") emer_contact: String,
        @Field("contact_address") contact_address: String,
        @Field("city_muni") city_muni: String,
        @Field("blood_type") blood_type: String,
        @Field("contact_person") contact_person: String, // Make sure to add this field if needed
        @Field("medical_history") medical_history: String,
        @Field("verified") verified: Int, // Make sure to add this field if needed

        @Field("date_added") 	date_added: String // Add the createdDtm field as a String
    ): Call<ApiResponse>

    @FormUrlEncoded
    @POST("updatetask") // Replace with your actual registration endpoint
    fun updatetask(
        @Field("taskId") user_id: String,
        @Field("description")description:String,
        @Field("rescue_status") status: String
    ): Call<ApiResponse>

    // Add a new method for checking user information
    //  @FormUrlEncoded
    @GET("listtask")
    fun check(@Query("rescue") rescue: String, @Query("city_muni") cityMuni: String): Call<ApiResponse>

    @GET("listrelative")
    fun listrelative(@Query("relative_id") relative_id: String): Call<ApiResponse>


    @POST("checkdevice")
    fun checkerdevice(@Query("user_id") userId: String): Call<ApiResponse>

    @POST("checkrelative")
    fun checkrelative(@Query("relative_id") relative_id: String): Call<ApiResponse>
}

