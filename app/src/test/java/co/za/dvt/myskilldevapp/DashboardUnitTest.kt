package co.za.dvt.myskilldevapp

import co.za.dvt.myskilldevapp.features.dashboard.DashboardViewModel
import io.mockk.mockk
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DashboardUnitTest {

    private val dashboardViewModel: DashboardViewModel = mockk()

    @Test
    fun isLuckyNumber_set_correctly() {
        // given
        // dashboardViewModel.luckyNumber.value = 0

        // when
        dashboardViewModel.setLuckyNumber()
        val actualResult = dashboardViewModel.luckyNumber.value?.luckyNumber != 0 && dashboardViewModel.isError.value == false
        val expectedResult = true

        // then
        Assertions.assertEquals(expectedResult, actualResult)
    }

}