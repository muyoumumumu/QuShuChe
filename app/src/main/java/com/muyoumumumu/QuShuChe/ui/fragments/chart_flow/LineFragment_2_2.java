package com.muyoumumumu.QuShuChe.ui.fragments.chart_flow;

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
import android.widget.SeekBar;
import android.widget.Toast;

import com.amumu.QuShuChe.very_important.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;
import com.muyoumumumu.QuShuChe.ui.acitivity.ChartActivity_1;
import com.muyoumumumu.QuShuChe.widget.MyMarkerView;
import com.muyoumumumu.QuShuChe.widget.MyValueFormatter_0;

import java.util.ArrayList;
import java.util.List;


public class LineFragment_2_2 extends Fragment implements SeekBar.OnSeekBarChangeListener,
        OnChartGestureListener, OnChartValueSelectedListener {

    public static Fragment newInstance() {
        return new LineFragment_2_2();
    }

    private LineChart mChart;

    private View v;

    //能够自适应大小
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frag_simple_line, container, false);


        ImageView img1 = (ImageView) v.findViewById(R.id.imageViewLine);

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupmenu();
            }
        });


//----------------------------------------------

        mChart = (LineChart) v.findViewById(R.id.lineChart1);
        //手势监听
        mChart.setOnChartGestureListener(this);
        mChart.setOnChartValueSelectedListener(this);
        //绘制底格背景
        mChart.setDrawGridBackground(false);

        // no description text，无横向描述文本
        mChart.setDescription("");
        mChart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        //比例缩放
        mChart.setScaleEnabled(true);
        // mChart.setScaleXEnabled(true);
        // mChart.setScaleYEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        //限制比例缩放，false为单独横向或纵向缩放
        mChart.setPinchZoom(false);

        mChart.setDescriptionTextSize(6f);


        // set an alternative background color
        //图表背景颜色（备用）
        // mChart.setBackgroundColor(Color.GRAY);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        //自定义markerview
        MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view);

        // set the marker to the chart
        mChart.setMarkerView(mv);

        // x-axis limit line
        //x轴限制线，就是表里红色的间断粗线。
        //位置及显示的文字
        LimitLine llXAxis = new LimitLine(10f, "Index 10");
        //限制线宽度
        llXAxis.setLineWidth(4f);
        //定义样式，可中断型的限制线，，- - - - - 像这种
        llXAxis.enableDashedLine(10f, 10f, 0f);
        //设置显示的文字的相对位置，右上
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        //文本大小
        llXAxis.setTextSize(10f);
        //其实上面的没使用用，举例用的


        //xAxis.setValueFormatter(new MyCustomXAxisValueFormatter());
        //
        //xAxis.addLimitLine(llXAxis); // add x-axis limit line


        //使用自定义新字体，getassets为获取自定义库
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");

        //略
        LimitLine ll1 = new LimitLine(130f, "Upper Limit");
        ll1.setLineWidth(4f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(10f);
        ll1.setTypeface(tf);

        LimitLine ll2 = new LimitLine(-30f, "Lower Limit");
        ll2.setLineWidth(4f);
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(10f);
        ll2.setTypeface(tf);

        YAxis leftAxis = mChart.getAxisLeft();
        //清除所有线条，以防重复生成
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines


        //我就是不用限制线
//        leftAxis.addLimitLine(ll1);
//        leftAxis.addLimitLine(ll2);
        //下面的我觉得是重点。。。。。。。。。，以后会重点运用
        //如果设置下面的这句Y轴最大值将被限定不变
//        leftAxis.setAxisMaxValue(220f);

        leftAxis.setAxisMinValue(0f);

        //下面的一句话为y轴文本（其实就是y轴）相对的位移量，比如20就是向下平移20，可能为了对齐考虑
        //leftAxis.setYOffset(20f);

        //为止不知道啥意思。
        leftAxis.enableGridDashedLine(10f, 10f, 0f);

        //y轴0处的实线实线，最好别花，会与虚线重合
        leftAxis.setDrawZeroLine(false);

        // limit lines are drawn behind data (and not on top)
        //限制线在数据下方绘制且不置顶
        leftAxis.setDrawLimitLinesBehindData(true);

        //不绘制图表右方的y轴
        mChart.getAxisRight().setEnabled(false);

        //mChart.getViewPortHandler().setMaximumScaleY(2f);
        //mChart.getViewPortHandler().setMaximumScaleX(2f);



        /*// if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60);

        //不画圆点,以及各种设置，
       */


        // add data
        //插入数据。这个方法是下面定义的哦，它没用上面seekbar的数据



        //没用先不管
        //mChart.setVisibleXRange(20);
        //mChart.setVisibleYRange(20f, YAxis.AxisDependency.LEFT);
        //mChart.centerViewTo(20, 50, YAxis.AxisDependency.LEFT);

        //x轴动作
        mChart.animateY(2000, Easing.EasingOption.EaseInOutCubic);
//        mChart.invalidate();



        // get the legend (only possible after setting data)
        //插入数据后可用，管理数据与颜色用。
        Legend l = mChart.getLegend();


        //图表说明文本的样式，如---时间，----姓名。
        // modify the legend ...
        // l.setPosition(Legend.LegendPosition.LEFT_OF_CHART);//图表左侧，可能默认下方
        l.setForm(Legend.LegendForm.LINE);//标示前方的图标样式，直线啊方形啊圆形啊什么的


        //别忘了刷新绘制。
        // dont forget to refresh the drawing
//         mChart.invalidate();

        setData();

        return v;
    }


    private void  popupmenu() {

        //PopupMenu(环境，显示的相对位置参照)
        PopupMenu popupMenu = new PopupMenu(getActivity(), v.findViewById(R.id.imageViewLine));

        //注意顺序，不然会报错
        Menu menu = popupMenu.getMenu();
        menu.add(Menu.NONE, Menu.FIRST, 0, "显示数值");
        menu.add(Menu.NONE, Menu.FIRST + 1, 1, "数据气泡");
        menu.add(Menu.NONE, Menu.FIRST + 2, 2, "是否填充");
        menu.add(Menu.NONE, Menu.FIRST + 3, 3, "是否绘制标点");
        menu.add(Menu.NONE, Menu.FIRST + 4, 4, "直线 / 曲线");
        menu.add(Menu.NONE, Menu.FIRST + 5, 5, "限制双向缩放");
        menu.add(Menu.NONE, Menu.FIRST + 6, 6, "X轴重绘图表");
        menu.add(Menu.NONE, Menu.FIRST + 7, 7, "Y轴重绘图表");
        menu.add(Menu.NONE, Menu.FIRST + 8, 8, "X+Y轴重绘图表");
        menu.add(Menu.NONE, Menu.FIRST + 9, 9, "保存到SD卡");


        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {



                    case Menu.FIRST: {
                        List<ILineDataSet> sets = mChart.getData()
                                .getDataSets();

                        for (ILineDataSet iSet : sets) {

                            LineDataSet set = (LineDataSet) iSet;
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

                        List<ILineDataSet> sets = mChart.getData()
                                .getDataSets();

                        for (ILineDataSet iSet : sets) {

                            LineDataSet set = (LineDataSet) iSet;
                            if (set.isDrawFilledEnabled())
                                set.setDrawFilled(false);
                            else
                                set.setDrawFilled(true);
                        }
                        mChart.invalidate();
                        break;
                    }
                    case Menu.FIRST + 3: {
                        List<ILineDataSet> sets = mChart.getData()
                                .getDataSets();

                        for (ILineDataSet iSet : sets) {

                            LineDataSet set = (LineDataSet) iSet;
                            if (set.isDrawCirclesEnabled())
                                set.setDrawCircles(false);
                            else
                                set.setDrawCircles(true);
                        }
                        mChart.invalidate();
                        break;
                    }
                    case Menu.FIRST + 4: {
                        List<ILineDataSet> sets = mChart.getData()
                                .getDataSets();

                        for (ILineDataSet iSet : sets) {

                            LineDataSet set = (LineDataSet) iSet;
                            set.setMode(set.getMode() == LineDataSet.Mode.CUBIC_BEZIER
                                    ? LineDataSet.Mode.LINEAR
                                    :  LineDataSet.Mode.CUBIC_BEZIER);
                        }
                        mChart.invalidate();
                        break;
                    }
                    case Menu.FIRST + 5: {
                        if (mChart.isPinchZoomEnabled())
                            mChart.setPinchZoom(false);
                        else
                            mChart.setPinchZoom(true);

                        mChart.invalidate();
                        break;
                    }
                    case Menu.FIRST + 6: {
                        mChart.animateX(3000);
                        break;
                    }
                    case Menu.FIRST + 7: {
                        mChart.animateY(3000, Easing.EasingOption.EaseInOutCubic);
                        break;
                    }
                    case Menu.FIRST + 8: {
                        mChart.animateXY(3000, 3000);
                        break;
                    }
                    case Menu.FIRST + 9: {
                        if (mChart.saveToPath("title" + System.currentTimeMillis(), "")) {
                            Toast.makeText(getActivity(), "保存成功",
                                    Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(getActivity(), "保存失败", Toast.LENGTH_SHORT)
                                    .show();

                        // mChart.saveToGallery("title"+System.currentTimeMillis())
                        break;
                    }
                    default:
                        break;
                }
                return false;
            }
        });

        popupMenu.show();
    }


    private void setData() {


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


        ArrayList<Entry> yVals = new ArrayList<>();
        ArrayList<Entry> yVals_2 = new ArrayList<>();
        ArrayList<Entry> yVals_3 = new ArrayList<>();
        ArrayList<Entry> yVals_4 = new ArrayList<>();

        //此处的i似乎是相对坐标而不是上面的xzh轴对应的参数
        //果然，是相对的

        //zhi
        for(int i=0;i<y_Vals.size();i++) {
            yVals.add(new Entry(y_Vals.get(i) + y_Vals_2.get(5), i));
        }

        //zuo
        for(int i=0;i<y_Vals_2.size();i++) {
            yVals_2.add(new Entry(y_Vals_2.get(i) + y_Vals_6.get(i), i));
        }

        //you
        for(int i=0;i<y_Vals_2.size();i++) {
            yVals_3.add(new Entry(y_Vals_3.get(i) + y_Vals_7.get(i), i));
        }

        //diao
        for(int i=0;i<y_Vals_2.size();i++) {
            yVals_4.add(new Entry(y_Vals_4.get(i) + y_Vals_8.get(i), i));
        }

        //设定横坐标相关参数
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < y_Vals.size(); i++) {
            //x轴的显示内容
            xVals.add(((i+1)*ChartActivity_1.how_time_to_show /60) + "");
        }

        LineDataSet set1;
        LineDataSet set2;
        LineDataSet set3;
        LineDataSet set4;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet)mChart.getData().getDataSetByIndex(0);
            set2 = (LineDataSet)mChart.getData().getDataSetByIndex(0);
            set3 = (LineDataSet)mChart.getData().getDataSetByIndex(0);
            set4 = (LineDataSet)mChart.getData().getDataSetByIndex(0);
            set1.setYVals(yVals);
            set2.setYVals(yVals_2);
            set3.setYVals(yVals_3);
            set4.setYVals(yVals_4);
            mChart.getData().setXVals(xVals);
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(yVals, "直行流量");

            //各种设置
            // set the line to be drawn like this "- - - - - -"
            set1.enableDashedLine(10f, 0f, 0f);
            set1.enableDashedHighlightLine(10f, 0f, 0f);
            set1.setColor(ColorTemplate.MYZIDINGYI_COLORS_4[0]);
            //果断设置,手机果然不是纯白的 是241241241灰的
            set1.setCircleColorHole(Color.WHITE);
            set1.setLineWidth(2.5f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(ChartActivity_1.not_Over_60);
            set1.setCircleColor(ColorTemplate.MYZIDINGYI_COLORS_4[0]);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(ChartActivity_1.not_Over_60);
            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            //填充透明度
            set1.setFillAlpha(70);
            set1.setDrawCircles(ChartActivity_1.not_Over_60);

            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.fade_set1);
                set1.setFillDrawable(drawable);
            }
            else {
                set1.setFillColor(ColorTemplate.MYZIDINGYI_COLORS_4[0]);
            }


            set2 = new LineDataSet(yVals_2, "左转流量");


            //各种设置
            // set the line to be drawn like this "- - - - - -"
            set2.enableDashedLine(10f, 0f, 0f);
            set2.enableDashedHighlightLine(10f, 0f, 0f);
            set2.setColor(ColorTemplate.MYZIDINGYI_COLORS_4[1]);
            //果断设置,手机果然不是纯白的 是241241241灰的
            set2.setCircleColorHole(Color.WHITE);
            set2.setLineWidth(2.5f);
            set2.setCircleRadius(3f);
            set2.setDrawCircleHole(ChartActivity_1.not_Over_60);
            set2.setCircleColor(ColorTemplate.MYZIDINGYI_COLORS_4[1]);
            set2.setValueTextSize(9f);
            set2.setDrawFilled(ChartActivity_1.not_Over_60);
            set2.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            //填充透明度
            set2.setFillAlpha(70);
            set2.setDrawCircles(ChartActivity_1.not_Over_60);

            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.fade_set2);
                set2.setFillDrawable(drawable);
            }
            else {
                set2.setFillColor(ColorTemplate.MYZIDINGYI_COLORS_4[1]);
            }

            set3 = new LineDataSet(yVals_3, "右转流量");


            //各种设置
            // set the line to be drawn like this "- - - - - -"
            set3.enableDashedLine(10f, 0f, 0f);
            set3.enableDashedHighlightLine(10f, 0f, 0f);
            set3.setColor(ColorTemplate.MYZIDINGYI_COLORS_4[2]);
            //果断设置,手机果然不是纯白的 是241241241灰的
            set3.setCircleColorHole(Color.WHITE);
            set3.setLineWidth(2.5f);
            set3.setCircleRadius(3f);
            set3.setDrawCircleHole(ChartActivity_1.not_Over_60);
            set3.setCircleColor(ColorTemplate.MYZIDINGYI_COLORS_4[2]);
            set3.setValueTextSize(9f);
            set3.setDrawFilled(ChartActivity_1.not_Over_60);
            set3.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            //填充透明度
            set3.setFillAlpha(70);
            set3.setDrawCircles(ChartActivity_1.not_Over_60);

            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.fade_set3);
                set3.setFillDrawable(drawable);
            }
            else {
                set3.setFillColor(ColorTemplate.MYZIDINGYI_COLORS_4[2]);
            }


            set4 = new LineDataSet(yVals_4, "掉头流量");


            //各种设置
            // set the line to be drawn like this "- - - - - -"
            set4.enableDashedLine(10f, 0f, 0f);
            set4.enableDashedHighlightLine(10f, 0f, 0f);
            set4.setColor(ColorTemplate.MYZIDINGYI_COLORS_4[3]);
            //果断设置,手机果然不是纯白的 是241241241灰的
            set4.setCircleColorHole(Color.WHITE);
            set4.setLineWidth(2.5f);
            set4.setCircleRadius(3f);
            set4.setDrawCircleHole(ChartActivity_1.not_Over_60);
            set4.setCircleColor(ColorTemplate.MYZIDINGYI_COLORS_4[3]);
            set4.setValueTextSize(9f);
            set4.setDrawFilled(ChartActivity_1.not_Over_60);
            set4.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            //填充透明度
            set4.setFillAlpha(70);
            set4.setDrawCircles(ChartActivity_1.not_Over_60);

            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.fade_set4);
                set4.setFillDrawable(drawable);
            }
            else {
                set4.setFillColor(ColorTemplate.MYZIDINGYI_COLORS_4[3]);
            }


            ArrayList<ILineDataSet> dataSets = new ArrayList<>();

            dataSets.add(set1);
            dataSets.add(set2);
            dataSets.add(set3);
            dataSets.add(set4);

            LineData data = new LineData(xVals, dataSets);
            data.setValueFormatter(new MyValueFormatter_0());

            mChart.setData(data);
        }
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
}
