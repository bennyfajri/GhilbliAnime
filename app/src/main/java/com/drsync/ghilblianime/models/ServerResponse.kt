package com.drsync.ghilblianime.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ServerResponse(
    val description: String,
    val director: String,
    val id: String,
    val image: String,
    val locations: List<String>,
    val movie_banner: String,
    val original_title: String,
    val original_title_romanised: String,
    val producer: String,
    val release_date: String,
    val rt_score: String,
    val running_time: String,
    val title: String,
    val url: String,
) : Parcelable