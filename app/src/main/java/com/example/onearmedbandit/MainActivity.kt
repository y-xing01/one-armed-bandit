package com.example.onearmedbandit

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.onearmedbandit.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private val pokerArray: IntArray = intArrayOf(
        R.drawable.img_poker_j,
        R.drawable.img_poker_q,
        R.drawable.img_poker_k,
        R.drawable.img_poker_joker
    )

    private var resultArray = IntArray(3){0}
    private var numOfWins: Int = 0
    private var numOfSpins: Int = 0

    private lateinit var binding: ActivityMainBinding
    private var randomGenerator = Random(System.currentTimeMillis())
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("Log", "onCreate: Running onCreate")

        prefs = getSharedPreferences("data", Context.MODE_PRIVATE)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            btnSpin.setOnClickListener { spin() }
            updateResult()
        }
    }

    private fun spin() {
        binding.imgResult.visibility = View.GONE

        for (i in 0..2) {
            resultArray[i] = randomGenerator.nextInt(0, pokerArray.size)
            Log.d("Log", "spin: Random number $i - ${resultArray.get(i)}")
        }

        binding.imgPoker1.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                pokerArray[resultArray[0]]
            )
        )
        binding.imgPoker2.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                pokerArray[resultArray[1]]
            )
        )
        binding.imgPoker3.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                pokerArray[resultArray[2]]
            )
        )

        checkResult()
    }

    private fun checkResult() {
        binding.apply {
            if (resultArray.get(0) == resultArray.get(1) && resultArray.get(1) == resultArray.get(2)) {
                imgResult.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.img_result_win))
                numOfWins++
            } else {
                imgResult.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.img_result_lose))
            }

            imgResult.visibility = View.VISIBLE

            numOfSpins++

            prefs.edit()
                .putInt("numOfSpins", numOfSpins)
                .putInt("numOfWins", numOfWins)
                .apply()

            updateResult()
        }
    }

    private fun updateResult() {
        numOfSpins = prefs.getInt("numOfSpins", 0)
        numOfWins = prefs.getInt("numOfWins", 0)

        binding.apply {
            statNumOfSpinsDesc.text = numOfSpins.toString()
            statNumOfWinsDesc.text = numOfWins.toString()
            statWinSpinRatioDesc.text = (numOfWins.toFloat() / numOfSpins * 100).toInt().toString() + "%"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.instructions -> {
                startActivity(Intent(this, InstructionsActivity::class.java))
                true
            }
            R.id.statistics -> {
                startActivity(Intent(this, StatisticsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.imgResult.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.img_welcome))
        updateResult()
    }
}