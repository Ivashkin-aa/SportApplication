package com.example.sportapplication

import android.graphics.drawable.Drawable
import com.bumptech.glide.RequestBuilder

class Event(
    val date: String,
    val hours: String,
    val stats: String,
    val homeLogo: RequestBuilder<Drawable>,
    val homeTeam: String,
    val homeScore: String,
    val awayLogo: RequestBuilder<Drawable>,
    val awayTeam: String,
    val awayScore: String
) {}
