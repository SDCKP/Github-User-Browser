package dev.sdckp.githubuserbrowser.screen.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import dev.sdckp.githubuserbrowser.R
import dev.sdckp.githubuserbrowser.databinding.ActivityBaseBinding

@AndroidEntryPoint
class UserListActivity : AppCompatActivity() {

    private val binding by lazy { ActivityBaseBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Attach fragment to activity
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, UserListFragment())
            .commit()
    }
}