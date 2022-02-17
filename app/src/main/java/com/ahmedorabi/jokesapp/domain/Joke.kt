package com.ahmedorabi.jokesapp.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Joke(
    val category: String = "",
    val delivery: String = "",
    val flags : Flags? = null,
    val id: Int = 0,
    val joke: String = "",
    val lang: String = "",
    val safe: Boolean = false,
    val setup: String = "",
    val type: String = ""
) : Parcelable