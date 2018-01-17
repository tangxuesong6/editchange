package com.example.testinndexof;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtnIndexof;
    private Button mBtnMatcher;
    private String mString;
    private String mKeyword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnIndexof = (Button) findViewById(R.id.btn_indexof);
        mBtnMatcher = (Button) findViewById(R.id.btn_matcher);
        mString = "第一天第一夜第一个时辰";
        mKeyword = "一";
        setListener();
    }
    private void setListener() {
        mBtnIndexof.setOnClickListener(this);
        mBtnMatcher.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_indexof:
               int pos = mString.indexOf(mKeyword);
                Log.e("test", "indexof== "+pos);
                break;
            case R.id.btn_matcher:
                //条件 keyword
                Pattern pattern = Pattern.compile(mKeyword);
                //匹配
                Matcher matcher = pattern.matcher(mString);
                while (matcher.find()) {
                    int start = matcher.start();
                    Log.e("test", "macher== "+start);
                    int end = matcher.end();
                }
                break;
            default:
                break;
        }
    }
}
