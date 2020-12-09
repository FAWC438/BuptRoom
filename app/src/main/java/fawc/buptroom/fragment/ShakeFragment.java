package fawc.buptroom.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import fawc.buptroom.R;


public class ShakeFragment extends Fragment {
    private boolean shakedFlag = false;
    public Button startBtn;
    public Button endBtn;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shake, container, false);
    }


}
