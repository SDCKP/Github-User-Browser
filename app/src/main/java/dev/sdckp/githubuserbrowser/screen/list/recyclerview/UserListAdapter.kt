package dev.sdckp.githubuserbrowser.screen.list.recyclerview

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import dev.sdckp.githubuserbrowser.databinding.ViewUserListEntryBinding
import dev.sdckp.githubuserbrowser.repository.network.model.User

class UserListAdapter : RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {

    private val userList: MutableList<User> = mutableListOf()

    var onUserClickListener: ((User) -> Unit)? = null

    fun insertToList(users: List<User>) {
        val currentUserCount = userList.size
        userList.addAll(users)
        notifyItemRangeInserted(currentUserCount, users.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UserListViewHolder(
            ViewUserListEntryBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                parent,
                false
            ),
            onUserClickListener
        )

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.update(userList[position])
    }

    override fun getItemCount() = userList.size

    class UserListViewHolder(
        private val binding: ViewUserListEntryBinding,
        private val onUserClickListener: ((User) -> Unit)?
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun update(user: User) {
            with(binding) {
                userListEntryId.text = user.id.toString()
                userListEntryLogin.text = "@${user.login}"
                userListEntryUrl.text = user.htmlUrl
                Glide.with(root.context)
                    .load(Uri.parse(user.avatarUrl))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .circleCrop()
                    .into(userListEntryAvatar)

                root.setOnClickListener {
                    onUserClickListener?.invoke(user)
                }
            }
        }
    }
}