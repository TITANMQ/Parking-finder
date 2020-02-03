package com.example.parkingfinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CarParkListViewAdapter extends ArrayAdapter<CarparkListDataModel> implements View.OnClickListener{

    private ArrayList<CarparkListDataModel> dataModels;
    private Context context;

    //lookup cache
    private static class ViewHolder {
        TextView carParkName;
        TextView numberOfSpaces;
    }


    public CarParkListViewAdapter(@NonNull Context context, ArrayList<CarparkListDataModel> dataModels) {
        super(context, R.layout.carpark_listview, dataModels);
        this.dataModels = dataModels;
        this.context = context;
    }


    private int position= -1;
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CarparkListDataModel dataModel = dataModels.get(position);

        ViewHolder viewHolder;

        final View result;

        if(convertView == null )
        {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.carpark_listview, parent,false);
            viewHolder.carParkName = (TextView) convertView.findViewById(R.id.carParkName);
            viewHolder.numberOfSpaces = (TextView) convertView.findViewById(R.id.carParkSpaces);

            result = convertView;
            convertView.setTag(viewHolder);
        }else
            {
                viewHolder = (ViewHolder) convertView.getTag();
                result = convertView;
            }

        viewHolder.carParkName.setText(dataModel.getCarParkName());
        viewHolder.numberOfSpaces.setText(Long.toString(dataModel.getNumberOfSpaces()));
        //



        return convertView;
    }

    @Override
    public void onClick(View view) {
        //TODO add onclick functionality
    }
}
