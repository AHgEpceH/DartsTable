package com.example.dartstable


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class MainActivity : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bStartGame = findViewById<View>(R.id.b_start_game) as Button
        val etFirstGamerName = findViewById<View>(R.id.et_first_gamer) as EditText
        val etSecondGamerName = findViewById<View>(R.id.et_second_gamer) as EditText
        val etThirdGamerName = findViewById<View>(R.id.et_third_gamer) as EditText
        val etFourthGamerName = findViewById<View>(R.id.et_fourth_gamer) as EditText

        var firstGamerName = ""
        var secondGamerName = ""
        var thirdGamerName = ""
        var fourthGamerName = ""

        bStartGame.setOnClickListener {
            etFirstGamerName.text.toString().also { firstGamerName = it }
            etSecondGamerName.text.toString().also { secondGamerName = it }
            etThirdGamerName.text.toString().also { thirdGamerName = it }
            etFourthGamerName.text.toString().also { fourthGamerName = it }

            if(!(firstGamerName == "")&&!(secondGamerName == "")){
                val intent = Intent(this@MainActivity, GameProcess::class.java)
                intent.putExtra("first", firstGamerName)
                intent.putExtra("second", secondGamerName)

                intent.putExtra("third", thirdGamerName)
                intent.putExtra("fourth", fourthGamerName)
                startActivity(intent)


            }else{
                val toast = Toast.makeText(applicationContext, "Заполни имена хотя бы двух игроков.", Toast.LENGTH_LONG)
                toast.show()

            }
        }


    }
}