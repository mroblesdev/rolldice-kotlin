package com.cdp.rolldice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cdp.rolldice.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: GridAdapter
    private val diceList = mutableListOf<Pair<Int, Int>>()

    private val dicesFaces = listOf(
        R.drawable.dice_1 to 1,
        R.drawable.dice_2 to 2,
        R.drawable.dice_3 to 3,
        R.drawable.dice_4 to 4,
        R.drawable.dice_5 to 5,
        R.drawable.dice_6 to 6
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
    }

    private fun setupUI() {
        adapter = GridAdapter(this, diceList)
        binding.gridContainer.adapter = adapter
        addDice()

        binding.btnRollDice.setOnClickListener {
            binding.btnRollDice.isEnabled = false
            rollDice()
        }

        binding.btnAddDice.setOnClickListener {
            addDice()
        }

        binding.btnRemoveDice.setOnClickListener {
            removeDice()
        }
    }

    private fun addDice() {
        if (diceList.size < 36) {
            val newDice = dicesFaces.random()
            diceList.add(newDice)
            updateGridView()
        }
    }

    private fun removeDice() {
        if (diceList.isNotEmpty() && diceList.size > 1) {
            diceList.removeAt(diceList.size - 1)
            updateGridView()
        }
    }

    private fun rollDice() {
        if (diceList.isNotEmpty()) {
            val totalUpdates = 8
            val interval = 200L
            var updateCount = 0

            CoroutineScope(Dispatchers.Main).launch {
                while (updateCount < totalUpdates) {
                    delay(interval)

                    diceList.forEachIndexed { index, _ ->
                        val (newImage, newValue) = dicesFaces.random()
                        diceList[index] = newImage to newValue
                    }

                    updateGridView()
                    updateCount++
                }
                binding.btnRollDice.isEnabled = true
            }
        }
    }

    private fun updateGridView() {
        (binding.gridContainer.adapter as GridAdapter).notifyDataSetChanged()
        adjustGridViewColumns(diceList.size)
        updateTotal()
    }

    private fun adjustGridViewColumns(itemCount: Int) {
        val columns = when {
            itemCount <= 1 -> 1
            itemCount <= 4 -> 2
            itemCount <= 9 -> 3
            itemCount <= 16 -> 4
            itemCount <= 25 -> 5
            else -> 6
        }
        binding.gridContainer.numColumns = columns
    }

    private fun updateTotal(){
        val total = diceList.sumOf { it.second }
        binding.txtResult.text = getString(R.string.total, total)
    }
}