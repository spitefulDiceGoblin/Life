package com.example.life.ui.game

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.life.ConwayArray
import com.example.life.R

class GameSettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}