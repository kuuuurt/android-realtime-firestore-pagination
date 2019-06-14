package com.kurt.example.androidpaginationsample.presentation.records

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.kurt.example.androidpaginationsample.R
import com.kurt.example.androidpaginationsample.data.models.Record

/**
 * Copyright 2019, Kurt Renzo Acosta, All rights reserved.
 *
 * @author Kurt Renzo Acosta
 * @since 06/14/2019
 */
class RecordViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val crdRecord by lazy { view.findViewById<MaterialCardView>(R.id.crd_record) }
    val txtRecordName by lazy { view.findViewById<TextView>(R.id.txt_record_name) }

    fun bind(record: Record) {
        txtRecordName.text = record.name
        crdRecord.isEnabled = record.isActive
        if (!record.isActive) {
            crdRecord.setCardBackgroundColor(
                ContextCompat.getColor(
                    view.context,
                    android.R.color.darker_gray
                )
            )
        }
    }
}