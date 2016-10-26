package com.muyoumumumu.QuShuChe.ui.acitivity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.amumu.QuShuChe.very_important.R;
import com.muyoumumumu.QuShuChe.model.bean.Mnn;
import com.muyoumumumu.QuShuChe.ui.fragments.chart_time.BarChartFrag_1;
import com.muyoumumumu.QuShuChe.ui.fragments.chart_time.BarChartFrag_2;
import com.muyoumumumu.QuShuChe.ui.fragments.chart_time.BarChartFrag_3;
import com.muyoumumumu.QuShuChe.ui.fragments.chart_time.BarChartFrag_4;
import com.muyoumumumu.QuShuChe.ui.fragments.chart_time.LineFragment_3;

import java.util.ArrayList;

public class ChartActivity_2 extends AppCompatActivity {


    //一个用来设置图标自适应提高流畅度的布尔值
    public static Boolean not_Over_300 =true;

    //设置取得各时距的链表
    public static ArrayList<Integer> arg_zhixing,arg_zuozhuan,arg_youzhuan,arg_diaotou;
    //流量的
    public static ArrayList<Integer> arg_zhixing_l,arg_zuozhuan_l,arg_youzhuan_l,arg_diaotou_l;

    //表名
    public static String  name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        //得到表名
        name = Mnn.getInstance().getName_mark()[0];

        //创建一个适配器可以给每个viewpager页面返回一个fragment
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // 创建一个viewpager并将mSectionsPagerAdapter适配器加载给它
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        assert mViewPager != null;
        mViewPager.setAdapter(mSectionsPagerAdapter);

        //参数小于1时，会默认用1来作为参数，未设置之前，ViewPager会默认加载两个Fragment。
        mViewPager.setOffscreenPageLimit(0);

        //创建tabLayout显示标题，并链接到viewpager上
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        assert tabLayout != null;
        tabLayout.setupWithViewPager(mViewPager);

        //设置not_Over_300属性
        not_Over_300 = Main_activity.get_zongLiuLiang(name) <= 300;

        //得到链表,饱和流率的
        arg_zhixing_l= (ArrayList<Integer>) Tongji_Shiju.arg_zhixing_obj.get(0);
        arg_zuozhuan_l= (ArrayList<Integer>)Tongji_Shiju.arg_zuozhuan_obj.get(0);
        arg_youzhuan_l= (ArrayList<Integer>)Tongji_Shiju.arg_youzhuan_obj.get(0);
        arg_diaotou_l= (ArrayList<Integer>)Tongji_Shiju.arg_diaotou_obj.get(0);

        //得到链表,视距的
        arg_zhixing= (ArrayList<Integer>)Tongji_Shiju.arg_zhixing_obj.get(1);
        arg_zuozhuan= (ArrayList<Integer>)Tongji_Shiju.arg_zuozhuan_obj.get(1);
        arg_youzhuan= (ArrayList<Integer>)Tongji_Shiju.arg_youzhuan_obj.get(1);
        arg_diaotou= (ArrayList<Integer>)Tongji_Shiju.arg_diaotou_obj.get(1);

    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.

            Fragment f = null;

            switch(position) {
                case 0:
                    f = BarChartFrag_1.newInstance();
                    break;
                case 1:
                    f = BarChartFrag_2.newInstance();
                    break;
                case 2:
                    f = BarChartFrag_3.newInstance();
                    break;
                case 3:
                    f = BarChartFrag_4.newInstance();
                    break;
                case 4:
                    f = LineFragment_3.newInstance();
                    break;
            }
            return f;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "直行";
                case 1:
                    return "左转";
                case 2:
                    return "右转";
                case 3:
                    return "掉头";
                case 4:
                    return "遍历图";
            }
            return null;
        }
    }
}
