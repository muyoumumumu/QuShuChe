package com.muyoumumumu.QuShuChe.ui.fragments.chart_flow;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.muyoumumumu.QuShuChe.ui.acitivity.ChartActivity_1;
import com.muyoumumumu.QuShuChe.widget.MyMarkerView;
import com.muyoumumumu.QuShuChe.widget.MyValueFormatter_0;
import com.muyoumumumu.QuShuChe.widget.MyYAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;


public class MixedBarChartFrag_2 extends Fragment implements SeekBar.OnSeekBarChangeListener,
        OnChartValueSelectedListener {

    public static Fragment newInstance() {
        return new MixedBarChartFrag_2();
    }

    private BarChart mChart;

    PopupMenu popupMenu;

    Menu menu;

    ImageView img1;

    View v;



    String name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frag_simple_bar, container, false);

        img1=(ImageView)v.findViewById(R.id.imageViewBar);

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupmenu();
            }
        });


//----------------------------------------------

        mChart = (BarChart) v.findViewById(R.id.BarChart1);
        mChart.setOnChartValueSelectedListener(this);

        mChart.setDescription("");

        //超过24组数据则不显示其值
        mChart.setMaxVisibleValueCount(24);

        // 限制双向缩放
        mChart.setPinchZoom(false);

        //无背景色
        mChart.setDrawGridBackground(false);

        //无阴影
        mChart.setDrawBarShadow(false);

        mChart.animateY(2000, Easing.EasingOption.EaseInOutCubic);

        //值位于条形之上或之下
        mChart.setDrawValueAboveBar(false);

        MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view);

        // set the marker to the chart
        mChart.setMarkerView(mv);

        //设置文本数字颜色



        // 设置y轴标签的状态
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setValueFormatter(new MyYAxisValueFormatter());
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        //不绘制右方的y轴
        mChart.getAxisRight().setEnabled(false);
        //设置x轴在上方
        XAxis xLabels = mChart.getXAxis();
        xLabels.setPosition(XAxisPosition.TOP);

        // mChart.setDrawXLabels(false);
        // mChart.setDrawYLabels(false);

        // setting data

        //设置标签样式
        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setFormSize(8f);//色块大小
        //l.setTextSize(20f);//设置文字大小
        l.setFormToTextSpace(4f);//色块与文字的间距
        l.setXEntrySpace(6f);//每个单元（色块+文字）的间距


//        //设置markerView，,有bug不用了
//        MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view);
//
//        // set the marker to the chart
//        mChart.setMarkerView(mv);

        //设置数据，用mMonth标签替换x轴标签


        //中转用
        //注意：此处并没有新建对象，是复用了原来的内存，
        // 因此不存在因为新建太多y_Vals太消耗内存的问题

        ArrayList<Integer> y_Vals= (ArrayList<Integer>) ChartActivity_1.arg.get(1);
        ArrayList<Integer> y_Vals_2 = (ArrayList<Integer>) ChartActivity_1.arg.get(2);
        ArrayList<Integer> y_Vals_3 = (ArrayList<Integer>) ChartActivity_1.arg.get(3);
        ArrayList<Integer> y_Vals_4 = (ArrayList<Integer>) ChartActivity_1.arg.get(4);
        ArrayList<Integer> y_Vals_5 = (ArrayList<Integer>) ChartActivity_1.arg.get(5);
        ArrayList<Integer> y_Vals_6 = (ArrayList<Integer>) ChartActivity_1.arg.get(6);
        ArrayList<Integer> y_Vals_7 = (ArrayList<Integer>) ChartActivity_1.arg.get(7);
        ArrayList<Integer> y_Vals_8 = (ArrayList<Integer>) ChartActivity_1.arg.get(8);

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();



        //设定横坐标相关参数
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < y_Vals.size(); i++) {
            //x轴的显示内容
            xVals.add(((i+1)*ChartActivity_1.how_time_to_show /60) + "");
        }


        for (int i = 0; i < y_Vals.size(); i++) {



            //下面是普通的barChart的数据插入，比较一下会发现问题
            //yVals1.add(new BarEntry(val, i));
            yVals1.add(new BarEntry(new float[]
                    { (float)(y_Vals.get(i)
                            +y_Vals_2.get(i)
                            +y_Vals_3.get(i)
                            +y_Vals_4.get(i)
                    ),
                            (float)(y_Vals_5.get(i)
                                    +y_Vals_6.get(i)
                                    +y_Vals_7.get(i)
                                    +y_Vals_8.get(i))}, i));
        }

        BarDataSet set1;


        //chart的一些设置
        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet)mChart.getData().getDataSetByIndex(0);
            set1.setYVals(yVals1);
            mChart.getData().setXVals(xVals);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            //chart标签设置
            set1 = new BarDataSet(yVals1, "各车型流量(veh)");
            set1.setColors(getColors());
            set1.setValueTextColor(Color.WHITE);
            set1.setStackLabels(new String[]{"大车", "小车"});

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(xVals, dataSets);

            data.setValueFormatter(new MyValueFormatter_0());

            mChart.setData(data);
        }

        mChart.invalidate();

        return v;
    }


    private void  popupmenu() {

        //PopupMenu(环境，显示的相对位置参照)
        popupMenu = new PopupMenu(getActivity(), v.findViewById(R.id.imageViewBar));

        //注意顺序，不然会报错
        menu = popupMenu.getMenu();
        menu.add(Menu.NONE, Menu.FIRST + 0, 0, "显示数值");
        menu.add(Menu.NONE, Menu.FIRST + 1, 1, "选择项高亮");
        menu.add(Menu.NONE, Menu.FIRST + 2, 1, "限制双向缩放");

        menu.add(Menu.NONE, Menu.FIRST + 4, 1, "设置黑边");

        menu.add(Menu.NONE, Menu.FIRST + 6, 1, "X轴重绘");
        menu.add(Menu.NONE, Menu.FIRST + 7, 1, "保存到SB卡");



        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case Menu.FIRST + 0: {
                        List<IBarDataSet> sets = mChart.getData()
                                .getDataSets();

                        for (IBarDataSet iSet : sets) {

                            BarDataSet set = (BarDataSet) iSet;
                            set.setDrawValues(!set.isDrawValuesEnabled());
                        }

                        mChart.invalidate();
                        break;
                    }
                    case Menu.FIRST + 1: {
                        if(mChart.getData() != null) {
                            mChart.getData().setHighlightEnabled(!mChart.getData().isHighlightEnabled());
                            mChart.invalidate();
                        }
                        break;
                    }
                    case Menu.FIRST + 2: {
                        if (mChart.isPinchZoomEnabled())
                            mChart.setPinchZoom(false);
                        else
                            mChart.setPinchZoom(true);

                        mChart.invalidate();
                        break;
                    }
                    case Menu.FIRST + 3: {
                        mChart.setAutoScaleMinMaxEnabled(!mChart.isAutoScaleMinMaxEnabled());
                        mChart.notifyDataSetChanged();
                        break;
                    }
                    case Menu.FIRST + 4: {
                        for (IBarDataSet set : mChart.getData().getDataSets())
                            ((BarDataSet)set).setBarBorderWidth(set.getBarBorderWidth() == 1.f ? 0.f : 1.f);

                        mChart.invalidate();
                        break;
                    }


                    case Menu.FIRST + 6: {
                        mChart.animateY(3000,Easing.EasingOption.EaseInOutCubic);
                        break;
                    }

                    case Menu.FIRST + 7: {
                        if (mChart.saveToGallery("title" + System.currentTimeMillis(), 50)) {
                            Toast.makeText(getActivity(), "保存成功", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(getActivity(), "保存失败", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                return false;
            }
        });

        popupMenu.show();
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    //设置颜色
    private int[] getColors() {
        //设置色块数量
        int stacksize = 2;

        // have as many colors as stack-values per entry
        int[] colors = new int[stacksize];

        System.arraycopy(ColorTemplate.MYZIDINGYI_COLORS_2, 0, colors, 0, stacksize);

        return colors;
    }
}
