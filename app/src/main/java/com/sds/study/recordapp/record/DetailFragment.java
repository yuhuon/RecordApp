package com.sds.study.recordapp.record;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sds.study.recordapp.R;

/**
 파일 재생 화면을 보여줄 프레그먼트
 */

public class DetailFragment extends Fragment{

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fagment_detail,null);
    }
}
