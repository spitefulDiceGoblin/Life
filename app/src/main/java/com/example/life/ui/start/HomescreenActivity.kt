package com.example.life.ui.start

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.life.R
import com.example.life.ui.host.ContentActivity

class HomescreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homescreen)

        val buttonGame = findViewById<Button>(R.id.button_game)
        buttonGame.setOnClickListener{
            ContentActivity.open(this, true)
        }

        val buttonWiki = findViewById<Button>(R.id.button_wiki)
        buttonWiki.setOnClickListener{
            ContentActivity.open(this, false)
        }
    }
}