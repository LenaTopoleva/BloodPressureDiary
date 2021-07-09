package com.lenatopoleva.bloodpressurediary.data.provider

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.lenatopoleva.bloodpressurediary.data.entity.HealthData
import com.lenatopoleva.bloodpressurediary.data.entity.User
import com.lenatopoleva.bloodpressurediary.data.errors.NoAuthException
import com.lenatopoleva.bloodpressurediary.data.model.HealthDataResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirestoreDataProvider(val store: FirebaseFirestore, val auth: FirebaseAuth): DataProvider {

    companion object{
        private const val HEALTH_DATA_COLLECTION = "healthdata"
        private const val USERS_COLLECTION = "users"
    }

    private val healthDataReference
        get() = currentUser?.let { store.collection(USERS_COLLECTION).document(it.uid).collection(HEALTH_DATA_COLLECTION) } ?: throw NoAuthException()

    private val currentUser
        get() = auth.currentUser

    override suspend fun getCurrentUser(): User? = suspendCoroutine { continuation ->
        val user = currentUser?.let { User(it.displayName ?: "", it.email ?: "") }
        continuation.resume(user)
    }

    override fun subscribeToHealthData(): ReceiveChannel<HealthDataResult> = Channel<HealthDataResult>(Channel.CONFLATED).apply {
        try {
            healthDataReference.addSnapshotListener { snapshot, error ->
                val value = error?.let {
                    HealthDataResult.Error(it)
                } ?: snapshot?.let {
                    val healthDataList = it.documents.map { it.toObject(HealthData::class.java) as HealthData }
                    HealthDataResult.Success(healthDataList)
                }
                value?.let { offer(it) }
            }
        } catch (e: Throwable){
            offer(HealthDataResult.Error(e))
        }
    }

    override suspend fun saveHealthData(healthData: HealthData): HealthData = suspendCoroutine { continuation ->
        try {
            healthDataReference.document(healthData.id).set(healthData)
                .addOnSuccessListener {
                    continuation.resume(healthData)
                }.addOnFailureListener{
                    continuation.resumeWithException(it)
                }
        } catch (e: Throwable){
            continuation.resumeWithException(e)
        }
    }

    override suspend fun deleteHealthData(id: String): Unit = suspendCoroutine { continuation ->
        try{
            healthDataReference.document(id).delete()
                .addOnSuccessListener {
                    continuation.resume(Unit)
                }.addOnFailureListener{
                    continuation.resumeWithException(it)
                }
        } catch (e: Throwable){
            continuation.resumeWithException(e)
        }
    }

    override suspend fun getHealthDataById(id: String): HealthData = suspendCoroutine {continuation ->
        try{
            healthDataReference.document(id).get()
                .addOnSuccessListener { snapshot->
                    val note = snapshot.toObject(HealthData::class.java) as HealthData
                    continuation.resume(note)
                }.addOnFailureListener{
                    continuation.resumeWithException(it)
                }
        } catch (e: Throwable){
            continuation.resumeWithException(e)
        }
    }

}