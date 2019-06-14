package com.kurt.example.androidpaginationsample.presentation.records

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kurt.example.androidpaginationsample.data.RecordsRepository
import com.kurt.example.androidpaginationsample.data.models.Record

/**
 * Copyright 2019, Kurt Renzo Acosta, All rights reserved.
 *
 * @author Kurt Renzo Acosta
 * @since 06/14/2019
 */
class RecordsViewModel(private val recordsRepository: RecordsRepository) : ViewModel() {
    private var recordsList = mutableListOf<Record>()

    private val _records = MutableLiveData<List<Record>>()
    val records: LiveData<List<Record>> = _records

    init {
        loadRecords()
    }

    fun loadRecords() {
        recordsRepository.getRecords {
            recordsList.addAll(it)
            recordsList = recordsList.distinctBy { it.id }.toMutableList()
            _records.postValue(recordsList)
        }
    }

    fun refresh() {
        recordsList.clear()
        recordsRepository.refresh()
        loadRecords()
    }
}