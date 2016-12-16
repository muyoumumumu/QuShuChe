package com.muyoumumumu.QuShuChe.ui.fragments.chart_time;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;
import com.muyoumumumu.QuShuChe.R;
import com.muyoumumumu.QuShuChe.ui.acitivity.ChartActivity_2;
import com.muyoumumumu.QuShuChe.widget.MyMarkerView;
import com.muyoumumumu.QuShuChe.widget.MyValueFormatter_0;
import com.muyoumumumu.QuShuChe.widget.MyYAxisValueFormatter;
import com.muyoumumumu.QuShuChe.widget.My_X_ValueFormatter_1;

import java.util.ArrayList;
import java.util.List;


public class BarChartFrag_2 extends Fragment implements OnChartGestureListener,
        OnChartValueSelectedListener {

    public BarChartFrag_2() {
    }

    public static Fragment newInstance() {
        return new BarChartFrag_2();
    }

    private BarChart mChart_bar;

    private LineChart mChart_line;

    PopupMenu popupMenu;

    Menu menu;

    ImageView img1;

    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frag_simple_bar_zhuanyong, container, false);

        img1=(ImageView)v.findViewById(R.id.imageViewBar);

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupmenu();
            }
        });


//----------------------------------------------


        mChart_bar = (BarChart) v.findViewById(R.id.BarChart1);
        mChart_bar.setOnChartValueSelectedListener(this);

        mChart_bar.setDescription("");

        //超过12组数据则不显示其值
        mChart_bar.setMaxVisibleValueCount(12);

        // 限制双向缩放
        mChart_bar.setPinchZoom(false);

        //无背景色
        mChart_bar.setDrawGridBackground(false);

        mChart_bar.animateY(2000, Easing.EasingOption.EaseInOutCubic);

        //无阴影
        mChart_bar.setDrawBarShadow(false);

        //值位于条形之上或之下
        mChart_bar.setDrawValueAboveBar(true);

        // 设置y轴标签的状态
        YAxis leftAxis = mChart_bar.getAxisLeft();
        leftAxis.setValueFormatter(new MyYAxisValueFormatter());
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        //不绘制右方的y轴
        mChart_bar.getAxisRight().setEnabled(false);
        //设置x轴在上方
        XAxis xLabels = mChart_bar.getXAxis();
        xLabels.setPosition(XAxisPosition.BOTTOM);

        // mChart_bar.setDrawXLabels(false);
        // mChart_bar.setDrawYLabels(false);

        // setting data

        //设置标签样式
        Legend l = mChart_bar.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_RIGHT);
        l.setFormSize(8f);//色块大小
        //l.setTextSize(20f);//设置文字大小
        l.setFormToTextSpace(4f);//色块与文字的间距
        l.setXEntrySpace(6f);//每个单元（色块+文字）的间距

        //设置markerView,有bug不用了
//        MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view);
//
//        // set the marker to the chart
//        mChart_bar.setMarkerView(mv);

        //设置数据，用mMonth标签替换x轴标签
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("0~2");
        xVals.add("2~5");
        xVals.add("5~10");
        xVals.add(">10");



        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();


        //获取内存数据
        ArrayList<Integer> arg= ChartActivity_2.arg_zuozhuan;

        //设置分配
        float a=0,b=0,c=0,d=0;
        for(int i=0;i<arg.size();i++) {
            if (arg.get(i)<2000) {a++;continue;}
            if (arg.get(i)<5000&&arg.get(i)>=2000) {b++;continue;}
            if (arg.get(i)<10000&&arg.get(i)>=5000) {c++;continue;}
            d++;
        }

        yVals1.add(new BarEntry(a, 0));
        yVals1.add(new BarEntry(b, 1));
        yVals1.add(new BarEntry(c, 2));
        yVals1.add(new BarEntry(d, 3));

        BarDataSet set1;


        //chart的一些设置
        if (mChart_bar.getData() != null &&
                mChart_bar.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart_bar.getData().getDataSetByIndex(0);
            set1.setYVals(yVals1);
            mChart_bar.getData().setXVals(xVals);
            mChart_bar.getData().notifyDataChanged();
            mChart_bar.notifyDataSetChanged();
        } else {
            //chart标签设置
            set1 = new BarDataSet(yVals1, "左转车头时距分布");
            set1.setColors(getColors());
            set1.setValueTextSize(20f);
            set1.setStackLabels(new String[]{"直行", "左转", "右转", "掉头"});

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(xVals, dataSets);
            data.setValueFormatter(new MyValueFormatter_0());

            mChart_bar.setData(data);
        }

        mChart_bar.invalidate();



        /////--------------------------------------------------------------------------------------


        //----------------------------------------------
        LineChart mChart_line = (LineChart) v.findViewById(R.id.LineChart1);
        //手势监听
        mChart_line.setOnChartGestureListener(this);
        mChart_line.setOnChartValueSelectedListener(this);
        //绘制底格背景
        mChart_line.setDrawGridBackground(false);

        // no description text，无横向描述文本
        mChart_line.setDescription("");

        mChart_line.setNoDataTextDescription("You need to provide data for the chart.");

        // enable touch gestures
        mChart_line.setTouchEnabled(true);

        // enable scaling and dragging
        mChart_line.setDragEnabled(true);
        //比例缩放
        mChart_line.setScaleEnabled(true);

        //限制比例缩放，false为单独横向或纵向缩放
        mChart_line.setPinchZoom(false);

        mChart_line.setDescriptionTextSize(6f);

        // set an alternative background color
        //图表背景颜色（备用）
        // mChart.setBackgroundColor(Color.GRAY);


        //使用自定义新字体，getassets为获取自定义库
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");


        YAxis leftAxis_line = mChart_line.getAxisLeft();
        //清除所有线条，以防重复生成
        leftAxis_line.removeAllLimitLines();
        leftAxis_line.setAxisMinValue(0f);

        //设置横坐标保留一位小数
        XAxis ff=new XAxis();
        ff.setValueFormatter(new My_X_ValueFormatter_1());


        //横向分隔线样式  -  -  -  -  -这样
        leftAxis_line.enableGridDashedLine(5f, 10f, 0f);

        //y轴0处的实线实线，最好别花，会与虚线重合
        leftAxis_line.setDrawZeroLine(false);

        // limit lines are drawn behind data (and not on top)
        //限制线在数据下方绘制且不置顶
        leftAxis_line.setDrawLimitLinesBehindData(true);

        //不绘制图表右方的y轴
        mChart_line.getAxisRight().setEnabled(false);

        //60条数据即不显示值
        mChart_line.setMaxVisibleValueCount(60);

        MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view);

        mChart_line.setMarkerView(mv);

        mChart_line.getXAxis().setDrawGridLines(false);


        //x轴动作，有时太卡不画
//        mChart.animateY(2000, Easing.EasingOption.EaseInOutCubic);
        //mChart.invalidate();

        // get the legend (only possible after setting data)
        //插入数据后可用，管理数据与颜色用。
        Legend l_line = mChart_line.getLegend();

        //图表说明文本的样式，如---时间，----姓名。
        // modify the legend ...
        // l.setPosition(Legend.LegendPosition.LEFT_OF_CHART);//图表左侧，可能默认下方
        l_line.setForm(Legend.LegendForm.LINE);//标示前方的图标样式，直线啊方形啊圆形啊什么的


        //别忘了刷新绘制。
        // dont forget to refresh the drawing
        //mChart.invalidate();

        //中转用
        //注意：此处并没有新建对象，是复用了原来的内存，
        // 因此不存在因为新建太多y_Vals太消耗内存的问题

        ArrayList<Integer> y_Vals  = ChartActivity_2.arg_zuozhuan;
        ArrayList<Integer> y_Val_l=ChartActivity_2.arg_zuozhuan_l;

        ArrayList<Entry> yVals = new ArrayList<>();

        int henghengheng=0;
        for(int i=0;i<y_Vals.size();i++) {
            //横轴为时距的宽度
            yVals.add(new Entry(y_Val_l.get(i), henghengheng/100));
            henghengheng += y_Vals.get(i);
        }


        //设定横坐标相关参数
        ArrayList<String> xVals_line = new ArrayList<>();
        for (int i = 0; i < henghengheng/100+1; i++) {
            //x轴的显示内容
            xVals_line.add(i/600+"");
        }

        LineDataSet set1_line;

        set1_line = new LineDataSet(yVals, "左转交通流率");

        //各种设置
        // "- - - - - -"
        set1_line.enableDashedLine(10f, 0f, 0f);
        set1_line.enableDashedHighlightLine(10f, 0f, 0f);
        set1_line.setColor(ColorTemplate.MYZIDINGYI_COLORS_1[0]);
        //果断设置,手机果然不是纯白的 是241241241灰的
        set1_line.setCircleColorHole(Color.WHITE);
        set1_line.setLineWidth(2.5f);
        set1_line.setCircleRadius(3f);
        set1_line.setDrawCircleHole(ChartActivity_2.not_Over_300);
        set1_line.setCircleColor(ColorTemplate.MYZIDINGYI_COLORS_1[0]);
        set1_line.setValueTextSize(9f);
        set1_line.setDrawFilled(ChartActivity_2.not_Over_300);
        set1_line.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        //填充透明度
        set1_line.setFillAlpha(70);
        set1_line.setDrawCircles(ChartActivity_2.not_Over_300);

        if (Utils.getSDKInt() >= 18) {
            Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.fade_set3);
            set1_line.setFillDrawable(drawable);
        } else {
            set1_line.setFillColor(ColorTemplate.MYZIDINGYI_COLORS_1[0]);
        }

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        dataSets.add(set1_line);


        LineData data = new LineData(xVals_line, dataSets);


//设值为整数
        data.setValueFormatter(new MyValueFormatter_0());
        mChart_line.setData(data);

        mChart_line.invalidate();

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
        menu.add(Menu.NONE, Menu.FIRST + 3, 1, "没什么卵用");
        menu.add(Menu.NONE, Menu.FIRST + 4, 1, "设置黑边");
        menu.add(Menu.NONE, Menu.FIRST + 5, 1, "数据气泡");
        menu.add(Menu.NONE, Menu.FIRST + 6, 1, "X轴重绘");
        menu.add(Menu.NONE, Menu.FIRST + 7, 1, "保存到SB卡");



        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case Menu.FIRST + 0: {
                        List<IBarDataSet> sets = mChart_bar.getData()
                                .getDataSets();

                        for (IBarDataSet iSet : sets) {

                            BarDataSet set = (BarDataSet) iSet;
                            set.setDrawValues(!set.isDrawValuesEnabled());
                        }

                        mChart_bar.invalidate();
                        break;
                    }
                    case Menu.FIRST + 1: {
                        if(mChart_bar.getData() != null) {
                            mChart_bar.getData().setHighlightEnabled(!mChart_bar.getData().isHighlightEnabled());
                            mChart_bar.invalidate();
                        }
                        break;
                    }
                    case Menu.FIRST + 2: {
                        if (mChart_bar.isPinchZoomEnabled())
                            mChart_bar.setPinchZoom(false);
                        else
                            mChart_bar.setPinchZoom(true);

                        mChart_bar.invalidate();
                        break;
                    }
                    case Menu.FIRST + 3: {
                        mChart_bar.setAutoScaleMinMaxEnabled(!mChart_bar.isAutoScaleMinMaxEnabled());
                        mChart_bar.notifyDataSetChanged();
                        break;
                    }
                    case Menu.FIRST + 4: {
                        for (IBarDataSet set : mChart_bar.getData().getDataSets())
                            ((BarDataSet)set).setBarBorderWidth(set.getBarBorderWidth() == 1.f ? 0.f : 1.f);

                        mChart_bar.invalidate();
                        break;
                    }
                    case Menu.FIRST + 5: {
                        if (mChart_bar.isDrawHighlightArrowEnabled())
                            mChart_bar.setDrawHighlightArrow(false);
                        else
                            mChart_bar.setDrawHighlightArrow(true);
                        mChart_bar.invalidate();
                        break;
                    }
                    case Menu.FIRST + 6: {
                        mChart_bar.animateY(3000,Easing.EasingOption.EaseInOutCubic);
                        break;
                    }

                    case Menu.FIRST + 7: {
                        if (mChart_bar.saveToGallery("title" + System.currentTimeMillis(), 50)) {
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


    //设置颜色
    private int[] getColors() {
        //设置色块数量
        int stacksize = 4;

        // have as many colors as stack-values per entry
        int[] colors = new int[stacksize];

        System.arraycopy(ColorTemplate.MYZIDINGYI_COLORS_4, 0, colors, 0, stacksize);

        return colors;
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }
}
