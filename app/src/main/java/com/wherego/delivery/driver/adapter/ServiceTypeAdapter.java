package com.wherego.delivery.driver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wherego.delivery.driver.R;
import com.wherego.delivery.driver.model.servicetype.ServiceType;

import java.util.ArrayList;
import java.util.HashSet;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServiceTypeAdapter extends RecyclerView.Adapter<ServiceTypeAdapter.ServiceHolder> {

    private ArrayList<ServiceType> serviceTypes;
    private Context context;
    HashSet<String> checkdata = new HashSet();

    public ServiceTypeAdapter(ArrayList<ServiceType> serviceTypes, Context context) {
        this.serviceTypes = serviceTypes;
        this.context = context;
    }

    @NonNull
    @Override
    public ServiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ServiceHolder(LayoutInflater.from(context)
                .inflate(R.layout.service_type_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceHolder holder, int position) {
        if (serviceTypes.get(position).isSelected()) {
            holder.cbService.setChecked(true);
            checkdata.add(serviceTypes.get(position).getId() + "");
        } else {
            holder.cbService.setChecked(false);
            checkdata.remove(serviceTypes.get(position).getId() + "");
        }

        if (serviceTypes.get(position).getName() != null) {
            holder.tvService.setText(serviceTypes.get(position).getName());
        }
        holder.cbService.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                addData(position, true);
            } else {
                removeData(position, false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return serviceTypes.size();
    }

    public class ServiceHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvService)
        TextView tvService;
        @BindView(R.id.cbService)
        CheckBox cbService;

        public ServiceHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void removeData(int pos, Boolean b) {
        serviceTypes.get(pos).setSelected(b);
        notifyDataSetChanged();
    }

    public void addData(int pos, Boolean b) {
        serviceTypes.get(pos).setSelected(b);
        notifyDataSetChanged();
    }

    public HashSet<String> CheckData() {
        return checkdata;
    }
}
