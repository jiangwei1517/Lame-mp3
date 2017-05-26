package com.jiangwei.lameexample;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnVersion;
    private Button btnConvert;
    private Button btnWav;
    private Button btnMp3;
    private String wavPath;
    private String mp3Path;

    static {
        System.loadLibrary("gnustl_shared");
        System.loadLibrary("play_sound");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wavPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "testcase.wav";
        mp3Path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "testcase.mp3";
        btnVersion = (Button) findViewById(R.id.btn_version);
        btnVersion.setOnClickListener(this);
        btnConvert = (Button) findViewById(R.id.btn_convert);
        btnConvert.setOnClickListener(this);
        btnWav = (Button) findViewById(R.id.btn_wav);
        btnWav.setOnClickListener(this);
        btnMp3 = (Button) findViewById(R.id.btn_mp3);
        btnMp3.setOnClickListener(this);

        btnWav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File wav = new File(wavPath);
                if (wav.exists() && wav.isFile()) {
                    MediaPlayer mPlayer = new MediaPlayer();
                    try {
                        mPlayer.setDataSource(wavPath);
                        mPlayer.prepare();
                        mPlayer.start();
                        Toast.makeText(MainActivity.this, "wav文件大小:" + wav.length() + "B", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "没有找到wav文件", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnMp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File mp3 = new File(mp3Path);
                if (mp3.exists() && mp3.isFile()) {
                    MediaPlayer mPlayer = new MediaPlayer();
                    try {
                        mPlayer.setDataSource(mp3Path);
                        mPlayer.prepare();
                        mPlayer.start();
                        Toast.makeText(MainActivity.this, "转换成功,mp3大小是:" + mp3.length() + "B", Toast.LENGTH_SHORT)
                                .show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "没有找到mp3文件", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public native String getLameVersionName();

    public native void converToMp3(String wav, String mp3);

    @Override
    public void onClick(View v) {
        File mp3 = new File(mp3Path);
        MediaPlayer mPlayer = new MediaPlayer();
        switch (v.getId()) {
            case R.id.btn_version:
                Toast.makeText(MainActivity.this, getLameVersionName(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_convert:
                converToMp3(wavPath, mp3Path);
                if (mp3.exists() && mp3.isFile()) {
                    Toast.makeText(MainActivity.this, "转换成功,mp3大小是:" + mp3.length() + "B", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "file doesn't exist or is not a file", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_wav:
                File wav = new File(wavPath);
                if (wav.exists() && wav.isFile()) {
                    mPlayer = new MediaPlayer();
                    try {
                        mPlayer.setDataSource(wavPath);
                        mPlayer.prepare();
                        mPlayer.start();
                        Toast.makeText(MainActivity.this, "wav文件大小:" + wav.length() + "B", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "没有找到wav文件", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_mp3:
                if (mp3.exists() && mp3.isFile()) {
                    mPlayer = new MediaPlayer();
                    try {
                        mPlayer.setDataSource(mp3Path);
                        mPlayer.prepare();
                        mPlayer.start();
                        Toast.makeText(MainActivity.this, "转换成功,mp3大小是:" + mp3.length() + "B", Toast.LENGTH_SHORT)
                                .show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "没有找到mp3文件", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
