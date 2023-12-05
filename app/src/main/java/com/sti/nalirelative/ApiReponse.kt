package com.sti.nalirelative

import java.math.BigInteger
import java.util.Date


data class User(
    val userId: String,
    val roleId: Int,
    val name :String,
    val assign :String

    // Add other user properties here
)


data class ApiResponse(
    val status: Boolean,
    val message: String,
    val device_data: List<DeviceData>,
    val tasks:List<TaskData>,
    val login: Boolean,
    val result: Boolean,
    val user: User  // Define it as an object, not a string
)

data class DeviceData(
    val user_id: String,
    val device_id: String
)
data class TaskData(

    val taskId: BigInteger,
    val taskTitle: String,
    val device_id: String,
    val emergency_type: String,
    val location:String,
    val link:String,
    val message: String,
    val description: String,
    val type:String,
    val status:String,
    val city_muni:String,
    val date: Date,
    val createdDtm:String
)