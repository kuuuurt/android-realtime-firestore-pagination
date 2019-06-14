package com.kurt.example.androidpaginationsample.presentation.records

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.kurt.example.androidpaginationsample.data.RecordsRepository
import com.kurt.example.androidpaginationsample.data.models.RealtimeRecord
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

/**
 * Copyright 2019, Kurt Renzo Acosta, All rights reserved.
 *
 * @author Kurt Renzo Acosta
 * @since 06/14/2019
 */
class RecordsViewModel(recordsRepository: RecordsRepository) : ViewModel() {
    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val config = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setPrefetchDistance(10)
        .setPageSize(20)
        .build()

    val records: LiveData<PagedList<RealtimeRecord>> =
        LivePagedListBuilder<String, RealtimeRecord>(
            RecordsDataSource.Factory(recordsRepository, uiScope),
            config
        ).build()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
