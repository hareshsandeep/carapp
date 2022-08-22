package com.challenge.carapp.util

fun Int?.priceInThousands(): Int = this?.div(1000) ?: 0