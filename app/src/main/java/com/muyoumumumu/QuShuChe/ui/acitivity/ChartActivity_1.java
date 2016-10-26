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
import com.muyoumumumu.QuShuChe.ui.fragments.chart_flow.LineFragment;
import com.muyoumumumu.QuShuChe.ui.fragments.chart_flow.LineFragment_2;
import com.muyoumumumu.QuShuChe.ui.fragments.chart_flow.LineFragment_2_2;
import com.muyoumumumu.QuShuChe.ui.fragments.chart_flow.MixedBarChartFrag;
import com.muyoumumumu.QuShuChe.ui.fragments.chart_flow.MixedBarChartFrag_2;
import com.muyoumumumu.QuShuChe.ui.fragments.chart_flow.PieChartFrag;

import java.util.ArrayList;

public class ChartActivity_1 extends AppCompatActivity {

    //差点又忘了申明为public 因为为不同的包
    public static float how_time_to_show;

    //存放数据的 ArrayList<Object>表链,其中存储的是Integer类型的链表
    //全体的fragment都可以访问的量
    public static ArrayList<Object>arg=new ArrayList<>();

    //一个用来设置图标自适应提高流畅度的布尔值
    public static Boolean not_Over_60 =true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        //取出表名
        String name = Mnn.getInstance().getName_mark()[0];

        //只访问一次关于自定义多少分钟浏览流量的函数，将Obj放进内存
        //只构建一次内存
        arg = Main_activity.n_time_show(name, how_time_to_show);

        //创建一个适配器可以给每个viewpager页面返回一个fragment
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // 创建一个viewpager并将mSectionsPagerAdapter适配器加载给它
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        assert mViewPager != null;
        mViewPager.setAdapter(mSectionsPagerAdapter);

        //参数小于1时，会默认用1来作为参数，未设置之前，ViewPager会默认加载两个Fragment。
        mViewPager.setOffscreenPageLimit(1);

        //创建tabLayout显示标题，并链接到viewpager上
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        assert tabLayout != null;
        tabLayout.setupWithViewPager(mViewPager);

        //设置是否超过60个需要绘制的点
        not_Over_60=!(((ArrayList<Integer>)arg.get(0)).size()>60);

    }


        //定义
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            // 获取每页的fragment实例
            Fragment f = null;

            switch(position) {
                case 0:
                    f = LineFragment.newInstance();
                    break;
                case 1:
                    f = LineFragment_2_2.newInstance();
                    break;
                case 2:
                    f = LineFragment_2.newInstance();
                    break;
                case 3:
                    f = MixedBarChartFrag.newInstance();
                    break;
                case 4:
                    f = MixedBarChartFrag_2.newInstance();
                    break;
                case 5:
                    f = PieChartFrag.newInstance();
                    break;
            }

            return f;
        }

        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "总流量";
                case 1:
                    return "各方向";
                case 2:
                    return "各车型";
                case 3:
                    return "方向对比";
                case 4:
                    return "车型对比";
                case 5:
                    return "总分布";
            }
            return null;
        }
    }
}
