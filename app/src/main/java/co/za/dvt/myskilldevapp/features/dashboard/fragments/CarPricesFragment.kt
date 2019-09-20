package co.za.dvt.myskilldevapp.features.dashboard.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.adapters.CarPricesAdapter
import co.za.dvt.myskilldevapp.constants.CATID
import co.za.dvt.myskilldevapp.features.dashboard.DashboardActivity
import co.za.dvt.myskilldevapp.features.fragments.BaseDialogFragment
import co.za.dvt.myskilldevapp.models.Car

class CarPricesFragment : BaseDialogFragment(), CarPricesAdapter.ItemClickListener {

    private var dashboardActivity: DashboardActivity? = null
    private var redeemPriceButton: Button? = null
    private var lstAsigneesRv: RecyclerView? = null
    private var cars: List<Car>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val parentView = super.onCreateView(inflater, container, savedInstanceState)
        initViews(parentView)
        return parentView
    }

    protected fun initViews(parentView: View) {

        lstAsigneesRv = parentView.findViewById(R.id.lstAsignees)
        lstAsigneesRv?.layoutManager = LinearLayoutManager(dashboardActivity)

        cars = dashboardActivity?.dashboardViewModel?.availableCars?.value
        val workersViewAdapter = CarPricesAdapter(dashboardActivity as Context, cars!!)
        workersViewAdapter.setClickListener(this)
        lstAsigneesRv?.adapter = workersViewAdapter

        redeemPriceButton = parentView.findViewById(R.id.btnCreateTask)
    }

    override fun onItemClick(view: View, position: Int) {

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        dashboardActivity = context as DashboardActivity
    }

    companion object {
        fun newInstance(catId: String): BaseDialogFragment {
            val catFragment = CarPricesFragment()
            val bundle = Bundle()
            bundle.putString(CATID, catId)
            catFragment.arguments = bundle
            return catFragment
        }
    }
}