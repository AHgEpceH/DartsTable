package com.example.dartstable

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast

class GameProcess : AppCompatActivity() {

    private var lvFirstGamerScore: ListView? = null
    private var lvSecondGamerScore: ListView? = null
    private var lvThirdGamerScore: ListView? = null
    private var lvFourthGamerScore: ListView? = null

    private lateinit var teInputFirstNumber:EditText

    private var bMakeResult:Button? = null

    private val items1 = mutableListOf<Int>() // Список для первого ListView
    private val items2 = mutableListOf<Int>()
    private val items3 = mutableListOf<Int>()
    private val items4 = mutableListOf<Int>()

    private var selectedListView: ListView? = null

    var tvFirstGamerName: TextView? = null
    var tvSecondGamerName: TextView? = null
    var tvThirdGamerName: TextView? = null
    var tvFourthGamerName: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_process)

        tvFirstGamerName = findViewById<View>(R.id.tv_first_name) as TextView
        tvSecondGamerName = findViewById<View>(R.id.tv_second_name) as TextView
        tvThirdGamerName = findViewById<View>(R.id.tv_third_name) as TextView
        tvFourthGamerName = findViewById<View>(R.id.tv_fourth_name) as TextView

        teInputFirstNumber = findViewById(R.id.et_input_first_number)

        lvFirstGamerScore = findViewById(R.id.lv_first_gamer_score)
        lvSecondGamerScore = findViewById(R.id.lv_second_gamer_score)
        lvThirdGamerScore = findViewById(R.id.lv_third_gamer_score)
        lvFourthGamerScore = findViewById(R.id.lv_fourth_gamer_score)

        bMakeResult = findViewById(R.id.b_confirm_button)

        items1.add(501) // Пример начального значения для первого списка
        items2.add(501) // Пример начального значения для второго списка
        items3.add(501) // Пример начального значения для второго списка
        items4.add(501) // Пример начального значения для второго списка


        updateListViews()

        // Создаем адаптеры для списков
        val adapter1 = ArrayAdapter(this, android.R.layout.simple_list_item_1, items1)
        lvFirstGamerScore!!.adapter = adapter1

        val adapter2 = ArrayAdapter(this, android.R.layout.simple_list_item_1, items2)
        lvSecondGamerScore!!.adapter = adapter2

        val intent = intent

        tvFirstGamerName!!.text = intent.getStringExtra("first").toString()
        tvSecondGamerName!!.text = intent.getStringExtra("second").toString()
        tvThirdGamerName!!.text = intent.getStringExtra("third").toString()
        tvFourthGamerName!!.text = intent.getStringExtra("fourth").toString()

        // Инициализация элементов интерфейса

        lvFirstGamerScore!!.setOnItemClickListener{ _, _, _, _ ->
            selectedListView  = lvFirstGamerScore

            highlightSelectedPlayer(lvFirstGamerScore!!)
            //Toast.makeText(this, "Вы выбрали из ListView 1, chosenList: $chosenList", Toast.LENGTH_SHORT).show()
        }
        lvFirstGamerScore!!.setOnItemLongClickListener(AdapterView.OnItemLongClickListener {parent, view, position, id ->
            //showEditDialog(position)
            true
        })

        lvSecondGamerScore!!.setOnItemClickListener{ _, _, _, _ ->
            selectedListView  = lvSecondGamerScore

            highlightSelectedPlayer(lvSecondGamerScore!!)
            //Toast.makeText(this, "Вы выбрали из ListView 2, chosenList: $chosenList", Toast.LENGTH_SHORT).show()
        }

        bMakeResult!!.setOnClickListener{
            subtractNumber()
            updateListViews()
        }
    }

    private fun subtractNumber() {
        val inputText = teInputFirstNumber.text.toString()
        if (inputText.isNotEmpty() && selectedListView != null) {
            val numberToSubtract = inputText.toIntOrNull()
            if (numberToSubtract != null) {
                val items = if (selectedListView == lvFirstGamerScore) items1 else items2
                if (items.isNotEmpty()) {
                    val lastValue = items.filter { it != -1 }.lastOrNull()

                    if (lastValue != null) {
                        val newValue = lastValue - numberToSubtract // Вычитаем число

                        // Проверяем результат
                        if (newValue > 0) {
                            // Если результат больше нуля, добавляем его в список
                            items.add(newValue)
                        } else {
                            // Если результат стал равен нулю, игрок выигрывает
                            if (newValue == 0) {
                                Toast.makeText(this, "Игрок ${if (selectedListView == lvFirstGamerScore) "1" else "2"} выиграл!", Toast.LENGTH_LONG).show()
                                //resetGame() // Сбрасываем игру
                                return
                            } else {
                                // Если результат меньше нуля, добавляем прочерк
                                items.add(-1) // Добавляем -1 как индикатор прочерка
                            }
                        }
                        updateListViews()

                        // Очищаем EditText
                        teInputFirstNumber.text.clear()
                    } else {
                        Toast.makeText(this, "Нет доступных числовых значений для вычитания", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Список пуст", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Введите корректное число", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Выберите список и введите число для вычитания", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateListViews() {
        val adapter1 = ArrayAdapter(this, android.R.layout.simple_list_item_1, items1.map { if (it == -1) "-" else it.toString() })
        lvFirstGamerScore?.adapter = adapter1
        lvFirstGamerScore?.deferNotifyDataSetChanged()
        lvFirstGamerScore?.setSelection(adapter1.count - 1)

        val adapter2 = ArrayAdapter(this, android.R.layout.simple_list_item_1, items2.map { if (it == -1) "-" else it.toString() })
        lvSecondGamerScore?.adapter = adapter2
        lvSecondGamerScore?.deferNotifyDataSetChanged()
        lvSecondGamerScore?.setSelection(adapter2.count - 1)
    }

    private fun highlightSelectedPlayer(selectedListView: ListView) {
        // Сбрасываем цвет для обоих TextView
        tvFirstGamerName!!.setBackgroundColor(Color.TRANSPARENT)
        tvFirstGamerName!!.setTextColor(Color.GREEN)

        tvSecondGamerName!!.setBackgroundColor(Color.TRANSPARENT)
        tvSecondGamerName!!.setTextColor(Color.GREEN)

        // Выделяем выбранный игрока
        if (selectedListView == lvFirstGamerScore) {
            tvFirstGamerName!!.setBackgroundColor(Color.parseColor("#A8E6CF")) // Цвет фона для первого игрока
            tvFirstGamerName!!.setTextColor(Color.BLACK) // Цвет текста для первого игрока
        } else if (selectedListView == lvSecondGamerScore) {
            tvSecondGamerName!!.setBackgroundColor(Color.parseColor("#A8E6CF")) // Цвет фона для второго игрока
            tvSecondGamerName!!.setTextColor(Color.BLACK) // Цвет текста для второго игрока
        }
    }
}