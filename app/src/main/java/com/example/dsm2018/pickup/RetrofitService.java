package com.example.dsm2018.pickup;

import com.example.dsm2018.pickup.model.PartyDetailResponse;
import com.example.dsm2018.pickup.model.PartyLocationResponse;
import com.example.dsm2018.pickup.model.PartyLogResponse;
import com.example.dsm2018.pickup.model.PartySearchLocationResponse;
import com.example.dsm2018.pickup.model.PartySearchTextResponse;
import com.example.dsm2018.pickup.model.SigninResponse;
import com.example.dsm2018.pickup.model.SignupResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitService {

    @FormUrlEncoded
    @POST("account/signup")
    Call<SignupResponse> signup(
            @FieldMap Map<String, String> data
    );

    @FormUrlEncoded
    @POST("account/signin")
    Call<List<SigninResponse>> signin(
            @FieldMap Map<String, String> data
    );

    @FormUrlEncoded
    @POST("account/signout")
    Call<Void> signout(
            @FieldMap Map<String, String> data
    );

    @FormUrlEncoded
    @POST("account/modifyinfo")
    Call<Void> modifyinfo(
            @FieldMap Map<String, String> data
    );

    @FormUrlEncoded
    @POST("party/location")
    Call<PartyLocationResponse> partyLocation(
            @FieldMap Map<String, String> data
    );

    @FormUrlEncoded
    @POST("party/create")
    Call<Void> partyCreate(
            @FieldMap Map<String, String> data
    );

    @FormUrlEncoded
    @POST("party/log")
    Call<List<PartyLogResponse>> partyLog(
            @FieldMap Map<String, String> data
    );

    @FormUrlEncoded
    @POST("party/search/location")
    Call<List<PartySearchLocationResponse>> partySearchLocation(
            @FieldMap Map<String, String> data
    );

    @FormUrlEncoded
    @POST("party/search/text")
    Call<List<PartySearchTextResponse>> partySearchText(
            @FieldMap Map<String, String> data
    );

    @FormUrlEncoded
    @POST("party/detail")
    Call<PartyDetailResponse> partyDetail(
            @FieldMap Map<String, String> data
    );

    @FormUrlEncoded
    @POST("party/join")
    Call<Void> partyJoin(
            @FieldMap Map<String, String> data
    );

    @FormUrlEncoded
    @POST("party/delete")
    Call<Void> partyDelete(
            @FieldMap Map<String, String> data
    );
}
