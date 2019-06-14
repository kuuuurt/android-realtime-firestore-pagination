package com.kurt.example.androidpaginationsample.presentation.records

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.kurt.example.androidpaginationsample.R
import com.kurt.example.androidpaginationsample.di.DaggerRecordsComponent
import javax.inject.Inject


/**
 * Copyright 2019, Kurt Renzo Acosta, All rights reserved.
 *
 * @author Kurt Renzo Acosta
 * @since 06/14/2019
 */
class RecordsActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: RecordsViewModelFactory
    lateinit var viewModel: RecordsViewModel

    val swpRecords by lazy { findViewById<SwipeRefreshLayout>(R.id.swp_records) }
    val recRecords by lazy { findViewById<RecyclerView>(R.id.rec_records) }
    val recordsAdapter by lazy { RecordsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_records)

        DaggerRecordsComponent.create().inject(this)

        recRecords.adapter = recordsAdapter

        viewModel = ViewModelProviders.of(this, factory).get(RecordsViewModel::class.java)
        viewModel.records.observe(this, Observer {
            swpRecords.isRefreshing = false
            recordsAdapter.submitList(it)
        })

        recRecords.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    if(!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN)){
                        viewModel.loadRecords()
                    }
                }
            }
        })

        swpRecords.setOnRefreshListener {
            viewModel.refresh()
        }
    }
}