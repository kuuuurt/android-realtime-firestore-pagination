package com.kurt.example.androidpaginationsample.presentation.records

import androidx.paging.DataSource
import androidx.paging.ItemKeyedDataSource
import com.kurt.example.androidpaginationsample.data.RecordsRepository
import com.kurt.example.androidpaginationsample.data.models.RealtimeRecord
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Copyright 2019, Kurt Renzo Acosta, All rights reserved.
 *
 * @author Kurt Renzo Acosta
 * @since 06/14/2019
 */
class RecordsDataSource(
    private val recordsRepository: RecordsRepository,
    private val scope: CoroutineScope
) : ItemKeyedDataSource<String, RealtimeRecord>() {

    class Factory(
        private val myRepository: RecordsRepository,
        private val scope: CoroutineScope
    ) : DataSource.Factory<String, RealtimeRecord>() {
        override fun create(): DataSource<String, RealtimeRecord> =
            RecordsDataSource(myRepository, scope)
    }

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<RealtimeRecord>
    ) {
        scope.launch {
            val items = recordsRepository.getRecords(params.requestedLoadSize)
            callback.onResult(items)
        }
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<RealtimeRecord>) {
        scope.launch {
            val items = recordsRepository.getRecords(params.requestedLoadSize, loadAfter = params.key)
            callback.onResult(items)
        }
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<RealtimeRecord>) {
        scope.launch {
            val items = recordsRepository.getRecords(params.requestedLoadSize, loadBefore = params.key)
            callback.onResult(items)
        }
    }

    override fun getKey(item: RealtimeRecord) = item.id
}