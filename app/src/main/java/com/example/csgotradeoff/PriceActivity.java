package com.example.csgotradeoff;

import android.app.ListActivity;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PriceActivity extends ListActivity implements Runnable {
    private static String TAG = "kk";
    Handler handler;
    private ArrayList<HashMap<String,String>> listItems;
    private SimpleAdapter listItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_price);
        //initListView();

        Thread t = new Thread(this);
        t.start();
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what == 9){
                    List<HashMap<String,String>> list2 = (List<HashMap<String, String>>)msg.obj;
                    listItemAdapter = new SimpleAdapter(PriceActivity.this,list2,R.layout.activity_price,new String[]{"ItemTitle","ItemDetail"},new int[]{R.id.itemTitle,R.id.itemDetail});
                    setListAdapter(listItemAdapter);
                }
                super.handleMessage(msg);
            }
        };


    }

    @Override
    public void run() {
        List<HashMap<String,String>> retList = new ArrayList<HashMap<String, String>>();
        Bundle bundle = new Bundle();
        Document doc = null;
        Log.i(TAG, "run: prossing....");
        try {

            Thread.sleep(300);
            Log.i(TAG, "run:  price....");
            //doc = Jsoup.connect("http://www.usd-cny.com/bankofchina.htm").get();
            doc = Jsoup.connect("https://www.igxe.cn/csgo/730").get();
            //Elements tables = doc.getElementsByTag("table");
            Elements divs = doc.getElementsByTag("div");
            //Element table = tables.get(0);
//            int i = 0;
//            for(Element div : divs){
//                Log.i(TAG, "run: div["+i+"]"+div);
//                i++;
//           }
            Element div = divs.get(114);
            Elements tds = div.getElementsByTag("div");
//            int i = 0;
//            for(Element td : tds){
//                Log.i(TAG, "run:td["+i+"]"+td);
//                i++;
//            }

            //Elements tds = table.getElementsByTag("td");
            for(int i = 19; i <= 55; i+=6){
                Element td1 = tds.get(i);
                Element td2 = tds.get(i+2);

                String str1 = td1.text();
                String str2 = td2.text();

                HashMap<String,String> map = new HashMap<String, String>();
                map.put("ItemTitle",str1);
                map.put("ItemDetail",str2);
                retList.add(map);
            }
            for(int i = 66; i <= 114; i+=6){
                Element td1 = tds.get(i);
                Element td2 = tds.get(i+2);

                String str1 = td1.text();
                String str2 = td2.text();

                HashMap<String,String> map = new HashMap<String, String>();
                map.put("ItemTitle",str1);
                map.put("ItemDetail",str2);
                retList.add(map);
            }

        } catch (IOException e) {
            Log.i(TAG, "run: fuck1");
            e.printStackTrace();
        } catch (InterruptedException e) {
            Log.i(TAG, "run: fuck2");
            e.printStackTrace();
        }
        Message msg = handler.obtainMessage(9);

        msg.obj = retList;
        handler.sendMessage(msg);
    }

}
