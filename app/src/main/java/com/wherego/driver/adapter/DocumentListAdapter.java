package com.wherego.driver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wherego.driver.R;
import com.wherego.driver.helpers.URLHelper;
import com.wherego.driver.listners.DocUploadClick;
import com.wherego.driver.model.documents.Document;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class DocumentListAdapter extends RecyclerView.Adapter<DocumentListAdapter.DocumentHolder> {

    private Context context;
    private ArrayList<Document> documentLists;

    DocUploadClick docUploadClick;
    public void onDocItemClick(DocUploadClick docUploadClick){
        this.docUploadClick = docUploadClick;
    }

    public DocumentListAdapter(Context context, ArrayList<Document> documentLists) {
        this.context = context;
        this.documentLists = documentLists;
    }

    @NonNull
    @Override
    public DocumentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DocumentHolder(LayoutInflater.from(context)
                .inflate(R.layout.document_update_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DocumentHolder holder, int position) {
        Document d = documentLists.get(position);
        if (d.getName() != null){
            holder.tvDocName.setText(d.getName());
        }
        if (d.getImage().equals("not uploaded")){
            holder.tvDocUpload.setVisibility(View.VISIBLE);
        }
        if (!d.getImage().equals("not uploaded")){
            holder.tvDocUpload.setVisibility(View.GONE);
            Picasso.get()
                    .load(URLHelper.IMAGE_BASE + d.getImage())
                    .into(holder.ivDoc);
            holder.ivDoc.setVisibility(View.VISIBLE);
//            holder.tvDocUpdate.setVisibility(View.VISIBLE);
        }
        if (d.getStatus() != null){
            holder.tvDocStatus.setText(d.getStatus());
        }
    }

    @Override
    public int getItemCount() {
        return documentLists.size();
    }

    public class DocumentHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tvDocName)
        TextView tvDocName;
        @BindView(R.id.tvDocStatus)
        TextView tvDocStatus;
        @BindView(R.id.ivDoc)
        CircleImageView ivDoc;
        @BindView(R.id.tvDocUpdate)
        TextView tvDocUpdate;
        @BindView(R.id.tvDocUpload)
        TextView tvDocUpload;

        public DocumentHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            ivDoc.setOnClickListener(this);
            tvDocUpload.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (docUploadClick != null){
                switch (v.getId()){
                    case R.id.ivDoc:
                        docUploadClick.onUpdateClick(getLayoutPosition(), v);
                        break;
                    case R.id.tvDocUpload:
                        docUploadClick.onUploadClick(getLayoutPosition(), v);
                        break;
                }
            }
        }
    }
}
