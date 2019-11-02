package com.example.csgotradeoff;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;


import com.example.csgotradeoff.adapter.AllCommodityAdapter;
import com.example.csgotradeoff.bean.Commodity;
import com.example.csgotradeoff.util.CommodityDbHelper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    ListView lvAllCommodity;
    List<Commodity> allCommodities = new ArrayList<>();
    ImageButton ibLearning,ibElectronic,ibDaily,ibSports;

    CommodityDbHelper dbHelper;
    AllCommodityAdapter adapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvAllCommodity = findViewById(R.id.lv_all_commodity);
        dbHelper = new CommodityDbHelper(getApplicationContext(), CommodityDbHelper.DB_NAME, null, 1);
        adapter = new AllCommodityAdapter(getApplicationContext());
        allCommodities = dbHelper.readAllCommodities();
        adapter.setData(allCommodities);
        lvAllCommodity.setAdapter(adapter);
        final Bundle bundle = this.getIntent().getExtras();
        final TextView tvStuNumber = findViewById(R.id.tv_student_number);
        String str = "";
        if (bundle != null) {
            str = "欢迎" + bundle.getString("username") + ",你好!";
        }
        tvStuNumber.setText(str);
        //当前登录的账号
        final String stuNum = tvStuNumber.getText().toString().substring(2, tvStuNumber.getText().length() - 4);
        ImageButton IbAddProduct = findViewById(R.id.ib_add_product);
        //跳转到添加物品界面
        IbAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                if (bundle != null) {
                    bundle.putString("user_id", stuNum);
                    intent.putExtras(bundle);
                }
                startActivity(intent);
            }
        });
        ImageButton IbPersonalCenter = findViewById(R.id.ib_personal_center);
        //跳转到个人中心界面
        IbPersonalCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PersonActivity.class);
                if (bundle != null) {
                    bundle.putString("username1", stuNum);
                    intent.putExtras(bundle);
                }
                startActivity(intent);
            }
        });
        //刷新界面
        TextView tvRefresh = findViewById(R.id.tv_refresh);
        tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allCommodities = dbHelper.readAllCommodities();
                adapter.setData(allCommodities);
                lvAllCommodity.setAdapter(adapter);
            }
        });





                //为每一个item设置点击事件
                lvAllCommodity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Commodity commodity = (Commodity) lvAllCommodity.getAdapter().getItem(position);
                        Bundle bundle1 = new Bundle();
                        bundle1.putInt("position", position);
                        bundle1.putByteArray("picture", commodity.getPicture());
                        bundle1.putString("title", commodity.getTitle());
                        bundle1.putString("description", commodity.getDescription());
                        bundle1.putFloat("price", commodity.getPrice());
                        bundle1.putString("phone", commodity.getPhone());
                        bundle1.putString("stuId", stuNum);
                        Intent intent = new Intent(MainActivity.this, ReviewActivity.class);
                        intent.putExtras(bundle1);
                        startActivity(intent);
                    }
                });
        //点击不同的类别,显示不同的商品信息
        ibLearning = findViewById(R.id.ib_gun);
        ibElectronic = findViewById(R.id.ib_rush);
        ibDaily = findViewById(R.id.ib_knife);
        ibSports = findViewById(R.id.ib_glove);
        final Bundle bundle2 = new Bundle();

        ibLearning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle2.putInt("status",1);
                Intent intent = new Intent(MainActivity.this,TypeActivity.class);
                intent.putExtras(bundle2);
                startActivity(intent);
            }
        });

        ibElectronic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle2.putInt("status",2);
                Intent intent = new Intent(MainActivity.this,TypeActivity.class);
                intent.putExtras(bundle2);
                startActivity(intent);
            }
        });

        ibDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle2.putInt("status",3);
                Intent intent = new Intent(MainActivity.this,TypeActivity.class);
                intent.putExtras(bundle2);
                startActivity(intent);
            }
        });

        ibSports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle2.putInt("status",4);
                Intent intent = new Intent(MainActivity.this,TypeActivity.class);
                intent.putExtras(bundle2);
                startActivity(intent);
            }
        });
    }

    public void price(View view) {
        Intent intent = new Intent(this,PriceActivity.class);
        startActivity(intent);
    }
}
