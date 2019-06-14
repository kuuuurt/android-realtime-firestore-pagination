package com.kurt.example.androidpaginationsample.presentation.records

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.kurt.example.androidpaginationsample.R
import com.kurt.example.androidpaginationsample.data.models.Record


/**
 * Copyright 2019, Kurt Renzo Acosta, All rights reserved.
 *
 * @author Kurt Renzo Acosta
 * @since 06/14/2019
 */
class RecordsActivity : AppCompatActivity() {
    val recRecords by lazy { findViewById<RecyclerView>(R.id.rec_records) }

    val query = FirebaseFirestore.getInstance().collection("records").orderBy("name")
    lateinit var myFirestoreAdapter: FirestorePagingAdapter<Record, RecordViewHolder>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_records)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPrefetchDistance(10)
            .setPageSize(20)
            .build()

        val options = FirestorePagingOptions.Builder<Record>()
            .setLifecycleOwner(this)
            .setQuery(query, config, Record::class.java)
            .build()

        myFirestoreAdapter = object : FirestorePagingAdapter<Record, RecordViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.list_item_record,
                    parent,
                    false
                )

                return RecordViewHolder(view)
            }
            override fun onBindViewHolder(holder: RecordViewHolder, position: Int, model: Record) {
                holder.bind(model)
            }
        }

        recRecords.adapter = myFirestoreAdapter
    }
}