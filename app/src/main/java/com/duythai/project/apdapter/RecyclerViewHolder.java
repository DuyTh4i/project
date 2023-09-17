package com.duythai.project.apdapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.duythai.project.R;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    TextView tvContent, tvCategory, tvDate;

    CheckBox cbDone;
    CardView cvItem;

    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        tvContent = itemView.findViewById(R.id.tvContent);
        tvCategory = itemView.findViewById(R.id.tvCategory);
        tvDate = itemView.findViewById(R.id.tvDate);
        cbDone = itemView.findViewById(R.id.cbDone);
        cvItem = itemView.findViewById(R.id.cvItem);
    }
}
