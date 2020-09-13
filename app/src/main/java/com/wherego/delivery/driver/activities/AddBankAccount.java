package com.wherego.delivery.driver.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hbb20.CountryCodePicker;
import com.wherego.delivery.driver.App;
import com.wherego.delivery.driver.R;
import com.wherego.delivery.driver.helpers.CustomDialog;
import com.wherego.delivery.driver.helpers.RestInterface;
import com.wherego.delivery.driver.helpers.ServiceGenerator;
import com.wherego.delivery.driver.helpers.SharedHelper;
import com.wherego.delivery.driver.helpers.URLHelper;
import com.wherego.delivery.driver.model.addbank.AddBank;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBankAccount extends AppCompatActivity {

    RestInterface restInterface;
    CustomDialog customDialog;

    @OnClick(R.id.backArrow)
    void backArrow(){
        onBackPressed();
    }
    @BindView(R.id.self)
    TextView self;
    @BindView(R.id.others)
    TextView others;

    @OnClick(R.id.self)
    void self(){
        self.setBackgroundColor(App.getContext().getResources().getColor(R.color.colorPrimary));
        self.setTextColor(App.getContext().getResources().getColor(R.color.white));
        others.setBackgroundColor(App.getContext().getResources().getColor(R.color.white));
        others.setTextColor(App.getContext().getResources().getColor(R.color.black));
        llBank.setVisibility(View.VISIBLE);
        llPaypal.setVisibility(View.GONE);
    }

    @OnClick(R.id.others)
    void others(){
        others.setBackgroundColor(App.getContext().getResources().getColor(R.color.colorPrimary));
        self.setTextColor(App.getContext().getResources().getColor(R.color.black));
        self.setBackgroundColor(App.getContext().getResources().getColor(R.color.white));
        others.setTextColor(App.getContext().getResources().getColor(R.color.white));
        llBank.setVisibility(View.GONE);
        llPaypal.setVisibility(View.VISIBLE);
    }

    @BindView(R.id.llPaypal)
    LinearLayout llPaypal;
    @BindView(R.id.etPaypalID)
    EditText etPaypalID;
    @BindView(R.id.paypalCpp)
    CountryCodePicker paypalCpp;
    @OnClick(R.id.addPaypal)
    void addPaypal(){
        countryName = paypalCpp.getSelectedCountryName();
        if (etPaypalID.getText().toString().isEmpty()){
            displayMessage(getString(R.string.paypal_id));
        }else if (countryName.isEmpty()){
            displayMessage(getString(R.string.select_country));
        }else {
            addBank("", "", "", "", "paypal",
                    etPaypalID.getText().toString(), countryName);
        }
    }

    @BindView(R.id.llBank)
    LinearLayout llBank;
    @BindView(R.id.etBankName)
    EditText etBankName;
    @BindView(R.id.etIFSCCode)
    EditText etIFSCCode;
    @BindView(R.id.etAccountNo)
    EditText etAccountNo;
    @BindView(R.id.etAccountHolderName)
    EditText etAccountHolderName;
    @BindView(R.id.ccp)
    CountryCodePicker ccp;
    @OnClick(R.id.addAccount)
    void addAccount(){
        countryName = ccp.getSelectedCountryName();
        if (etBankName.getText().toString().isEmpty()){
            displayMessage(getString(R.string.bank_name));
        }else if (etAccountHolderName.getText().toString().isEmpty()){
            displayMessage(getString(R.string.account_holder_name));
        }else if (etAccountNo.getText().toString().isEmpty()){
            displayMessage(getString(R.string.account_number));
        }else if (etIFSCCode.getText().toString().isEmpty()){
            displayMessage(getString(R.string.ifsc_code));
        }else if (countryName.isEmpty()){
            displayMessage(getString(R.string.select_country));
        }else {
            addBank(etBankName.getText().toString(), etAccountHolderName.getText().toString(),
                    etAccountNo.getText().toString(), etIFSCCode.getText().toString(), "bank",
                    "", countryName);
        }
    }
    String providerID;
    String countryName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_bank_account);
        ButterKnife.bind(this);
        restInterface = ServiceGenerator.createService(RestInterface.class);
        providerID = SharedHelper.getKey(App.getContext(), "id");
    }

    private void addBank(String bankName, String accountName, String accountNo, String routinNo,
                         String type, String paypal_id, String countryName){
        String auth = "Bearer" + " " + SharedHelper.getKey(App.getContext(), "access_token");
        customDialog = new CustomDialog(this);
        customDialog.setCancelable(false);
        customDialog.show();
        Map<String, String> fields = new HashMap<>();
        fields.put("account_name", accountName);
        fields.put("account_number", accountNo);
        fields.put("routing_number", routinNo);
        fields.put("provider_id", providerID);
        fields.put("bank_name", bankName);
        fields.put("type", type);
        fields.put("paypal_id", paypal_id);
        fields.put("country", countryName);

        Call<AddBank> call = restInterface.addBank(URLHelper.REQUEST_WITH, auth, fields);
        call.enqueue(new Callback<AddBank>() {
            @Override
            public void onResponse(Call<AddBank> call, Response<AddBank> response) {
                customDialog.dismiss();
                if (response.code() == 200){
                    if (response.body().getStatus() == 1){
                        SharedHelper.putKey(App.getContext(), "AccountId_SP", String.valueOf(response.body().getData().getId()));
                        displayMessage("Account Added");
                    }else {
                        displayMessage("You already have an account");
                    }
                    onBackPressed();
                }else {
                    displayMessage(getString(R.string.please_try_again));
                }
            }

            @Override
            public void onFailure(Call<AddBank> call, Throwable t) {
                customDialog.dismiss();
                displayMessage(getString(R.string.something_went_wrong));
            }
        });
    }

    public void displayMessage(String toastString) {
        Toasty.info(App.getContext(), toastString, Toasty.LENGTH_SHORT, true).show();
    }

}
