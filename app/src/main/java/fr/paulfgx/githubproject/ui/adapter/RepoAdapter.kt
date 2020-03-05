package fr.paulfgx.githubproject.ui.adapter

import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.paulfgx.githubproject.data.model.Repo
import fr.paulfgx.githubproject.ui.utils.dp
import fr.paulfgx.githubproject.ui.widget.holder.RepoViewHolder

class RepoAdapter : RecyclerView.Adapter<RepoViewHolder>() {

    private var _data = emptyList<Repo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder.newInstance(parent)
    }

    override fun getItemCount(): Int = _data.count()

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(_data[position])
    }

    /**
     * Set new data in the list and refresh it
     */
    fun submitList(data: List<Repo>) {
        _data = data
        notifyDataSetChanged()
    }

    /**
     * Define how decorate an item
     */
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