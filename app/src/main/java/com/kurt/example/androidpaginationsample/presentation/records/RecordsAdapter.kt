package com.kurt.example.androidpaginationsample.presentation.records

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.kurt.example.androidpaginationsample.R
import com.kurt.example.androidpaginationsample.data.models.RealtimeRecord
import com.kurt.example.androidpaginationsample.data.models.Record
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import java.util.ArrayList

/**
 * Copyright 2019, Kurt Renzo Acosta, All rights reserved.
 *
 * @author Kurt Renzo Acosta
 * @since 06/14/2019
 */
class RecordsAdapter : PagedListAdapter<RealtimeRecord, RecordsAdapter.RecordViewHolder>(
    object : DiffUtil.ItemCallback<RealtimeRecord>() {
        override fun areItemsTheSame(old: RealtimeRecord, new: RealtimeRecord): Boolean = old.id == new.id
        override fun areContentsTheSame(old: RealtimeRecord, new: RealtimeRecord): Boolean = old == new
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item_record,
            parent,
            false
        )
        return RecordViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        val record = getItem(position)
        holder.bind(record)
    }

    override fun onViewRecycled(holder: RecordViewHolder) {
        super.onViewRecycled(holder)
        holder.viewHolderDisposables.clear()
    }

    inner class RecordViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val viewHolderDisposables = CompositeDisposable()

        val crdRecord by lazy { view.findViewById<MaterialCardView>(R.id.crd_record) }
        val txtRecordName by lazy { view.findViewById<TextView>(R.id.txt_record_name) }

        fun bind(realtimeRecord: RealtimeRecord?) {
            realtimeRecord?.let {
                it.record
                    .subscribeBy(
                        onNext = {
                            txtRecordName.text = it.name
                            crdRecord.isEnabled = it.isActive
                            if(!it.isActive) {
                                crdRecord.setCardBackgroundColor(ContextCompat.getColor(view.context, android.R.color.darker_gray))
                            }
                        },
                        onError = {
                            // Handle error here
                            // Record maybe deleted
                        }
                    )
                    .addTo(viewHolderDisposables)
            }
        }
    }
}