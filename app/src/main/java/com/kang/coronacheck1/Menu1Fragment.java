package com.kang.coronacheck1;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Menu1Fragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "로그";

    ViewGroup viewGroup;
    private FloatingActionButton fabQrcode;
    MainActivity activity;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "Menu1Fragment - onAttach() called");
        //이 메소드가 호출될떄는 프래그먼트가 엑티비티위에 올라와있는거니깐 getActivity메소드로 엑티비티참조가능
        activity = (MainActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "Menu1Fragment - onDetach() called");
        //이제 더이상 엑티비티 참조가 안됨
        activity = null;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(TAG, "Menu1Fragment - onCreateView() called");

        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_menu1,container,false);

        fabQrcode = (FloatingActionButton)viewGroup.findViewById(R.id.fab);
        fabQrcode.setOnClickListener(this);


        return viewGroup;
    };

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onClick(View v) {
        Log.d(TAG, "Menu1Fragment - onClick() called");
        Intent intent = new Intent(getActivity(), QrcodeActivity.class);
        startActivity(intent);
        getActivity().isDestroyed();
    }
}