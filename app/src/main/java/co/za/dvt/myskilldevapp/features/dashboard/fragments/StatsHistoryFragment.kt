package co.za.dvt.myskilldevapp.features.dashboard.fragments

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
import co.za.dvt.myskilldevapp.adapters.StatsAdapter
import co.za.dvt.myskilldevapp.constants.TITLE
import co.za.dvt.myskilldevapp.features.dashboard.DashboardActivity
import co.za.dvt.myskilldevapp.features.database.tables.GameStats
import co.za.dvt.myskilldevapp.features.fragments.BaseDialogFragment
import com.wang.avi.AVLoadingIndicatorView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class StatsHistoryFragment : BaseDialogFragment(), StatsAdapter.ItemClickListener {
    private var dashboardActivity: DashboardActivity? = null
    private var parentRl: RelativeLayout? = null
    private var avlProgressBarLoading: AVLoadingIndicatorView? = null
    private var titleTv: TextView? = null
    private var noStatsTv: TextView? = null
    private var statsRv: RecyclerView? = null
    private var stats: List<GameStats>? = null
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

            uiScope.launch {

                hideLoading()

                if(stats.isNullOrEmpty()){
                    noStatsTv = parentView.findViewById(R.id.tvNoStats)
                    noStatsTv?.visibility = View.VISIBLE
                    return@launch
                }

                val statsAdapterAdapter = StatsAdapter(dashboardActivity as Context, stats as java.util.ArrayList<GameStats>)
                statsAdapterAdapter.setClickListener(statsAdapter)

                statsRv = parentView.findViewById(R.id.rvStats)
                statsRv?.layoutManager = LinearLayoutManager(dashboardActivity)
                statsRv?.adapter = statsAdapterAdapter
            }
        }
    }

    override fun onAttach(context: Context) {
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


    override fun onDismiss(dialog: DialogInterface) {
    }

    companion object {
        fun newInstance(): BaseDialogFragment {
            val statsHistoryFragment = StatsHistoryFragment()
            statsHistoryFragment.arguments = Bundle()
            return statsHistoryFragment
        }
    }
}