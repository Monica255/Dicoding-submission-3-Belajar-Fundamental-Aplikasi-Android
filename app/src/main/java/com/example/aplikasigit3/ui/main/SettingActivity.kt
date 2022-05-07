package com.example.aplikasigit3.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.aplikasigit3.R
import com.example.aplikasigit3.SettingPreferences
import com.example.aplikasigit3.databinding.ActivitySettingBinding
import com.example.aplikasigit3.viewmodels.SettingViewModel
import com.example.aplikasigit3.viewmodels.ViewModelFactory


class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding


    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar?.setDefaultDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val pref = SettingPreferences.getInstance(dataStore)
        val settingViewModel =ViewModelProvider(this, ViewModelFactory(pref))[SettingViewModel::class.java]

        settingViewModel.getThemeSettings().observe(this)
            { isDarkModeActive: Boolean ->
                setNightModeSum(isDarkModeActive)
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    binding.switchNightmode.isChecked = true
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    binding.switchNightmode.isChecked = false
                }
            }

        binding.switchNightmode.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            settingViewModel.saveThemeSetting(isChecked)
            setNightModeSum(isChecked)
        }
    }

    private fun setNightModeSum(isNightmode: Boolean) {
        binding.tvNightmodeSum.text =
            if (isNightmode) resources.getString(R.string.ONnight_mode_sum) else resources.getString(
                R.string.OFFnight_mode_sum
            )
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}

