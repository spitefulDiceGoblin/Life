package com.example.life.ui.wiki

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel

class WikiViewModel : ViewModel() {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=C2vgICfQawE&t"))
}