package com.sti.nalirelative

import java.math.BigInteger
import java.util.Date

data class Task(
    val taskId: Int,
    val taskTitle: String,
    val device_id: String,
    val emergency_type: String,
    val location: String,
    val link: String,

    val message: String,
    val description: String?,
    val type: String?,
    val status: String?,
    val createdDtm: String?
)