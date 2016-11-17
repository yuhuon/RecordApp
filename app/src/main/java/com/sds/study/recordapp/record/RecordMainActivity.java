package com.sds.study.recordapp.record;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.sds.study.recordapp.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RecordMainActivity extends AppCompatActivity{
    MediaRecorder recorder;
    String TAG;
    boolean record_start=false;
    ImageView bt_record;

    static final int REQUEST_RECORD_PERMISSION=1;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG=this.getClass().getName();

        setContentView(R.layout.record_main);
        bt_record=(ImageView)findViewById(R.id.bt_record);
        init();
    }
    /*저장 파일 구하기*/
    public String getSaveFile(){
        File dir=new File(Environment.getExternalStorageDirectory(),"iot_record");

        /*현재 시간 구하기!*/
        Date d= new Date();
        String currentTime=new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(d);
        Log.d(TAG,"시간은"+currentTime);

        File saveFile=new File(dir,currentTime+".mp4");
        return saveFile.getAbsolutePath();
    }
    public void init(){
        recorder=new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
    }

    //녹음 파일 리스트 화면 띄우기
    public void showList(){
        Intent intent=new Intent(this, FileLIstActivity.class);
        startActivity(intent);
    }

    public void startRecord(){
        if(record_start==false) {
            try {
                recorder.setOutputFile(getSaveFile());
                recorder.prepare();
                recorder.start();
                record_start=!record_start;
                bt_record.setImageResource(R.drawable.stop);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            recorder.stop();
            recorder.reset(); /*재 녹음을 위한 초기화*/
            bt_record.setImageResource(R.drawable.record1);
            record_start=!record_start;

            /*녹음이 완료된 화면을 보여주자!!!!*/
            showList();
        }
    }

    /*유저의 권한 처리 결과 받기*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Log.d(TAG,"requestCode는"+ requestCode+"grantResults 0번째는"+grantResults[0]);
        //Log.d(TAG,"requestCode는"+ requestCode+"grantResults 1번째는"+grantResults[1]);
        switch (requestCode){
            case REQUEST_RECORD_PERMISSION:
                if(permissions.length>0 && grantResults[0]==PackageManager.PERMISSION_DENIED){
                    Toast.makeText(this,"앱사용을 위해서는 미디어 접근 권한을 주셔야 합니다.",Toast.LENGTH_SHORT).show();
                }else if(permissions.length>0 && grantResults[1]==PackageManager.PERMISSION_DENIED){
                    Toast.makeText(this,"앱사용을 위해서는 오디오 권한을 주셔야 합니다.",Toast.LENGTH_SHORT).show();
                }
        }
    }

    /*각종 권한을 체크하자*/
    public void btnClick(View view){
        int writePermission= ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int recordPermission=ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO);

        if(writePermission== PackageManager.PERMISSION_DENIED || recordPermission==PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.RECORD_AUDIO},
                    REQUEST_RECORD_PERMISSION);
        }else {
            startRecord();
        }
    }
}
