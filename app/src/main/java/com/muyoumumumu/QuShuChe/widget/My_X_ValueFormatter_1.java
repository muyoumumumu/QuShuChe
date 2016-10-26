package com.muyoumumumu.QuShuChe.widget;

import com.github.mikephil.charting.formatter.XAxisValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

public class My_X_ValueFormatter_1 implements XAxisValueFormatter {

    private DecimalFormat mFormat;
    //显示为1位小数哈哈哈哈哈
    public My_X_ValueFormatter_1() {
        mFormat = new DecimalFormat("###########0.00");
    }



    @Override
    public String getXValue(String original, int index, ViewPortHandler viewPortHandler) {
        return mFormat.format(original) + "$";
    }
}
