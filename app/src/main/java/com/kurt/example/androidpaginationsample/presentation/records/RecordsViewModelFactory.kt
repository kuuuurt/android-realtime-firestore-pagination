package com.kurt.example.androidpaginationsample.presentation.records

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kurt.example.androidpaginationsample.data.RecordsRepository
import javax.inject.Inject

/**
 * Copyright 2019, Kurt Renzo Acosta, All rights reserved.
 *
 * @author Kurt Renzo Acosta
 * @since 06/14/2019
 */
class RecordsViewModelFactory @Inject constructor(
    private val recordsRepository: RecordsRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecordsViewModel::class.java)) {
            return RecordsViewModel(recordsRepository) as T
        }
        throw IllegalArgumentException("ViewModel class does not exist")
    }
}