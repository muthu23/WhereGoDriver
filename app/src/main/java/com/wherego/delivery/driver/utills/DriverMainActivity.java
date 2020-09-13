//package com.mycourier.driver.utills;
//
//import android.Manifest;
//import android.annotation.SuppressLint;
//import android.annotation.TargetApi;
//import android.app.Activity;
//import android.app.NotificationManager;
//import android.content.BroadcastReceiver;
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.content.IntentSender;
//import android.content.ServiceConnection;
//import android.content.pm.PackageManager;
//import android.content.res.Resources;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Point;
//import android.graphics.PorterDuff;
//import android.graphics.Typeface;
//import android.graphics.drawable.LayerDrawable;
//import android.location.Address;
//import android.location.Geocoder;
//import android.location.Location;
//import android.location.LocationManager;
//import android.media.MediaPlayer;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.CountDownTimer;
//import android.os.Handler;
//import android.os.IBinder;
//import android.text.Spannable;
//import android.text.SpannableString;
//import android.util.Base64;
//import android.util.DisplayMetrics;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RadioGroup;
//import android.widget.RatingBar;
//import android.widget.RelativeLayout;
//import android.widget.Switch;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.annotation.RequiresApi;
//import androidx.appcompat.app.ActionBarDrawerToggle;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.cardview.widget.CardView;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//import androidx.core.view.GravityCompat;
//import androidx.drawerlayout.widget.DrawerLayout;
//import androidx.fragment.app.FragmentManager;
//import androidx.localbroadcastmanager.content.LocalBroadcastManager;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.akexorcist.googledirection.DirectionCallback;
//import com.akexorcist.googledirection.GoogleDirection;
//import com.akexorcist.googledirection.constant.TransportMode;
//import com.akexorcist.googledirection.model.Direction;
//import com.akexorcist.googledirection.model.Leg;
//import com.akexorcist.googledirection.model.Route;
//import com.akexorcist.googledirection.model.Step;
//import com.akexorcist.googledirection.util.DirectionConverter;
//import com.android.volley.Request;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.github.gcacace.signaturepad.views.SignaturePad;
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.common.api.PendingResult;
//import com.google.android.gms.common.api.Status;
//import com.google.android.gms.location.LocationListener;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.location.LocationSettingsRequest;
//import com.google.android.gms.location.LocationSettingsResult;
//import com.google.android.gms.location.LocationSettingsStatusCodes;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.MapsInitializer;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
//import com.google.android.gms.maps.model.CameraPosition;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.LatLngBounds;
//import com.google.android.gms.maps.model.MapStyleOptions;
//import com.google.android.gms.maps.model.Marker;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.android.gms.maps.model.PolylineOptions;
//import com.google.android.material.bottomsheet.BottomSheetDialog;
//import com.google.android.material.navigation.NavigationView;
//import com.mycourier.driver.App;
//import com.mycourier.driver.R;
//import com.mycourier.driver.activities.EarningActivity;
//import com.mycourier.driver.activities.HelpActivity;
//import com.mycourier.driver.activities.HistoryActivity;
//import com.mycourier.driver.activities.Profile;
//import com.mycourier.driver.activities.ShowProfile;
//import com.mycourier.driver.activities.SplashScreen;
//import com.mycourier.driver.activities.SummaryActivity;
//import com.mycourier.driver.activities.UploadDocumentActivity;
//import com.mycourier.driver.activities.WithdrawActivity;
//import com.mycourier.driver.adapter.ImageAdapter;
//import com.mycourier.driver.bean.Connect;
//import com.mycourier.driver.bean.User;
//import com.mycourier.driver.chat.UserChatActivity;
//import com.mycourier.driver.helpers.ConnectionHelper;
//import com.mycourier.driver.helpers.CustomDialog;
//import com.mycourier.driver.helpers.RestInterface;
//import com.mycourier.driver.helpers.ServiceGenerator;
//import com.mycourier.driver.helpers.SharedHelper;
//import com.mycourier.driver.helpers.URLHelper;
//import com.mycourier.driver.model.Image;
//import com.mycourier.driver.model.documents.DocumentList;
//import com.mycourier.driver.model.profile.DriverStatus;
//import com.mycourier.driver.model.trip.RequestItem;
//import com.mycourier.driver.model.trip.RequestList;
//import com.mycourier.driver.services.LocationUpdatesService;
//import com.squareup.picasso.Picasso;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.ByteArrayOutputStream;
//import java.text.ParseException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Locale;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import de.hdodenhof.circleimageview.CircleImageView;
//import es.dmoral.toasty.Toasty;
//import okhttp3.ResponseBody;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class DriverMainActivity extends AppCompatActivity implements
//        OnMapReadyCallback,
//        LocationListener,
//        GoogleApiClient.ConnectionCallbacks,
//        GoogleApiClient.OnConnectionFailedListener,
//        GoogleMap.OnCameraMoveListener {
//
//    private MyReceiver myReceiver;
//    private LocationUpdatesService mService = null;
//    private boolean mBound = false;
//
//    private final ServiceConnection mServiceConnection = new ServiceConnection() {
//
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            LocationUpdatesService.LocalBinder binder = (LocationUpdatesService.LocalBinder) service;
//            mService = binder.getService();
//            mBound = true;
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//            mService = null;
//            mBound = false;
//        }
//    };
//
//
//    private RestInterface restInterface;
//    private static final int REQUEST_LOCATION = 1450;
//    public static String TAG = "DriverMapFragment";
//    private static SupportMapFragment mapFragment = null;
//
//    @BindView(R.id.llSignature)
//    LinearLayout llSignature;
//    @BindView(R.id.signature_pad)
//    SignaturePad signature_pad;
//    @BindView(R.id.clear_button)
//    Button clear_button;
//    @OnClick(R.id.clear_button)
//    void clear_button(){
//        signature_pad.clear();
//    }
//    @BindView(R.id.save_button)
//    Button save_button;
//    @OnClick(R.id.save_button)
//    void save_button(){
//        Bitmap bitmap = signature_pad.getSignatureBitmap();
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
//        byte[] byteArray = byteArrayOutputStream .toByteArray();
//        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
//        Log.d("signature", encoded);
//    }
//
//
//    @OnClick(R.id.btn_01_status)
//    void btn_01_statusClick() {
//        if (CurrentStatus.equalsIgnoreCase("DROPPED")) {
//            dropTrip(request_id, CurrentStatus);
//        } else {
//            updateTripStatus(request_id, CurrentStatus);
//        }
//
//    }
//
//    @OnClick(R.id.btn_confirm_payment)
//    void btn_confirm_paymentClick() {
//        updateTripStatus(request_id, CurrentStatus);
//    }
//
//    @OnClick(R.id.btn_rate_submit)
//    void btn_rate_submitClick() {
//        rateTrip(request_id, feedBackRating, edt05Comment.getText().toString());
//    }
//
//    @OnClick(R.id.imgCurrentLoc)
//    void imgCurrentLocClick() {
//        Double crtLat, crtLng;
//        if (!crt_lat.equalsIgnoreCase("") && !crt_lng.equalsIgnoreCase("")) {
//            crtLat = Double.parseDouble(crt_lat);
//            crtLng = Double.parseDouble(crt_lng);
//            if (crtLat != null && crtLng != null) {
//                LatLng loc = new LatLng(crtLat, crtLng);
//                CameraPosition cameraPosition = new CameraPosition.Builder().target(loc).zoom(14).build();
//                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//            }
//        }
//    }
//
//    @OnClick(R.id.btn_02_accept)
//    void btn_02_acceptClick() {
//        countDownTimer.cancel();
//        if (mPlayer != null && mPlayer.isPlaying()) {
//            mPlayer.stop();
//            mPlayer = null;
//        }
//        acceptRequest(request_id);
//    }
//
//    @OnClick(R.id.btn_02_reject)
//    void btn_02_rejectClick() {
//        countDownTimer.cancel();
//        if (mPlayer != null && mPlayer.isPlaying()) {
//            mPlayer.stop();
//            mPlayer = null;
//        }
//        rejectRequest(request_id);
//    }
//
//    @OnClick(R.id.btn_cancel_ride)
//    void btn_cancel_rideClick() {
//        showCancelDialog();
//    }
//
//    @SuppressLint("WrongConstant")
//    @OnClick(R.id.menuIcon)
//    void menuIconClick() {
//        if (NAV_DRAWER == 0) {
//            drawer_layout.openDrawer(Gravity.START);
//        } else {
//            NAV_DRAWER = 0;
//            drawer_layout.closeDrawers();
//        }
//    }
//
//    @OnClick(R.id.img_chat)
//    void img_chatClick() {
//        Intent intentChat = new Intent(DriverMainActivity.this,
//                UserChatActivity.class);
//        intentChat.putExtra("requestId", request_id);
//        intentChat.putExtra("providerId", providerId);
//        intentChat.putExtra("userId", userID);
//        intentChat.putExtra("userName", userFirstName);
//        DriverMainActivity.this.startActivity(intentChat);
//    }
//
//    @OnClick(R.id.img03Call)
//    void img03CallClick() {
//        String mobile = SharedHelper.getKey(App.getContext(), "provider_mobile_no");
//        if (mobile != null && !mobile.equalsIgnoreCase("null") && mobile.length() > 0) {
//            Intent intent = new Intent(Intent.ACTION_DIAL);
//            intent.setData(Uri.parse("tel:" + SharedHelper.getKey(App.getContext(), "provider_mobile_no")));
//            startActivity(intent);
//        } else {
//            Utils.infoToast(App.getContext().getString(R.string.user_no_mobile));
//        }
//    }
//
//    RequestItem requestItem;
//    JSONObject itemObject;
//    @SuppressLint("SetTextI18n")
//    @OnClick(R.id.img03Info)
//    void img03Info() {
//        if (itemObject != null){
//            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(DriverMainActivity.this);
//            bottomSheetDialog.setContentView(R.layout.courier_info);
//            bottomSheetDialog.show();
//            ArrayList<Image> images = new ArrayList<>();
//            ImageView imgClose = bottomSheetDialog.findViewById(R.id.imgClose);
//            TextView tvParcelname = bottomSheetDialog.findViewById(R.id.tvParcelname);
//            TextView tvParcelQty = bottomSheetDialog.findViewById(R.id.tvParcelQty);
//            TextView tvParcelWeight = bottomSheetDialog.findViewById(R.id.tvParcelWeight);
//            TextView tvReceiverName = bottomSheetDialog.findViewById(R.id.tvReceiverName);
//            TextView tvReceiverEmail = bottomSheetDialog.findViewById(R.id.tvReceiverEmail);
//            TextView tvreceiverMobile = bottomSheetDialog.findViewById(R.id.tvreceiverMobile);
//            TextView tvReceiverAddress = bottomSheetDialog.findViewById(R.id.tvReceiverAddress);
//            ImageView imgCourier = bottomSheetDialog.findViewById(R.id.imgCourier);
//            TextView tvTitle = bottomSheetDialog.findViewById(R.id.tvTitle);
//            TextView tvname = bottomSheetDialog.findViewById(R.id.tvname);
//            TextView tvWeight = bottomSheetDialog.findViewById(R.id.tvWeight);
//            TextView tvQuantity = bottomSheetDialog.findViewById(R.id.tvQuantity);
//            RecyclerView recyclerView = bottomSheetDialog.findViewById(R.id.recyclerView);
//            ImageAdapter adapter = new ImageAdapter(DriverMainActivity.this);
//            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DriverMainActivity.this,
//                    LinearLayoutManager.HORIZONTAL, false);
//            recyclerView.setLayoutManager(layoutManager);
//            recyclerView.setAdapter(adapter);
//
//            if (itemObject != null) {
//
//                tvTitle.setText(itemObject.optString("document_type") + "  Info");
//                tvname.setText(itemObject.optString("document_type") + "  Name");
//                tvWeight.setText(itemObject.optString("document_type") + "  Weight");
//                tvQuantity.setText(itemObject.optString("document_type") + "  Quantity");
//                tvParcelname.setText(itemObject.optString("name"));
//                tvParcelQty.setText(itemObject.optString("qty") + " Pcs");
//                tvParcelWeight.setText(itemObject.optString("weight") + " Gm");
//                tvReceiverName.setText(itemObject.optString("rec_name"));
//                tvReceiverEmail.setText(itemObject.optString("rec_email"));
//                tvreceiverMobile.setText(itemObject.optString("rec_mobile"));
//                tvReceiverAddress.setText(itemObject.optString("rec_address"));
//                if (itemObject.optJSONArray("item_image") != null) {
//                    for (int k = 0; k < itemObject.optJSONArray("item_image").length(); k++) {
//                        String imagePath = URLHelper.BASE + itemObject.optJSONArray("item_image")
//                                .optJSONObject(k).optString("image_path");
//                        Image image = new Image(k, "a", imagePath);
//                        images.add(image);
//                    }
//                    adapter.setData(images);
//
//
//                }
//
//                tvreceiverMobile.setOnClickListener(v -> {
//                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tvreceiverMobile.getText().toString()));
//                    startActivity(intent);
//                });
//
//            }
//            imgClose.setOnClickListener(v -> bottomSheetDialog.dismiss());
//        }else {
//            displayMessage("Information not available");
//        }
//
//    }
//
//    @OnClick(R.id.imgNavigationToSource)
//    void imgNavigationToSourceClick() {
//        String url = "http://maps.google.com/maps?"
//                + "saddr=" + address
//                + "&daddr=" + daddress;
//        Log.e("url", url + "url");
//        if (btn_01_status.getText().toString().equalsIgnoreCase("ARRIVED")) {
//            Uri naviUri = Uri.parse("http://maps.google.com/maps?"
//                    + "saddr=" + crt_lat + "," + crt_lng
//                    + "&daddr=" + srcLatitude + "," + srcLongitude);
//
//            Intent intent = new Intent(Intent.ACTION_VIEW, naviUri);
//            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
//            startActivity(intent);
//        } else {
//            Uri naviUri2 = Uri.parse("http://maps.google.com/maps?"
//                    + "saddr=" + srcLatitude + "," + srcLongitude
//                    + "&daddr=" + destLatitude + "," + destLongitude);
//
//            Intent intent = new Intent(Intent.ACTION_VIEW, naviUri2);
//            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
//            startActivity(intent);
//        }
//    }
//
//    @OnClick(R.id.online_offline_switch)
//    void online_offline_switchClick() {
//        goOnline();
//    }
//
//
//    @BindView(R.id.menuIcon)
//    ImageView menuIcon;
//    @BindView(R.id.imgCurrentLoc)
//    ImageView imgCurrentLoc;
//    @BindView(R.id.ll_01_mapLayer)
//    LinearLayout ll_01_mapLayer;
//    @BindView(R.id.driverArrived)
//    LinearLayout driverArrived;
//    @BindView(R.id.driverPicked)
//    LinearLayout driverPicked;
//    @BindView(R.id.driveraccepted)
//    LinearLayout driveraccepted;
//    @BindView(R.id.tvTrips)
//    TextView tvTrips;
//    @BindView(R.id.tvCommision)
//    TextView tvCommision;
//    @BindView(R.id.tvEarning)
//    TextView tvEarning;
//    @BindView(R.id.txtTotalEarning)
//    TextView txtTotalEarning;
//    @BindView(R.id.btn_01_status)
//    Button btn_01_status;
//    @BindView(R.id.btn_rate_submit)
//    Button btn_rate_submit;
//    @BindView(R.id.btn_confirm_payment)
//    Button btn_confirm_payment;
//
//    @BindView(R.id.total_earn_layout)
//    CardView total_earn_layout;
//    @BindView(R.id.btn_02_accept)
//    Button btn_02_accept;
//    @BindView(R.id.btn_02_reject)
//    TextView btn_02_reject;
//    @BindView(R.id.btn_cancel_ride)
//    Button btn_cancel_ride;
//    @BindView(R.id.btn_go_offline)
//    Button btn_go_offline;
//    @BindView(R.id.btn_go_online)
//    Button btn_go_online;
//    @BindView(R.id.activeStatus)
//    TextView activeStatus;
//    @BindView(R.id.offline_layout)
//    RelativeLayout offline_layout;
//    @BindView(R.id.ll_01_contentLayer_accept_or_reject_now)
//    LinearLayout ll_01_contentLayer_accept_or_reject_now;
//    @BindView(R.id.ll_02_contentLayer_accept_or_reject_later)
//    LinearLayout ll_02_contentLayer_accept_or_reject_later;
//    @BindView(R.id.ll_03_contentLayer_service_flow)
//    LinearLayout ll_03_contentLayer_service_flow;
//    @BindView(R.id.ll_04_contentLayer_payment)
//    LinearLayout ll_04_contentLayer_payment;
//    @BindView(R.id.ll_05_contentLayer_feedback)
//    LinearLayout ll_05_contentLayer_feedback;
//    @BindView(R.id.lnrGoOffline)
//    LinearLayout lnrGoOffline;
//    @BindView(R.id.layoutinfo)
//    LinearLayout layoutinfo;
//    @BindView(R.id.lnrNotApproved)
//    LinearLayout lnrNotApproved;
//    @BindView(R.id.imgNavigationToSource)
//    ImageView imgNavigationToSource;
//    @BindView(R.id.img01User)
//    ImageView img01User;
//    @BindView(R.id.sos)
//    ImageView sos;
//    @BindView(R.id.rat01UserRating)
//    RatingBar rat01UserRating;
//    @BindView(R.id.txtPickup)
//    TextView txt01Pickup;
//    @BindView(R.id.txtDropOff)
//    TextView txtDropOff;
//    @BindView(R.id.txt01Timer)
//    TextView txt01Timer;
//    @BindView(R.id.txt01UserName)
//    TextView txt01UserName;
//    @BindView(R.id.tvDistance)
//    TextView tvDistance;
//    @BindView(R.id.txtSchedule)
//    TextView txtSchedule;
//    @BindView(R.id.img02User)
//    ImageView img02User;
//    @BindView(R.id.txt02UserName)
//    TextView txt02UserName;
//    @BindView(R.id.rat02UserRating)
//    RatingBar rat02UserRating;
//    @BindView(R.id.txt02ScheduledTime)
//    TextView txt02ScheduledTime;
//    @BindView(R.id.txt02From)
//    TextView txt02From;
//    @BindView(R.id.txt02To)
//    TextView txt02To;
//    @BindView(R.id.img03User)
//    CircleImageView img03User;
//    @BindView(R.id.txt03UserName)
//    TextView txt03UserName;
//    @BindView(R.id.lblCmfrmDestAddress)
//    TextView lblCmfrmDestAddress;
//    @BindView(R.id.lblCmfrmSourceAddress)
//    TextView lblCmfrmSourceAddress;
//    @BindView(R.id.rat03UserRating)
//    RatingBar rat03UserRating;
//    @BindView(R.id.img03Call)
//    ImageButton img03Call;
//    @BindView(R.id.img_chat)
//    ImageButton img_chat;
//    @BindView(R.id.img03Status1)
//    ImageView img03Status1;
//    @BindView(R.id.img03Status2)
//    ImageView img03Status2;
//    @BindView(R.id.img03Status3)
//    ImageView img03Status3;
//    @BindView(R.id.invoice_txt)
//    TextView txt04InvoiceId;
//    @BindView(R.id.txtTotal)
//    TextView txtTotal;
//    @BindView(R.id.txt04BasePrice)
//    TextView txt04BasePrice;
//    @BindView(R.id.txt04Distance)
//    TextView txt04Distance;
//
//    @BindView(R.id.txt04Tax)
//    TextView txt04Tax;
//    @BindView(R.id.txt04Total)
//    TextView txt04Total;
//    @BindView(R.id.txt04PaymentMode)
//    TextView txt04PaymentMode;
//    @BindView(R.id.txt04Commision)
//    TextView txt04Commision;
//    @BindView(R.id.destination)
//    TextView destination;
//    @BindView(R.id.lblProviderName)
//    TextView lblProviderName;
//    @BindView(R.id.paymentTypeImg)
//    ImageView paymentTypeImg;
//    @BindView(R.id.lnrErrorLayout)
//    LinearLayout errorLayout;
//    @BindView(R.id.destinationLayer)
//    LinearLayout destinationLayer;
//    @BindView(R.id.txtNotes)
//    TextView txtNotes;
//    @BindView(R.id.layoutNotes)
//    LinearLayout layoutNotes;
//    @BindView(R.id.img05User)
//    ImageView img05User;
//    @BindView(R.id.rat05UserRating)
//    RatingBar rat05UserRating;
//    @BindView(R.id.user_name)
//    TextView user_name;
//    @BindView(R.id.user_type)
//    TextView user_type;
//    @BindView(R.id.user_total_ride_distanse)
//    TextView user_total_ride_distanse;
//    @BindView(R.id.online_offline_switch)
//    Switch online_offline_switch;
//    @BindView(R.id.active_Status)
//    TextView active_Status;
//    @BindView(R.id.edt05Comment)
//    EditText edt05Comment;
//    @BindView(R.id.src_dest_txt)
//    TextView topSrcDestTxtLbl;
//    String docopen = "";
//    private static String currentTripStatus = "";
//    String CurrentStatus = " ";
//    String PreviousStatus = " ";
//    String request_id = " ";
//    int method;
//    CountDownTimer countDownTimer;
//    int value = 0;
//    Marker currentMarker;
//    GoogleApiClient mGoogleApiClient;
//    LocationRequest mLocationRequest;
//    boolean normalPlay = false;
//    boolean push = false;
//    int NAV_DRAWER = 0;
//    Utilities utils = new Utilities();
//    MediaPlayer mPlayer;
//    String crt_lat = "", crt_lng = "";
//    boolean isRunning = false, timerCompleted = false;
//    ConnectionHelper helper;
//    Animation slide_down, slide_up;
//    boolean scheduleTrip = false;
//    String type = null, datas = null;
//    String providerId = "";
//    String userID = "";
//    String userFirstName = "";
//    String cancaltype = "";
//    String cancalReason = "";
//    private Handler ha;
//    private String myLat = "";
//    //map variable
//    private String myLng = "";
//    private String token;
//    private GoogleMap mMap;
//    private double srcLatitude = 0;
//    private double srcLongitude = 0;
//    private double destLatitude = 0;
//    private double destLongitude = 0;
//    private LatLng sourceLatLng;
//    private LatLng destLatLng;
//    private LatLng currentLatLng;
//    private String bookingId;
//    private String address;
//    private String daddress;
//    private User user = new User();
//    //Button layout
//    private CustomDialog customDialog;
//    private Object previous_request_id = " ";
//    private String count;
//    private ArrayList<RequestList> requestLists;
//    private JSONArray statusResponses;
//    private String feedBackRating;
//    private String feedBackComment;
//    private AlertDialog Waintingdialog;
//    private String earning = "";
//
//
//    Toolbar toolbar;
//
//    @BindView(R.id.drawer_layout)
//    DrawerLayout drawer_layout;
//    @BindView(R.id.nav_view)
//    NavigationView nav_view;
//    @BindView(R.id.legal_id)
//    TextView legal_id;
//    @BindView(R.id.footer_item_version)
//    TextView footer_item_version;
//
//    CircleImageView img_profile;
//    CircleImageView status;
//    TextView usernameTxt;
//    TextView tvRate;
//    TextView status_txt;
//    private NotificationManager notificationManager;
//    public static String statustg = "";
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        mService.requestLocationUpdates();
//        bindService(new Intent(this, LocationUpdatesService.class), mServiceConnection,
//                Context.BIND_AUTO_CREATE);
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_driver_main);
//        ButterKnife.bind(this);
//        Bundle extras = getIntent().getExtras();
//        restInterface = ServiceGenerator.createService(RestInterface.class);
//        setNavView();
//        navHeader();
//        myReceiver = new MyReceiver();
//
//
//        findViewById();
//        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        notificationManager.cancelAll();
//        if (extras != null) {
//            push = extras.getBoolean("push");
//            type = extras.getString("type");
//            datas = extras.getString("datas");
//        }
//        Connect.addMyBooleanListener(() -> Toast.makeText(getApplication(),
//                "Changed", Toast.LENGTH_SHORT).show());
//        if (getIntent().getStringExtra("status") != null) {
//            statustg = getIntent().getStringExtra("status");
//        }
//
//        token = SharedHelper.getKey(App.getContext(), "access_token");
//        helper = new ConnectionHelper(DriverMainActivity.this);
//        customDialog = new CustomDialog(this);
//        customDialog.setCancelable(true);
//        customDialog.show();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            // Android M Permission check
//            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//        } else {
//            setUpMapIfNeeded();
//            MapsInitializer.initialize(this);
//        }
//
////        if (type != null) {
////            checkStatusSchedule();
////        } else {
////            checkStatus();
////        }
//        checkStatus();
//        ha = new Handler();
//        //check status every 5 sec
//        ha.postDelayed(new Runnable() {
//            @Override
//            public void run() {
////                if (type != null) {
////                    checkStatusSchedule();
////                } else {
////                    checkStatus();
////                }
//                checkStatus();
//                ha.postDelayed(this, 5000);
//            }
//        }, 5000);
//
//        signature_pad.setOnSignedListener(new SignaturePad.OnSignedListener() {
//            @Override
//            public void onStartSigning() {
//
//            }
//
//            @Override
//            public void onSigned() {
//
//                save_button.setEnabled(true);
//                clear_button.setEnabled(true);
//            }
//
//            @Override
//            public void onClear() {
//                save_button.setEnabled(false);
//                clear_button.setEnabled(false);
//            }
//        });
//
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    private void statusCheck() {
//        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            enableLoc();
//        }
//    }
//
//    private void enableLoc() {
//        mGoogleApiClient = new GoogleApiClient.Builder(DriverMainActivity.this)
//                .addApi(LocationServices.API)
//                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
//                    @Override
//                    public void onConnected(Bundle bundle) {
//
//                    }
//
//                    @Override
//                    public void onConnectionSuspended(int i) {
//                        mGoogleApiClient.connect();
//                    }
//                })
//                .addOnConnectionFailedListener(connectionResult -> utils.print("Location error", "Location error " + connectionResult.getErrorCode())).build();
//        mGoogleApiClient.connect();
//
//        LocationRequest locationRequest = LocationRequest.create();
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        locationRequest.setInterval(30 * 1000);
//        locationRequest.setFastestInterval(5 * 1000);
//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
//                .addLocationRequest(locationRequest);
//
//        builder.setAlwaysShow(true);
//
//        PendingResult<LocationSettingsResult> result =
//                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
//        result.setResultCallback(result1 -> {
//            final Status status = result1.getStatus();
//            switch (status.getStatusCode()) {
//                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//                    try {
//                        status.startResolutionForResult(this, REQUEST_LOCATION);
//                    } catch (IntentSender.SendIntentException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//            }
//        });
//    }
//
//
//    @SuppressLint("SetTextI18n")
//    private void navHeader() {
//        // name, website
//        status = nav_view.getHeaderView(0).findViewById(R.id.status);
//        status_txt = nav_view.getHeaderView(0).findViewById(R.id.status_txt);
//        usernameTxt = nav_view.getHeaderView(0).findViewById(R.id.usernameTxt);
//        img_profile = nav_view.getHeaderView(0).findViewById(R.id.img_profile);
//        tvRate = nav_view.getHeaderView(0).findViewById(R.id.tvRate);
//
//        usernameTxt.setText(SharedHelper.getKey(App.getContext(), "first_name"));
//        if (SharedHelper.getKey(App.getContext(), "approval_status").equals("new") ||
//                SharedHelper.getKey(App.getContext(), "approval_status").equals("onboarding")) {
//            status_txt.setTextColor(Color.YELLOW);
//            status_txt.setText(getText(R.string.waiting_for_approval));
//            status.setImageResource(R.drawable.newuser);
//        } else if (SharedHelper.getKey(App.getContext(), "approval_status").equals("banned")) {
//            status_txt.setTextColor(Color.RED);
//            status_txt.setText(getText(R.string.banned));
//            status.setImageResource(R.drawable.banned);
//        } else if (SharedHelper.getKey(App.getContext(), "approval_status").equals("approved")) {
//            status_txt.setTextColor(Color.GREEN);
//            status_txt.setText(getText(R.string.approved));
//            status.setImageResource(R.drawable.approved);
//        } else {
//            status_txt.setTextColor(Color.YELLOW);
//            status_txt.setText(getText(R.string.waiting_for_approval));
//            status.setImageResource(R.drawable.newuser);
//        }
//
//        if (SharedHelper.getKey(App.getContext(), "picture") != null
//                && !SharedHelper.getKey(App.getContext(), "picture").isEmpty()) {
//            Picasso.get().load(URLHelper.IMAGE_BASE +
//                    SharedHelper.getKey(App.getContext(), "picture"))
//                    .error(R.drawable.ic_dummy_user)
//                    .into(img_profile);
//        }
//        if (SharedHelper.getKey(App.getContext(), "rating") != null
//                && SharedHelper.getKey(App.getContext(), "rating") != "") {
//            tvRate.setText(SharedHelper.getKey(App.getContext(), "rating") + "");
//        } else {
//            tvRate.setText("0");
//        }
//    }
//
//    private void setNavView() {
//        nav_view.setNavigationItemSelectedListener(menuItem -> {
//            switch (menuItem.getItemId()) {
//                case R.id.nav_home:
//                    drawer_layout.closeDrawers();
//                    new Handler().postDelayed(() -> startActivity(new
//                                    Intent(DriverMainActivity.this,
//                                    DriverMainActivity.class)),
//                            250);
//                    break;
//                case R.id.nav_document:
//                    drawer_layout.closeDrawers();
//                    new Handler().postDelayed(() -> startActivity(new
//                                    Intent(DriverMainActivity.this,
//                                    UploadDocumentActivity.class)),
//                            250);
//                    break;
//
//                case R.id.nav_yourtrips:
//                    drawer_layout.closeDrawers();
//                    new Handler().postDelayed(() -> startActivity(new
//                                    Intent(DriverMainActivity.this,
//                                    HistoryActivity.class)),
//                            250);
//                    break;
//                case R.id.nav_withdraw:
//                    drawer_layout.closeDrawers();
//                    new Handler().postDelayed(() -> startActivity(new
//                                    Intent(DriverMainActivity.this,
//                                    WithdrawActivity.class)),
//                            250);
//                    break;
//                case R.id.nav_wallet:
//                    drawer_layout.closeDrawers();
//                    new Handler().postDelayed(() -> startActivity(new
//                                    Intent(DriverMainActivity.this,
//                                    SummaryActivity.class)),
//                            250);
//                    break;
//                case R.id.nav_help:
//                    drawer_layout.closeDrawers();
//                    new Handler().postDelayed(() -> startActivity(new
//                                    Intent(DriverMainActivity.this,
//                                    HelpActivity.class)),
//                            250);
//                    break;
//                case R.id.nav_earnings:
//                    drawer_layout.closeDrawers();
//                    new Handler().postDelayed(() -> startActivity(new
//                                    Intent(DriverMainActivity.this,
//                                    EarningActivity.class)),
//                            250);
//                    break;
//            }
//            return true;
//        });
//
//        Menu m = nav_view.getMenu();
//
//        for (int i = 0; i < m.size(); i++) {
//            MenuItem menuItem = m.getItem(i);
//            applyFontToMenuItem(menuItem);
//
//        }
//        ActionBarDrawerToggle actionBarDrawerToggle = new
//                ActionBarDrawerToggle(this, drawer_layout, toolbar,
//                        R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
//
//                    @Override
//                    public void onDrawerClosed(View drawerView) {
//                        super.onDrawerClosed(drawerView);
//                    }
//
//                    @Override
//                    public void onDrawerOpened(View drawerView) {
//                        super.onDrawerOpened(drawerView);
//                        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
//                            navHeader();
//                        }
//                    }
//                };
//        drawer_layout.setDrawerListener(actionBarDrawerToggle);
//        actionBarDrawerToggle.syncState();
//    }
//
//    private void applyFontToMenuItem(MenuItem mi) {
//        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.ttf");
//        SpannableString mNewTitle = new SpannableString(mi.getTitle());
//        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0,
//                mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//        mi.setTitle(mNewTitle);
//    }
//
//
//    private void findViewById() {
//        LayerDrawable drawable = (LayerDrawable) rat01UserRating.getProgressDrawable();
//        drawable.getDrawable(0).setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
//        drawable.getDrawable(1).setColorFilter(Color.parseColor("#FFAB00"), PorterDuff.Mode.SRC_ATOP);
//        drawable.getDrawable(2).setColorFilter(Color.parseColor("#FFAB00"), PorterDuff.Mode.SRC_ATOP);
//        LayerDrawable stars02 = (LayerDrawable) rat02UserRating.getProgressDrawable();
//        stars02.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
//        LayerDrawable drawable_02 = (LayerDrawable) rat03UserRating.getProgressDrawable();
//        drawable_02.getDrawable(0).setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
//        drawable_02.getDrawable(1).setColorFilter(Color.parseColor("#FFAB00"), PorterDuff.Mode.SRC_ATOP);
//        drawable_02.getDrawable(2).setColorFilter(Color.parseColor("#FFAB00"), PorterDuff.Mode.SRC_ATOP);
//        LayerDrawable stars05 = (LayerDrawable) rat05UserRating.getProgressDrawable();
//        stars05.getDrawable(0).setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
//        stars05.getDrawable(1).setColorFilter(Color.parseColor("#FFAB00"), PorterDuff.Mode.SRC_ATOP);
//        stars05.getDrawable(2).setColorFilter(Color.parseColor("#FFAB00"), PorterDuff.Mode.SRC_ATOP);
//
//        earning = SharedHelper.getKey(App.getContext(), "totalearning");
//        total_earn_layout.setVisibility(View.VISIBLE);
//        if (earning != null && !earning.isEmpty() && !earning.equals("")) {
//            txtTotalEarning.setText(earning);
//        } else
//            txtTotalEarning.setText(SharedHelper.getKey(App.getContext(), "currency") + " 0.00");
//        slide_down = AnimationUtils.loadAnimation(App.getContext(), R.anim.slide_down);
//        slide_up = AnimationUtils.loadAnimation(App.getContext(), R.anim.slide_up);
//
//        user_name.setText(SharedHelper.getKey(App.getContext(), "first_name"));
//        user_type.setText(SharedHelper.getKey(App.getContext(), "service"));
//
//        if (SharedHelper.getKey(App.getContext(), "picture") != null) {
//            Picasso.get().load(URLHelper.IMAGE_BASE +
//                    SharedHelper.getKey(App.getContext(), "picture"))
//                    .error(R.drawable.ic_dummy_user)
//                    .into(img_profile);
//        }
//
//        img_profile.setOnClickListener(v -> startActivity(new Intent(DriverMainActivity.this,
//                Profile.class)));
//        sos.setOnClickListener(v -> showSosDialog());
//        lnrGoOffline.setVisibility(View.GONE);
//    }
//
//    private void mapClear() {
//        if (mMap != null) {
//            if (!crt_lat.equalsIgnoreCase("") && !crt_lat.equalsIgnoreCase("")) {
//                LatLng myLocation = new LatLng(Double.parseDouble(crt_lat), Double.parseDouble(crt_lng));
//                CameraPosition cameraPosition = new CameraPosition.Builder().target(myLocation).zoom(14).build();
//                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//            }
//            mMap.clear();
//            srcLatitude = 0;
//            srcLongitude = 0;
//            destLatitude = 0;
//            destLongitude = 0;
//        }
//    }
//
//    public void clearVisibility() {
//
//        if (ll_01_contentLayer_accept_or_reject_now.getVisibility() == View.VISIBLE) {
//            ll_01_contentLayer_accept_or_reject_now.startAnimation(slide_down);
//        } else if (ll_02_contentLayer_accept_or_reject_later.getVisibility() == View.VISIBLE) {
//            ll_02_contentLayer_accept_or_reject_later.startAnimation(slide_down);
//        } else if (ll_03_contentLayer_service_flow.getVisibility() == View.VISIBLE) {
//        } else if (ll_04_contentLayer_payment.getVisibility() == View.VISIBLE) {
//            ll_04_contentLayer_payment.startAnimation(slide_down);
//        } else if (ll_04_contentLayer_payment.getVisibility() == View.VISIBLE) {
//            ll_04_contentLayer_payment.startAnimation(slide_down);
//        } else if (ll_05_contentLayer_feedback.getVisibility() == View.VISIBLE) {
//            ll_05_contentLayer_feedback.startAnimation(slide_down);
//        }
//
//        ll_01_contentLayer_accept_or_reject_now.setVisibility(View.GONE);
//        ll_02_contentLayer_accept_or_reject_later.setVisibility(View.GONE);
//        ll_03_contentLayer_service_flow.setVisibility(View.GONE);
//        ll_04_contentLayer_payment.setVisibility(View.GONE);
//        ll_05_contentLayer_feedback.setVisibility(View.GONE);
//        lnrGoOffline.setVisibility(View.GONE);
//    }
//
//    @TargetApi(Build.VERSION_CODES.M)
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case 1:
//                if (grantResults.length > 0) {
//                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                        setUpMapIfNeeded();
//                        MapsInitializer.initialize(DriverMainActivity.this);
//
//                        if (ContextCompat.checkSelfPermission(DriverMainActivity.this,
//                                Manifest.permission.ACCESS_FINE_LOCATION)
//                                == PackageManager.PERMISSION_GRANTED) {
//
//                            if (mGoogleApiClient == null) {
//                                buildGoogleApiClient();
//                            }
//                            setUpMapIfNeeded();
//                            MapsInitializer.initialize(DriverMainActivity.this);
//
//                        }
//                    } else {
//                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//                    }
//                }
//                break;
//            default:
//                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }
//    }
//
//    private void setUpMapIfNeeded() {
//        if (mMap == null) {
//            FragmentManager fm = getSupportFragmentManager();
//            mapFragment = ((SupportMapFragment) fm.findFragmentById(R.id.provider_map));
//            mapFragment.getMapAsync(this);
//        }
//        if (mMap != null) {
//            setupMap();
//        }
//    }
//
//    private void setSourceLocationOnMap(LatLng latLng) {
//        if (latLng != null) {
//            mMap.clear();
//            CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(14).build();
//            MarkerOptions options = new MarkerOptions().position(latLng).anchor(0.5f, 0.75f);
//            options.position(latLng).isDraggable();
//            mMap.addMarker(options);
//            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//        }
//    }
//
//    private void setPickupLocationOnMap() {
//        if (mMap != null) {
//            mMap.clear();
//        }
//        sourceLatLng = currentLatLng;
//        if (currentTripStatus.equals("PICKEDUP")) {
//            destLatLng = new LatLng(destLatitude, destLongitude);
//            if (sourceLatLng != null && destLatLng != null) {
//                try {
//                    trackPickToDest(destLatLng.latitude, destLatLng.longitude);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        } else {
//            destLatLng = new LatLng(srcLatitude, srcLongitude);
//            if (sourceLatLng != null && destLatLng != null) {
//                try {
//                    trackPickToDest(destLatLng.latitude, destLatLng.longitude);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    private void setDestinationLocationOnMap() {
//        if (currentLatLng != null) {
//            sourceLatLng = currentLatLng;
//            destLatLng = new LatLng(destLatitude, destLongitude);
//            try {
//                trackPickToDest(destLatLng.latitude, destLatLng.longitude);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @SuppressWarnings("MissingPermission")
//    private void setupMap() {
//        mMap.setMyLocationEnabled(false);
//        mMap.getUiSettings().setMyLocationButtonEnabled(false);
//        mMap.setBuildingsEnabled(true);
//        mMap.getUiSettings().setCompassEnabled(false);
//        mMap.getUiSettings().setRotateGesturesEnabled(false);
//        mMap.getUiSettings().setTiltGesturesEnabled(false);
//        mMap.setOnCameraMoveListener(this);
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        try {
//            boolean success = googleMap.setMapStyle(
//                    MapStyleOptions.loadRawResourceStyle(
//                            DriverMainActivity.this, R.raw.style_json));
//            if (!success) {
//                Log.e("DriverMapFragment:Style", "Style parsing failed.");
//            } else {
//                Log.e("DriverMapFragment:Style", "Style Applied.");
//            }
//        } catch (Resources.NotFoundException e) {
//            Log.e("DriverMapFragment:Style", "Can't find style. Error: ", e);
//        }
//        mMap = googleMap;
//        setupMap();
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (ContextCompat.checkSelfPermission(DriverMainActivity.this,
//                    Manifest.permission.ACCESS_FINE_LOCATION)
//                    == PackageManager.PERMISSION_GRANTED) {
//                buildGoogleApiClient();
//            } else {
//                checkLocationPermission();
//            }
//        } else {
//            buildGoogleApiClient();
//        }
//    }
//
//    private void checkLocationPermission() {
//        if (ContextCompat.checkSelfPermission(DriverMainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(DriverMainActivity.this,
//                    Manifest.permission.ACCESS_FINE_LOCATION)) {
//                new AlertDialog.Builder(DriverMainActivity.this)
//                        .setTitle("Location Permission Needed")
//                        .setMessage("This app needs the Location permission, please accept to use location functionality")
//                        .setPositiveButton("OK", (dialogInterface, i) -> {
//                            ActivityCompat.requestPermissions(DriverMainActivity.this,
//                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                                    1);
//                        })
//                        .create()
//                        .show();
//            } else {
//                ActivityCompat.requestPermissions(DriverMainActivity.this,
//                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                        1);
//            }
//        }
//    }
//
//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//        mLocationRequest = new LocationRequest();
//        mLocationRequest.setInterval(3000);
//        mLocationRequest.setFastestInterval(3000);
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
//        if (ContextCompat.checkSelfPermission(DriverMainActivity.this,
//                Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED && mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
//            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
//        }
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }
//
//    @Override
//    public void onLocationChanged(Location location) {
//        //GPSTracker gps = new GPSTracker(DriverMainActivity.this);
//        if ((customDialog != null) && (customDialog.isShowing()))
//            customDialog.dismiss();
//        if (mMap != null) {
//            if (currentMarker != null) {
//                currentMarker.remove();
//            }
//
//            MarkerOptions markerOptions1 = new MarkerOptions()
//                    .position(new LatLng(location.getLatitude(), location.getLongitude()))
//                    .anchor(0.5f, 0.5f).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_map_current_location));
//            currentMarker = mMap.addMarker(markerOptions1);
//
//            Log.e("DriverSide", "DriveronLocationChanged: " + location.getLatitude());
//            Log.e("DriverSide", "DriveronLocationChanged: " + location.getLongitude());
//
//            if (value == 0) {
//                myLat = String.valueOf(location.getLatitude());
//                myLng = String.valueOf(location.getLongitude());
//
//                LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
//                CameraPosition cameraPosition = new CameraPosition.Builder().target(myLocation).zoom(16).build();
//                MarkerOptions markerOptions = new MarkerOptions();
//                markerOptions.position(myLocation).anchor(0.5f, 0.75f);
//                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//                value++;
//            }
//
//            crt_lat = String.valueOf(location.getLatitude());
//            Log.e(TAG, "crt_lat" + crt_lat);
//            crt_lng = String.valueOf(location.getLongitude());
//            Log.e(TAG, "crt_lng" + crt_lng);
//            currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
//
//            checkStatus();
//
////            if (type != null) {
////                checkStatusSchedule();
////            } else {
////                checkStatus();
////            }
//
//        }
//
//    }
//
//    @Override
//    public void onCameraMove() {
//        utils.print("Current marker", "Zoom Level " + mMap.getCameraPosition().zoom);
//        if (currentMarker != null) {
//            if (!mMap.getProjection().getVisibleRegion().latLngBounds.contains(currentMarker.getPosition())) {
//                utils.print("Current marker", "Current Marker is not visible");
//                if (imgCurrentLoc.getVisibility() == View.GONE) {
//                    imgCurrentLoc.setVisibility(View.VISIBLE);
//                }
//            } else {
//                utils.print("Current marker", "Current Marker is visible");
//                if (imgCurrentLoc.getVisibility() == View.VISIBLE) {
//                    imgCurrentLoc.setVisibility(View.GONE);
//                }
//                if (mMap.getCameraPosition().zoom < 15.0f) {
//                    if (imgCurrentLoc.getVisibility() == View.GONE) {
//                        imgCurrentLoc.setVisibility(View.VISIBLE);
//                    }
//                }
//            }
//        }
//    }
//
//    private void checkStatus() {
//        try {
//
//            if (helper.isConnectingToInternet()) {
//                String url = URLHelper.BASE + "api/provider/trip?latitude=" + crt_lat + "&longitude=" + crt_lng;
//
//                utils.print("Destination Current Lat", "" + crt_lat);
//                utils.print("Destination Current Lng", "" + crt_lng);
//                Log.i(TAG, "checkStatus url : " + url);
//
//                final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
//                    if ((customDialog != null) && (customDialog.isShowing()))
//                        customDialog.dismiss();
//                    if (errorLayout.getVisibility() == View.VISIBLE) {
//                        errorLayout.setVisibility(View.GONE);
//                    }
//                    Log.e("CheckStatus", "" + response.toString());
//                    try {
//                        serviceStatus = response.optString("service_status");
//                        if (response.optString("service_status").equalsIgnoreCase("offline")) {
//
//                            online_offline_switch.setChecked(false);
//                            active_Status.setText(App.getContext().getString(R.string.offline));
//                            offline_layout.setVisibility(View.VISIBLE);
//                        }
//                        try {
//                            tvCommision.setText(response.optString("commision"));
//                            tvEarning.setText(response.optString("earnings"));
//                            tvTrips.setText(response.optString("trips"));
//                            txtTotalEarning.setText(SharedHelper.getKey(App.getContext(), "currency") + response.optString("earnings"));
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        if (response.optJSONArray("requests").length() > 0) {
//
//                            providerId = response.optJSONArray("requests")
//                                    .getJSONObject(0).optJSONObject("request")
//                                    .optString("provider_id");
//                            userID = response.optJSONArray("requests")
//                                    .getJSONObject(0).optJSONObject("request")
//                                    .optString("user_id");
//
//                            JSONObject jsonObject = response.optJSONArray("requests")
//                                    .getJSONObject(0).optJSONObject("request").optJSONObject("user");
//                            userFirstName = jsonObject.optString("first_name");
//                            user.setFirstName(jsonObject.optString("first_name"));
////                                user.setLastName(jsonObject.optString("last_name"));
//                            user.setEmail(jsonObject.optString("email"));
//                            if (jsonObject.optString("picture").startsWith("http"))
//                                user.setImg(jsonObject.optString("picture"));
//                            else
//                                user.setImg(URLHelper.BASE + "storage/app/public/" + jsonObject.optString("picture"));
//                            user.setRating(jsonObject.optString("rating"));
//                            user.setMobile(jsonObject.optString("mobile"));
//                            bookingId = response.optJSONArray("requests").getJSONObject(0)
//                                    .optJSONObject("request").optString("booking_id");
//                            address = response.optJSONArray("requests").getJSONObject(0).optJSONObject("request").optString("s_address");
//                            daddress = response.optJSONArray("requests").getJSONObject(0).optJSONObject("request").optString("d_address");
//
//
//                            lblCmfrmSourceAddress.setText(address);
//                            lblCmfrmDestAddress.setText(daddress);
//
//                            if (response.optJSONObject("item")!=null)
//                            {
//                                itemObject =response.optJSONObject("item");
//                            }
//
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                    if (response.optString("account_status").equals("new") ||
//                            response.optString("account_status").equals("onboarding")) {
//                        ha.removeMessages(0);
////                        checkDocumentStatus();
//                        documentList();
//                    } else {
//
//                        if (response.optString("service_status").equals("offline")) {
//                            ha.removeMessages(0);
//                        } else {
//
//                            if (response.optJSONArray("requests") != null && response.optJSONArray("requests").length() > 0) {
//                                JSONObject statusResponse = null;
//                                try {
//                                    statusResponses = response.optJSONArray("requests");
//                                    statusResponse = response.optJSONArray("requests").getJSONObject(0).optJSONObject("request");
//                                    request_id = response.optJSONArray("requests").getJSONObject(0).optString("request_id");
//                                    if (statusResponse.optString("special_note") != null &&
//                                            statusResponse.optString("special_note") != "null") {
//                                        layoutNotes.setVisibility(View.VISIBLE);
//                                        txtNotes.setText(statusResponse.getString("special_note"));
//                                    }
//
//                                    Log.e("request_idjson", request_id + "");
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//
//                                if ((statusResponse != null) && (request_id != null)) {
//                                    currentTripStatus = statusResponse.optString("status");
//                                    srcLatitude = Double.valueOf(statusResponse.optString("s_latitude"));
//                                    srcLongitude = Double.valueOf(statusResponse.optString("s_longitude"));
//
//                                    destLatitude = Double.valueOf(statusResponse.optString("d_latitude"));
//                                    destLongitude = Double.valueOf(statusResponse.optString("d_longitude"));
//                                    Log.d("currentTripStatus", currentTripStatus);
//                                    if ((!previous_request_id.equals(request_id) || previous_request_id.equals(" ")) && mMap != null) {
//                                        previous_request_id = request_id;
//                                        srcLatitude = Double.valueOf(statusResponse.optString("s_latitude"));
//                                        srcLongitude = Double.valueOf(statusResponse.optString("s_longitude"));
//
//                                        destLatitude = Double.valueOf(statusResponse.optString("d_latitude"));
//                                        destLongitude = Double.valueOf(statusResponse.optString("d_longitude"));
//                                        //noinspection deprecation
//                                        setSourceLocationOnMap(currentLatLng);
//                                        setPickupLocationOnMap();
//                                        sos.setVisibility(View.GONE);
//
//                                    }
//                                    utils.print("Cur_and_New_status :", "" + CurrentStatus + "," + statusResponse.optString("status"));
////                                        String ok = "ok";
////                                        if (ok.equals(ok))
//                                    if (!PreviousStatus.equals(statusResponse.optString("status"))) {
////                                            || statusResponse.optString("paid").equals("1") || statusResponse.optString("paid").equals("0")
//                                        PreviousStatus = statusResponse.optString("status");
//                                        clearVisibility();
//                                        utils.print("responseObj(" + request_id + ")", statusResponse.toString());
//                                        utils.print("Cur_and_New_status :", "" + CurrentStatus + "," + statusResponse.optString("status"));
//                                        if (!statusResponse.optString("status").equals("SEARCHING")) {
//                                            timerCompleted = false;
//                                            if (mPlayer != null && mPlayer.isPlaying()) {
//                                                mPlayer.stop();
//                                                mPlayer = null;
//                                                countDownTimer.cancel();
//                                            }
//                                        }
//                                        if (statusResponse.optString("status").equals("SEARCHING")) {
//                                            scheduleTrip = false;
//                                            if (!timerCompleted) {
//                                                setValuesTo_ll_01_contentLayer_accept_or_reject_now(statusResponses);
//                                                if (ll_01_contentLayer_accept_or_reject_now.getVisibility() == View.GONE) {
//                                                    ll_01_contentLayer_accept_or_reject_now.startAnimation(slide_up);
//                                                }
//                                                ll_01_contentLayer_accept_or_reject_now.setVisibility(View.VISIBLE);
//                                            }
//                                            CurrentStatus = "STARTED";
//                                        } else if (statusResponse.optString("status").equals("STARTED")) {
//                                            setValuesTo_ll_03_contentLayer_service_flow(statusResponses, response);
//                                            ll_03_contentLayer_service_flow.setVisibility(View.VISIBLE);
//                                            try {
//                                                btn_01_status.setText(App.getContext().getString(R.string.tap_when_arrived));
//                                            } catch (NullPointerException ne) {
//                                                btn_01_status.setText(App.getContext().getString(R.string.tap_when_arrived));
//                                            }
//                                            CurrentStatus = "ARRIVED";
//                                            sos.setVisibility(View.GONE);
//                                            if (srcLatitude == 0 && srcLongitude == 0 && destLatitude == 0 && destLongitude == 0) {
//                                                mapClear();
//                                                srcLatitude = Double.valueOf(statusResponse.optString("s_latitude"));
//                                                srcLongitude = Double.valueOf(statusResponse.optString("s_longitude"));
//                                                destLatitude = Double.valueOf(statusResponse.optString("d_latitude"));
//                                                destLongitude = Double.valueOf(statusResponse.optString("d_longitude"));
//                                                //noinspection deprecation
//                                                //
////                                                setSourceLocationOnMap(currentLatLng);
////                                                setPickupLocationOnMap();
//                                            }
//                                            sos.setVisibility(View.GONE);
//                                            btn_cancel_ride.setVisibility(View.VISIBLE);
//                                            destinationLayer.setVisibility(View.VISIBLE);
//                                            layoutinfo.setVisibility(View.GONE);
//                                            String address = statusResponse.optString("s_address");
//                                            if (address != null && !address.equalsIgnoreCase("null") && address.length() > 0)
//                                                destination.setText(address);
//                                            else
//                                                destination.setText(getAddress(statusResponse.optString("s_latitude"),
//                                                        statusResponse.optString("s_longitude")));
//                                            try {
//                                                topSrcDestTxtLbl.setText(App.getContext().getString(R.string.pick_up));
//                                            } catch (NullPointerException ne) {
//                                                ne.printStackTrace();
//                                                topSrcDestTxtLbl.setText(App.getContext().getString(R.string.pick_up));
//                                            }
//
//                                            setSourceLocationOnMap(currentLatLng);
//                                            setPickupLocationOnMap();
//
//                                        } else if (statusResponse.optString("status").equals("ARRIVED")) {
//                                            setValuesTo_ll_03_contentLayer_service_flow(statusResponses, response);
//                                            ll_03_contentLayer_service_flow.setVisibility(View.VISIBLE);
//                                            try {
//                                                btn_01_status.setText(App.getContext().getString(R.string.tap_when_pickedup));
//                                            } catch (Exception e) {
//                                                e.printStackTrace();
//                                            }
//                                            sos.setVisibility(View.GONE);
//                                            img03Status1.setImageResource(R.drawable.arrived_select);
//                                            img03Status2.setImageResource(R.drawable.pickeddisable);
//                                            driveraccepted.setVisibility(View.VISIBLE);
//                                            driverArrived.setVisibility(View.GONE);
//                                            driverPicked.setVisibility(View.GONE);
//                                            CurrentStatus = "PICKEDUP";
//                                            driveraccepted.setVisibility(View.GONE);
//                                            driverArrived.setVisibility(View.VISIBLE);
//                                            driverPicked.setVisibility(View.GONE);
//
//                                            btn_cancel_ride.setVisibility(View.VISIBLE);
//                                            destinationLayer.setVisibility(View.VISIBLE);
//                                            String address = statusResponse.optString("d_address");
//                                            try {
//                                                if (address != null && !address.equalsIgnoreCase("null") && address.length() > 0)
//                                                    destination.setText(address);
//                                                else
//                                                    destination.setText(getAddress(statusResponse.optString("d_latitude"),
//                                                            statusResponse.optString("d_longitude")));
//                                            } catch (Exception e) {
//                                                e.printStackTrace();
//                                            }
//                                            try {
//                                                topSrcDestTxtLbl.setText(App.getContext().getString(R.string.drop_at));
//                                            } catch (Exception e) {
//                                                topSrcDestTxtLbl.setText(App.getContext().getString(R.string.drop_at));
//                                            }
//                                            setSourceLocationOnMap(currentLatLng);
//                                            setPickupLocationOnMap();
//
//                                        } else if (statusResponse.optString("status").equals("PICKEDUP")) {
//                                            setValuesTo_ll_03_contentLayer_service_flow(statusResponses, response);
//                                            ll_03_contentLayer_service_flow.setVisibility(View.VISIBLE);
//                                            try {
//                                                btn_01_status.setText(App.getContext().getString(R.string.tap_when_dropped));
//                                            } catch (Exception e) {
//                                                e.printStackTrace();
//                                            }
//                                            sos.setVisibility(View.VISIBLE);
////                                                navigate.setVisibility(View.VISIBLE);
//                                            img03Status1.setImageResource(R.drawable.arrived_select);
//                                            img03Status2.setImageResource(R.drawable.pickup_select);
//                                            driveraccepted.setVisibility(View.GONE);
//                                            driverArrived.setVisibility(View.GONE);
//                                            driverPicked.setVisibility(View.VISIBLE);
//                                            CurrentStatus = "DROPPED";
//                                            destinationLayer.setVisibility(View.VISIBLE);
//                                            layoutinfo.setVisibility(View.GONE);
//                                            btn_cancel_ride.setVisibility(View.GONE);
//                                            String address = statusResponse.optString("d_address");
//                                            try {
//                                                if (address != null && !address.equalsIgnoreCase("null") && address.length() > 0)
//                                                    destination.setText(address);
//                                                else
//                                                    destination.setText(getAddress(statusResponse.optString("d_latitude"),
//                                                            statusResponse.optString("d_longitude")));
//                                            } catch (NullPointerException ne) {
//                                                ne.printStackTrace();
//                                            }
//                                            topSrcDestTxtLbl.setText(App.getContext().getString(R.string.drop_at));
//
//                                            mapClear();
//                                            srcLatitude = Double.valueOf(statusResponse.optString("s_latitude"));
//                                            srcLongitude = Double.valueOf(statusResponse.optString("s_longitude"));
//                                            destLatitude = Double.valueOf(statusResponse.optString("d_latitude"));
//                                            destLongitude = Double.valueOf(statusResponse.optString("d_longitude"));
//                                            //noinspection deprecation
//                                            //
//                                            setSourceLocationOnMap(currentLatLng);
//                                            setPickupLocationOnMap();
//
//
//                                        } else if (statusResponse.optString("status").equals("DROPPED")
//                                                && statusResponse.optString("paid").equals("0")) {
//                                            setValuesTo_ll_04_contentLayer_payment(statusResponses);
//                                            if (ll_04_contentLayer_payment.getVisibility() == View.GONE) {
//                                                ll_04_contentLayer_payment.startAnimation(slide_up);
//                                            }
//                                            ll_04_contentLayer_payment.setVisibility(View.VISIBLE);
//                                            img03Status1.setImageResource(R.drawable.arriveddisable);
//                                            img03Status2.setImageResource(R.drawable.pickeddisable);
//                                            driveraccepted.setVisibility(View.VISIBLE);
//                                            driverArrived.setVisibility(View.GONE);
//                                            driverPicked.setVisibility(View.GONE);
//                                            try {
//                                                btn_01_status.setText(App.getContext().getString(R.string.confirm_payment));
//                                            } catch (Exception e) {
//                                                e.printStackTrace();
//                                            }
//                                            sos.setVisibility(View.VISIBLE);
////                                                navigate.setVisibility(View.GONE);
//                                            destinationLayer.setVisibility(View.GONE);
//                                            layoutinfo.setVisibility(View.VISIBLE);
//                                            CurrentStatus = "COMPLETED";
//
//                                            LocationTracking.distance = 0.0f;
//                                        } else if (statusResponse.optString("status").equals("DROPPED")
//                                                && statusResponse.optString("paid").equals("0")) {
//                                            setValuesTo_ll_04_contentLayer_payment(statusResponses);
//                                            if (ll_04_contentLayer_payment.getVisibility() == View.GONE) {
//                                                ll_04_contentLayer_payment.startAnimation(slide_up);
//                                            }
//                                            ll_04_contentLayer_payment.setVisibility(View.VISIBLE);
//                                            img03Status1.setImageResource(R.drawable.arriveddisable);
//                                            img03Status2.setImageResource(R.drawable.pickeddisable);
//                                            driveraccepted.setVisibility(View.VISIBLE);
//                                            driverArrived.setVisibility(View.GONE);
//                                            driverPicked.setVisibility(View.GONE);
//                                            try {
//                                                btn_01_status.setText(App.getContext().getString(R.string.confirm_payment));
//                                            } catch (Exception e) {
//                                                e.printStackTrace();
//                                            }
//                                            sos.setVisibility(View.VISIBLE);
////                                                navigate.setVisibility(View.GONE);
//                                            destinationLayer.setVisibility(View.GONE);
//                                            layoutinfo.setVisibility(View.VISIBLE);
//                                            CurrentStatus = "COMPLETED";
//
//                                            LocationTracking.distance = 0.0f;
//                                        } else if (statusResponse.optString("status").equals("COMPLETED")
//                                                && statusResponse.optString("paid").equals("0")) {
//
//                                            setValuesTo_ll_04_contentLayer_payment(statusResponses);
//                                            if (ll_04_contentLayer_payment.getVisibility() == View.GONE) {
//                                                ll_04_contentLayer_payment.startAnimation(slide_up);
//                                            }
//                                            ll_04_contentLayer_payment.setVisibility(View.VISIBLE);
//                                            img03Status1.setImageResource(R.drawable.arriveddisable);
//                                            img03Status2.setImageResource(R.drawable.pickeddisable);
//                                            driveraccepted.setVisibility(View.VISIBLE);
//                                            driverArrived.setVisibility(View.GONE);
//                                            driverPicked.setVisibility(View.GONE);
//                                            try {
//                                                btn_01_status.setText(App.getContext().getString(R.string.confirm_payment));
//                                            } catch (Exception e) {
//                                                e.printStackTrace();
//                                            }
//                                            sos.setVisibility(View.VISIBLE);
////                                                navigate.setVisibility(View.GONE);
//                                            destinationLayer.setVisibility(View.GONE);
//                                            layoutinfo.setVisibility(View.VISIBLE);
//                                            CurrentStatus = "COMPLETED";
//
//                                            LocationTracking.distance = 0.0f;
//                                        } else if (statusResponse.optString("status").equals("COMPLETED")
//                                                && statusResponse.optString("paid").equals("1")) {
////                                                ok = "not";
//                                            if (ll_04_contentLayer_payment.getVisibility() == View.VISIBLE) {
//                                                ll_04_contentLayer_payment.setVisibility(View.GONE);
//                                            }
//
//                                            setValuesTo_ll_05_contentLayer_feedback(statusResponses);
//                                            if (ll_05_contentLayer_feedback.getVisibility() == View.GONE) {
//                                                ll_05_contentLayer_feedback.startAnimation(slide_up);
//                                            }
//                                            ll_04_contentLayer_payment.setVisibility(View.GONE);
//                                            edt05Comment.setText("");
//                                            ll_05_contentLayer_feedback.setVisibility(View.VISIBLE);
//                                            sos.setVisibility(View.GONE);
//                                            destinationLayer.setVisibility(View.GONE);
//                                            layoutinfo.setVisibility(View.VISIBLE);
//                                            try {
//                                                btn_01_status.setText(App.getContext().getString(R.string.submit));
//                                            } catch (Exception e) {
//                                                e.printStackTrace();
//                                            }
//
//                                            CurrentStatus = "RATE";
//
//                                            LocationTracking.distance = 0.0f;
//                                        } else if (statusResponse.optString("status").equals("SCHEDULED")) {
//                                            if (mMap != null) {
//                                                if (ActivityCompat.checkSelfPermission(App.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(App.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                                                    return;
//                                                }
//                                                mMap.clear();
//                                            }
//                                            clearVisibility();
//                                            CurrentStatus = "SCHEDULED";
//                                            utils.print("statusResponse", "null");
//                                            destinationLayer.setVisibility(View.GONE);
//                                            layoutinfo.setVisibility(View.VISIBLE);
//
//                                            LocationTracking.distance = 0.0f;
//                                        }
//                                    }
//                                } else {
//                                    if (mMap != null) {
//                                        if (ActivityCompat.checkSelfPermission(App.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(App.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                                            return;
//                                        }
//                                        timerCompleted = false;
//                                        mMap.clear();
//                                        if (mPlayer != null && mPlayer.isPlaying()) {
//                                            mPlayer.stop();
//                                            mPlayer = null;
//                                            countDownTimer.cancel();
//                                        }
//
//                                    }
//
//                                    LocationTracking.distance = 0.0f;
//
//                                    clearVisibility();
//                                    destinationLayer.setVisibility(View.GONE);
//                                    layoutinfo.setVisibility(View.VISIBLE);
//                                    CurrentStatus = "ONLINE";
//                                    PreviousStatus = "NULL";
//                                    utils.print("statusResponse", "null");
//                                }
//
//                            } else {
//                                timerCompleted = false;
//                                if (!PreviousStatus.equalsIgnoreCase("NULL")) {
//                                    utils.print("response", "null");
//                                    if (mMap != null) {
//                                        if (ActivityCompat.checkSelfPermission(App.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(App.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                                            return;
//                                        }
//                                        mMap.clear();
//                                    }
//                                    if (mPlayer != null && mPlayer.isPlaying()) {
//                                        mPlayer.stop();
//                                        mPlayer = null;
//                                        countDownTimer.cancel();
//                                    }
//                                    clearVisibility();
//                                    lnrGoOffline.setVisibility(View.VISIBLE);
//                                    destinationLayer.setVisibility(View.GONE);
//                                    layoutinfo.setVisibility(View.VISIBLE);
//                                    CurrentStatus = "ONLINE";
//                                    PreviousStatus = "NULL";
//                                    utils.print("statusResponse", "null");
//
//                                    LocationTracking.distance = 0.0f;
//                                }
//
//                            }
//                        }
//                    }
//                }, error -> {
//                    utils.print("Error", error.toString());
//                    //errorHandler(error);
//                    timerCompleted = false;
//                    mapClear();
//                    clearVisibility();
//                    CurrentStatus = "ONLINE";
//                    PreviousStatus = "NULL";
//                    destinationLayer.setVisibility(View.GONE);
//                    layoutinfo.setVisibility(View.VISIBLE);
//                    if (mPlayer != null && mPlayer.isPlaying()) {
//                        mPlayer.stop();
//                        mPlayer = null;
//                        countDownTimer.cancel();
//                    }
//                    displayMessage(error.toString());
//                }) {
//                    @Override
//                    public java.util.Map<String, String> getHeaders() {
//                        HashMap<String, String> headers = new HashMap<>();
//                        headers.put("X-Requested-With", "XMLHttpRequest");
//                        headers.put("Authorization", "Bearer " + token);
//                        return headers;
//                    }
//                };
//                App.getInstance().addToRequestQueue(jsonObjectRequest);
//            } else {
//                displayMessage(App.getContext().getString(R.string.oops_connect_your_internet));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void setValuesTo_ll_01_contentLayer_accept_or_reject_now(JSONArray status) {
//        JSONObject statusResponse = new JSONObject();
//        try {
//            statusResponse = status.getJSONObject(0).getJSONObject("request");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        try {
//            if (!status.getJSONObject(0).optString("time_left_to_respond").equals("")) {
//                count = status.getJSONObject(0).getString("time_left_to_respond");
//            } else {
//                count = "0";
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        countDownTimer = new CountDownTimer(Integer.parseInt(count) * 1000, 1000) {
//
//            @SuppressLint("SetTextI18n")
//            public void onTick(long millisUntilFinished) {
//                txt01Timer.setText("" + millisUntilFinished / 1000);
//                try {
//                    if (mPlayer == null) {
//                        mPlayer = MediaPlayer.create(App.getContext(), R.raw.alert_tone);
//                    } else {
//                        if (!mPlayer.isPlaying()) {
//                            mPlayer.start();
//                        }
//                    }
//                    isRunning = true;
//                    timerCompleted = false;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            public void onFinish() {
//                txt01Timer.setText("0");
//                mapClear();
//                clearVisibility();
//                if (mMap != null) {
//                    mMap.clear();
//                }
//                if (mPlayer != null && mPlayer.isPlaying()) {
//                    mPlayer.stop();
//                    mPlayer = null;
//                }
//                ll_01_contentLayer_accept_or_reject_now.setVisibility(View.GONE);
//                CurrentStatus = "ONLINE";
//                PreviousStatus = "NULL";
//                destinationLayer.setVisibility(View.GONE);
//                isRunning = false;
//                timerCompleted = true;
//                rejectRequest(request_id);
////                handleIncomingRequest("Reject", request_id);
//            }
//        };
//
//
//        countDownTimer.start();
//
//        try {
//            if (!statusResponse.optString("schedule_at").trim().equalsIgnoreCase("") && !statusResponse.optString("schedule_at").equalsIgnoreCase("null")) {
//                txtSchedule.setVisibility(View.VISIBLE);
//                String strSchedule = "";
//                try {
//                    strSchedule = Utils.getDate(statusResponse.optString("schedule_at")) + "th " +
//                            Utils.getMonth(statusResponse.optString("schedule_at")) + " " +
//                            Utils.getYear(statusResponse.optString("schedule_at")) + " at " +
//                            Utils.getTime(statusResponse.optString("schedule_at"));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                txtSchedule.setText(App.getContext().getString(R.string.schedulet_at) + strSchedule);
//            } else {
//                txtSchedule.setVisibility(View.GONE);
//            }
//
//            final JSONObject user = statusResponse.getJSONObject("user");
//            if (user != null) {
//                if (!user.optString("picture").equals("null")) {
//                    if (user.optString("picture").startsWith("http"))
//                        Picasso.get().load(user.getString("picture")).placeholder(R.drawable.ic_dummy_user).error(R.drawable.ic_dummy_user).into(img01User);
//                    else
//                        Picasso.get().load(URLHelper.BASE + "storage/app/public/" + user.getString("picture")).placeholder(R.drawable.ic_dummy_user).error(R.drawable.ic_dummy_user).into(img01User);
//                } else {
//                    img01User.setImageResource(R.drawable.ic_dummy_user);
//                }
//                final User userProfile = this.user;
//                img01User.setOnClickListener(v -> {
//                    Intent intent = new Intent(DriverMainActivity.this, ShowProfile.class);
//                    intent.putExtra("user", userProfile);
//                    startActivity(intent);
//                });
//                txt01UserName.setText(user.optString("first_name"));
//                if (!statusResponse.isNull("distance")) {
//                    Double d = Double.parseDouble(statusResponse.optString("distance"));
//                    tvDistance.setText(Math.round(d) + "KM");
//                }
//                if (statusResponse.getJSONObject("user").getString("rating") != null) {
//                    rat01UserRating.setRating(Float.valueOf(user.getString("rating")));
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        txt01Pickup.setText(address);
//        txtDropOff.setText(daddress);
//    }
//
//    private void setValuesTo_ll_03_contentLayer_service_flow(JSONArray status, JSONObject responess) {
//        JSONObject statusResponse = new JSONObject();
//        Log.e(TAG, "statusResponse: " + statusResponse);
//        try {
//            statusResponse = status.getJSONObject(0).getJSONObject("request");
//            lblCmfrmSourceAddress.setText(statusResponse.optString("s_address"));
//            lblCmfrmDestAddress.setText(statusResponse.optString("d_address"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        try {
//            JSONObject user = statusResponse.getJSONObject("user");
//            if (user != null) {
//                if (!user.optString("mobile").equals("null")) {
//                    SharedHelper.putKey(App.getContext(), "provider_mobile_no", "" + user.optString("mobile"));
//                } else {
//                    SharedHelper.putKey(App.getContext(), "provider_mobile_no", "");
//                }
//
//                if (!user.optString("picture").equals("null")) {
//                    if (user.optString("picture").startsWith("http"))
//                        Picasso.get().load(user.getString("picture")).placeholder(R.drawable.ic_dummy_user).error(R.drawable.ic_dummy_user).into(img03User);
//                    else
//                        Picasso.get().load(URLHelper.BASE + "storage/app/public/" + user.getString("picture")).placeholder(R.drawable.ic_dummy_user).error(R.drawable.ic_dummy_user).into(img03User);
//                } else {
//                    img03User.setImageResource(R.drawable.ic_dummy_user);
//                }
//                final User userProfile = this.user;
//                img03User.setOnClickListener(v -> {
//                    Intent intent = new Intent(DriverMainActivity.this, ShowProfile.class);
//                    intent.putExtra("user", userProfile);
//                    startActivity(intent);
//                });
//
//                txt03UserName.setText(user.optString("first_name"));
//
//                if (statusResponse.getJSONObject("user").getString("rating") != null) {
//                    rat03UserRating.setRating(Float.valueOf(user.getString("rating")));
//                } else {
//                    rat03UserRating.setRating(0);
//                }
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @SuppressLint("SetTextI18n")
//    private void setValuesTo_ll_04_contentLayer_payment(JSONArray status) {
//        JSONObject statusResponse = new JSONObject();
//        try {
//            statusResponse = status.getJSONObject(0).getJSONObject("request");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        try {
//            txt04InvoiceId.setText(bookingId);
//            txt04BasePrice.setText(SharedHelper.getKey(App.getContext(), "currency") + "" +
//                    statusResponse.getJSONObject("payment").optString("fixed"));
//            txt04Distance.setText(SharedHelper.getKey(App.getContext(), "currency") + "" +
//                    statusResponse.getJSONObject("payment").optString("distance"));
//
////            txt04Minute.setText(SharedHelper.getKey(App.getContext(), "currency") + "" +
////                    statusResponse.getJSONObject("payment").optString("minute_price"));
////            txt04CancelCharge.setText(SharedHelper.getKey(App.getContext(), "currency") + "" +
////                    statusResponse.getJSONObject("payment").optString("cancellation_charge"));
//
//            txt04Tax.setText(SharedHelper.getKey(App.getContext(), "currency") + "" +
//                    statusResponse.getJSONObject("payment").optString("tax"));
//            txt04Total.setText(SharedHelper.getKey(App.getContext(), "currency") + "" +
//                    statusResponse.getJSONObject("payment").optString("total"));
//            txt04PaymentMode.setText(statusResponse.getString("payment_mode"));
//            txt04Commision.setText(SharedHelper.getKey(App.getContext(), "currency") + "" +
//                    statusResponse.getJSONObject("payment").optString("commision"));
//            txtTotal.setText(SharedHelper.getKey(App.getContext(), "currency") + "" +
//                    statusResponse.getJSONObject("payment").optString("total"));
//            if (statusResponse.getString("payment_mode").equals("CASH")) {
//                paymentTypeImg.setImageResource(R.drawable.money1);
//                btn_confirm_payment.setVisibility(View.VISIBLE);
//            } else {
//                paymentTypeImg.setImageResource(R.drawable.visa_icon);
//                btn_confirm_payment.setVisibility(View.GONE);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    private void setValuesTo_ll_05_contentLayer_feedback(JSONArray status) {
//        rat05UserRating.setRating(1.0f);
//        feedBackRating = "1";
//        rat05UserRating.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
//            utils.print("rating", rating + "");
//            if (rating < 1.0f) {
//                rat05UserRating.setRating(1.0f);
//                feedBackRating = "1";
//            }
//            feedBackRating = String.valueOf((int) rating);
//        });
//        JSONObject statusResponse = new JSONObject();
//        try {
//            statusResponse = status.getJSONObject(0).getJSONObject("request");
//            JSONObject user = statusResponse.getJSONObject("user");
//            if (user != null) {
//                lblProviderName.setText(user.optString("first_name"));
//                if (!user.optString("picture").equals("null")) {
//                    if (user.optString("picture").startsWith("http"))
//                        Picasso.get().load(user.getString("picture"))
//                                .placeholder(R.drawable.ic_dummy_user)
//                                .error(R.drawable.ic_dummy_user).into(img05User);
//                    else
//                        Picasso.get().load(URLHelper.BASE + "storage/app/public/" + user
//                                .getString("picture")).placeholder(R.drawable.ic_dummy_user)
//                                .error(R.drawable.ic_dummy_user).into(img05User);
//                } else {
//                    img05User.setImageResource(R.drawable.ic_dummy_user);
//                }
//                final User userProfile = this.user;
//                img05User.setOnClickListener(v -> {
//                    Intent intent = new Intent(DriverMainActivity.this, ShowProfile.class);
//                    intent.putExtra("user", userProfile);
//                    startActivity(intent);
//                });
//
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        feedBackComment = edt05Comment.getText().toString();
//    }
//
//    public void displayMessage(String toastString) {
//        Toasty.info(App.getContext(), toastString, Toasty.LENGTH_SHORT, true).show();
//    }
//
//    public void GoToBeginActivity() {
//        SharedHelper.putKey(App.getContext(), "loggedIn", getString(R.string.False));
//        Intent mainIntent = new Intent(DriverMainActivity.this, SplashScreen.class);
//        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(mainIntent);
//        finish();
//    }
//
//
//    @Override
//    public void onDestroy() {
//        if (mPlayer != null && mPlayer.isPlaying()) {
//            mPlayer.stop();
//            mPlayer = null;
//        }
//        ha.removeCallbacksAndMessages(null);
//        super.onDestroy();
//    }
//
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_LOCATION) {
//            if (resultCode == Activity.RESULT_CANCELED) {
//                Toast.makeText(App.getContext(), "RequestList Cancelled", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    public String getAddress(String strLatitude, String strLongitude) {
//        Geocoder geocoder;
//        List<Address> addresses;
//        geocoder = new Geocoder(DriverMainActivity.this, Locale.getDefault());
//        double latitude = Double.parseDouble(strLatitude);
//        double longitude = Double.parseDouble(strLongitude);
//        String address = "", city = "", state = "";
//        try {
//            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
//            address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//            city = addresses.get(0).getLocality();
//            state = addresses.get(0).getAdminArea();
//            String country = addresses.get(0).getCountryName();
//            String postalCode = addresses.get(0).getPostalCode();
//            String knownName = addresses.get(0).getFeatureName();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (address.length() > 0 || city.length() > 0)
//            return address + ", " + city;
//        else
//            return getString(R.string.no_address);
//    }
//
//    @Override
//    public void onPause() {
//
//        super.onPause();
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceiver);
//        Utilities.onMap = false;
//        if (customDialog != null) {
//            if (customDialog.isShowing()) {
//                customDialog.dismiss();
//            }
//        }
//        if (ha != null) {
//            isRunning = true;
//            if (mPlayer != null && mPlayer.isPlaying()) {
//                normalPlay = true;
//                mPlayer.stop();
//            } else {
//                normalPlay = false;
//            }
//            ha.removeCallbacksAndMessages(null);
//        }
//    }
//
//    private void showCancelDialog() {
//        AlertDialog.Builder builder;
//        builder = new AlertDialog.Builder(DriverMainActivity.this);
//        builder.setTitle(DriverMainActivity.this.getString(R.string.cancel_confirm));
//
//        builder.setPositiveButton(R.string.yes, (dialog, which) -> showReasonDialog());
//
//        builder.setNegativeButton(R.string.no, (dialog, which) -> {
//            //Reset to previous seletion menu in navigation
//            dialog.dismiss();
//        });
//        builder.setCancelable(false);
//        final AlertDialog dialog = builder.create();
//        dialog.setOnShowListener(arg -> {
//        });
//        dialog.show();
//    }
//
//    private void showReasonDialog() {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(DriverMainActivity.this);
//        View view = LayoutInflater.from(DriverMainActivity.this).inflate(R.layout.cancel_dialog, null);
//        final EditText reasonEtxt = view.findViewById(R.id.reason_etxt);
//        Button submitBtn = view.findViewById(R.id.submit_btn);
//        RadioGroup radioCancel = view.findViewById(R.id.radioCancel);
//        radioCancel.setOnCheckedChangeListener((group, checkedId) -> {
//            if (checkedId == R.id.driver) {
//                reasonEtxt.setVisibility(View.VISIBLE);
//                cancaltype = getResources().getString(R.string.plan_changed);
//            }
//            if (checkedId == R.id.vehicle) {
//                reasonEtxt.setVisibility(View.VISIBLE);
//                cancaltype = getResources().getString(R.string.booked_another_cab);
//            }
//            if (checkedId == R.id.app) {
//                reasonEtxt.setVisibility(View.VISIBLE);
//                cancaltype = getResources().getString(R.string.my_reason_is_not_listed);
//            }
//        });
//        builder.setView(view)
//                .setCancelable(true);
//        final AlertDialog dialog = builder.create();
//        submitBtn.setOnClickListener(v -> {
//
//            if (cancaltype.isEmpty()) {
//                Toast.makeText(App.getContext(), getResources().getString(R.string.please_select_reason), Toast.LENGTH_SHORT).show();
//
//            } else {
//                cancalReason = reasonEtxt.getText().toString();
//                if (cancalReason.isEmpty()) {
//                    reasonEtxt.setError(getResources().getString(R.string.please_specify_reason));
//                } else {
//                    if (reasonEtxt.getText().toString().length() > 0)
//                        cancelTrip(request_id, reasonEtxt.getText().toString());
//                    else
//                        cancelTrip(request_id, "");
//                    dialog.dismiss();
//                }
//            }
//        });
//        dialog.show();
//
//    }
//
//    private void showSosDialog() {
//        AlertDialog.Builder builder;
//        builder = new AlertDialog.Builder(DriverMainActivity.this);
//        builder.setTitle(DriverMainActivity.this.getString(R.string.sos_confirm));
//
//        builder.setPositiveButton(R.string.yes, (dialog, which) -> {
//            //cancelRequest(request_id);
//            dialog.dismiss();
//            String mobile = SharedHelper.getKey(App.getContext(), "sos");
//            if (mobile != null && !mobile.equalsIgnoreCase("null") && mobile.length() > 0) {
//                Intent intent = new Intent(Intent.ACTION_DIAL);
//                intent.setData(Uri.parse("tel:" + mobile));
//                startActivity(intent);
//            } else {
//                displayMessage(App.getContext().getString(R.string.user_no_mobile));
//            }
//
//        });
//
//        builder.setNegativeButton(R.string.no, (dialog, which) -> dialog.dismiss());
//        builder.setCancelable(false);
//        final AlertDialog dialog = builder.create();
//        dialog.setOnShowListener(arg -> {
//        });
//        dialog.show();
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//
//        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver,
//                new IntentFilter(LocationUpdatesService.ACTION_BROADCAST));
//
//        Utilities.onMap = true;
//        if (Utilities.clearSound) {
//            NotificationManager notificationManager = (NotificationManager) DriverMainActivity.this
//                    .getSystemService(NOTIFICATION_SERVICE);
//            notificationManager.cancelAll();
//        }
//        utils.print(TAG, "onResume: Handler Call out" + isRunning);
//        if (isRunning) {
//            if (mPlayer != null && normalPlay) {
//                mPlayer = MediaPlayer.create(DriverMainActivity.this, R.raw.alert_tone);
//                mPlayer.start();
//            }
//            utils.print(TAG, "onResume: Handler Call" + isRunning);
//            ha.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    //call function
////                    if (type != null) {
////                        checkStatusSchedule();
////                    } else {
////                        checkStatus();
////                    }
//                    checkStatus();
//                    ha.postDelayed(this, 3000);
//                }
//            }, 3000);
//        }
//    }
//
//    protected synchronized void buildGoogleApiClient() {
//        mGoogleApiClient = new GoogleApiClient.Builder(DriverMainActivity.this)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API)
//                .build();
//        mGoogleApiClient.connect();
//    }
//
//
//    Marker sourceMarker;
//    Marker destinationMarker;
//
//    private void trackPickToDest(Double destLat, Double destLng) throws Exception {
//        GoogleDirection.withServerKey(App.getContext().getString(R.string.google_map_api))
//                .from(new LatLng(sourceLatLng.latitude, sourceLatLng.longitude))
//                .to(new LatLng(destLat, destLng))
//                .transportMode(TransportMode.DRIVING)
//                .execute(new DirectionCallback() {
//                    @Override
//                    public void onDirectionSuccess(Direction direction) {
//                        try {
//                            if (direction != null) {
//                                if (direction.isOK()) {
//                                    Log.v("direction", direction + "");
////                                    float totalDistance = 0;
////                                    int totalDuration = 0;
//                                    mMap.clear();
//                                    Route route = direction.getRouteList().get(0);
//                                    int legCount = route.getLegList().size();
//                                    for (int index = 0; index < legCount; index++) {
//                                        Leg leg = route.getLegList().get(index);
//                                        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_user_location);
//                                        Bitmap icon1 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_provider_marker);
//                                        mMap.addMarker(new MarkerOptions()
//                                                .icon(BitmapDescriptorFactory.fromBitmap(icon1))
//                                                .position(leg.getStartLocation().getCoordination()));
//                                        if (index == legCount - 1) {
//                                            mMap.addMarker(new MarkerOptions()
//                                                    .icon(BitmapDescriptorFactory.fromBitmap(icon))
//                                                    .position(leg.getEndLocation().getCoordination()));
//                                        }
//                                        List<Step> stepList = leg.getStepList();
//                                        ArrayList<PolylineOptions> polylineOptionList = DirectionConverter
//                                                .createTransitPolyline(DriverMainActivity.this,
//                                                        stepList,
//                                                        3,
//                                                        Color.BLACK,
//                                                        2,
//                                                        Color.GRAY);
//                                        for (PolylineOptions polylineOption : polylineOptionList) {
//                                            mMap.addPolyline(polylineOption);
//                                        }
//                                    }
//
//                                    mMap.setOnCameraIdleListener(() -> {
//                                        if (sourceMarker != null) {
//                                            String lat = String.valueOf(sourceLatLng.latitude);
//                                            String lng = String.valueOf(sourceLatLng.longitude);
//                                            if (((lat != null) && !lat.equals("") && !lat.isEmpty() && !lat.equalsIgnoreCase("0,0")) &&
//                                                    ((lng != null) && !lng.equals("") && !lng.isEmpty() && !lng.equalsIgnoreCase("0,0"))) {
//                                                Point PickupPoint = mMap.getProjection().toScreenLocation(new LatLng(sourceLatLng.latitude, sourceLatLng.longitude));
//                                                sourceMarker.setAnchor(PickupPoint.x < dpToPx(App.getContext(), 200) ? 0.00f : 1.00f, PickupPoint.y < dpToPx(App.getContext(), 100) ? 0.20f : 1.20f);
//                                            }
//
//                                        }
//                                        if (destinationMarker != null) {
//                                            if (((destLat != null) && !destLat.equals("") &&
//                                                    ((destLng != null) && !destLng.equals("")))) {
//                                                Point PickupPoint = mMap.getProjection()
//                                                        .toScreenLocation(new LatLng(destLat, destLng));
//                                                destinationMarker.setAnchor(PickupPoint.x < dpToPx(App.getContext(), 200) ? 0.00f : 1.00f, PickupPoint.y < dpToPx(App.getContext(), 100) ? 0.20f : 1.20f);
//                                            }
//                                        }
//                                    });
//                                    setCameraWithCoordinationBounds(route);
//                                }
//                            }
//                        } catch (NullPointerException ne) {
//                            ne.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onDirectionFailure(Throwable t) {
//
//                    }
//                });
//
//    }
//
//    private void setCameraWithCoordinationBounds(Route route) {
//        LatLng southwest = route.getBound().getSouthwestCoordination().getCoordination();
//        LatLng northeast = route.getBound().getNortheastCoordination().getCoordination();
//        LatLngBounds bounds = new LatLngBounds(southwest, northeast);
//        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
//    }
//
//    private int dpToPx(Context context, float dpValue) {
//        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
//        return Math.round(dpValue * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
//    }
//
//    private Bitmap createDrawableFromView(Context context, View view) {
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
//        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
//        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
//        view.buildDrawingCache();
//        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        view.draw(canvas);
//        return bitmap;
//    }
//
//    public String convertHours(int runtime) {
//        int hours = runtime / 60;
//        int minutes = runtime % 60; // 5 in this case.
//        return hours + "h " + minutes + " m";
//    }
//
//    private void documentList() {
//        String auth = "Bearer" + " " + SharedHelper.getKey(App.getContext(), "access_token");
//        Call<DocumentList> call = restInterface.documentList(URLHelper.REQUEST_WITH, auth);
//        call.enqueue(new Callback<DocumentList>() {
//            @Override
//            public void onResponse(Call<DocumentList> call, Response<DocumentList> response) {
//                if (response.code() == 200) {
//                    DocumentList list = response.body();
//                    if (list.getStatus() == 0) {
//                        if (Waintingdialog == null) {
//                            if (docopen.equalsIgnoreCase("")) {
//                                docopen = "yes";
//                                Intent intent1 = new Intent(DriverMainActivity.this, UploadDocumentActivity.class);
//                                DriverMainActivity.this.startActivity(intent1);
//                            }
//                        }
//                    } else {
//                        ha.removeMessages(0);
//                        lnrGoOffline.setVisibility(View.GONE);
//                        lnrNotApproved.setVisibility(View.VISIBLE);
//                    }
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<DocumentList> call, Throwable t) {
//            }
//        });
//    }
//
//    String serviceStatus;
//
//    private void goOnline() {
//        String status;
//        if (serviceStatus.equalsIgnoreCase("OFFLINE")) {
//            status = "active";
//        } else {
//            status = "offline";
//        }
//        String auth = "Bearer" + " " + SharedHelper.getKey(App.getContext(), "access_token");
//        customDialog = new CustomDialog(DriverMainActivity.this);
//        customDialog.setCancelable(false);
//        customDialog.show();
//
//        Call<DriverStatus> call = restInterface.updateDriverStatus(URLHelper.REQUEST_WITH, auth, status);
//        call.enqueue(new Callback<DriverStatus>() {
//            @Override
//            public void onResponse(Call<DriverStatus> call, Response<DriverStatus> response) {
//                customDialog.dismiss();
//                if (response.code() == 200) {
//                    DriverStatus ds = response.body();
//                    if (ds.getAccountStatus() != null) {
//                        if (ds.getAccountStatus().equalsIgnoreCase("approved")) {
//                            lnrNotApproved.setVisibility(View.GONE);
//                            if (ds.getServiceStatus() != null) {
//                                serviceStatus = ds.getServiceStatus();
//                                if (ds.getServiceStatus().equalsIgnoreCase("active")) {
//                                    active_Status.setText(App.getContext().getString(R.string.online));
//                                    offline_layout.setVisibility(View.GONE);
//                                    online_offline_switch.setChecked(true);
//                                } else {
//                                    active_Status.setText(App.getContext().getString(R.string.offline));
//                                    offline_layout.setVisibility(View.VISIBLE);
//                                    online_offline_switch.setChecked(false);
//                                }
//                            }
//                            activeStatus.setText(App.getContext().getString(R.string.online));
//                        } else {
//                            serviceStatus = ds.getServiceStatus();
//                            displayMessage(ds.getError().toString());
//                        }
//                    }
//
//                } else {
//                    displayMessage(getString(R.string.please_try_again));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<DriverStatus> call, Throwable t) {
//                customDialog.dismiss();
//                displayMessage(getString(R.string.something_went_wrong));
//            }
//        });
//    }
//
//    private void acceptRequest(String id) {
//        String auth = "Bearer" + " " + SharedHelper.getKey(App.getContext(), "access_token");
//        customDialog = new CustomDialog(DriverMainActivity.this);
//        customDialog.setCancelable(false);
//        customDialog.show();
//        Call<ResponseBody> call = restInterface.acceptTrip(URLHelper.REQUEST_WITH, auth, id);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                customDialog.dismiss();
//                if (response.code() == 200) {
//                    displayMessage("Order accepted successfully");
//                } else {
//                    displayMessage(getString(R.string.please_try_again));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                customDialog.dismiss();
//                displayMessage(getString(R.string.something_went_wrong));
//            }
//        });
//    }
//
//    private void rejectRequest(String id) {
//        String auth = "Bearer" + " " + SharedHelper.getKey(App.getContext(), "access_token");
//        customDialog = new CustomDialog(DriverMainActivity.this);
//        customDialog.setCancelable(false);
//        customDialog.show();
//        Call<ResponseBody> call = restInterface.rejectTrip(URLHelper.REQUEST_WITH, auth, id);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                customDialog.dismiss();
//                if (response.code() == 200) {
//                    if (!timerCompleted) {
//                        if (ll_01_contentLayer_accept_or_reject_now.getVisibility() == View.VISIBLE) {
//                            mapClear();
//                            clearVisibility();
//                            if (mMap != null) {
//                                mMap.clear();
//                            }
//                            ll_01_contentLayer_accept_or_reject_now.setVisibility(View.GONE);
//                            CurrentStatus = "ONLINE";
//                            PreviousStatus = "NULL";
//                            destinationLayer.setVisibility(View.GONE);
//                            layoutinfo.setVisibility(View.VISIBLE);
//                            isRunning = false;
//                            timerCompleted = true;
//                        }
//                        Toast.makeText(App.getContext(), "Order rejected successfully", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(App.getContext(), "Order Timeout", Toast.LENGTH_SHORT).show();
//                    }
//                    displayMessage("Order rejected successfully");
//                } else {
//                    displayMessage(getString(R.string.please_try_again));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                customDialog.dismiss();
//                displayMessage(getString(R.string.something_went_wrong));
//            }
//        });
//    }
//
//    private void rateTrip(String id, String rating, String comment) {
//        String auth = "Bearer" + " " + SharedHelper.getKey(App.getContext(), "access_token");
//        customDialog = new CustomDialog(DriverMainActivity.this);
//        customDialog.setCancelable(false);
//        customDialog.show();
//        Call<ResponseBody> call = restInterface.rateTrip(URLHelper.REQUEST_WITH, auth, id, "rate",
//                rating, comment);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                customDialog.dismiss();
//                if (response.code() == 200) {
//                    destinationLayer.setVisibility(View.GONE);
//                    layoutinfo.setVisibility(View.VISIBLE);
//                    LatLng myLocation = new LatLng(Double.parseDouble(crt_lat), Double.parseDouble(crt_lng));
//                    CameraPosition cameraPosition = new CameraPosition.Builder().target(myLocation).zoom(14).build();
//                    mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//                    mapClear();
//                    clearVisibility();
//                    mMap.clear();
//                } else {
//                    displayMessage(getString(R.string.please_try_again));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                customDialog.dismiss();
//                displayMessage(getString(R.string.something_went_wrong));
//            }
//        });
//    }
//
//    private void dropTrip(String id, String status) {
//        String auth = "Bearer" + " " + SharedHelper.getKey(App.getContext(), "access_token");
//        String dist = String.valueOf(LocationTracking.distance * 0.001);
//        customDialog = new CustomDialog(DriverMainActivity.this);
//        customDialog.setCancelable(false);
//        customDialog.show();
//        Call<ResponseBody> call = restInterface.dropTrip(URLHelper.REQUEST_WITH, auth, id, "PATCH", status, crt_lat, crt_lng, dist);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                customDialog.dismiss();
//                if (response.code() == 200) {
//
//                } else {
//                    displayMessage(getString(R.string.please_try_again));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                customDialog.dismiss();
//                displayMessage(getString(R.string.something_went_wrong));
//            }
//        });
//    }
//
//    private void updateTripStatus(String id, String status) {
//        String auth = "Bearer" + " " + SharedHelper.getKey(App.getContext(), "access_token");
//        customDialog = new CustomDialog(DriverMainActivity.this);
//        customDialog.setCancelable(false);
//        customDialog.show();
//        Call<ResponseBody> call = restInterface.updateTripStatus(URLHelper.REQUEST_WITH, auth, id, "PATCH", status);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                customDialog.dismiss();
//                if (response.code() == 200) {
//
//                } else {
//                    displayMessage(getString(R.string.please_try_again));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                customDialog.dismiss();
//                displayMessage(getString(R.string.something_went_wrong));
//            }
//        });
//    }
//
//    private void cancelTrip(String id, String cancelReason) {
//        String auth = "Bearer" + " " + SharedHelper.getKey(App.getContext(), "access_token");
//        customDialog = new CustomDialog(DriverMainActivity.this);
//        customDialog.setCancelable(false);
//        customDialog.show();
//        Call<ResponseBody> call = restInterface.cancelTrip(URLHelper.REQUEST_WITH, auth, id, cancelReason);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                customDialog.dismiss();
//                if (response.code() == 200) {
//                    displayMessage("You have cancelled the order");
//                    mapClear();
//                    clearVisibility();
//                    layoutinfo.setVisibility(View.VISIBLE);
//                    destinationLayer.setVisibility(View.GONE);
//                    CurrentStatus = "ONLINE";
//                    PreviousStatus = "NULL";
//                } else {
//                    displayMessage(getString(R.string.please_try_again));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                customDialog.dismiss();
//                displayMessage(getString(R.string.something_went_wrong));
//            }
//        });
//    }
//
//    @Override
//    protected void onStop() {
//        if (mBound) {
//            unbindService(mServiceConnection);
//            mBound = false;
//        }
//        super.onStop();
//    }
//
//    private class MyReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            Location location = intent.getParcelableExtra(LocationUpdatesService.EXTRA_LOCATION);
//            if (location != null) {
//                Toast.makeText(DriverMainActivity.this, Utils.getLocationText(location),
//                        Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//}
