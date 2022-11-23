package dev.sdckp.githubuserbrowser.screen.userdetail

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import dev.sdckp.githubuserbrowser.R
import dev.sdckp.githubuserbrowser.databinding.ActivityBaseBinding

@AndroidEntryPoint
class UserDetailActivity : AppCompatActivity() {

    private val binding by lazy { ActivityBaseBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Show back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Attach fragment to activity
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, UserDetailFragment())
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}