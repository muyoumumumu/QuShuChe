package com.muyoumumumu.QuShuChe.model.bean;

public class Mnn {
	//改正为name+remark
	 private String[] name1=null;
	 private float time =0;


	 private static Mnn mnn;
	 public static Mnn getInstance() {
	 if (mnn == null) {
		 mnn = new Mnn();
	 }
	 return mnn;
	 }
	 public String[] getName_mark() {
	 return name1;
	 }
	 public void setName_mark(String[] name1) {
	 this.name1 = name1;
	 }

	//设置自定义专用
	public float getTime() {
		return time;
	}
	public void setTime(float time) {
		this.time = time;
	}

}
