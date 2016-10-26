package com.muyoumumumu.QuShuChe.widget;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;

import java.text.DecimalFormat;

public class MyYAxisValueFormatter implements YAxisValueFormatter {

    private DecimalFormat mFormat;

    public MyYAxisValueFormatter() {
        //格式化十进制数字，#为不包括0的数字，0为数字
        mFormat = new DecimalFormat("#########");
    }

    @Override
    public String getFormattedValue(float value, YAxis yAxis) {
        //加单位，此处为空
        return mFormat.format(value) + "";
    }
}
