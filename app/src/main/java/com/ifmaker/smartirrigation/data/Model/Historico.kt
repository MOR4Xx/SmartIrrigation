package com.ifmaker.smartirrigation.data.Model


import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.Date

data class Historico(
    val data: String = "",
    val hora: String = "",
    val quantidade: Double = 0.0,
    val modo: String = "",
    val usuario: String = "",
    val timestamp: Date? = null
)
