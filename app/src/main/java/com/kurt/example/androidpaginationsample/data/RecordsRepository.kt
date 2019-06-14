package com.kurt.example.androidpaginationsample.data

import com.google.firebase.firestore.FirebaseFirestore
import com.kurt.example.androidpaginationsample.data.models.RealtimeRecord
import com.kurt.example.androidpaginationsample.data.models.Record
import io.reactivex.Observable
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * Copyright 2019, Kurt Renzo Acosta, All rights reserved.
 *
 * @author Kurt Renzo Acosta
 * @since 06/14/2019
 */

class RecordsRepository @Inject constructor() {
    val db = FirebaseFirestore.getInstance()

    suspend fun getRecords(
        pageSize: Int,
        loadBefore: String? = null,
        loadAfter: String? = null
    ): List<RealtimeRecord> {
        var query = db.collection("records").orderBy("name").limit(pageSize.toLong())

        loadBefore?.let {
            val item = db.collection("records").document(it)
                .get()
                .await()

            query = query.endBefore(item)
        }

        loadAfter?.let {
            val item = db.collection("records").document(it)
                .get()
                .await()

            query = query.startAfter(item)
        }

        return query.get()
            .await()
            .map {
                RealtimeRecord(
                    it.id,
                    getRecord(it.id)
                )
            }
    }

    private fun getRecord(itemId: String): Observable<Record> =
        Observable.create<Record> { emitter ->
            db.collection("records")
                .document(itemId)
                .addSnapshotListener { snapshot, exception ->
                    if (exception != null) {
                        emitter.onError(exception)
                    } else if (snapshot != null && snapshot.exists()) {
                        emitter.onNext(
                            snapshot.toObject(Record::class.java)
                                ?: throw IllegalArgumentException()
                        )
                    } else {
                        emitter.onError(Throwable("Record does not exist"))
                    }
                }
        }
}
