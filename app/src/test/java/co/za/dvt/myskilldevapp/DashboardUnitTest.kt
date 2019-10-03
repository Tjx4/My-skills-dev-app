package co.za.dvt.myskilldevapp

import android.app.Application
import co.za.dvt.myskilldevapp.features.dashboard.DashboardRepository
import co.za.dvt.myskilldevapp.features.dashboard.DashboardViewModel
import co.za.dvt.myskilldevapp.features.dashboard.database.GameStatsDAO
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.TestInstance
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@RunWith(JUnit4::class)
class DashboardUnitTest {

    lateinit var dashboardViewModel: DashboardViewModel
    @Mock
    lateinit var repository: DashboardRepository
    @Mock
    lateinit var database: GameStatsDAO
    @Mock
    lateinit var application: Application

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        dashboardViewModel = DashboardViewModel(repository, database, application)
    }

    @Test
    fun isLuckyNumber_set_correctly() {
        // given
        var testLuckyNumber = 1

        // when
        dashboardViewModel.setLuckyNumber(testLuckyNumber)
        val actualResult = dashboardViewModel.luckyNumber.value?.luckyNumber != 0 && dashboardViewModel.isError.value == false
        val expectedResult = true

        // then
        Assertions.assertEquals(expectedResult, actualResult)
    }

}