package com.ahmedorabi.jokesapp.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class JokesResponse(
    val amount: Int,
    val error: Boolean,
    val jokes: List<Joke>
) : Parcelable