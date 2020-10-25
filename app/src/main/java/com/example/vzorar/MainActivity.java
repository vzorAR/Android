package com.example.vzorar;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextInputEditText mobile;
    TextInputLayout containerVerificationCode, containerButtons;
    Button btnRequestCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mobile = findViewById(R.id.txtMobile);
        containerVerificationCode = findViewById(R.id.containerVerificationCode);
        containerButtons = findViewById(R.id.containerButtons);
        btnRequestCode = findViewById(R.id.btnRequestCode);

        btnRequestCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(mobile.getText().toString())) {
                    Toast.makeText(MainActivity.this, "شماره همراه را وارد نمایید", Toast.LENGTH_SHORT).show();
                } else {
                    verificationCode();
                }
            }
        });
    }

    public void verificationCode() {
        VerificationCodeRequest verificationCodeRequest = new VerificationCodeRequest();
        verificationCodeRequest.setMobile(mobile.getText().toString());

        Call<VerificationCodeResponse> verificationCodeResponseCall = ApiClient.getUserService().getVerificationCode(verificationCodeRequest);
        verificationCodeResponseCall.enqueue(new Callback<VerificationCodeResponse>() {
            @Override
            public void onResponse(Call<VerificationCodeResponse> call, Response<VerificationCodeResponse> response) {
                if(response.isSuccessful()) {
                    VerificationCodeResponse verificationCodeResponse = response.body();

                    Toast.makeText(MainActivity.this, verificationCodeResponse.getVerification_code().toString(), Toast.LENGTH_SHORT).show();
                    containerVerificationCode.setVisibility(View.VISIBLE);
                    containerButtons.setVisibility(View.VISIBLE);
                    btnRequestCode.setVisibility(View.GONE);
                } else {
                    Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VerificationCodeResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Throwable: " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}