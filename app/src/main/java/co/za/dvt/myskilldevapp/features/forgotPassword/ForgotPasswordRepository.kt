package co.za.dvt.myskilldevapp.features.forgotPassword

import co.za.dvt.myskilldevapp.helpers.RetrofitHelper
import co.za.dvt.myskilldevapp.models.OtpModel
import co.za.dvt.myskilldevapp.models.ResetModel

class ForgotPasswordRepository(var retrofitHelper: RetrofitHelper)  {

    suspend fun getOtp(email: String, mobile: String): OtpModel {
        return try {
            retrofitHelper.getOTP(email, mobile) ?: OtpModel(false, "")
        }
        catch (ex: Exception){
            OtpModel(false, "$ex")
        }
    }

    suspend fun resetPassword(params: Map<String, String>): ResetModel {
        return try {
            retrofitHelper.resetPassword(params) ?: ResetModel(false, "")
        }
        catch (ex: Exception){
            ResetModel(false, "$ex")
        }
    }

}