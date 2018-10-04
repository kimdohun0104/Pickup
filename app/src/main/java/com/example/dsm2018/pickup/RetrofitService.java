package com.example.dsm2018.pickup;

import com.example.dsm2018.pickup.model.ModifyEmailRequest;
import com.example.dsm2018.pickup.model.ModifyNameRequest;
import com.example.dsm2018.pickup.model.ModifyPhoneRequest;
import com.example.dsm2018.pickup.model.ModifyProfileRequest;
import com.example.dsm2018.pickup.model.PartyCreateRequest;
import com.example.dsm2018.pickup.model.PartyLocationRequest;
import com.example.dsm2018.pickup.model.PartyLocationResponse;
import com.example.dsm2018.pickup.model.PartyLogRequest;
import com.example.dsm2018.pickup.model.PartyLogResponse;
import com.example.dsm2018.pickup.model.PartySearchLocationRequest;
import com.example.dsm2018.pickup.model.PartySearchLocationResponse;
import com.example.dsm2018.pickup.model.PartySearchTextRequest;
import com.example.dsm2018.pickup.model.PartySearchTextResponse;
import com.example.dsm2018.pickup.model.SigninRequest;
import com.example.dsm2018.pickup.model.SigninResponse;
import com.example.dsm2018.pickup.model.SignoutRequest;
import com.example.dsm2018.pickup.model.SignupRequest;
import com.example.dsm2018.pickup.model.SignupResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitService {
    @POST("account/signup")
    Call<SignupResponse> signup(
            @Body SignupRequest params
    );

    @POST("account/signin")
    Call<SigninResponse> signin(
            @Body SigninRequest params
    );

    @POST("account/signout")
    Call<SignoutRequest> signout(
            @Body SignoutRequest params
    );

    @POST("account/modify/name")
    Call<ModifyNameRequest> modifyName(
            @Body ModifyNameRequest params
    );

    @POST("account/modify/profile")
    Call<ModifyProfileRequest> modifyProfile(
            @Body ModifyProfileRequest params
    );

    @POST("account/modify/phone")
    Call<ModifyPhoneRequest> modifyPhone(
            @Body ModifyPhoneRequest params
    );

    @POST("account/modify/email")
    Call<ModifyEmailRequest> modifyEmail(
            @Body ModifyEmailRequest params
    );

    @POST("party/location")
    Call<PartyLocationResponse> partyLocation(
            @Body PartyLocationRequest params
    );

    @POST("party/create")
    Call<PartyCreateRequest> partyCreate(
            @Body PartyCreateRequest params
    );

    @POST("party/log")
    Call<List<PartyLogResponse>> partyLog(
            @Body PartyLogRequest params
    );

    @POST("party/search/location")
    Call<List<PartySearchLocationResponse>> partySearchLocation(
            @Body PartySearchLocationRequest params
    );

    @POST("party/search/text")
    Call<List<PartySearchTextResponse>> partySearchText(
            @Body PartySearchTextRequest params
    );


}
