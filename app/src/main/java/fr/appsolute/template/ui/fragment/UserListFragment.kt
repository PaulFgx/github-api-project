package fr.appsolute.template.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import fr.appsolute.template.R
import fr.appsolute.template.data.model.User
import fr.appsolute.template.ui.activity.MainActivity
import fr.appsolute.template.ui.adapter.UserAdapter
import fr.appsolute.template.ui.viewmodel.UserViewModel
import fr.appsolute.template.ui.widget.holder.ClickType
import fr.appsolute.template.ui.widget.holder.OnUserClickListener
import kotlinx.android.synthetic.main.fragment_user_list.view.*


class UserListFragment : Fragment(),
    OnUserClickListener {

    private lateinit var userViewModel: UserViewModel
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.run {
            userViewModel = ViewModelProvider(this, UserViewModel).get()
        } ?: throw IllegalStateException("Invalid Activity")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? MainActivity)?.supportActionBar?.apply {
            this.setTitle(R.string.profils)
            this.setDisplayHomeAsUpEnabled(false)
        }
        // We need to inject the OnUserClickListener in the constructor of the adapter
        userAdapter = UserAdapter(this)
        view.user_list_recycler_view.apply {
            adapter = userAdapter
            if (itemDecorationCount == 0) addItemDecoration(UserAdapter.OffsetDecoration())
        }

        userViewModel.usersPagedList.observe(this) {
            userAdapter.submitList(it)
        }
    }

    // Implementation of OnUserClickListener
    override fun invoke(view: View, user: User, type: ClickType) {
        when (type) {
            ClickType.NORMAL -> goGoDetailFragment(user)
            ClickType.LONG -> askForPersistence(user)
            else -> throw IllegalStateException("This should not have happened")
        }
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
        builder.setPositiveButton(R.string.oui) { dialog, which ->
            persistInDatabase(user)
        }
        builder.setNegativeButton(R.string.non) { dialog, which ->
            //
        }
        var alert = builder.create()
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
}
