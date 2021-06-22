package com.example.simpletodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Lance Gleason on 6/16/21 of Polyglot Programming LLC.
 * Web: http://www.polygotprogramminginc.com
 * Twitter: @lgleasain
 * Github: @lgleasain
 */

class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

  List<String> items;
  OnLongClickListener longClickListener;
  OnClickListener onClickListener;

  public interface OnLongClickListener{
    void onLongClickListener(int positon);
  }

  public interface OnClickListener{
    void onItemClicked(int position);
  }

  public ItemsAdapter(List<String> items, OnLongClickListener longClickListener, OnClickListener onClickListener){
    this.items = items;
    this.longClickListener = longClickListener;
    this.onClickListener = onClickListener;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_expandable_list_item_1, parent, false);
    return new ViewHolder(todoView);
  }

  @Override
  public void onBindViewHolder(@NonNull ItemsAdapter.ViewHolder holder, int position) {
    String item = items.get(position);
    holder.bind(item);
  }

  @Override
  public int getItemCount() {
    return items.size();
  }

  // container for recyclerview

  class ViewHolder extends RecyclerView.ViewHolder{



    TextView tvItem;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      tvItem = itemView.findViewById(android.R.id.text1);
    }

    public void bind(String listItem){
      tvItem.setText(listItem);

      tvItem.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          onClickListener.onItemClicked(getAdapterPosition());
        }
      });

      tvItem.setOnLongClickListener(new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
          longClickListener.onLongClickListener(getAdapterPosition());
          return false;
        }
      });
    }
  }
}
