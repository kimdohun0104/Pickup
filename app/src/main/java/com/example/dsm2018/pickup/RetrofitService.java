package com.example.dsm2018.pickup;

import com.example.dsm2018.pickup.model.ModifyinfoRequest;
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

    @POST("account/modifyinfo")
    Call<ModifyinfoRequest> modifyinfo(
            @Body ModifyinfoRequest params
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
