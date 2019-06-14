package com.kurt.example.androidpaginationsample.data.models

import io.reactivex.Observable

/**
 * Copyright 2019, Kurt Renzo Acosta, All rights reserved.
 *
 * @author Kurt Renzo Acosta
 * @since 06/14/2019
 */
data class RealtimeRecord(
    val id: String,
    val record: Observable<Record>
)