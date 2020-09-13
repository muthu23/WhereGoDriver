package com.wherego.delivery.driver.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.wherego.delivery.driver.App;
import com.wherego.delivery.driver.R;
import com.wherego.delivery.driver.helpers.CustomDialog;
import com.wherego.delivery.driver.helpers.RestInterface;
import com.wherego.delivery.driver.helpers.ServiceGenerator;
import com.wherego.delivery.driver.helpers.SharedHelper;
import com.wherego.delivery.driver.helpers.URLHelper;
import com.wherego.delivery.driver.model.banklist.BankList;
import com.wherego.delivery.driver.model.banklist.Datum;
import com.wherego.delivery.driver.model.widrawrequest.WithdrawRequestRes;
import com.wherego.delivery.driver.utills.Utilities;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WithdrawAmountActivity extends AppCompatActivity implements View.OnClickListener {

    RestInterface restInterface;
    ArrayList<Datum> bankList = new ArrayList<>();

//    ArrayList<JSONObject> listItems;
    Utilities utils = new Utilities();
    private CustomDialog customDialog;
//    private ArrayList<CardDetails> cardArrayList;
    private ListView payment_list_view;
    private PaymentListAdapter paymentAdapter;
    private Button addBankAccountBtn;
    private Button addAmountBtn;
//    private Stripe stripe;
//    private EditText addAccountName;
    private ImageView backArrow;
    private EditText amountEditText;
    private String providerID;
//    private int accountId;
    private LinearLayout withdrawLayout;
//    private EditText addBankName;
    private TextView selectAmountTxt;
    private LinearLayout noBankDetailsFoundLayout;
//    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_amount);
        restInterface = ServiceGenerator.createService(RestInterface.class);
//        stripe = new Stripe(this);
//        stripe.setDefaultPublishableKey(URLHelper.STRIPE_TOKEN);

        providerID = SharedHelper.getKey(WithdrawAmountActivity.this, "id");

        initViews();
        getCardList();


//        payment_list_view.setOnItemLongClickListener((parent, view, position, id) -> {
//
//            String accountHolderName = cardArrayList.get(position).getAccountName();
//            String bankName = cardArrayList.get(position).getBankName();
//            int accountNumber = cardArrayList.get(position).getAccountNumber();
//            int routingNumber = cardArrayList.get(position).getRoutingNumber();
//            String countryName = cardArrayList.get(position).getCountryName();
//            String currency = cardArrayList.get(position).getCurrency();
//
//
//            bankDetailsPoupUp(accountHolderName, bankName, accountNumber, routingNumber, countryName);
//
//            return true;
//        });

    }

    private void bankDetailsPoupUp(String accountHName, String bankName, int accountNumber, int routingNumber, String countryName) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(WithdrawAmountActivity.this);
        View customView = LayoutInflater.from(WithdrawAmountActivity.this).inflate(R.layout.bank_details_popup, null);
        // View customView = layoutInflater.inflate(R.layout.bank_details_popup,null);
        builder.setIcon(R.mipmap.ic_logo)
                .setTitle(R.string.bank_details)
                .setView(customView)
                .setCancelable(true);
        final AlertDialog dialog = builder.create();

        Button oKBtn = customView.findViewById(R.id.oKBtn);
        //instantiate popup window
        //popupWindow = new PopupWindow(customView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        TextView textViewBankName = customView.findViewById(R.id.textViewBankName);
        TextView textViewAccountNumber = customView.findViewById(R.id.textViewAccountNumber);
        TextView textViewRoutingNumber = customView.findViewById(R.id.textViewRoutingNumber);
        TextView textViewAccountName = customView.findViewById(R.id.textViewAccountName);
        TextView textViewCountry = customView.findViewById(R.id.textViewCountry);

        textViewAccountName.setText(accountHName);
        textViewBankName.setText(bankName);
        textViewAccountNumber.setText(accountNumber + "");
        textViewRoutingNumber.setText(routingNumber + "");
        textViewCountry.setText(countryName);
        //close the popup window on button click
        oKBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void initViews() {
        payment_list_view = findViewById(R.id.payment_list_view);
        addBankAccountBtn = findViewById(R.id.addBankAccountBtn);
        addAmountBtn = findViewById(R.id.addAmountBtn);
        backArrow = findViewById(R.id.backArrow);
        amountEditText = findViewById(R.id.amountEditText);
        withdrawLayout = findViewById(R.id.withdrawLayout);
        noBankDetailsFoundLayout = findViewById(R.id.noBankDetailsFoundLayout);
        selectAmountTxt = findViewById(R.id.selectAmountTxt);
        addBankAccountBtn.setOnClickListener(this);
        backArrow.setOnClickListener(this);
        addAmountBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addBankAccountBtn:
                startActivity(new Intent(WithdrawAmountActivity.this, AddBankAccount.class));
                finish();
                break;
            case R.id.backArrow:
                onBackPressed();
                break;
            case R.id.addAmountBtn:
                withdrawAmount();
                break;

        }
    }

    private void getCardList(){
        String auth = "Bearer" + " " + SharedHelper.getKey(App.getContext(), "access_token");
        customDialog = new CustomDialog(this);
        customDialog.setCancelable(false);
        customDialog.show();
        Call<BankList> call = restInterface.bankList(URLHelper.REQUEST_WITH, auth);
        call.enqueue(new Callback<BankList>() {
            @Override
            public void onResponse(Call<BankList> call, retrofit2.Response<BankList> response) {
                bankList.clear();
                customDialog.dismiss();
                if (response.code() == 200){
                    if (response.body().getData() != null){
                        List<Datum> bList = response.body().getData();
                        if (!bList.isEmpty()){
                            bankList.addAll(bList);
                            SharedHelper.putKey(App.getContext(), "AccountId_SP", String.valueOf(bankList.get(0).getId()));
                            paymentAdapter = new PaymentListAdapter(WithdrawAmountActivity.this, R.layout.payment_list_item, bankList);
                            payment_list_view.setAdapter(paymentAdapter);
                        }else {
                            withdrawLayout.setVisibility(View.GONE);
                            selectAmountTxt.setVisibility(View.GONE);
                            noBankDetailsFoundLayout.setVisibility(View.VISIBLE);
                        }
                    }
                }else {
                    withdrawLayout.setVisibility(View.GONE);
                    selectAmountTxt.setVisibility(View.GONE);
                    noBankDetailsFoundLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<BankList> call, Throwable t) {
                customDialog.dismiss();
            }
        });
    }

    private void withdrawAmount(){
        String accountIdSp = SharedHelper.getKey(App.getContext(), "AccountId_SP");
        String auth = "Bearer" + " " + SharedHelper.getKey(App.getContext(), "access_token");
        customDialog = new CustomDialog(this);
        customDialog.setCancelable(false);
        customDialog.show();
        Map<String, String> fields = new HashMap<>();
        fields.put("provider_id", providerID);
        fields.put("bank_account_id", accountIdSp);
        fields.put("amount", amountEditText.getText().toString());

        Call<WithdrawRequestRes> call = restInterface.withdrwaRequest(URLHelper.REQUEST_WITH, auth, fields);
        call.enqueue(new Callback<WithdrawRequestRes>() {
            @Override
            public void onResponse(Call<WithdrawRequestRes> call, Response<WithdrawRequestRes> response) {
                customDialog.dismiss();
                if (response.code() == 200){
                    if (response.body().getStatus() == 1){
                        displayMessage(App.getContext().getResources()
                                .getString(R.string.fifteen_days));
                    }else {
                        displayMessage(getString(R.string.please_try_again));
                    }
                    onBackPressed();
                }else {
                    displayMessage(getString(R.string.please_try_again));
                }
            }

            @Override
            public void onFailure(Call<WithdrawRequestRes> call, Throwable t) {
                customDialog.dismiss();
                displayMessage(getString(R.string.something_went_wrong));
            }
        });
    }

    private void displayMessage(String toastString) {
        Toasty.info(App.getContext(), toastString, Toasty.LENGTH_SHORT, true).show();
    }


}
