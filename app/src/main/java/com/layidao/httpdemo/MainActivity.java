package com.layidao.httpdemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.layidao.httpdemo.adapter.MyQiushiAdapter;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Context mContext = this;
    private ListView listView_main;
    private TextView textView_empty;
    private ProgressBar progressBar_main;
    private MyQiushiAdapter adapter = null;
    private List<Map<String, String>> totalList = new ArrayList<>();
    private Handler handler = new Handler();
    private int currPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件
        initView();
        //加载网络数据
        loadNetworkData();
    }

    private void loadNetworkData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String resultString = OkHttpUtils.loadStringFromUrl(String.format(Constant.URL_LATEST, 1));
                    if (resultString == null) {
                        Toast.makeText(mContext, "网络访问异常", Toast.LENGTH_LONG).show();
                    } else {
                        final List<Map<String, String>> result = jsonStringToList(resultString);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                progressBar_main.setVisibility(View.GONE);
                                if (currPage == 1){
                                    adapter.reloadData(result, true);
                                }else{
                                    adapter.reloadData(result,false);
                                }
                            }
                        });

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, "网络访问异常", Toast.LENGTH_LONG).show();
                }
            }
        }).start();
    }

    private void initView() {
        listView_main = (ListView) findViewById(R.id.listview_main);
        textView_empty = (TextView) findViewById(R.id.textView_empty);
        progressBar_main = (ProgressBar) findViewById(R.id.progressBar_main);

        adapter = new MyQiushiAdapter(totalList, mContext);
        listView_main.setAdapter(adapter);
        listView_main.setEmptyView(textView_empty);
    }

    private List<Map<String, String>> jsonStringToList(String jsonString) {
        List<Map<String, String>> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray_items = jsonObject.getJSONArray("items");
            for (int i = 0, n = jsonArray_items.length(); i < n; i++) {
                Map<String, String> map = new HashMap<>();
                JSONObject jsonObject_item = jsonArray_items.getJSONObject(i);
                map.put("content", jsonObject_item.getString("content"));
                map.put("image", jsonObject_item.getString("image"));
                map.put("comments_count", jsonObject_item.getString("comments_count"));

                JSONObject jsonObject_user = jsonObject_item
                        .optJSONObject("user");
                if (jsonObject_user == null) {
                    map.put("login", "");
                } else {
                    map.put("login", jsonObject_user.optString("login"));
                }
                list.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


}
