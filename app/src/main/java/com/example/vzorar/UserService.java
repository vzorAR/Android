package com.example.vzorar;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("verification_code")
    Call<VerificationCodeResponse> getVerificationCode(@Body VerificationCodeRequest verificationCodeRequest);

}
