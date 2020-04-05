package co.za.dvt.myskilldevapp.features.dashboard.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.adapters.StatsAdapter
import co.za.dvt.myskilldevapp.constants.CARS
import co.za.dvt.myskilldevapp.constants.STATS
import co.za.dvt.myskilldevapp.constants.TITLE
import co.za.dvt.myskilldevapp.features.dashboard.DashboardActivity
import co.za.dvt.myskilldevapp.features.database.tables.GameStats
import co.za.dvt.myskilldevapp.features.fragments.BaseDialogFragment

class StatsHistoryFragment : BaseDialogFragment(), StatsAdapter.ItemClickListener {
    private var dashboardActivity: DashboardActivity? = null
    private var titleTv: TextView? = null
    private var statsRv: RecyclerView? = null
    private var stats: List<GameStats>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val parentView = super.onCreateView(inflater, container, savedInstanceState)
        initViews(parentView)
        return parentView
    }

    private fun initViews(parentView: View) {
        titleTv = parentView.findViewById(R.id.tvHeading)
        titleTv?.text = arguments?.getString(TITLE)

        stats = arguments?.getParcelableArrayList(CARS) ?: return

        val statsAdapterAdapter = StatsAdapter(dashboardActivity as Context, stats as java.util.ArrayList<GameStats>)
        statsAdapterAdapter.setClickListener(this)

        statsRv = parentView.findViewById(R.id.rvStats)
        statsRv?.layoutManager = LinearLayoutManager(dashboardActivity)
        statsRv?.adapter = statsAdapterAdapter
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        dashboardActivity = context as DashboardActivity
    }

    override fun onItemClick(view: View, position: Int) {
        dismiss()
    }

    companion object {
        fun newInstance(stats: List<GameStats>?): BaseDialogFragment {
            val bundle = Bundle()
            bundle.putParcelableArrayList(STATS, stats as ArrayList<GameStats>)

            val statsHistoryFragment = StatsHistoryFragment()
            statsHistoryFragment.arguments = bundle
            return statsHistoryFragment
        }
    }
}