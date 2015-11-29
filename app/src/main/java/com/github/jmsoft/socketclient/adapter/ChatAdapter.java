package com.github.jmsoft.socketclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import socketclient.lg.com.socketclient.R;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.HistoryViewHolder> {

    private List<String> chatMessagesList;

    public ChatAdapter(List<String> historyList) {
        this.chatMessagesList = historyList;
    }

    @Override
    public int getItemCount() {
        return chatMessagesList.size();
    }

    @Override
    public void onBindViewHolder(final HistoryViewHolder historyViewHolder, int i) {
        String chatMessage = chatMessagesList.get(i);
        historyViewHolder.tvName.setText(chatMessage);
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.chat_card, viewGroup, false);

        return new HistoryViewHolder(itemView);
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        protected TextView tvName;

        public HistoryViewHolder(View v) {
            super(v);
            tvName =  (TextView) v.findViewById(R.id.tvChat);
        }
    }
}
