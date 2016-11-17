package com.sds.study.recordapp.record;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sds.study.recordapp.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 파일 목록을 보여줄 프레그먼트
 */

public class ListFragment extends Fragment implements AdapterView.OnItemClickListener{
    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> list;
    String TAG;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fagment_list,null);
        TAG=this.getClass().getName();
        listView=(ListView)view.findViewById(R.id.listView);
        list=(ArrayList) getFiles();
        adapter=new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this); //리스트 뷰와 리스너 연결
        return view;
    }
    /*외부 저장소인 iot_record디렉토리의 모든 파일을 가져 오자!!*/
    public List getFiles(){
        File dir=new File(Environment.getExternalStorageDirectory(),"iot_record");
        File[] files=dir.listFiles();
        ArrayList list=new ArrayList();
        for(int i=0;i<files.length;i++){
            list.add(files[i].getName());
        }
        return list;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int index, long id) {
        TextView txt=(TextView) view;
        String filename=txt.getText().toString();
        Toast.makeText(getContext(),"파일명은?"+filename,Toast.LENGTH_SHORT).show();

        /*뷰페이저 중 index1에 해당하는 페이지 소환 하기*/
        FileLIstActivity fileLIstActivity=(FileLIstActivity) getContext();
        Log.d(TAG,"fileListActivity"+fileLIstActivity);
        fileLIstActivity.viewPager.setCurrentItem(1);
    }
}
