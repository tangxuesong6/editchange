package com.example.txs.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivityWhole extends AppCompatActivity {
    /**
     * 搜索框
     */
    private EditText mEdtSearch;
    /**
     * 删除按钮
     */
    private ImageView mImgvDelete;
    /**
     * recyclerview
     */
    private RecyclerView mRcSearch;
    /**
     * 全部匹配的适配器
     */
    private RcAdapterWholeChange adapter;
    /**
     * 所有数据 可以是联网获取 如果有需要可以将其储存在数据库中 我们用简单的String做演示
     */
    private List<String> wholeList;
    /**
     * 此list用来保存符合我们规则的数据
     */
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_whole);
        initView();
        initData();
        refreshUI();
        setListener();
    }

    /**
     * 设置监听
     */
    private void setListener() {
        //edittext的监听
        mEdtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            //每次edittext内容改变时执行 控制删除按钮的显示隐藏
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    mImgvDelete.setVisibility(View.GONE);
                } else {
                    mImgvDelete.setVisibility(View.VISIBLE);
                }
                //匹配文字 变色
                doChangeColor(editable.toString().trim());
            }
        });
        //recyclerview的点击监听
        adapter.setOnItemClickListener(new RcAdapterWholeChange.onItemClickListener() {
            @Override
            public void onClick(View view, int pos) {
                Toast.makeText(MainActivityWhole.this, "妹子 pos== " + pos, Toast.LENGTH_SHORT).show();
            }
        });
        //删除按钮的监听
        mImgvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEdtSearch.setText("");
            }
        });
    }

    /**
     * 字体匹配方法
     */
    private void doChangeColor(String text) {
        //clear是必须的 不然只要改变edittext数据，list会一直add数据进来
        list.clear();
        //不需要匹配 把所有数据都传进来 不需要变色
        if (text.equals("")) {
            list.addAll(wholeList);
            //防止匹配过文字之后点击删除按钮 字体仍然变色的问题
            adapter.setText(null);
            refreshUI();
        } else {
            //如果edittext里面有数据 则根据edittext里面的数据进行匹配 用contains判断是否包含该条数据 包含的话则加入到list中
            for (String i : wholeList) {
                if (i.contains(text)) {
                    list.add(i);
                }
            }
            //设置要变色的关键字
            adapter.setText(text);
            refreshUI();
        }
    }

    private void initData() {
        //假数据  实际开发中请从网络或者数据库获取
        wholeList = new ArrayList<>();
        list = new ArrayList<>();
        wholeList.add("第一天一天");
        wholeList.add("第二天一天");
        wholeList.add("第三天一天");
        wholeList.add("第四天一天");
        wholeList.add("第五天五天");
        wholeList.add("第六天一天");
        wholeList.add("第七天七天");
        wholeList.add("第一天八天");
        wholeList.add("第一天九天");
        wholeList.add("第一天十天");
        wholeList.add("第一天十一天");
        //初次进入程序时 展示全部数据
        list.addAll(wholeList);
    }

    /**
     * 刷新UI
     */
    private void refreshUI() {
        if (adapter == null) {
            adapter = new RcAdapterWholeChange(this, list);
            mRcSearch.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private void initView() {
        mEdtSearch = (EditText) findViewById(R.id.edt_search);
        mImgvDelete = (ImageView) findViewById(R.id.imgv_delete);
        mRcSearch = (RecyclerView) findViewById(R.id.rc_search);
        //Recyclerview的配置
        mRcSearch.setLayoutManager(new LinearLayoutManager(this));
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
