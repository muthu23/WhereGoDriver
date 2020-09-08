package com.wherego.driver.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.wherego.driver.App;
import com.wherego.driver.R;
import com.wherego.driver.adapter.DocumentListAdapter;
import com.wherego.driver.helpers.CustomDialog;
import com.wherego.driver.helpers.RestInterface;
import com.wherego.driver.helpers.ServiceGenerator;
import com.wherego.driver.helpers.SharedHelper;
import com.wherego.driver.helpers.URLHelper;
import com.wherego.driver.listners.DocUploadClick;
import com.wherego.driver.model.documents.Document;
import com.wherego.driver.model.documents.DocumentList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadDocumentActivity extends AppCompatActivity {

    private RestInterface restInterface;
    private DocumentListAdapter adapter;
    private int docStatus = 0;
    private String docId;
    private String imageType = "";

    ArrayList<Document> documents = new ArrayList<>();

    @BindView(R.id.rvDocList)
    RecyclerView rvDocList;
    @BindView(R.id.cbTerms)
    CheckBox cbTerms;
    @BindView(R.id.llSubmit)
    LinearLayout llSubmit;
    @BindView(R.id.tvTerms)
    TextView tvTerms;
    @OnClick(R.id.btDone)
    void btDone() {
        if (docStatus == 1) {
            if (!cbTerms.isChecked()) {
                displayMessage(App.getContext().getString(R.string.agree_with_check_term_and_conditions));
            } else {
                submitAllDoc();
            }
        } else {
            displayMessage(App.getContext().getString(R.string.please_upload_documents));
        }
    }

    @OnClick(R.id.tvTerms)
    void openTerms()
    {
        Intent intent =new Intent(UploadDocumentActivity.this,WebPage.class);
        intent.putExtra("page",getString(R.string.terms_amp_conditions));
        intent.putExtra("url", URLHelper.termcondition);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_document);
        getSupportActionBar().setTitle(getString(R.string.documents));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        restInterface = ServiceGenerator.createService(RestInterface.class);
        setupRecyclerView();
        documentList();
        tvTerms.setText(Html.fromHtml(String.format(getString(R.string.terms_amp_conditionsu))));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressLint("WrongConstant")
    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        if (adapter == null) {
            adapter = new DocumentListAdapter(UploadDocumentActivity.this,
                    documents);
            rvDocList.setLayoutManager(layoutManager);
            rvDocList.setAdapter(adapter);
            rvDocList.setHasFixedSize(true);
            rvDocList.setItemAnimator(new DefaultItemAnimator());
            rvDocList.setNestedScrollingEnabled(true);
        } else {
            adapter.notifyDataSetChanged();
        }
        setupOnClick();
    }

    private void setupOnClick() {
        if (adapter != null) {
            adapter.onDocItemClick(new DocUploadClick() {
                @Override
                public void onUploadClick(int position, View view) {
                    imageType = "Upload";
                    docId = String.valueOf(documents.get(position).getId());
                    ImagePicker.Companion.with(UploadDocumentActivity.this)
                            .crop()
                            .compress(1024)
                            .maxResultSize(612, 816)
                            .start();
                }

                @Override
                public void onUpdateClick(int position, View view) {
                    imageType = "Update";
                    docId = String.valueOf(documents.get(position).getId());
                    ImagePicker.Companion.with(UploadDocumentActivity.this)
                            .crop()
                            .compress(1024)
                            .maxResultSize(612, 816)
                            .start();
                }
            });
        }
    }

    private void documentList() {
        String auth = "Bearer" + " " + SharedHelper.getKey(App.getContext(), "access_token");
        CustomDialog customDialog = new CustomDialog(this);
        customDialog.setCancelable(false);
        customDialog.show();
        Call<DocumentList> call = restInterface.documentList(URLHelper.REQUEST_WITH, auth);
        call.enqueue(new Callback<DocumentList>() {
            @Override
            public void onResponse(Call<DocumentList> call, Response<DocumentList> response) {
                documents.clear();
                customDialog.dismiss();
                if (response.code() == 200) {
                    docStatus = response.body().getStatus();
//                    if (docStatus == 1) {
//                        cbTerms.setOnCheckedChangeListener((buttonView, isChecked) -> {
//                            if (isChecked) {
//                                cbTerms.setChecked(false);
//                            } else {
//                                cbTerms.setChecked(true);
//                            }
//                        });
//                    }
                    if (response.body().getTerm() != null){
                        if (response.body().getTerm().equalsIgnoreCase("1")){
                            llSubmit.setVisibility(View.GONE);
                        }
                    }
                    if (response.body().getDocument() != null) {
                        List<Document> doc = response.body().getDocument();
                        if (!doc.isEmpty()) {
                            documents.addAll(doc);
                            adapter.notifyDataSetChanged();
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<DocumentList> call, Throwable t) {
                customDialog.dismiss();
                documents.clear();
            }
        });
    }

    private void uploadDocument(String docI, String docPath) {
        String auth = "Bearer" + " " + SharedHelper.getKey(App.getContext(), "access_token");
        CustomDialog customDialog = new CustomDialog(this);
        customDialog.setCancelable(false);
        customDialog.show();

        RequestBody id = prepareText(docI);
        MultipartBody.Part image = prepareImage("document", docPath);

        Call<DocumentList> call = restInterface.uploadDoc(URLHelper.REQUEST_WITH, auth,
                id, image);
        call.enqueue(new Callback<DocumentList>() {
            @Override
            public void onResponse(Call<DocumentList> call, Response<DocumentList> response) {
                documents.clear();
                customDialog.dismiss();
                if (response.code() == 200) {
                    if (response.body().getTerm() != null){
                        if (response.body().getTerm().equalsIgnoreCase("1")){
                            llSubmit.setVisibility(View.GONE);
                        }
                    }
                    docStatus = response.body().getStatus();
//                    if (docStatus == 1) {
//                        cbTerms.setOnCheckedChangeListener((buttonView, isChecked) -> {
//                            if (isChecked) {
//                                cbTerms.setChecked(false);
//                            } else {
//                                cbTerms.setChecked(true);
//                            }
//                        });
//                    }
                    if (response.body().getDocument() != null) {
                        List<Document> doc = response.body().getDocument();
                        if (!doc.isEmpty()) {
                            documents.addAll(doc);
                            adapter.notifyDataSetChanged();
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<DocumentList> call, Throwable t) {
                customDialog.dismiss();
            }
        });
    }

    private void submitAllDoc() {
        String auth = "Bearer" + " " + SharedHelper.getKey(App.getContext(), "access_token");
        CustomDialog customDialog = new CustomDialog(this);
        customDialog.setCancelable(false);
        customDialog.show();
        Call<DocumentList> call = restInterface.docSubmission(URLHelper.REQUEST_WITH, auth);
        call.enqueue(new Callback<DocumentList>() {
            @Override
            public void onResponse(Call<DocumentList> call, Response<DocumentList> response) {
                customDialog.dismiss();
                if (response.code() == 200){
                    if (response.body().getTerm() != null){
                        if (response.body().getTerm().equalsIgnoreCase("1")){
                            llSubmit.setVisibility(View.GONE);
                            finish();
                        }else {
                            displayMessage(getString(R.string.please_try_again));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<DocumentList> call, Throwable t) {
                customDialog.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                if (imageType.equalsIgnoreCase("Upload")) {
                    uploadDocument(docId, data.getData().getPath());
                }
                if (imageType.equalsIgnoreCase("Update")) {
                    uploadDocument(docId, data.getData().getPath());
                }
            }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            displayMessage(ImagePicker.Companion.getError(data));
        } else {
            displayMessage("Task Cancelled");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @NonNull
    private MultipartBody.Part prepareImage(String keyName, String path) {
        File file = new File(path);
        int imageSize = Integer.parseInt(String.valueOf(file.length() / 1024));
        Log.d("imageSize", String.valueOf(imageSize));
        RequestBody requestFile = RequestBody.create(file, MediaType.parse("image/*"));
        return MultipartBody.Part.createFormData(keyName, file.getName(), requestFile);
    }

    @NonNull
    private RequestBody prepareText(String descString) {
        return RequestBody.create(descString, MultipartBody.FORM);
    }

    private void displayMessage(String message) {
        Toasty.info(App.getContext(), message, Toasty.LENGTH_SHORT, true).show();
    }

}
