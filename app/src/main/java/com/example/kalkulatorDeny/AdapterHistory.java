package com.example.kalkulatorDeny;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterHistory extends RecyclerView.Adapter<AdapterHistory.HistoryViewHolder> {
    private Context c;
    private List<History> historyList;

    public AdapterHistory(Context c, List<History> historyList) {
        this.c = c;
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(c);
        View view = inflater.inflate(R.layout.list_history,parent, false);
        HistoryViewHolder historyViewHolder = new HistoryViewHolder(view);
        return historyViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, final int position) {
        final History history = historyList.get(position);
        holder.hasil.setText(history.getHasil());
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView hasil;
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            hasil = itemView.findViewById(R.id.hasil);
        }
    }
}
