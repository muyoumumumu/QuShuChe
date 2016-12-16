package com.muyoumumumu.QuShuChe.ui.acitivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.muyoumumumu.QuShuChe.R;
import com.muyoumumumu.QuShuChe.model.bean.Mnn;
import com.muyoumumumu.QuShuChe.widget.CustomDialog;

import java.util.ArrayList;

public class Tongji_liuliang extends AppCompatActivity {

	//设置环境
	private Context context = this;

	//选项框
	private AlertDialog.Builder builder;

	//分钟记的时间小时，注意是小数float
	private float time_Min;

	//首先定义20天，单位为秒
	float how_time_to_show = 20*24*60*60;
	//获取总流量等的数据，
	ArrayList<Object> arg=new ArrayList<>();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.liuliang_tongji);

		String name = Mnn.getInstance().getName_mark()[0];

		//获取数据
		arg = Main_activity.n_time_show(name, how_time_to_show);

		//中转用
		//注意：此处并没有新建对象，是复用了原来的内存，
		// 因此不存在因为新建太多y_Vals太消耗内存的问题
		//注意方向 总 小直左右掉  大直左右掉

		ArrayList<Integer> y_Vals_zong = (ArrayList<Integer>) arg.get(0);
		ArrayList<Integer> y_Vals_1 = (ArrayList<Integer>) arg.get(1);
		ArrayList<Integer> y_Vals_2 = (ArrayList<Integer>) arg.get(2);
		ArrayList<Integer> y_Vals_3 = (ArrayList<Integer>) arg.get(3);
		ArrayList<Integer> y_Vals_4 = (ArrayList<Integer>) arg.get(4);
		ArrayList<Integer> y_Vals_5 = (ArrayList<Integer>) arg.get(5);
		ArrayList<Integer> y_Vals_6 = (ArrayList<Integer>) arg.get(6);
		ArrayList<Integer> y_Vals_7 = (ArrayList<Integer>) arg.get(7);
		ArrayList<Integer> y_Vals_8 = (ArrayList<Integer>) arg.get(8);

		TextView show1 = (TextView) findViewById(R.id.showresult2);
		TextView show2 = (TextView) findViewById(R.id.showresult3);
		TextView show3 = (TextView) findViewById(R.id.showresult4);
		TextView show4 = (TextView) findViewById(R.id.showresult5);
		TextView show5 = (TextView) findViewById(R.id.showresult6);
		TextView show6 = (TextView) findViewById(R.id.showresult7);
		TextView show7 = (TextView) findViewById(R.id.showresult8);





		//设置assert show3 != null;的原因是避免产生NullPointerException的错误

		assert show1 != null;
		show1.setText("总交通流量：" + y_Vals_zong.get(0) + "  平均小时交通流量:  "+(int)(y_Vals_zong.get(0)/(((float)(int)arg.get(9))/3600000))+" pcu/h");
		assert show2 != null;
		show2.setText("小车:      "
				+ (y_Vals_1.get(0)+y_Vals_2.get(0)+y_Vals_3.get(0)+y_Vals_4.get(0)) + "  " + "\n"
				+ "直行:      "+y_Vals_1.get(0) + "  "
				+ "左转:  "+y_Vals_2.get(0) + "  "
				+ "右转:  "+y_Vals_3.get(0) + "  "
				+ "掉头:  "+y_Vals_4.get(0) + "  "
				+"  ");
		assert show3 != null;
		show3.setText("大车:      "
				+ (y_Vals_5.get(0)+y_Vals_6.get(0)+y_Vals_7.get(0)+y_Vals_8.get(0)) + "  " + "\n"
				+ "直行:      "+y_Vals_5.get(0) + "  "
				+ "左转:  "+y_Vals_6.get(0) + "  "
				+ "右转:  "+y_Vals_7.get(0) + "  "
				+ "掉头:  "+y_Vals_8.get(0) + "  "
				+"  ");
		assert show4 != null;
		show4.setText("直行:      "
				+ "小车:  "+y_Vals_1.get(0) + "  "
				+ "大车:  "+y_Vals_5.get(0) + "  "
				+"  ");
		assert show5 != null;
		show5.setText("右转:      "
				+ "小车:  "+y_Vals_3.get(0) + "  "
				+ "大车:  "+y_Vals_7.get(0) + "  "
				+"  ");
		assert show6 != null;
		show6.setText("左转:      "
				+ "小车:  "+y_Vals_2.get(0) + "  "
				+ "大车:  "+y_Vals_6.get(0) + "  "
				+"  ");
		assert show7 != null;
		show7.setText("掉头:      "
				+ "小车:  "+y_Vals_4.get(0) + "  "
				+ "大车:  "+y_Vals_8.get(0) + "  "
				+"  ");




		//由于要5分钟的流量统计 就只能再用一遍,并显示个小图表
		arg=Main_activity.n_time_show(name,300);

		//然后创建一个5分钟的流量图表----------------------------------------------------------

		LineChart mChart= (LineChart) findViewById(R.id.lineChart_5_min);

		assert mChart != null;

		ArrayList<Entry> yVals=new ArrayList<>();


		for(int i=0;i<((ArrayList<Integer>) arg.get(0)).size();i++){
			yVals.add(new Entry(((ArrayList<Integer>) arg.get(0)).get(i),i));
		}

		ArrayList<String> xVals=new ArrayList<>();
		for(int i=0;i<((ArrayList<Integer>) arg.get(0)).size();i++){
			xVals.add(5*(i+1)+"");
		}



		//无文本
		mChart.setDescription("");

		//不绘制右边轴
		mChart.getAxisRight().setEnabled(false);

		//横轴不画等高线
		mChart.getXAxis().setDrawGridLines(false);

		//画说明标签
		mChart.getLegend().setEnabled(true);
		mChart.getLegend().setForm(Legend.LegendForm.LINE);
		mChart.getLegend().setPosition(Legend.LegendPosition.BELOW_CHART_RIGHT);

		//动作
		mChart.animateY(3000, Easing.EasingOption.EaseInOutCubic);

		LineDataSet set ;

		if (mChart.getData() != null &&
				mChart.getData().getDataSetCount() > 0) {
			set = (LineDataSet)mChart.getData().getDataSetByIndex(0);
			set.setYVals(yVals);
			mChart.getData().setXVals(xVals);
			mChart.notifyDataSetChanged();
		} else {

		set = new LineDataSet(yVals,"5分钟交通流量采集统计");

		//线条颜色
		set.setColor(Color.rgb(35,39,142));

		//标点颜色
		set.setCircleColor(Color.rgb(35,39,142));
		set.setCircleColorHole(Color.WHITE);

		LineData data =new LineData(xVals,set);

		mChart.setData(data);

		}


//按钮部分-----------------------------------------------------------------------------

		Button btn_how_time = (Button) findViewById(R.id.btn_very_import);

		OnClickListener listener = new OnClickListener() {
			@Override
			public void onClick(View v) {

				//设置一个选项框选择时间的分段大小，并将时间传递给Chart_activity1中的n_MIN_To_Show
				final String items[] = {"10s", "30s", "1min", "5min", "20min", "1h", "自定义"};
				//设置环境
				builder = new AlertDialog.Builder(context);
				//标题
				builder.setTitle("请选择统计周期");
				//图标
				builder.setIcon(R.drawable.ddddd);

				builder.setItems(items, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
							case 0:
								//10s
								ChartActivity_1.how_time_to_show = 10;
								startActivity(new Intent(Tongji_liuliang.this, ChartActivity_1.class));
								break;
							case 1:
								//
								ChartActivity_1.how_time_to_show = 30;
								startActivity(new Intent(Tongji_liuliang.this, ChartActivity_1.class));
								break;
							case 2:
								//
								ChartActivity_1.how_time_to_show = 60;
								startActivity(new Intent(Tongji_liuliang.this, ChartActivity_1.class));
								break;
							case 3:
								//5min
								ChartActivity_1.how_time_to_show = 300;
								startActivity(new Intent(Tongji_liuliang.this, ChartActivity_1.class));
								break;
							case 4:
								ChartActivity_1.how_time_to_show = 1200;
								startActivity(new Intent(Tongji_liuliang.this, ChartActivity_1.class));
								break;
							case 5:
								//1h
								ChartActivity_1.how_time_to_show = 3600;
								startActivity(new Intent(Tongji_liuliang.this, ChartActivity_1.class));
								break;
							case 6:
								//自定义时间间隔大小
								try {
									show_zidingyi();
								} catch (Exception e) {
									Log.e(null, "时间设置不成功", e);
								}

								break;
						}
					}
				});

				builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

				//显示选项框
				builder.create().show();

			}
		};


		assert btn_how_time != null;
		btn_how_time.setOnClickListener(listener);


	}


	//自定义
	private void show_zidingyi() {

		CustomDialog dialog;

		CustomDialog.Builder customBuilder = new
				CustomDialog.Builder(context);
		customBuilder.setTitle("自定义时间间隔（单位分钟）\n可输入小数~最小0.05 (3s)")
				.setNegativeButton("确定",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {

								//为何这样，不如直接nameDate;Mnn这招好阴
								time_Min = Mnn.getInstance().getTime();
								dialog.dismiss();

//搬到这里来了================================================================================
								ChartActivity_1.how_time_to_show = time_Min * 60;

								startActivity(new Intent(Tongji_liuliang.this, ChartActivity_1.class));
							}
						})
				.setPositiveButton("取消",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						});


		dialog = customBuilder.create2();

		dialog.show();

	}

}
