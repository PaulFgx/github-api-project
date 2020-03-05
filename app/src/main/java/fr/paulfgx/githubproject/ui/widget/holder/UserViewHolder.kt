package fr.paulfgx.githubproject.ui.widget.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.paulfgx.githubproject.R
import fr.paulfgx.githubproject.data.model.User
import kotlinx.android.synthetic.main.holder_user.view.*

/**
 * SAM (Single Abstract Method) to listen a click.
 *
 * This callback contains the view clicked, and the character attached to the view
 */
typealias OnUserClickListener = (view: View, user: User, type: ClickType) -> Unit

enum class ClickType {
    NORMAL,
    LONG
}

class UserViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(model: User, onClick: OnUserClickListener) {
        itemView.apply {
            this.setOnClickListener { onClick(it, model, ClickType.NORMAL) }
            this.setOnLongClickListener {
                onClick(it, model, ClickType.LONG)
                true
            }
            this.holder_user_login.text = model.login
            this.holder_user_type.text = model.type
            this.holder_user_site_admin.isChecked = model.site_admin
            Glide.with(this)
                .load(model.avatar_url)
                .into(this.holder_user_avatar)
        }
    }

    companion object {
        /**
         * Create a new Instance of [UserViewHolder]
         */
        fun create(parent: ViewGroup): UserViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.holder_user,
                parent,
                false
            )
            return UserViewHolder(view)
        }
    }
}