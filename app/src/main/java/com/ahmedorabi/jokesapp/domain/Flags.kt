package com.ahmedorabi.jokesapp.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Flags(
    val explicit: Boolean = false,
    val nsfw: Boolean = false,
    val political: Boolean = false,
    val racist: Boolean = false,
    val religious: Boolean = false,
    val sexist: Boolean = false
) : Parcelable