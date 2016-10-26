package com.muyoumumumu.QuShuChe.ui.acitivity;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.amumu.QuShuChe.very_important.R;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.muyoumumumu.QuShuChe.db.Traffic_db;
import com.muyoumumumu.QuShuChe.model.bean.Mnn;
import com.muyoumumumu.QuShuChe.widget.CustomDialog;
import com.muyoumumumu.QuShuChe.widget.Query_data;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Main_activity extends AppCompatActivity implements OnItemClickListener {

    /****
     * 定义区
     ***/
    public static SQLiteDatabase mDb;
    static SQLiteDatabaseDao dao;
    private static SimpleCursorAdapter adapter = null;
    public static SQLiteDatabase dbRead;
    private SQLiteDatabase dbWrite;
    private CustomDialog dialog;

    private File path = new File("/sdcard/a_tdcs");// 创建目录

    private File f = new File("/sdcard/a_tdcs/A_tdcs.db");// 创建文件

    private SwipeMenuListView myListView;

    private FloatingActionButton fab;

    private CoordinatorLayout container;


    //64k，某些机器还是环境问题dex文件溢出
    /*@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
	}*/


    /****
     * onCreate方法
     ***/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        container = (CoordinatorLayout) findViewById(R.id.main);

        //因为少了这一句
        //<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
        //所以才一直显示判断都是false


        if (!path.exists()) {// 目录存在返回false

            path.mkdirs();// 创建一个目录

        }

        if (!f.exists()) {// 文件存在返回false

            try {

                f.createNewFile();//创建文件

            } catch (IOException e) {


                e.printStackTrace();

            }

        }
        dao = new SQLiteDatabaseDao();
        //==================================================================
        //设置右滑菜单
        myListView = (SwipeMenuListView) findViewById(R.id.mylistview);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                // 新建删除图标
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // 设置背景色
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // 设置item宽度
                deleteItem.setWidth(dp2px(90));
                //设置图标
                deleteItem.setIcon(R.drawable.delete);

                // 新建图标
                SwipeMenuItem changeItem = new SwipeMenuItem(
                        getApplicationContext());
                // 设置背景色
                changeItem.setBackground(new ColorDrawable(Color.rgb(30, 206, 103)));
                // 设置item宽度
                changeItem.setWidth(dp2px(90));
                //设置图标
                changeItem.setIcon(R.drawable.pen2_bule);

                // add到menu
                menu.addMenuItem(changeItem);
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        myListView.setMenuCreator(creator);

        myListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {

                //改成这样的原因是因为c=adapter.getcursor的move
                // 会改变adapter的属性从而导致list在点击后消失再也回不来了，所以没用adapter
                Cursor c = dbRead.query(Traffic_db.TABLE_NAME_data, null, null, null, null, null, null);
                c.moveToPosition(position);
                final String name = c.getString(1);
                c.close();

                switch (index) {
                    case 1:
                        // delete

                    {
                        CustomDialog.Builder customBuilder = new
                                CustomDialog.Builder(Main_activity.this);
                        customBuilder.setTitle("提醒")
                                .setMessage("您确定要删除该项吗")
                                .setNegativeButton("确定",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                                dbWrite.delete(Traffic_db.TABLE_NAME_data, Traffic_db.COLUMN_NAME_data_NAME + "=?", new String[]{name});

                                                dao.dropTable(mDb, name);
                                                refreshNotesListView();
                                                setListViewHeightBasedOnChildren(myListView);
                                                Snackbar.make(container, "删除数据表成功！", Snackbar.LENGTH_SHORT)
                                                        .setAction("Action", null)
                                                        .show();
                                                dialog.dismiss();
                                                fab.show();

                                            }
                                        })
                                .setPositiveButton("取消",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                fab.show();

                                            }
                                        });
                        dialog = customBuilder.create();
                        dialog.show();
                        fab.hide();
                        break;
                    }

                    case 0:
                        //

                    {
                        CustomDialog.Builder customBuilder_change = new
                                CustomDialog.Builder(Main_activity.this);
                        customBuilder_change.setTitle("修改")
                                .setNegativeButton("确定",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                                dbWrite.delete(Traffic_db.TABLE_NAME_data, Traffic_db.COLUMN_NAME_data_NAME + "=?", new String[]{name});

                                                ContentValues cv = new ContentValues();
                                                //为何这样，不如直接nameDate;Mnn这招好阴
                                                cv.put(Traffic_db.COLUMN_NAME_data_NAME, Mnn.getInstance().getName_mark()[0]);
                                                cv.put(Traffic_db.COLUMN_NAME_data_REMARK, Mnn.getInstance().getName_mark()[1]);
                                                cv.put(Traffic_db.COLUMN_NAME_data_DATE, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
                                                //将目录表与数据库文件分开存放，操作都只针对目录表，数据库文件内表一旦创建不能删除
                                                dbWrite.insert(Traffic_db.TABLE_NAME_data, null, cv);

                                                dao.changeTable(mDb, name, Mnn.getInstance().getName_mark()[0]);

                                                refreshNotesListView();
                                                setListViewHeightBasedOnChildren(myListView);
                                                Snackbar.make(container, "修改数据表成功！", Snackbar.LENGTH_SHORT)
                                                        .setAction("Action", null)
                                                        .show();
                                                dialog.dismiss();
                                                fab.show();

                                            }
                                        })
                                .setPositiveButton("取消",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                fab.show();

                                            }
                                        });

                        dialog = customBuilder_change.create3(name);
                        dialog.show();
                        fab.hide();
                        break;
                    }


                }

                return true;


            }

        });

//======================================================================

        Traffic_db db = new Traffic_db(this);
        dbRead = db.getReadableDatabase();

        dbWrite = db.getWritableDatabase();
        adapter = new SimpleCursorAdapter(this, R.layout.list_item, null, new String[]{Traffic_db.COLUMN_NAME_data_NAME, Traffic_db.COLUMN_NAME_data_DATE, Traffic_db.COLUMN_NAME_data_REMARK}, new int[]{R.id.tvName1, R.id.tvDate1, R.id.tvmark});

        //设置adapter数据监听，很重要
        adapter.notifyDataSetChanged();
        myListView.setAdapter(adapter);

		/*//设置一个编号
		ArrayAdapter<String>arradapter=new ArrayAdapter<>(this,R.layout.list_item,R.id.ttttView);
		for(int i=0;i<100;i++){arradapter.add(i+"");}
		myListView.setAdapter(arradapter);*/


        refreshNotesListView();
//		myListView.setOnItemLongClickListener(listViewItemLongClickListener);
        setListViewHeightBasedOnChildren(myListView);
        myListView.setOnItemClickListener(this);


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setBackgroundColor(Color.rgb(0xF9, 0x3F, 0x25));
        fab.setImageResource(R.drawable.qqqq);
        fab.setRippleColor(Color.WHITE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//						.setAction("Action", null).show();
                CustomDialog.Builder customBuilder = new
                        CustomDialog.Builder(Main_activity.this);
                customBuilder.setTitle("新建项目")
                        .setNegativeButton("确定",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        ContentValues cv = new ContentValues();
                                        //为何这样，不如直接nameDate;Mnn这招好阴
                                        cv.put(Traffic_db.COLUMN_NAME_data_NAME, Mnn.getInstance().getName_mark()[0]);
                                        cv.put(Traffic_db.COLUMN_NAME_data_REMARK, Mnn.getInstance().getName_mark()[1]);
                                        cv.put(Traffic_db.COLUMN_NAME_data_DATE, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
                                        //将目录表与数据库文件分开存放，操作都只针对目录表，数据库文件内表一旦创建不能删除
                                        dbWrite.insert(Traffic_db.TABLE_NAME_data, null, cv);
                                        String name1 = Mnn.getInstance().getName_mark()[0];

                                        dao.createTable(mDb, name1);
                                        refreshNotesListView();

                                        setListViewHeightBasedOnChildren(myListView);

                                        Snackbar.make(container, "创建数据表成功！", Snackbar.LENGTH_SHORT)
                                                .setAction("Action", null)
                                                .show();
                                        dialog.dismiss();
                                        fab.show();
                                    }
                                })
                        .setPositiveButton("取消",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        fab.show();
                                    }
                                });


                dialog = customBuilder.create1();
                //反之
                dialog.show();
                fab.hide();
            }
        });

    }


    /**
     * 自己写个query
     * 看不下去了
     *时距系统的相关查询
     *
     * **/
    /******
     * 各方向车头时距变化折线图数据传输
     * <p/>
     * 原来的 卡到死 我改了下
     * <p/>
     * 需要输入表名name,方向direction
     * 查询相应结果
     * 得到相应的时距链表
     * 返回
     ******/

    public static ArrayList<Object> shiju_main(String name, String direction, int red_time) {

        //因为rawQuery中arg必须为String[]型的
        //madan string不能定义数量String[1]是错的
        String[] strings = new String[]{direction};
        //查询相应结果
        Cursor c = mDb.rawQuery(" select * from " + name + " where direction = ? ", strings);
        //设置饱和流率
        ArrayList<Integer> arrayList = new ArrayList<>();
        //设置时距
        ArrayList<Integer> arrayList_time = new ArrayList<>();

        //设置有效时距，剔除大于redtime的
        ArrayList<Integer> arrayList_time_v = new ArrayList<>();
        if (c.moveToFirst()) {
            if (c.getCount() != 1) {
                int time1;
                for (int i = 0; i < c.getCount() - 1; i++) {
                    time1 = c.getInt(3);

                    c.moveToNext();
                    int m = c.getInt(3) - time1;

                    //首先添加与前一辆车的时距
                    arrayList_time.add(m);
                    //添加此时这辆车的交通流率
                    //必须价格大0
                    if (m < red_time && m > 0) {
                        arrayList.add(3600000 / m);
                        arrayList_time_v.add(m);
                    } else if (m >= red_time) {
                        arrayList.add(0);
                        arrayList_time_v.add(0);
                    } else {
                        arrayList.add(0);
                        arrayList_time_v.add(0);
                    }
                }
            } else {
                ArrayList<Integer> n = new ArrayList<>();
                ArrayList<Integer> m = new ArrayList<>();
                ArrayList<Integer> j = new ArrayList<>();
                ArrayList<Object> obj = new ArrayList<>();
                n.add(0);
                m.add(0);
                j.add(0);
                obj.add(n);
                obj.add(m);
                obj.add(j);
                return obj;
            }
            //如果游标为空则说明没数据返回空
        } else {
            ArrayList<Integer> n = new ArrayList<>();
            ArrayList<Integer> m = new ArrayList<>();
            ArrayList<Integer> j = new ArrayList<>();
            ArrayList<Object> obj = new ArrayList<>();
            n.add(0);
            m.add(0);
            j.add(0);
            obj.add(n);
            obj.add(m);
            obj.add(j);
            return obj;
        }
        c.close();

        ArrayList<Object> obj = new ArrayList<>();
        obj.add(arrayList);
        obj.add(arrayList_time);
        obj.add(arrayList_time_v);

        return obj;
    }


    /******
     * n分钟交通流量 图形数据传输
     *
     *很重要
     ******/
    /*****
     * //此处返回的是链表形式ArrayList<Object>,改这个改的要死
     * //以前代码游标重复太多次，这个能很大程度上提高一定的效率
     * //反应时间也不慢，避免多次查询，将数据首先拉入内存处理
     * <p/>
     * //将 n_time_show 设置为 20天 即可以得到总直行小车等数量
     * //如果你要问为什么是20天，因为int数最大能容纳的毫秒数相当于24天
     * //输出的Obj类型的表链内int类型的流量计数的方向为，总，小直左右掉，大直左右掉，共9个
     *****/
    public static ArrayList<Object> n_time_show(String name, float how_time_to_show) {

        int positon = 0;

        //*60太少,我设置为秒为单位，但是可以在后面的选项中*60
        //通过int强转为整数，比如1234什么的
        int period = (int) how_time_to_show * 1000;

        //并不能用int制定z，z1等，不够长会引起负数，因为int只有8位好像
        // 虽然在你的图表没问题但是我的图表有问题,
        //后来截取字符解决了，不过有瑕疵,改成long就没瑕疵了
        long z, z1, z_last, z_first;

        int zongliuliang = 0,
                xiaoche_zhixin = 0, xiaoche_zuozhuan = 0, xiaoche_youzhuan = 0, xiaoche_diaotou = 0,
                dache_zhixin = 0, dache_zuozhuan = 0, dache_youzhuan = 0, dache_diaotou = 0;

        String xiao = "小车";
        String da = "大车";
        String zhixin = "直行";
        String zuozhuan = "左转";
        String youzhuan = "右转";
        String diaotou = "掉头";

        String cartype, turntype;

        //定义一堆链表
        ArrayList<Object> arg = new ArrayList<>();

        ArrayList<Integer> Arrzongliuliang = new ArrayList<>();

        ArrayList<Integer> Arrxiaoche_zhixin = new ArrayList<>();
        ArrayList<Integer> Arrxiaoche_zuozhuan = new ArrayList<>();
        ArrayList<Integer> Arrxiaoche_youzhuan = new ArrayList<>();
        ArrayList<Integer> Arrxiaoche_diaotou = new ArrayList<>();
        ArrayList<Integer> Arrdache_zhixin = new ArrayList<>();
        ArrayList<Integer> Arrdache_zuozhuan = new ArrayList<>();
        ArrayList<Integer> Arrdache_youzhuan = new ArrayList<>();
        ArrayList<Integer> Arrdache_diaotou = new ArrayList<>();

        //全查询
        Cursor cursor = mDb.query(name, null, null, null, null, null, null);

        cursor.moveToFirst();

        z_first = cursor.getInt(3);

        z = cursor.getInt(3);

        cursor.moveToLast();

        z_last = cursor.getInt(3);

        //加个看时间的
        int zong_shi_chang = (int) (z_last - z_first);

        //根据首尾时间差及间隔计算 间隔单位的个数
        //	+1是因为int的去尾计算方式可能会出现0
        int jian_ge_shu = (int) ((z_last - z_first) / period) + 1;

        for (int j = 0; j < jian_ge_shu; j++) {

            cursor.moveToPosition(positon);//到制定位置

            for (int i = 0; i < cursor.getCount() - positon; i++) {

                cartype = cursor.getString(2);
                turntype = cursor.getString(1);
                z1 = cursor.getInt(3);

                //游标下移一格
                cursor.moveToNext();

                if (z1 <= z + period && z1 >= z) {

                    //此处略作修改===================================================================================

                    zongliuliang++;

                    if (cartype.equals(xiao)) if (turntype.equals(zhixin)) {
                        xiaoche_zhixin++;
                        continue;
                    }
                    if (cartype.equals(xiao)) if (turntype.equals(zuozhuan)) {
                        xiaoche_zuozhuan++;
                        continue;
                    }
                    if (cartype.equals(xiao)) if (turntype.equals(youzhuan)) {
                        xiaoche_youzhuan++;
                        continue;
                    }
                    if (cartype.equals(xiao)) if (turntype.equals(diaotou)) {
                        xiaoche_diaotou++;
                        continue;
                    }
                    if (cartype.equals(da)) if (turntype.equals(zhixin)) {
                        dache_zhixin++;
                        continue;
                    }
                    if (cartype.equals(da)) if (turntype.equals(zuozhuan)) {
                        dache_zuozhuan++;
                        continue;
                    }
                    if (cartype.equals(da)) if (turntype.equals(youzhuan)) {
                        dache_youzhuan++;
                        continue;
                    }
                    dache_diaotou++;
                }

            }

            positon = positon + zongliuliang;

            //记录流量
            Arrzongliuliang.add(zongliuliang);

            Arrxiaoche_zhixin.add(xiaoche_zhixin);
            Arrxiaoche_zuozhuan.add(xiaoche_zuozhuan);
            Arrxiaoche_youzhuan.add(xiaoche_youzhuan);
            Arrxiaoche_diaotou.add(xiaoche_diaotou);
            Arrdache_zhixin.add(dache_zhixin);
            Arrdache_zuozhuan.add(dache_zuozhuan);
            Arrdache_youzhuan.add(dache_youzhuan);
            Arrdache_diaotou.add(dache_diaotou);

            //重置
            zongliuliang = 0;
            xiaoche_zhixin = 0;
            xiaoche_zuozhuan = 0;
            xiaoche_youzhuan = 0;
            xiaoche_diaotou = 0;
            dache_zhixin = 0;
            dache_zuozhuan = 0;
            dache_youzhuan = 0;
            dache_diaotou = 0;

            z = z + period;

        }

        cursor.close();

        arg.add(Arrzongliuliang);

        arg.add(Arrxiaoche_zhixin);
        arg.add(Arrxiaoche_zuozhuan);
        arg.add(Arrxiaoche_youzhuan);
        arg.add(Arrxiaoche_diaotou);
        arg.add(Arrdache_zhixin);
        arg.add(Arrdache_zuozhuan);
        arg.add(Arrdache_youzhuan);
        arg.add(Arrdache_diaotou);
        //加了个看时间的
        arg.add(zong_shi_chang);

        return arg;

    }


    /***
     * 查询数据表中某个值
     ****/

    //为什么不直接getCount？？
    //改过---------------------------------------------------------------------------------。。。。。。。。。。。。。。。。。。。。。。--
    public static int get_zongLiuLiang(String name) {
        Cursor c = mDb.query(name, null, null, null, null, null, null);
        int k;
        k = c.getCount();
        c.close();
        return k;
    }


    /****
     * 数据库查询事件响应调用方法
     ***/
    public static Spanned ChaXunShuJu(String table_name) {

        Cursor c = dao.getAllData(mDb, table_name);
        String s = "  ";
        int columnsSize = c.getColumnCount();
        String[] columns = c.getColumnNames();
        String columnsName = "  ";
        //获取表头
        for (String col : columns) {

            columnsName += col + "\u0020 \u0020";
        }
        //获取表的内容
        while (c.moveToNext()) {

            for (int i = 0; i < columnsSize; i++) {
                s += c.getString(i) + "\u0020 \u0020";
            }
            s += "<br>";
        }
        c.close();
        return Html.fromHtml("<h5>" + columnsName + "</h5>" + s);
    }
    //


    /***
     * 刷新方法
     ****/
    public void refreshNotesListView() {

        adapter.changeCursor(dbRead.query(Traffic_db.TABLE_NAME_data, null, null, null, null, null, null));

    }


    /**
     * 点击单击响应事件
     ***/
    //改过
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//==============================================================================

        final int position1 = position;
        final String itemss[] = {"数据采集", "数据查询", "流量统计", "时距统计"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(itemss, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //改成这样的原因是因为c=adapter.getcursor的move
                // 会改变adapter的属性从而导致list在点击后消失再也回不来了，所以没用adapter
                Cursor c = dbRead.query(Traffic_db.TABLE_NAME_data, null, null, null, null, null, null);
                c.moveToPosition(position1);
                final String name = c.getString(1);
                final String[] name1 = new String[]{c.getString(1)};
                c.close();

                Mnn.getInstance().setName_mark(name1);

                switch (which) {
                    case 1:

                        if (get_zongLiuLiang(name) == 0) {
                            new AlertDialog.Builder(Main_activity.this).setTitle("警告！")
                                    .setIcon(R.drawable.mynotice)
                                    .setMessage("数据库为空！")
                                    .setNegativeButton("返回", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // 点击“返回”后的操作,这里不设置没有任何操作
                                        }
                                    }).show();
                        } else {
                            startActivity(new Intent(Main_activity.this, Query_data.class));
                        }
                        break;
                    case 0:

                        //此处添加一条警告，如果总流量不为0，即为曾经采集过数据，
                        // 那么就不应该 轻易 在很长的时间间隔之后还继续打开采集

                        if (get_zongLiuLiang(name) != 0) {
                            new AlertDialog.Builder(Main_activity.this).setTitle("警告！")
                                    .setIcon(R.drawable.mynotice)
                                    .setMessage("您以前曾近采集过数据\n过长的采集时间间隔会\n导致数据的准确性降低\n建议您新建项目采集，\n继续采集数据？")
                                    .setPositiveButton("继续", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // 点击“继续”后的操作
                                            startActivity(new Intent(Main_activity.this, Car_count.class));

                                        }
                                    })
                                    .setNegativeButton("返回", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // 点击“返回”后的操作,这里不设置没有任何操作
                                        }
                                    }).show();
                        } else {
                            startActivity(new Intent(Main_activity.this, Car_count.class));
                        }

                        break;
                    case 2:

                        //此处添加一条警告，如果总流量不为0，即为曾经采集过数据，
                        // 那么就不应该 轻易 在很长的时间间隔之后还继续打开采集

                        if (get_zongLiuLiang(name) == 0) {
                            new AlertDialog.Builder(Main_activity.this).setTitle("警告！")
                                    .setIcon(R.drawable.mynotice)
                                    .setMessage("数据库为空！")
                                    .setNegativeButton("返回", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // 点击“返回”后的操作,这里不设置没有任何操作
                                        }
                                    }).show();
                        } else {
                            startActivity(new Intent(Main_activity.this, Tongji_liuliang.class));
                        }

                        break;
                    case 3:

                        if (get_zongLiuLiang(name) == 0) {
                            new AlertDialog.Builder(Main_activity.this).setTitle("警告！")
                                    .setIcon(R.drawable.mynotice)
                                    .setMessage("数据库为空！")
                                    .setNegativeButton("返回", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // 点击“返回”后的操作,这里不设置没有任何操作
                                        }
                                    }).show();
                        } else {
                            startActivity(new Intent(Main_activity.this, Tongji_Shiju.class));
                        }
                        break;

                }
            }
        });
        builder.create().show();
    }


    /***
     * 动态设置listView的高度
     */
    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


    /************
     * 对数据库的操作
     ***********************/

    private class SQLiteDatabaseDao {

        public SQLiteDatabaseDao() {
            mDb = SQLiteDatabase.openOrCreateDatabase(f, null);
        }

        //改time为integer型方便查找
        public void createTable(SQLiteDatabase mDb, String table) {
            try {
                mDb.execSQL("create table if not exists " + table +
                        " (_id integer primary key autoincrement, "
                        + "direction text not null, " +
                        "type text not null, " +
                        "time integer not null, systime text not null);");
            } catch (SQLException e) {
                Toast.makeText(getApplicationContext(), "数据表创建失败",
                        Toast.LENGTH_LONG).show();
            }
        }

        // 删除一个表
        public void dropTable(SQLiteDatabase mDb, String table) {

            try {
                mDb.execSQL("drop table if exists " + table);
            } catch (SQLException e) {
                Toast.makeText(getApplicationContext(), "数据表删除失败",
                        Toast.LENGTH_LONG).show();
            }

        }

        //===================
        //修改表及备注
        public void changeTable(SQLiteDatabase mDb, String table, String after_name) {

            //如果表名不一样才修改，万一是只修改备注呢?
            if (!table.equals(after_name))
                mDb.execSQL("ALTER TABLE " + table + " RENAME TO " + after_name + " ; ");

        }

        /************
         * 对数据库的表数据增删改查操作
         ***********************/
        // 添加一条数据,默认只向username和info字段添加数据
        public Cursor getAllData(SQLiteDatabase mDb, String table) {

            //遍历表所有数据
            return mDb.rawQuery("select * from " + table, null);

        }

    }

    //从写方法退出确认
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setTitle("确认退出吗？")
                .setIcon(R.drawable.iiii)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击“确认”后的操作
                        Main_activity.this.finish();

                    }
                })
                .setNegativeButton("返回", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击“返回”后的操作,这里不设置没有任何操作
                    }
                }).show();
    }

    //删除用的不管
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //映射菜单
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_about) {
            startActivity(new Intent(Main_activity.this, AboutActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
