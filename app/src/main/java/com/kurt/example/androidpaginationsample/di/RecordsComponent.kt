package com.kurt.example.androidpaginationsample.di

import com.kurt.example.androidpaginationsample.presentation.records.RecordsActivity
import dagger.Component

/**
 * Copyright 2019, Kurt Renzo Acosta, All rights reserved.
 *
 * @author Kurt Renzo Acosta
 * @since 06/14/2019
 */
@Component
interface RecordsComponent {
    fun inject(recordsActivity: RecordsActivity)
}