package co.za.dvt.myskilldevapp

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import co.za.dvt.myskilldevapp.features.login.LoginRepository
import co.za.dvt.myskilldevapp.features.login.LoginViewModel
import co.za.dvt.myskilldevapp.features.database.USERSDAO
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginUnitTests {
    private lateinit var loginViewModel: LoginViewModel

    @Mock
    lateinit var repository: LoginRepository
    @Mock
    lateinit var database: USERSDAO
    @Mock
    lateinit var application: Application

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository.database = database
        loginViewModel = LoginViewModel(application, repository)
    }

    @Test
    fun `test username validation`(){

    }

    @Test
    fun `test password validation`(){

    }

    @Test
    fun `test login call`(){

        // whenever(repository.login(username, password)).thenReturn(LoginModel(user,2))
        //loginViewModel.signIn()
    }
}