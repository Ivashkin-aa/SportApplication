package com.example.sportapplication

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class Adapter(var ctx: Context, var resources: Int, var items: List<Event>) :
    ArrayAdapter<Event>(ctx, resources, items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater = LayoutInflater.from(ctx)
        val view = layoutInflater.inflate(resources, null)

        val date = view.findViewById<TextView>(R.id.date)
        val hours = view.findViewById<TextView>(R.id.hours)
        val stats = view.findViewById<TextView>(R.id.stats)
        val homeLogo = view.findViewById<ImageView>(R.id.homeLogo)
        val homeTeam = view.findViewById<TextView>(R.id.homeTeam)
        val homeScore = view.findViewById<TextView>(R.id.homeScore)
        val awayLogo = view.findViewById<ImageView>(R.id.awayLogo)
        val awayTeam = view.findViewById<TextView>(R.id.awayTeam)
        val awayScore = view.findViewById<TextView>(R.id.awayScore)

        val event: Event = items[position]
        date.text = event.date
        hours.text = event.hours
        stats.text = event.stats
        event.homeLogo.into(homeLogo)
        homeTeam.text = event.homeTeam
        homeScore.text = event.homeScore
        event.awayLogo.into(awayLogo)
        awayTeam.text = event.awayTeam
        awayScore.text = event.awayScore

        view?.setBackgroundColor(
            if (position % 2 == 0) {
                Color.WHITE
            } else {
                Color.LTGRAY
            }
        )

        return view
    }
}


