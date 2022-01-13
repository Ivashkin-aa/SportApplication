package com.example.sportapplication

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class MainFragment : Fragment() {

    private lateinit var exec: ExecutorService
    private lateinit var leagues: ListView

    @RequiresApi(Build.VERSION_CODES.O)
    fun getUrl(): String {
        val dateNow = LocalDate.now()
        val dateTo = dateNow.plusDays(7)
        val apikey = "604053e0-72e9-11ec-a89a-cfd2f14691ad"
        return "https://app.sportdataapi.com/api/v1/soccer/matches?apikey=$apikey&season_id=1980&date_from=$dateNow&date_to=$dateTo"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val layout = inflater.inflate(R.layout.fragment_main, container, false)
        leagues = layout.findViewById(R.id.leagues)

        val button = layout.findViewById<Button>(R.id.moreInf)
        button.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_webViewFragment)
        }

        return layout
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        exec = Executors.newSingleThreadExecutor()
        val dataForWork =
            exec.submit<String> {
                val connection = URL(getUrl()).openConnection()
                val data = BufferedReader(InputStreamReader(connection.getInputStream()))
                data.readLine()
            }
        val jsonObject = JSONObject(dataForWork.get())
        val events = jsonObject.getJSONArray("data")
        val matches = mutableListOf<Event>()

        for (i in 0 until events.length()) {
            val obj = events.getJSONObject(i)

            var date = obj.getString("match_start")
            val day = date.substringBefore(" ")
            val parseDay = LocalDate.parse(day, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            val hours = date.substringAfter(" ").substringBeforeLast(":")
            date = parseDay.dayOfMonth.toString() + " " + parseDay.month.toString()

            var statusCode = obj.getString("status_code")
            val aboutHomeTeam = obj.getJSONObject("home_team")
            val homeTeam = aboutHomeTeam.getString("name")
            val homeLogoURL = aboutHomeTeam.getString("logo")
            val homeLogo = Glide.with(this).load(URL(homeLogoURL))
            val aboutAwayTeam = obj.getJSONObject("away_team")
            val awayTeam = aboutAwayTeam.getString("name")
            val awayLogoURL = aboutAwayTeam.getString("logo")
            val awayLogo = Glide.with(this).load(URL(awayLogoURL))
            val stats = obj.getJSONObject("stats")
            val homeScore = stats.getString("home_score")
            val awayScore = stats.getString("away_score")

            statusCode = when (statusCode.toInt()) {
                0 -> "not started"
                1 -> "Inplay"
                3 -> "Ended"
                else -> "Update Later"
            }

            matches.add(
                Event(
                    date,
                    hours,
                    statusCode,
                    homeLogo,
                    homeTeam,
                    homeScore,
                    awayLogo,
                    awayTeam,
                    awayScore
                )
            )

        }

        matches.sortBy { it.date }

        leagues.adapter = activity?.let { Adapter(it.application, R.layout.events, matches) }

    }

    override fun onStop() {
        super.onStop()
        exec.shutdown()
    }

}