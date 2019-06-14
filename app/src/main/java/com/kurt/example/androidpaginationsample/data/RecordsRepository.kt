package com.kurt.example.androidpaginationsample.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.kurt.example.androidpaginationsample.data.models.Record
import javax.inject.Inject

/**
 * Copyright 2019, Kurt Renzo Acosta, All rights reserved.
 *
 * @author Kurt Renzo Acosta
 * @since 06/14/2019
 */
class RecordsRepository @Inject constructor() {
    val db = FirebaseFirestore.getInstance()

    val first = db.collection("records")
        .orderBy("name")
        .limit(25)

    var next: Query? = null

    fun getRecords(onSuccess: (List<Record>) -> Unit) {
        val nextQuery = next
        if (nextQuery != null) {
            nextQuery.get()
                .addOnSuccessListener { recordSnapshots ->
                    val lastVisible = recordSnapshots.documents[recordSnapshots.size() - 1]

                    next = db.collection("records")
                        .orderBy("name")
                        .startAfter(lastVisible)
                        .limit(25)

                    onSuccess(recordSnapshots.map {
                        val record = it.toObject(Record::class.java)
                        record.id = it.id
                        record
                    })
                }
        } else {
            first.get()
                .addOnSuccessListener { recordSnapshots ->
                    val lastVisible = recordSnapshots.documents[recordSnapshots.size() - 1]

                    next = db.collection("records")
                        .orderBy("name")
                        .startAfter(lastVisible)
                        .limit(25)

                    onSuccess(recordSnapshots.map {
                        val record = it.toObject(Record::class.java)
                        record.id = it.id
                        record
                    })
                }
        }
    }

    fun refresh() {
        next = null
    }
}