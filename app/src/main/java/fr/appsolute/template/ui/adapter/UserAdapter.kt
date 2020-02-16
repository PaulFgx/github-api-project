package fr.appsolute.template.ui.adapter

import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import fr.appsolute.template.data.model.User
import fr.appsolute.template.ui.utils.dp
import fr.appsolute.template.ui.widget.holder.OnUserClickListener
import fr.appsolute.template.ui.widget.holder.UserViewHolder

/**
 * Adapter that shows the paginated list
 */
class UserAdapter(
    private val onUserClickListener: OnUserClickListener
) : PagedListAdapter<User, UserViewHolder>(Companion) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(position)?.run { holder.bind(this, onUserClickListener) }
    }

    companion object : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }

    class OffsetDecoration : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            parent.run {
                outRect.set(
                    dp(16),
                    dp(4),
                    dp(16),
                    dp(4)
                )
            }
        }
    }
}