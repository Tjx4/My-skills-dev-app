package co.za.dvt.myskilldevapp.features.login.fragments

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.adapters.UsersAdapter
import co.za.dvt.myskilldevapp.constants.TITLE
import co.za.dvt.myskilldevapp.features.dashboard.DashboardActivity
import co.za.dvt.myskilldevapp.features.database.tables.UsersTable
import co.za.dvt.myskilldevapp.features.fragments.BaseDialogFragment
import com.wang.avi.AVLoadingIndicatorView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UsersFragment : BaseDialogFragment(), UsersAdapter.ItemClickListener {
    private var dashboardActivity: DashboardActivity? = null
    private var parentRl: RelativeLayout? = null
    private var avlProgressBarLoading: AVLoadingIndicatorView? = null
    private var titleTv: TextView? = null
    private var noUsersTv: TextView? = null
    private var usersRv: RecyclerView? = null
    private var users: List<UsersTable>? = null
    private val job =  Job()
    private val ioScope = CoroutineScope(Dispatchers.IO + job)
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val parentView = super.onCreateView(inflater, container, savedInstanceState)
        initViews(parentView)
        return parentView
    }

    private fun initViews(parentView: View) {
        val statsAdapter = this
        parentRl = parentView.findViewById(R.id.rlParent)
        avlProgressBarLoading = parentView.findViewById(R.id.avlLoading)

        titleTv = parentView.findViewById(R.id.tvHeading)
        titleTv?.text = arguments?.getString(TITLE)

        showLoading()

        ioScope.launch {
            users = dashboardActivity?.dashboardViewModel?.getUserInfo()

            uiScope.launch {

                hideLoading()

                if(users.isNullOrEmpty()){
                    noUsersTv = parentView.findViewById(R.id.tvNoStats)
                    noUsersTv?.visibility = View.VISIBLE
                    return@launch
                }

                val statsAdapterAdapter = UsersAdapter(dashboardActivity as Context, users as java.util.ArrayList<UsersTable>)
                statsAdapterAdapter.setClickListener(statsAdapter)

                usersRv = parentView.findViewById(R.id.rvUsers)
                usersRv?.layoutManager = LinearLayoutManager(dashboardActivity)
                usersRv?.adapter = statsAdapterAdapter
            }
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        dashboardActivity = context as DashboardActivity
    }

    fun showLoading() {
        parentRl?.visibility = View.INVISIBLE
        avlProgressBarLoading?.visibility = View.VISIBLE
    }

    fun hideLoading() {
        parentRl?.visibility = View.VISIBLE
        avlProgressBarLoading?.visibility = View.INVISIBLE
    }

    override fun onItemClick(view: View, position: Int) {}

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
       // dashboardActivity?.onStatsClose()
    }

    companion object {
        fun newInstance(): BaseDialogFragment {
            val statsHistoryFragment = UsersFragment()
            statsHistoryFragment.arguments = Bundle()
            return statsHistoryFragment
        }
    }
}