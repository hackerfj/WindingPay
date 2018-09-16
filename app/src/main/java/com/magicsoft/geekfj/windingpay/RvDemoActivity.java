package com.magicsoft.geekfj.windingpay;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.magicsoft.geekfj.windingpay.windrecyclerview.WindingRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fujie on 2018/4/24.
 */

public class RvDemoActivity extends AppCompatActivity {
    private WindingRecyclerView pullToRefreshRecyclerview;
    private MyAdapter myAdapter;
    private List<String> dataList;
    private Handler handler = new Handler(
            new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    if (msg.what == 1) {
                        if (dataList.size() > 0) {
                            dataList.clear();
                        } else {
                            for (int i = 0; i < 20; i++) {
                                dataList.add(i + "");
                            }
                        }
                        myAdapter.updateData(dataList);
//                        pullToRefreshRecyclerview.updateDataComplete();
                        pullToRefreshRecyclerview.showEmptyStatus();
                        return true;
                    } else if (msg.what == 2) {
                        for (int i = 1; i < 20; i++) {
                            dataList.add(i + "");
                        }
                        myAdapter.updateData(dataList);
                        pullToRefreshRecyclerview.updateDataComplete();
                    }
                    return false;
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv);
        initView();
    }

    private void initView() {
        pullToRefreshRecyclerview = findViewById(R.id.rv_winding);
        initData();
        myAdapter = new MyAdapter(this, R.layout.item_string, dataList);
        pullToRefreshRecyclerview.setAdapter(myAdapter);
        pullToRefreshRecyclerview.setEmptyLayout(R.layout.custom_empty_view);

        pullToRefreshRecyclerview.setOnRefreshOrLoadListener(new WindingRecyclerView.OnRefreshOrLoadListener() {
            @Override
            public void onRefresh() {
                new Thread(() -> {
                    try {
                        Thread.sleep(1000);
                        handler.sendEmptyMessage(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }

            @Override
            public void onLoadMore() {
                new Thread(() -> {
                    try {
                        Thread.sleep(1000);
                        handler.sendEmptyMessage(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        });

    }

    private void initData() {
        dataList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            dataList.add(i + "");
        }
    }
}
