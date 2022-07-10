package com.example.controllerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.controllerapp.R;
import com.example.controllerapp.objects.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater inflater;
    List<Model> modelList;
    ArrayList<Model> arrayList;

    public ListViewAdapter(Context mContext, List<Model> modelList) {
        this.mContext = mContext;
        this.modelList = modelList;
        inflater = LayoutInflater.from(mContext);
        this.arrayList = new ArrayList<Model>();
        this.arrayList.addAll(modelList);
    }

    public class ViewHolder{
        TextView name, full_ip;
        ImageView mIcon;
    }


    @Override
    public int getCount() {
        return modelList.size();
    }

    @Override
    public Object getItem(int i) {
        return modelList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.rows, null);
            holder.name = view.findViewById(R.id.roboName);
            holder.full_ip = view.findViewById(R.id.roboIP);
            holder.mIcon = view.findViewById(R.id.roboImage);
            view.setTag(holder);

        }else{
            holder = (ViewHolder) view.getTag();
        }
        if(modelList==null){
            modelList.add(new Model("null", "null"));
        }
        if(modelList==null){
            modelList.add(new Model("null", "null"));
        }
        holder.name.setText(modelList.get(position).getName());
        holder.full_ip.setText(modelList.get(position).getFull_ip());

      //  view.setOnClickListener(new View.OnClickListener() {
          //  @Override
           // public void onClick(View view) {
           //     Intent intent = new Intent(mContext.getApplicationContext(), JoyStickActivity.class);
           //     mContext.startActivity(intent);
         //   }
        //});

        return view;
    }


    public void filter(String txt){
        txt = txt.toLowerCase(Locale.getDefault());
        modelList.clear();
        if(txt.length()==0){
            modelList.addAll(arrayList);
        }else{
            for(Model model: arrayList){
                if(model.getName().toLowerCase(Locale.getDefault()).contains(txt)){
                    modelList.add(model);
                }
            }
        }
        notifyDataSetChanged();
    }
}
