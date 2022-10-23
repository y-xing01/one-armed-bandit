package com.example.onearmedbandit

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.onearmedbandit.databinding.ActivityMainBinding
import com.example.onearmedbandit.databinding.ActivityStatisticsBinding

class StatisticsActivity : AppCompatActivity() {
    private lateinit var dataSet: Array<StatisticItem?>

    private lateinit var binding: ActivityStatisticsBinding
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)

        var toolbar = supportActionBar
        toolbar?.setDisplayHomeAsUpEnabled(true)
        toolbar?.title = "Statistics"

        prefs = getSharedPreferences("data", Context.MODE_PRIVATE)

        dataSet = arrayOfNulls(3)
        val statisticsAdapter = StatisticsAdapter(dataSet)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_statistics)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = statisticsAdapter
        binding.btnReset.setOnClickListener { reset() }

        updateData()
    }

    private fun updateData() {
        val numOfSpins = prefs.getInt("numOfSpins", 0)
        val numOfWins = prefs.getInt("numOfWins", 0)

        dataSet.set(0, StatisticItem(getString(R.string.stat_num_of_spins_title_full), numOfSpins.toString()))
        dataSet.set(1, StatisticItem(getString(R.string.stat_num_of_wins_title_full), numOfWins.toString()))
        dataSet.set(2, StatisticItem(getString(R.string.stat_win_spin_ratio_title), (numOfWins.toFloat() / numOfSpins * 100).toInt().toString() + "%"))
//        dataSet.set(3, StatisticItem(getString(R.string.stat_num_of_spins_title_full), numOfSpins.toString()))
//        dataSet.set(4, StatisticItem(getString(R.string.stat_num_of_wins_title_full), numOfWins.toString()))
//        dataSet.set(5, StatisticItem(getString(R.string.stat_win_spin_ratio_title), (numOfWins.toFloat() / numOfSpins * 100).toInt().toString() + "%"))
//        dataSet.set(6, StatisticItem(getString(R.string.stat_num_of_spins_title_full), numOfSpins.toString()))
//        dataSet.set(7, StatisticItem(getString(R.string.stat_num_of_wins_title_full), numOfWins.toString()))
//        dataSet.set(8, StatisticItem(getString(R.string.stat_win_spin_ratio_title), (numOfWins.toFloat() / numOfSpins * 100).toInt().toString() + "%"))

        binding.recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun reset() {
        prefs.edit()
            .remove("numOfSpins")
            .remove("numOfWins")
            .apply()

        updateData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}