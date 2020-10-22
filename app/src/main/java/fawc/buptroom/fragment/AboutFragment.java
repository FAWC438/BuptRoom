package fawc.buptroom.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import fawc.buptroom.R;

/*
 * Created by think on 20162016/10/8 000818:35
 * PACKAGE:fawc.buptroom
 * PROJECT:BuptRoom
 */

public class AboutFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.about, container, false);
        TextView tv = v.findViewById(R.id.show_content);
        tv.setText(R.string.devoloper_openresource_string);
        return v;
    }

}
