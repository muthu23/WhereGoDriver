package com.wherego.delivery.driver.helpers;


import com.wherego.delivery.driver.model.SummryRes;
import com.wherego.delivery.driver.model.addbank.AddBank;
import com.wherego.delivery.driver.model.banklist.BankList;
import com.wherego.delivery.driver.model.documents.DocumentList;
import com.wherego.delivery.driver.model.earnings.EarningRes;
import com.wherego.delivery.driver.model.history.HistoryDetails;
import com.wherego.delivery.driver.model.historylist.HistoryList;
import com.wherego.delivery.driver.model.login.LoginRequest;
import com.wherego.delivery.driver.model.login.LoginResponse;
import com.wherego.delivery.driver.model.ongoing.OnGoingRes;
import com.wherego.delivery.driver.model.profile.DriverStatus;
import com.wherego.delivery.driver.model.profile.ProfileResponse;
import com.wherego.delivery.driver.model.servicetype.ServiceType;
import com.wherego.delivery.driver.model.servicetype.SignUpRequest;
import com.wherego.delivery.driver.model.servicetype.SignUpRes;
import com.wherego.delivery.driver.model.trip.TripRes;
import com.wherego.delivery.driver.model.widrawrequest.WithdrawRequestRes;
import com.wherego.delivery.driver.model.withdrawlist.WithDrawsList;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestInterface {

    @GET(URLHelper.DRIVER_SERVICE_TYPE)
    Call<List<ServiceType>> driverServiceType(@Header("X-Requested-With") String requestWith);

    @POST(URLHelper.SIGN_UP_REQUEST)
    Call<SignUpRes> signUp(@Header("X-Requested-With") String requestWith,
                           @Body SignUpRequest signUpRequest);

    @POST(URLHelper.LOGIN_REQUEST)
    Call<LoginResponse> login(@Header("X-Requested-With") String requestWith,
                              @Body LoginRequest loginRequest);

    @GET(URLHelper.GET_PROFILE)
    Call<ProfileResponse> driverProfile(@Header("X-Requested-With") String requestWith,
                                        @Header("Authorization") String authorization,
                                        @Query("device_id") String deviceId,
                                        @Query("device_token") String deviceToken,
                                        @Query("device_type") String deviceType);

    @POST(URLHelper.LOGOUT_USER)
    Call<ResponseBody> logout(@Header("X-Requested-With") String requestWith,
                              @Header("Authorization") String authorization);

    @GET(URLHelper.DOC_LIST)
    Call<DocumentList> documentList(@Header("X-Requested-With") String requestWith,
                                    @Header("Authorization") String authorization);

    @Multipart
    @POST(URLHelper.UPLOAD_DOC)
    Call<DocumentList> uploadDoc(@Header("X-Requested-With") String requestWith,
                                 @Header("Authorization") String authorization,
                                 @Part("document_id") RequestBody docId,
                                 @Part MultipartBody.Part image);


    @GET(URLHelper.DOC_SUBMIT)
    Call<DocumentList> docSubmission(@Header("X-Requested-With") String requestWith,
                                     @Header("Authorization") String authorization);

    @FormUrlEncoded
    @POST(URLHelper.UPDATE_DRIVER_STATUS)
    Call<DriverStatus> updateDriverStatus(@Header("X-Requested-With") String requestWith,
                                          @Header("Authorization") String authorization,
                                          @Field("service_status") String serviceStatus);

    @GET(URLHelper.CURRENT_TRIP)
    Call<TripRes> currentTrip(@Header("X-Requested-With") String requestWith,
                              @Header("Authorization") String authorization,
                              @Query("latitude") String lat,
                              @Query("longitude") String lng);

    @POST(URLHelper.CURRENT_TRIP + "/{id}")
    Call<ResponseBody> acceptTrip(@Header("X-Requested-With") String requestWith,
                                  @Header("Authorization") String authorization,
                                  @Path("id") String id);
    @FormUrlEncoded
    @POST(URLHelper.UPLOAD_SIGN )
    Call<ResponseBody> saveSign(@Header("X-Requested-With") String requestWith,
                                  @Header("Authorization") String authorization,
                                @Field("request_id") String request_id,
                                @Field("signature") String signature);

    @DELETE(URLHelper.CURRENT_TRIP + "/{id}")
    Call<ResponseBody> rejectTrip(@Header("X-Requested-With") String requestWith,
                                  @Header("Authorization") String authorization,
                                  @Path("id") String id);

    @FormUrlEncoded
    @POST(URLHelper.CURRENT_TRIP + "/{id}")
    Call<ResponseBody> updateTripStatus(@Header("X-Requested-With") String requestWith,
                                        @Header("Authorization") String authorization,
                                        @Path("id") String id,
                                        @Field("_method") String patch,
                                        @Field("status") String status);

    @FormUrlEncoded
    @POST(URLHelper.CURRENT_TRIP + "/{id}")
    Call<ResponseBody> dropTrip(@Header("X-Requested-With") String requestWith,
                                @Header("Authorization") String authorization,
                                @Path("id") String id,
                                @Field("_method") String patch,
                                @Field("status") String status,
                                @Field("latitude") String lat,
                                @Field("longitude") String lng,
                                @Field("distance") String distance);

    @FormUrlEncoded
    @POST(URLHelper.CURRENT_TRIP + "/{id}" + "/{rate}")
    Call<ResponseBody> rateTrip(@Header("X-Requested-With") String requestWith,
                                @Header("Authorization") String authorization,
                                @Path("id") String id,
                                @Path("rate") String rate,
                                @Field("rating") String rating,
                                @Field("comment") String comment);

    @FormUrlEncoded
    @POST(URLHelper.CANCEL_TRIP)
    Call<ResponseBody> cancelTrip(@Header("X-Requested-With") String requestWith,
                                  @Header("Authorization") String authorization,
                                  @Field("request_id") String id,
                                  @Field("cancel_reason") String cancelReason);

    @GET(URLHelper.HISTORY_LIST)
    Call<List<HistoryList>> historyList(@Header("X-Requested-With") String requestWith,
                                        @Header("Authorization") String authorization);

    @GET(URLHelper.HISTORY_ITEM_DETAIL)
    Call<HistoryDetails> historyItemDetail(@Header("X-Requested-With") String requestWith,
                                           @Header("Authorization") String authorization,
                                           @Query("request_id") String requestID);

    @GET(URLHelper.BANK_LIST)
    Call<BankList> bankList(@Header("X-Requested-With") String requestWith,
                            @Header("Authorization") String authorization);

    @FormUrlEncoded
    @POST(URLHelper.ADD_BANK)
    Call<AddBank> addBank(@Header("X-Requested-With") String requestWith,
                          @Header("Authorization") String authorization,
                          @FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @POST(URLHelper.WITHDRAWS_REQUEST)
    Call<WithdrawRequestRes> withdrwaRequest(@Header("X-Requested-With") String requestWith,
                                             @Header("Authorization") String authorization,
                                             @FieldMap Map<String, String> fields);

    @GET(URLHelper.WITHDRAWS_LIST)
    Call<WithDrawsList> withdrawsList(@Header("X-Requested-With") String requestWith,
                                      @Header("Authorization") String authorization);

    @GET(URLHelper.UPCOMING_LIST)
    Call<List<OnGoingRes>> onGoingList(@Header("X-Requested-With") String requestWith,
                                       @Header("Authorization") String authorization);

    @GET(URLHelper.UPCOMING_ITEM_DETAIL)
    Call<OnGoingRes> onGoingItemDetail(@Header("X-Requested-With") String requestWith,
                                       @Header("Authorization") String authorization,
                                       @Query("request_id") String requestID);

    @Multipart
    @POST(URLHelper.GET_PROFILE)
    Call<ProfileResponse> updateProfile(@Header("X-Requested-With") String requestWith,
                                        @Header("Authorization") String authorization,
                                        @PartMap() Map<String, RequestBody> partMap);

    @Multipart
    @POST(URLHelper.GET_PROFILE)
    Call<ProfileResponse> updateProfileImage(@Header("X-Requested-With") String requestWith,
                                             @Header("Authorization") String authorization,
                                             @PartMap() Map<String, RequestBody> partMap,
                                             @Part MultipartBody.Part item_image);

    @POST(URLHelper.RIDE_SUMMARY)
    Call<SummryRes> rideSummary(@Header("X-Requested-With") String requestWith,
                               @Header("Authorization") String authorization);

    @GET(URLHelper.EARNINGS)
    Call<EarningRes> earnings(@Header("X-Requested-With") String requestWith,
                              @Header("Authorization") String authorization);

}
