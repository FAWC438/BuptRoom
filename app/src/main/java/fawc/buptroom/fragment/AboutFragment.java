package fawc.buptroom.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import fawc.buptroom.R;


public class AboutFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.about, container, false);
        TextView tv = v.findViewById(R.id.show_content);
        assert getArguments() != null;
        tv.setText("开发\nFAWC-bupt\n\n版本:V" + getArguments().getString("Version") + "\n\n项目开源:\nhttps://github.com/FAWC-bupt/BuptRoom\n");
        return v;
    }

}
