package co.za.dvt.myskilldevapp

import co.za.dvt.myskilldevapp.features.dashboard.DashboardViewModel
import io.mockk.mockk
import org.junit.Test
import org.junit.jupiter.api.TestInstance


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DashboardUnitTest {

    private val dashboardViewModel: DashboardViewModel = mockk()

    @Test
    fun isLuckyNumner_set_correctly() {

    }

}