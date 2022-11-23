package dev.sdckp.githubuserbrowser.screen.userdetail

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import dagger.hilt.android.AndroidEntryPoint
import dev.sdckp.githubuserbrowser.common.textOrGone
import dev.sdckp.githubuserbrowser.databinding.FragmentUserDetailBinding
import dev.sdckp.githubuserbrowser.screen.userdetail.recyclerview.RepositoryListAdapter


@AndroidEntryPoint
class UserDetailFragment : Fragment() {

    private lateinit var binding: FragmentUserDetailBinding
    private val viewModel by viewModels<UserDetailFragmentViewModel>()

    private val repositoryListAdapter = RepositoryListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val userId = activity?.intent?.extras?.getString("userId") ?: return
        viewModel.loadData(userId)

        with(binding) {
            // Set up list of users
            userDetailRepositoriesList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            userDetailRepositoriesList.adapter = repositoryListAdapter.apply {
                onRepositoryClickListener = { repository ->
                    // Launch webview with the url
                    startActivity(Intent(ACTION_VIEW, Uri.parse(repository.url)))
                }
            }
        }

        viewModel.userInfo.observe(viewLifecycleOwner) { userInfo ->
            with(binding) {
                activity?.title = "@${userInfo.login}"
                userDetailId.textOrGone(userInfo.id.toString())
                userDetailUsername.textOrGone(userInfo.name ?: userInfo.login)
                Glide.with(root.context)
                    .load(Uri.parse(userInfo.avatarUrl))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .circleCrop()
                    .into(userDetailAvatar)
                userDetailUrl.textOrGone(userInfo.htmlUrl)
                userDetailLocation.textOrGone(userInfo.location)
                userDetailEmail.textOrGone(userInfo.email)
                userDetailBio.textOrGone(userInfo.bio)
                repositoryListAdapter.insertToList(userInfo.repositoryList)
                // We could add here if the list of repositories is empty, show a text indicating it instead of showing nothing

                // When finishing setting the fields, show the whole UI to the user
                root.visibility = View.VISIBLE
            }
        }

    }

}