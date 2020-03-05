package fr.paulfgx.githubproject.ui.widget.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.paulfgx.githubproject.R
import fr.paulfgx.githubproject.data.model.Repo
import kotlinx.android.synthetic.main.holder_repo.view.*
import java.text.SimpleDateFormat
import java.util.*

class RepoViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(repo: Repo) {
        itemView.apply {
            holder_repo_name.text = repo.name
            repo.language?.run { if (repo.language.isNotEmpty()) holder_repo_language.text = repo.language}
            val parser =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
            val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.FRANCE)
            repo.created_at?.run {
                val formattedDate = formatter.format(parser.parse(repo.created_at))
                holder_repo_created_at.text = formattedDate
            }
        }
    }

    companion object {
        fun newInstance(parent: ViewGroup): RepoViewHolder {
            return RepoViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.holder_repo,
                    parent,
                    false
                )
            )
        }
    }
}