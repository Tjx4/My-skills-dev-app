package co.za.dvt.myskilldevapp.features.dashboard.fragments

import android.os.Bundle
import android.view.View
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
    private var createTaskBtn: Button? = null
    private var workerErrorTv: TextView? = null
    private var assigneeTv: TextView? = null
    private var lstAsigneesRv: RecyclerView? = null
    private var cars: List<Car>? = null

    protected fun initViews(parentView: View) {

        lstAsigneesRv = parentView.findViewById(R.id.lstAsignees)
        lstAsigneesRv!!.setLayoutManager(LinearLayoutManager(dashboardActivity))

        cars = dashboardActivity?.dashboardViewModel?.availableCars?.value
        val workersViewAdapter = CarPricesAdapter(context!!, cars!!)
        workersViewAdapter.setClickListener(this)
        lstAsigneesRv!!.setAdapter(workersViewAdapter)

        createTaskBtn = parentView.findViewById(R.id.btnCreateTask)
    }


    fun hideValidationLabels() {
        workerErrorTv!!.visibility = View.INVISIBLE
    }

    override fun onItemClick(view: View, position: Int) {
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