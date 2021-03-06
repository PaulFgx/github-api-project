package fr.paulfgx.githubproject.ui.fragment

import android.app.AlertDialog
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import fr.paulfgx.githubproject.R
import fr.paulfgx.githubproject.data.model.User
import fr.paulfgx.githubproject.ui.activity.MainActivity
import fr.paulfgx.githubproject.ui.adapter.UserAdapter
import fr.paulfgx.githubproject.ui.utils.SortUserType
import fr.paulfgx.githubproject.ui.utils.dismissKeyboard
import fr.paulfgx.githubproject.ui.utils.hide
import fr.paulfgx.githubproject.ui.viewmodel.UserViewModel
import fr.paulfgx.githubproject.ui.widget.holder.ClickType
import fr.paulfgx.githubproject.ui.widget.holder.OnUserClickListener
import kotlinx.android.synthetic.main.fragment_user_list.*

class UserListFragment : Fragment(),
    OnUserClickListener {

    private lateinit var toolbarMenu: Menu
    private lateinit var userViewModel: UserViewModel
    private lateinit var userAdapter: UserAdapter
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        activity?.run {
            userViewModel = ViewModelProvider(this@UserListFragment, UserViewModel).get()
        } ?: throw IllegalStateException("Invalid Activity")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_list, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.search_menu, menu)

        this.toolbarMenu = menu

        val manager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager

        val searchItem = menu.findItem(R.id.search_item)
        val searchView = searchItem.actionView as SearchView
        this.searchView = searchView

        searchView.setSearchableInfo(manager.getSearchableInfo(activity?.componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                searchView.clearFocus()
                user_list_main_layout.dismissKeyboard()
                userViewModel.getSearchUsersPagedList(query).observe(this@UserListFragment) {
                    userAdapter.submitList(it)
                }
                return true
            }
        })
        searchView.setOnCloseListener {
            // Displays standard list when user closes searchView
            initSimpleListLiveData()
            return@setOnCloseListener false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            /**
             * Handling toolbar submenu item click here
             */
            R.id.creation_date -> {
                userViewModel.currentSortUserType = SortUserType.CREATION_DATE
                handleCheck(item.itemId)
            }
            R.id.repositories -> {
                userViewModel.currentSortUserType = SortUserType.NB_REPOS
                handleCheck(item.itemId)
            }
            R.id.followers -> {
                userViewModel.currentSortUserType = SortUserType.NB_FOLLOWERS
                handleCheck(item.itemId)
            }
            R.id.no_filter -> {
                userViewModel.currentSortUserType = SortUserType.NONE
                handleCheck(item.itemId)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? MainActivity)?.supportActionBar?.apply {
            this.setTitle(R.string.profils)
            this.setDisplayHomeAsUpEnabled(false)
        }

        createAdapter()
        initSimpleListLiveData()
        hideLoaderWhenAdapterIsLoaded()
    }

    private fun createAdapter() {
        // We need to inject the OnUserClickListener in the constructor of the adapter
        userAdapter = UserAdapter(this)
        user_list_recycler_view.apply {
            adapter = userAdapter
            if (itemDecorationCount == 0) addItemDecoration(UserAdapter.OffsetDecoration())
        }
    }

    private fun initSimpleListLiveData() {
        userViewModel.usersPagedList.observe(this@UserListFragment) {
            userAdapter.submitList(it)
        }
    }

    private fun hideLoaderWhenAdapterIsLoaded() {
        // Observe the change on the root layout and hide progress bar
        // when first items are loaded
        user_list_main_layout.viewTreeObserver.addOnGlobalLayoutListener {
            user_list_recycler_view?.adapter?.itemCount?.run {
                if (this > 0) {
                    user_list_progress_bar.hide()
                }
            }
        }
    }

    private fun handleCheck(resId: Int) {
        toolbarMenu.findItem(R.id.creation_date).isChecked = false
        toolbarMenu.findItem(R.id.repositories).isChecked = false
        toolbarMenu.findItem(R.id.followers).isChecked = false
        toolbarMenu.findItem(R.id.no_filter).isChecked = false
        toolbarMenu.findItem(resId).isChecked = true
    }

    private fun goGoDetailFragment(user: User) {
        findNavController().navigate(
            R.id.action_user_list_fragment_to_user_details_fragment,
            bundleOf(
                UserDetailsFragment.ARG_USER_URL_KEY to user.url
            )
        )
    }

    private fun askForPersistence(user: User) {
        val builder = AlertDialog.Builder(this.context)
        builder.setMessage(R.string.save_user)
        builder.setPositiveButton(R.string.oui) { _, _ ->
            persistInDatabase(user)
        }
        builder.setNegativeButton(R.string.non) { _, _ ->
            //
        }
        val alert = builder.create()
        alert.show()
    }

    private fun persistInDatabase(user: User) {
        userViewModel.insertUser(user) { res ->
            if (res) {
                Toast.makeText(this.context, R.string.insert_ok, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this.context, R.string.insert_failed, Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Implementation of OnUserClickListener
    override fun invoke(view: View, user: User, type: ClickType) {
        when (type) {
            ClickType.NORMAL -> goGoDetailFragment(user)
            ClickType.LONG -> askForPersistence(user)
            // Switch is exhaustive, so no need for else statement
            // else -> throw IllegalStateException("This should not have happened")
        }
    }
}
