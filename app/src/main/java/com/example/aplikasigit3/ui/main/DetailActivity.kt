package com.example.aplikasigit3.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.aplikasigit3.*
import com.example.aplikasigit3.databinding.ActivityDetailBinding
import com.example.aplikasigit3.DetailUser
import com.example.aplikasigit3.User
import com.example.aplikasigit3.viewmodels.DetailViewModel
import com.example.aplikasigit3.viewmodels.FavoriteViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator


class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private var isFav: Boolean = false

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar?.setDefaultDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        binding.viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        val detailViewModel = obtainViewModel(this)
        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User

        detailViewModel.userlogin = user.login

        detailViewModel.detailuser.observe(this) {
            setDetailUser(it)
        }

        detailViewModel.getAllUsers().observe(this) {
            isFav = it.contains(user)
            if (isFav) {
                binding.iconFav.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.fav_filled,
                        theme
                    )
                )
            } else {
                binding.iconFav.setImageDrawable(resources.getDrawable(R.drawable.fav_empty, theme))
            }
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        binding.iconFav.setOnClickListener {
            if (isFav) {
                detailViewModel.delete(user)
                binding.iconFav.setImageDrawable(resources.getDrawable(R.drawable.fav_empty, theme))
                Snackbar.make(
                    binding.root,
                    StringBuilder(user.login + " ").append(resources.getString(R.string.is_deleted_from_fav)),
                    Snackbar.LENGTH_LONG
                )
                    .setAction(
                        resources.getString(R.string.undo)
                    ) {
                        detailViewModel.insert(user)
                        Toast.makeText(this, resources.getString(R.string.undo), Toast.LENGTH_SHORT)
                            .show()
                    }.show()
            } else {
                detailViewModel.insert(user)
                binding.iconFav.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.fav_filled,
                        theme
                    )
                )
                Snackbar.make(
                    binding.root,
                    StringBuilder(user.login + " ").append(resources.getString(R.string.is_added_to_fav)),
                    Snackbar.LENGTH_LONG
                )
                    .setAction(
                        resources.getString(R.string.see_favorite)
                    ) { startActivity(Intent(this, FavoriteActivity::class.java)) }.show()
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel {
        val factory = FavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[DetailViewModel::class.java]
    }

    private fun setDetailUser(detailuser: DetailUser) {
        binding.tvDetailNama.text = detailuser.name ?: " - "
        binding.tvRepo.text = detailuser.publicRepos
        binding.tvLocation.text = detailuser.location ?: " - "
        binding.tvFollower.text = detailuser.followers
        binding.tvFollowing.text = detailuser.following
        binding.tvCompany.text = detailuser.company ?: " - "
        val actionBar = supportActionBar
        actionBar!!.title = detailuser.login.toUsernameFormat()
        Glide.with(this)
            .load(detailuser.avatarUrl)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(binding.imgAvatar)

    }

    private fun String.toUsernameFormat(): String {
        return StringBuilder("@").append(this).toString()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val EXTRA_USER = "extra_user"


        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }

}