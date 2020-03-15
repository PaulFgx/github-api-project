package fr.paulfgx.githubproject.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.bumptech.glide.Glide
import fr.paulfgx.githubproject.R
import fr.paulfgx.githubproject.ui.activity.MainActivity
import fr.paulfgx.githubproject.ui.adapter.RepoAdapter
import fr.paulfgx.githubproject.ui.utils.hide
import fr.paulfgx.githubproject.ui.viewmodel.RepoViewModel
import fr.paulfgx.githubproject.ui.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_user_details.*
import kotlinx.android.synthetic.main.fragment_user_details.view.*
import kotlinx.android.synthetic.main.fragment_user_list.*

class UserDetailsFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var repoViewModel: RepoViewModel
    private lateinit var repoAdapter: RepoAdapter
    private var userUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.run {
            userViewModel = ViewModelProvider(this, UserViewModel).get()
            repoViewModel = ViewModelProvider(this, RepoViewModel).get()
        } ?: throw IllegalStateException("Invalid Activity")
        userUrl =
            arguments?.getString(ARG_USER_URL_KEY) ?: throw IllegalStateException("No URL found")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repoAdapter = RepoAdapter()
        view.repo_list_recycler_view.apply {
            adapter = this@UserDetailsFragment.repoAdapter
            if (itemDecorationCount == 0) addItemDecoration(RepoAdapter.OffsetDecoration())
        }
        label_repositories.visibility = View.INVISIBLE
        loadUser(view)

        // Observe the change on the root layout and hide progress bar
        // when first items are loaded
        user_details_root.viewTreeObserver.addOnGlobalLayoutListener {
            repo_list_recycler_view?.adapter?.itemCount?.run {
                if (this > 0) {
                    user_detail_progress_bar.hide()
                }
            }
        }
    }

    private fun loadUser(view: View) {
        if (userUrl != "") {
            userViewModel.getUserWithUrl(userUrl) {
                (activity as? MainActivity)?.supportActionBar?.apply {
                    this.setTitle(R.string.details)

                    this.setDisplayHomeAsUpEnabled(true)
                }
                view.apply {
                    this.user_details_name.text = it.name
                    it.location?.run { user_details_location.text = it.location }
                    it.public_repos?.run { user_details_nb_repos.text = it.public_repos.toString() }
                    it.followers?.run { user_details_nb_followers.text = it.followers.toString() }
                    it.following?.run { user_details_nb_followings.text = it.following.toString() }
                    Glide.with(this)
                        .load(it.avatar_url)
                        .into(this.user_details_image_view)
                    it.repos_url.run { loadRepos(it.repos_url) }
                }
            }
        }
    }

    private fun loadRepos(url: String) {

        repoViewModel.getReposWithUrl(url) {
            label_repositories.visibility = View.VISIBLE
            repoAdapter.submitList(it)
        }
    }

    companion object {
        const val ARG_USER_URL_KEY = "arg_user_url_key"
    }
}