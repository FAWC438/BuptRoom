package fawc.buptroom.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.annotation.Nullable;
import fawc.buptroom.R;


public class VersionFragment extends Fragment {
    @Nullable
    private View v;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.version, container, false);
        TextView tv = v.findViewById(R.id.show_content);
        tv.setText(R.string.version);
        TextView versiontv = v.findViewById(R.id.history_version);
        versiontv.setText(R.string.version_history);
        return v;


    }
}
