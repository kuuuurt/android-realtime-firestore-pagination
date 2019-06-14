package com.kurt.example.androidpaginationsample.data.models

/**
 * Copyright 2019, Kurt Renzo Acosta, All rights reserved.
 *
 * @author Kurt Renzo Acosta
 * @since 06/14/2019
 */
data class Record(
    @Transient
    var id: String = "",
    var name: String = "",
    var isActive: Boolean = false
)