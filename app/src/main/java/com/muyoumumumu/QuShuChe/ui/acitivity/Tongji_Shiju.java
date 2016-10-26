package com.muyoumumumu.QuShuChe.ui.acitivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.amumu.QuShuChe.very_important.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.muyoumumumu.QuShuChe.model.bean.Mnn;

import java.util.ArrayList;

public class Tongji_Shiju extends AppCompatActivity {

	TextView show1,show2,show3,show4,show5,show6,show7,show8;

	int red_time=10000;


	//设置取得各时距的链表
	public static ArrayList<Object> arg_zhixing_obj,arg_zuozhuan_obj,arg_youzhuan_obj,arg_diaotou_obj;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
     	setContentView(R.layout.shiju_tongji);


     	show1=(TextView)findViewById(R.id.showresult5s);
     	show2=(TextView)findViewById(R.id.showresult6s);
     	show3=(TextView)findViewById(R.id.showresult7s);
     	show4=(TextView)findViewById(R.id.showresult8s);
     	show5=(TextView)findViewById(R.id.showresult5ss);
     	show6=(TextView)findViewById(R.id.showresult6ss);
     	show7=(TextView)findViewById(R.id.showresult7ss);
     	show8=(TextView)findViewById(R.id.showresult8ss);

     	String  name= Mnn. getInstance().getName_mark()[0];

		//得到链表
		arg_zhixing_obj= Main_activity.shiju_main(name,"直行",red_time);
		arg_zuozhuan_obj= Main_activity.shiju_main(name,"左转",red_time);
		arg_youzhuan_obj= Main_activity.shiju_main(name,"右转",red_time);
		arg_diaotou_obj= Main_activity.shiju_main(name,"掉头",red_time);

		//都是有效的时距 10s以内的
		ArrayList<Integer>arg_zhixing=(ArrayList<Integer>) arg_zhixing_obj.get(2);
		ArrayList<Integer>arg_zuozhuan=(ArrayList<Integer>) arg_zuozhuan_obj.get(2);
		ArrayList<Integer>arg_youzhuan=(ArrayList<Integer>) arg_youzhuan_obj.get(2);
		ArrayList<Integer>arg_diaotou=(ArrayList<Integer>) arg_diaotou_obj.get(2);

		int num1=0,num2=0,num3=0,num4=0;

		for(int i=0;i<arg_zhixing.size();i++) {
			num1+=arg_zhixing.get(i);
		}for(int i=0;i<arg_zuozhuan.size();i++) {
			num2+=arg_zuozhuan.get(i);
		}for(int i=0;i<arg_youzhuan.size();i++) {
			num3+=arg_youzhuan.get(i);
		}for(int i=0;i<arg_diaotou.size();i++) {
			num4+=arg_diaotou.get(i);
		}

		show1.setText("直行:      "+ ((float)(num1/arg_zhixing.size()/100))/10+" s");
		show2.setText("左转:      "+ ((float)(num2/arg_zuozhuan.size()/100))/10+" s");
		show3.setText("右转:      "+ ((float)(num3/arg_youzhuan.size()/100))/10+" s");
		show4.setText("掉头:      "+ ((float)(num4/arg_diaotou.size()/100))/10+" s");

		//筛选，5s,,可能出现0
		int size_zhixing=0,num_zhixing=0;
		for(int i=0;i<arg_zhixing.size();i++){
			if(arg_zhixing.get(i)<=5000){
				size_zhixing++;
				num_zhixing+=arg_zhixing.get(i);
			}
		}

		//筛选，5s
		int size_zuozhuan=0,num_zuozhuan=0;
		for(int i=0;i<arg_zuozhuan.size();i++){
			if(arg_zuozhuan.get(i)<=5000){
				size_zuozhuan++;
				num_zuozhuan+=arg_zuozhuan.get(i);
			}
		}

		//筛选，5s,
		int size_youzhuan=0,num_youzhuan=0;
		for(int i=0;i<arg_youzhuan.size();i++){
			if(arg_youzhuan.get(i)<=5000){
				size_youzhuan++;
				num_youzhuan+=arg_youzhuan.get(i);
			}
		}

		//筛选，5s
		int size_diaotou=0,num_diaotou=0;
		for(int i=0;i<arg_diaotou.size();i++){
			if(arg_diaotou.get(i)<=5000){
				size_diaotou++;
				num_diaotou+=arg_diaotou.get(i);
			}
		}


		//如果视距都大于5s将会测不出饱和流量的
		int a1=(size_zhixing!=1)?num_zhixing/size_zhixing:1000000000;
		int a2=(size_zuozhuan!=1)?num_zuozhuan/size_zuozhuan:1000000000;
		int a3=(size_youzhuan!=1)?num_youzhuan/size_youzhuan:1000000000;
		int a4=(size_diaotou!=1)?num_diaotou/size_diaotou:1000000000;



		show5.setText("直行:      "+ 3600000/a1 +"  pcu/h");
		show6.setText("左转:      "+ 3600000/a2 +"  pcu/h");
		show7.setText("右转:      "+ 3600000/a3 +"  pcu/h");
		show8.setText("掉头:      "+ 3600000/a4 +"  pcu/h");

		//画图表===================================================================================

		HorizontalBarChart barChart1 = (HorizontalBarChart) findViewById(R.id.BarChart_shiju_1);
		HorizontalBarChart barChart2 = (HorizontalBarChart) findViewById(R.id.BarChart_shiju_2);

		assert barChart1 != null;
		barChart1.animateY(3000, Easing.EasingOption.EaseInCubic);
		assert barChart2 != null;
		barChart2.animateY(3000, Easing.EasingOption.EaseInCubic);

		barChart1.getAxisRight().setEnabled(false);
		barChart2.getAxisRight().setEnabled(false);

		barChart1.getXAxis().setDrawGridLines(false);
		barChart2.getXAxis().setDrawGridLines(false);

		barChart1.getLegend().setEnabled(false);
		barChart2.getLegend().setEnabled(false);

		barChart1.setDescription("");
		barChart2.setDescription("");

		ArrayList<String> xVals = new ArrayList<>();

		xVals.add("直行");
		xVals.add("左转");
		xVals.add("右转");
		xVals.add("掉头");

		ArrayList<BarEntry> yVals_1 = new ArrayList<>();
		ArrayList<BarEntry> yVals_2 = new ArrayList<>();

		yVals_1.add(new BarEntry(((float)num1/arg_zhixing.size())/1000,0));
		yVals_1.add(new BarEntry(((float)num2/arg_zuozhuan.size())/1000,1));
		yVals_1.add(new BarEntry(((float)num3/arg_youzhuan.size())/1000,2));
		yVals_1.add(new BarEntry(((float)num4/arg_diaotou.size())/1000,3));

		yVals_2.add(new BarEntry(3600000/a1,0));
		yVals_2.add(new BarEntry(3600000/a2,1));
		yVals_2.add(new BarEntry(3600000/a3,2));
		yVals_2.add(new BarEntry(3600000/a4,3));

		BarDataSet barset1,barset2;
		barset1 = new BarDataSet(yVals_1,"平均车头时距");
		barset1.setColors(getColors());
		barset2 = new BarDataSet(yVals_2,"饱和流率");
		barset2.setColors(getColors());

		BarData bardata1,bardata2;
		bardata1=new BarData(xVals,barset1);
		bardata2=new BarData(xVals,barset2);

		barChart1.setData(bardata1);
		barChart2.setData(bardata2);

//===============================================================================================

		Button very_tinme = (Button) findViewById(R.id.btn_very_import_time);

		OnClickListener listener=new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Tongji_Shiju.this, ChartActivity_2.class));
			}
		};


		assert very_tinme != null;
		very_tinme.setOnClickListener(listener);}


	//设置颜色
	private int[] getColors() {
		//设置色块数量
		int stacksize = 4;

		// have as many colors as stack-values per entry
		int[] colors = new int[stacksize];

		System.arraycopy(ColorTemplate.MYZIDINGYI_COLORS_4, 0, colors, 0, stacksize);

		return colors;
	}


}
