package com.liwen.dor.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.liwen.dor.R;
import com.liwen.dor.entity.Display;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class DisplayAdapter extends ArrayAdapter<Display> {

    int mResourceId = 0;

    public DisplayAdapter(@NonNull Context context, int resource, @NonNull List<Display> objects) {

        super(context, resource, objects);
        mResourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Display dis = getItem(position);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mResourceId, null);
        TextView textName = view.findViewById(R.id.display_item_name);
        textName.setText(dis.getName());

        Button btnSource = view.findViewById(R.id.display_item_source);
        btnSource.setText(dis.getCurrentSignalName());

        return view;
    }


}
