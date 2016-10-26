package com.muyoumumumu.QuShuChe.widget;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

public class MyValueFormatter_0 implements ValueFormatter {

    private DecimalFormat mFormat;
    //显示为整数哈哈哈哈哈
    public MyValueFormatter_0() {
        mFormat = new DecimalFormat("############");
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return mFormat.format(value) + " ";
    }
}
