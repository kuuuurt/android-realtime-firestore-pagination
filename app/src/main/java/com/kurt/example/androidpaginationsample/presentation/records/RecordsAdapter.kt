package com.kurt.example.androidpaginationsample.presentation.records

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.kurt.example.androidpaginationsample.R
import com.kurt.example.androidpaginationsample.data.models.Record
import java.util.ArrayList

/**
 * Copyright 2019, Kurt Renzo Acosta, All rights reserved.
 *
 * @author Kurt Renzo Acosta
 * @since 06/14/2019
 */
class RecordsAdapter : ListAdapter<Record, RecordsAdapter.RecordViewHolder>(
    object : DiffUtil.ItemCallback<Record>() {
        override fun areItemsTheSame(old: Record, new: Record): Boolean = old.id == new.id
        override fun areContentsTheSame(old: Record, new: Record): Boolean = old == new
    }
) {
    override fun submitList(list: List<Record>?) {
        super.submitList(ArrayList<Record>(list ?: listOf()))
    }

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

    inner class RecordViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val crdRecord by lazy { view.findViewById<MaterialCardView>(R.id.crd_record) }
        val txtRecordName by lazy { view.findViewById<TextView>(R.id.txt_record_name) }

        fun bind(record: Record) {
            txtRecordName.text = record.name
            crdRecord.isEnabled = record.isActive
            if(!record.isActive) {
                crdRecord.setCardBackgroundColor(ContextCompat.getColor(view.context, android.R.color.darker_gray))
            }
        }
    }
}