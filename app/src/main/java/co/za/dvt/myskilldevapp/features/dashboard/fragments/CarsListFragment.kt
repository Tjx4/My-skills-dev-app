package co.za.dvt.myskilldevapp.features.dashboard.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.adapters.CarPricesAdapter
import co.za.dvt.myskilldevapp.constants.CATID
import co.za.dvt.myskilldevapp.features.dashboard.DashboardActivity
import co.za.dvt.myskilldevapp.features.fragments.BaseDialogFragment
import co.za.dvt.myskilldevapp.models.Car

class CarsListFragment : BaseDialogFragment(), CarPricesAdapter.ItemClickListener {
    private var dashboardActivity: DashboardActivity? = null
    private var carsRv: RecyclerView? = null
    private var cars: List<Car>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val parentView = super.onCreateView(inflater, container, savedInstanceState)
        initViews(parentView)
        return parentView
    }

    private fun initViews(parentView: View) {
        cars = dashboardActivity?.dashboardViewModel?.availableCars?.value
        val workersViewAdapter = CarPricesAdapter(dashboardActivity as Context, cars!!)
        workersViewAdapter.setClickListener(this)

        carsRv = parentView.findViewById(R.id.rvCars)
        carsRv?.layoutManager = LinearLayoutManager(dashboardActivity)
        carsRv?.adapter = workersViewAdapter
    }

    override fun onItemClick(view: View, position: Int) {
        dismiss()
        var selectedPrice = cars?.get(position)?.brand + cars?.get(position)?.model
        Toast.makeText(dashboardActivity, "You chose the $selectedPrice", Toast.LENGTH_SHORT).show()
        dashboardActivity?.dashboardViewModel?.setJackpotPrice(selectedPrice)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        dashboardActivity = context as DashboardActivity
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
    }

    companion object {
        fun newInstance(): BaseDialogFragment {
            val carsListFragment = CarsListFragment()
            val bundle = Bundle()
            carsListFragment.arguments = bundle
            return carsListFragment
        }
    }
}