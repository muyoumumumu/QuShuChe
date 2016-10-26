package com.muyoumumumu.QuShuChe.ui.acitivity;

import android.app.AlertDialog;
import android.app.Service;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.amumu.QuShuChe.very_important.R;
import com.muyoumumumu.QuShuChe.model.bean.Mnn;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Car_count extends AppCompatActivity {

	//判定单双时间
	private static int DOUBLE_CLICK_TIME = 350;

	private List<Long> times = new ArrayList<>();
	private Handler mHandler = new Handler();

	private Runnable r;

	private List<Long> times1 = new ArrayList<>();
	private Handler mHandler1 = new Handler();

	private Runnable r1;

	private List<Long> times2 = new ArrayList<>();
	private Handler mHandler2 = new Handler();

	private Runnable r2;

	private List<Long> times3 = new ArrayList<>();
	private Handler mHandler3 = new Handler();

	private Runnable r3;

	private ContentValues values;

	private String name;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		name = Mnn.getInstance().getName_mark()[0];

		setContentView(R.layout.car_count);

		Button btnt1 = (Button) findViewById(R.id.btn_diaotou);
		Button btnt2 = (Button) findViewById(R.id.btn_zuozhuan);
		Button btnt3 = (Button) findViewById(R.id.btn_zhixing);
		Button btnt4 = (Button) findViewById(R.id.btn_youzhuan);
		assert btnt1 != null;
		btnt1.setOnClickListener(btnt1Listener);
		assert btnt2 != null;
		btnt2.setOnClickListener(btnt2Listener);
		assert btnt3 != null;
		btnt3.setOnClickListener(btnt3Listener);
		assert btnt4 != null;
		btnt4.setOnClickListener(btnt4Listener);


	}


/** btn1的注释*/
	/**
	 * 双击事件判断
	 */
	private boolean diaotou() {
		if (times.size() == 2) {

			if (times.get(times.size() - 1) - times.get(0) < DOUBLE_CLICK_TIME) {
				times.clear();
				insert_dache_diaotou();
				if (mHandler != null) {
					if (r != null) {
						//移除回调
						mHandler.removeCallbacks(r);
						r = null;
					}
				}
				return true;
			} else {
				//这种情况下，第一次点击的时间已经没有用处了，第二次就是“第一次”
				times.remove(0);
			}

		}
		//此处可以添加提示
		//showTips();
		r = new Runnable() {
			@Override
			public void run() {
				insert_xiaoche_diaotou();
			}
		};
		//DOUBLE_CLICK_TIME时间后提示单击事件
		mHandler.postDelayed(r, DOUBLE_CLICK_TIME);
		return false;
	}

	private OnClickListener btnt1Listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			times.add(SystemClock.uptimeMillis());
			diaotou();
			try {
				Vibrator vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
				vibrator.vibrate(new long[]{0, 1000}, -1);
			} catch (Exception ignored) {

			}
		}

	};



	/****
	 * 单双击按钮事件响应调用方法
	 * 由于太长的time13位很影响sqlite操作，而也不能在表中插入text型的time吧，根本不利于查找，于是
	 * 只插入9位数的time，有瑕疵不管不影响应用
	 ***/

	// 单击响应事件
	private void insert_xiaoche_diaotou() {
		//为values分配内存
		values = new ContentValues();
		values.put("direction", "掉头");
		values.put("type", "小车");
		values.put("time", (int) ((new Date().getTime()) % 1000000000));//插入9位的时间数
		values.put("systime", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		Main_activity.mDb.insert(name, null, values);
		//关闭value回收内存
		values.clear();
	}

	// 双击响应事件
	private void insert_dache_diaotou() {
		//为values分配内存
		values = new ContentValues();
		values.put("direction", "掉头");
		values.put("type", "大车");
		values.put("time", (int) ((new Date().getTime()) % 1000000000));
		values.put("systime", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		Main_activity.mDb.insert(name, null, values);
		//关闭value回收内存
		values.clear();
	}


	/** btn2的注释*/

	/**
	 * 双击事件判断
	 */
	private boolean zuozhuan() {
		if (times1.size() == 2) {

			if (times1.get(times1.size() - 1) - times1.get(0) < DOUBLE_CLICK_TIME) {
				times1.clear();
				insert_dache_zuozhuan();
				if (mHandler1 != null) {
					if (r1 != null) {
						//移除回调
						mHandler1.removeCallbacks(r1);
						r1 = null;
					}
				}
				return true;
			} else {
				//这种情况下，第一次点击的时间已经没有用处了，第二次就是“第一次”
				times1.remove(0);
			}

		}
		//此处可以添加提示
		//showTips();
		r1 = new Runnable() {
			@Override
			public void run() {
				insert_xiaoche_zuozhuan();
			}
		};
		//DOUBLE_CLICK_TIME时间后提示单击事件
		mHandler1.postDelayed(r1, DOUBLE_CLICK_TIME);
		return false;
	}

	private OnClickListener btnt2Listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			times1.add(SystemClock.uptimeMillis());
			zuozhuan();
			try {
				Vibrator vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
				vibrator.vibrate(new long[]{0, 1000}, -1);
			} catch (Exception ignored) {
			}

//	 		
		}
//			@Override
//			public void onClick(View v) {
//				nb++;
//				ettext.setText(nb+"");
//			}
	};

	// 单击响应事件
	private void insert_xiaoche_zuozhuan() {
//		       Log.i(TAG, "singleClick");
		//为values分配内存
		values = new ContentValues();
		values.put("direction", "左转");
		values.put("type", "小车");
		values.put("time", (int) ((new Date().getTime()) % 1000000000));
		values.put("systime", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		Main_activity.mDb.insert(name, null, values);
		//关闭value回收内存
		values.clear();
	}

	// 双击响应事件
	private void insert_dache_zuozhuan() {
//		       Log.i(TAG, "doubleClick");  
		//为values分配内存
		values = new ContentValues();
		values.put("direction", "左转");
		values.put("type", "大车");
		values.put("time", (int) ((new Date().getTime()) % 1000000000));
		values.put("systime", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		Main_activity.mDb.insert(name, null, values);
		//关闭value回收内存
		values.clear();

	}

	/** btn3的注释*/

	/**
	 * 双击事件判断
	 */
	private boolean zhixing() {
		if (times2.size() == 2) {

			if (times2.get(times2.size() - 1) - times2.get(0) < DOUBLE_CLICK_TIME) {
				times2.clear();
				insert_dache_zhixing();
				if (mHandler2 != null) {
					if (r2 != null) {
						//移除回调
						mHandler2.removeCallbacks(r2);
						r2 = null;
					}
				}
				return true;
			} else {
				//这种情况下，第一次点击的时间已经没有用处了，第二次就是“第一次”
				times2.remove(0);
			}

		}
		//此处可以添加提示
		//showTips();
		r2 = new Runnable() {
			@Override
			public void run() {
				insert_xiaoche_zhixin();
			}
		};
		//DOUBLE_CLICK_TIME时间后提示单击事件
		mHandler2.postDelayed(r2, DOUBLE_CLICK_TIME);
		return false;
	}

	private OnClickListener btnt3Listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			times2.add(SystemClock.uptimeMillis());
			zhixing();
			try {
				Vibrator vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
				vibrator.vibrate(new long[]{0, 1000}, -1);
			} catch (Exception ignored) {
			}
		}
//				@Override
//				public void onClick(View v) {
//					nb++;
//					ettext.setText(nb+"");
//				}
	};

	// 单击响应事件
	private void insert_xiaoche_zhixin() {
//			       Log.i(TAG, "singleClick");  

		values = new ContentValues();
		values.put("direction", "直行");
		values.put("type", "小车");
		values.put("time", (int) ((new Date().getTime()) % 1000000000));
		values.put("systime", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		Main_activity.mDb.insert(name, null, values);
		//关闭value回收内存
		values.clear();


	}

	// 双击响应事件
	private void insert_dache_zhixing() {
//			       Log.i(TAG, "doubleClick");  


		values = new ContentValues();
		values.put("direction", "直行");
		values.put("type", "大车");
		values.put("time", (int) ((new Date().getTime()) % 1000000000));
		values.put("systime", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		Main_activity.mDb.insert(name, null, values);
		//关闭value回收内存
		values.clear();
	}

	/** btn4的注释*/

	/**
	 * 双击事件判断
	 */
	private boolean youzhuan() {
		if (times3.size() == 2) {

			if (times3.get(times3.size() - 1) - times3.get(0) < DOUBLE_CLICK_TIME) {
				times3.clear();
				insert_dache_youzhuan();
				if (mHandler3 != null) {
					if (r3 != null) {
						//移除回调
						mHandler3.removeCallbacks(r3);
						r3 = null;
					}
				}
				return true;
			} else {
				//这种情况下，第一次点击的时间已经没有用处了，第二次就是“第一次”
				times3.remove(0);
			}

		}
		//此处可以添加提示
		//showTips();
		r3 = new Runnable() {
			@Override
			public void run() {
				insert_xiaoche_youzhuan();
			}
		};
		//DOUBLE_CLICK_TIME时间后提示单击事件
		mHandler3.postDelayed(r3, DOUBLE_CLICK_TIME);
		return false;
	}

	private OnClickListener btnt4Listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			times3.add(SystemClock.uptimeMillis());
			youzhuan();
			try {
				Vibrator vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
				vibrator.vibrate(new long[]{0, 1000}, -1);
			} catch (Exception ignored) {
			}
		}
//					@Override
//					public void onClick(View v) {
//						nb++;
//						ettext.setText(nb+"");
//					}
	};

	// 单击响应事件
	private void insert_xiaoche_youzhuan() {


		values = new ContentValues();
		values.put("direction", "右转");
		values.put("type", "小车");
		values.put("time", (int) ((new Date().getTime()) % 1000000000));
		values.put("systime", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		Main_activity.mDb.insert(name, null, values);
		//关闭value回收内存
		values.clear();

	}

	// 双击响应事件
	private void insert_dache_youzhuan() {

		values = new ContentValues();
		values.put("direction", "右转");
		values.put("type", "大车");
		values.put("time", (int) ((new Date().getTime()) % 1000000000));
		values.put("systime", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		Main_activity.mDb.insert(name, null, values);
		//关闭value回收内存
		values.clear();

	}


	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this).setTitle("确认退出采集界面吗？")
				.setIcon(R.drawable.iiii)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 点击“确认”后的操作
						Car_count.this.finish();

					}
				})
				.setNegativeButton("返回", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 点击“返回”后的操作,这里不设置没有任何操作
					}
				}).show();
	}

}
