package fawc.buptroom.utils;

/*
 * Created by think on 20162016/10/25 002511:14
 * PACKAGE:fawc.buptroom
 * PROJECT:BuptRoom
 */

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import fawc.buptroom.R;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

    private final List<Map<String, Object>> mDatas;
    private final LayoutInflater inflater;

    public MyRecyclerAdapter(Context context, List<Map<String, Object>> datas) {
        this.mDatas = datas;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {

        return mDatas.size();
    }

    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.tv.setText(((String) mDatas.get(position).get("text")));
        holder.img.setImageResource((Integer) mDatas.get(position).get("img"));

    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.cardview, parent, false);
        return new MyViewHolder(view);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv;
        ImageView img;

        MyViewHolder(View view) {
            super(view);
            tv = view.findViewById(R.id.textview);
            img = view.findViewById(R.id.imageview);
        }
    }

}