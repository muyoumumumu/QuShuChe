package com.muyoumumumu.QuShuChe.ui.fragments.chart_flow;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.muyoumumumu.QuShuChe.model.bean.Mnn;
import com.muyoumumumu.QuShuChe.ui.acitivity.ChartActivity_1;
import com.muyoumumumu.QuShuChe.widget.MyValueFormatter_0;

import java.util.ArrayList;


public class PieChartFrag extends Fragment implements  OnChartValueSelectedListener {

    public PieChartFrag() {
    }

    public static Fragment newInstance() {
        return new PieChartFrag();
    }

    private PieChart mChart,mChart_2;

    private Typeface tf;


    private View v;

    private int DaCheLiuLiang,XiaoCheLiuLiang,ZuoZhuanLiuLiang,YouZhuanLiuLiang,ZhiXinLiuLiang,DiaoTouLiuLiangLiuLiang;

    private String  name;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frag_simple_pie, container, false);

        name= Mnn.getInstance().getName_mark()[0];

        getDate();

//------------------------------

        ImageView img1 = (ImageView) v.findViewById(R.id.imageViewPie1);

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupmenu();
            }
        });




//----------------------------------------------


        //获取id
        mChart = (PieChart) v.findViewById(R.id.pieChart1);

        //设置百分号
//        mChart.setUsePercentValues(true);
        //设置表名，很多时候没用
        mChart.setDescription("");
        //设置piechart的周围间隔，为左上右下
        mChart.setExtraOffsets(5, 10, 5, 5);

        //阻力减速摩擦系数,
        mChart.setDragDecelerationFrictionCoef(0.95f);

        //字体
        tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
        //中部文字字体
        mChart.setCenterTextTypeface(Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf"));
        //设置中部文字，可直接接字符
        mChart.setCenterText(generateCenterSpannableText());

        //设置中间空洞样式
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);
        //设置内环（内边缘的）样式
        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(58f);//孔半径
        mChart.setTransparentCircleRadius(61f);//内环半径

        mChart.setDrawCenterText(true);//设置中间文字

        mChart.setRotationAngle(0);//默认旋转角
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);//可旋转
        mChart.setHighlightPerTapEnabled(true);//点击高亮突出

        // 设置选项监听-----------------
        mChart.setOnChartValueSelectedListener(this);
        //默认动作
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        //标签设置
        Legend l = mChart.getLegend();
        l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

//---------------------------------------------------------------------------




//----------------------------------------------


        //获取id
        mChart_2 = (PieChart) v.findViewById(R.id.pieChart2);

        //设置百分号
//        mChart_2.setUsePercentValues(false);
        //设置表名，很多时候没用
        mChart_2.setDescription("");
        //设置piechart的周围间隔，为左上右下
        mChart_2.setExtraOffsets(5, 10, 5, 5);

        //阻力减速摩擦系数,
        mChart_2.setDragDecelerationFrictionCoef(0.95f);

        //字体
        tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
        //中部文字字体
        mChart_2.setCenterTextTypeface(Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf"));
        //设置中部文字，可直接接字符
        mChart_2.setCenterText(generateCenterSpannableText_2());

        //设置中间空洞样式
        mChart_2.setDrawHoleEnabled(true);
        mChart_2.setHoleColor(Color.WHITE);
        //设置内环（内边缘的）样式
        mChart_2.setTransparentCircleColor(Color.WHITE);
        mChart_2.setTransparentCircleAlpha(110);

        mChart_2.setHoleRadius(58f);//孔半径
        mChart_2.setTransparentCircleRadius(61f);//内环半径

        mChart_2.setDrawCenterText(true);//设置中间文字

        mChart_2.setRotationAngle(0);//默认旋转角
        // enable rotation of the chart by touch
        mChart_2.setRotationEnabled(true);//可旋转
        mChart_2.setHighlightPerTapEnabled(true);//点击高亮突出

        // 设置选项监听-----------------
        mChart_2.setOnChartValueSelectedListener(this);
        //默认动作
        mChart_2.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        //标签设置
        Legend l_2 = mChart_2.getLegend();
        l_2.setPosition(LegendPosition.LEFT_OF_CHART);
        l_2.setXEntrySpace(7f);
        l_2.setYEntrySpace(0f);
        l_2.setYOffset(0f);


        //--------------------------------------------------------------
        setData();
        setData_2();
        //很重要的返回v
        return v;
    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("车型占比");

        s.setSpan(new RelativeSizeSpan(1.7f),0,s.length(),0);
        s.setSpan(new StyleSpan(Typeface.BOLD),0,s.length(),0);
        s.setSpan(new ForegroundColorSpan(Color.rgb(93,95,116)),0,s.length(),0);

        return s;
    }
    private SpannableString generateCenterSpannableText_2() {

        SpannableString s = new SpannableString("转向占比");

        s.setSpan(new RelativeSizeSpan(1.7f),0,s.length(),0);
        s.setSpan(new StyleSpan(Typeface.BOLD),0,s.length(),0);
        s.setSpan(new ForegroundColorSpan(Color.rgb(93,95,116)),0,s.length(),0);

        return s;
    }

    private void  popupmenu() {

        //PopupMenu(环境，显示的相对位置参照)
        PopupMenu popupMenu = new PopupMenu(getActivity(), v.findViewById(R.id.imageViewPie1));

        //注意顺序，不然会报错
        Menu menu = popupMenu.getMenu();
        menu.add(Menu.NONE, Menu.FIRST + 0, 0, "是否显示数值");
        menu.add(Menu.NONE, Menu.FIRST + 1, 3, "是否为环状");
        menu.add(Menu.NONE, Menu.FIRST + 2, 4, "是否显示中心标题");
        menu.add(Menu.NONE, Menu.FIRST + 3, 5, "是否显示环标签");
        menu.add(Menu.NONE, Menu.FIRST + 4, 6, "保存到SD卡");
        menu.add(Menu.NONE, Menu.FIRST + 5, 2, "百分比/数值");
        menu.add(Menu.NONE, Menu.FIRST + 6, 7, "重绘图表");



        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {

                    case Menu.FIRST + 0: {
                        for (IDataSet<?> set : mChart.getData().getDataSets())
                            set.setDrawValues(!set.isDrawValuesEnabled());

                        mChart.invalidate();
                        for (IDataSet<?> set : mChart_2.getData().getDataSets())
                            set.setDrawValues(!set.isDrawValuesEnabled());

                        mChart_2.invalidate();
                        break;
                    }
                    case Menu.FIRST + 1: {
                        if (mChart.isDrawHoleEnabled())
                            mChart.setDrawHoleEnabled(false);
                        else
                            mChart.setDrawHoleEnabled(true);
                        mChart.invalidate();

                        if (mChart_2.isDrawHoleEnabled())
                            mChart_2.setDrawHoleEnabled(false);
                        else
                            mChart_2.setDrawHoleEnabled(true);
                        mChart_2.invalidate();
                        break;
                    }
                    case Menu.FIRST + 2: {
                        if (mChart.isDrawCenterTextEnabled())
                            mChart.setDrawCenterText(false);
                        else
                            mChart.setDrawCenterText(true);
                        mChart.invalidate();

                        if (mChart_2.isDrawCenterTextEnabled())
                            mChart_2.setDrawCenterText(false);
                        else
                            mChart_2.setDrawCenterText(true);
                        mChart_2.invalidate();
                        break;
                    }
                    case Menu.FIRST + 3: {

                        mChart.setDrawSliceText(!mChart.isDrawSliceTextEnabled());
                        mChart.invalidate();

                        mChart_2.setDrawSliceText(!mChart_2.isDrawSliceTextEnabled());
                        mChart_2.invalidate();
                        break;
                    }
                    case Menu.FIRST + 4: {
                        if (mChart.saveToPath("title" + System.currentTimeMillis(), "")&&
                                mChart_2.saveToPath("title" + System.currentTimeMillis(), "")) {
                            Toast.makeText(getActivity(), "保存成功",
                                    Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(getActivity(), "保存失败", Toast.LENGTH_SHORT)
                                    .show();

                        // mChart.saveToGallery("title"+System.currentTimeMillis())
                        break;
                    }
                    case Menu.FIRST + 5:
                        //检测是否是百分比模式，是的话将设置没有百分号的格式，即去百分号且为整数，并且将模式翻转
                        //反之亦然
                       if(mChart.isUsePercentValuesEnabled()){
                           for (IDataSet<?> set : mChart.getData().getDataSets())
                               set.setValueFormatter(new MyValueFormatter_0());
                           mChart.setUsePercentValues(false);
                       }else{
                           for (IDataSet<?> set : mChart.getData().getDataSets())
                               set.setValueFormatter(new PercentFormatter());
                           mChart.setUsePercentValues(true);
                       }
                        mChart.invalidate();

                        if(mChart_2.isUsePercentValuesEnabled()){
                            for (IDataSet<?> set : mChart_2.getData().getDataSets())
                                set.setValueFormatter(new MyValueFormatter_0());
                            mChart_2.setUsePercentValues(false);
                        }else{
                            for (IDataSet<?> set : mChart_2.getData().getDataSets())
                                set.setValueFormatter(new PercentFormatter());
                            mChart_2.setUsePercentValues(true);
                        }
                        mChart_2.invalidate();
                        break;

                    case Menu.FIRST + 6: {
                        mChart.animateY(1400);
                        mChart_2.animateY(1400);
                        break;
                    }
                }
                return false;
            }
        });

        popupMenu.show();
    }



    private void setData() {

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        yVals1.add(new Entry(DaCheLiuLiang, 0));
        yVals1.add(new Entry(XiaoCheLiuLiang, 1));

        ArrayList<String> xVals = new ArrayList<String>();

        xVals.add("大车");
        xVals.add("小车");

        PieDataSet dataSet = new PieDataSet(yVals1, "车型分类占比");

        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);



        dataSet.setColors(getColors());
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(xVals, dataSet);
        //设置自己的显示数值样式
        data.setValueFormatter(new PercentFormatter());

        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(tf);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);
        mChart.setUsePercentValues(true);
        mChart.invalidate();
    }


    private void setData_2() {



        ArrayList<Entry> yVals1 = new ArrayList<Entry>();


         yVals1.add(new Entry(ZuoZhuanLiuLiang, 0));
         yVals1.add(new Entry(YouZhuanLiuLiang, 1));
         yVals1.add(new Entry(ZhiXinLiuLiang, 2));
         yVals1.add(new Entry(DiaoTouLiuLiangLiuLiang, 3));


        ArrayList<String> xVals = new ArrayList<>();


        xVals.add("左转");
        xVals.add("右转");
        xVals.add("直行");
        xVals.add("掉头");

        PieDataSet dataSet = new PieDataSet(yVals1, "转向分类占比");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);



        dataSet.setColors(getColors());
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(xVals, dataSet);
        //设置数值显示样式
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(tf);
        mChart_2.setData(data);

        // undo all highlights
        mChart_2.highlightValues(null);
        mChart_2.setUsePercentValues(true);
        mChart_2.invalidate();
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }


    private int[] getColors() {
        //设置色块数量
        int stacksize = 4;

        // have as many colors as stack-values per entry
        int[] colors = new int[stacksize];

        System.arraycopy(ColorTemplate.MYZIDINGYI_COLORS_4, 0, colors, 0, stacksize);

        return colors;
    }

    private void getDate(){

        ArrayList<Integer> y_Vals= (ArrayList<Integer>) ChartActivity_1.arg.get(1);
        ArrayList<Integer> y_Vals_2 = (ArrayList<Integer>) ChartActivity_1.arg.get(2);
        ArrayList<Integer> y_Vals_3 = (ArrayList<Integer>) ChartActivity_1.arg.get(3);
        ArrayList<Integer> y_Vals_4 = (ArrayList<Integer>) ChartActivity_1.arg.get(4);
        ArrayList<Integer> y_Vals_5 = (ArrayList<Integer>) ChartActivity_1.arg.get(5);
        ArrayList<Integer> y_Vals_6 = (ArrayList<Integer>) ChartActivity_1.arg.get(6);
        ArrayList<Integer> y_Vals_7 = (ArrayList<Integer>) ChartActivity_1.arg.get(7);
        ArrayList<Integer> y_Vals_8 = (ArrayList<Integer>) ChartActivity_1.arg.get(8);

        for(int i=0;i<y_Vals.size();i++) {

            DaCheLiuLiang += y_Vals_5.get(i)+y_Vals_6.get(i)+y_Vals_7.get(i)+y_Vals_8.get(i);

            XiaoCheLiuLiang += y_Vals.get(i)+y_Vals_2.get(i)+y_Vals_3.get(i)+y_Vals_4.get(i);

            ZhiXinLiuLiang += y_Vals.get(i)+y_Vals_5.get(i);

            ZuoZhuanLiuLiang += y_Vals_2.get(i)+y_Vals_6.get(i);

            YouZhuanLiuLiang += y_Vals_3.get(i)+y_Vals_7.get(i);

            DiaoTouLiuLiangLiuLiang += y_Vals_4.get(i)+y_Vals_8.get(i);
        }

    }

}
