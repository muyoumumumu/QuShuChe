package com.muyoumumumu.QuShuChe.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.muyoumumumu.QuShuChe.db.Traffic_db;
import com.muyoumumumu.QuShuChe.model.bean.Mnn;
import com.muyoumumumu.QuShuChe.ui.acitivity.Main_activity;

/**
 * 
 * Create custom Dialog windows for your application
 * Custom dialogs rely on custom layouts wich allow you to 
 * create and use your own look & feel.
 * 
 * Under GPL v3 : http://www.gnu.org/licenses/gpl-3.0.html
 * 
 * <a href="http://my.oschina.net/arthor" target="_blank" rel="nofollow">@author</a> antoine vianey
 *
 */
public class CustomDialog extends Dialog {

    public CustomDialog(Context context, int theme) {
        super(context, theme);
    }

    /**
     * Helper class for creating a custom dialog_new
     */
    public static class Builder {
 
        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
       
        private EditText etnametf,etremark;
        private OnClickListener
                        positiveButtonClickListener,
                        negativeButtonClickListener;
 
        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }
 

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText,
                OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }


        public Builder setNegativeButton(String negativeButtonText,
                OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }


       //删除按钮
        public CustomDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog_new with the custom Theme
            final CustomDialog dialog = new CustomDialog(context,R.style.Dialog);
            dialog.setCanceledOnTouchOutside(false);
            View layout = inflater.inflate(R.layout.dialog_delete, null);
            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

            ((TextView) layout.findViewById(R.id.title)).setText(title);

            if (positiveButtonText != null) {
                ((Button) layout.findViewById(R.id.positiveButton))
                        .setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    layout.findViewById(R.id.positiveButton)
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListener.onClick(
                                    		dialog, 
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else {

                layout.findViewById(R.id.positiveButton).setVisibility(
                        View.GONE);
            }

            if (negativeButtonText != null) {
                ((Button)layout.findViewById(R.id.negativeButton))
                        .setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    layout.findViewById(R.id.negativeButton)
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                	negativeButtonClickListener.onClick(
                                    		dialog, 
                                            DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.negativeButton).setVisibility(
                        View.GONE);
            }

            if (message != null) {
                ((TextView) layout.findViewById(
                		R.id.message)).setText(message);
            } else if (contentView != null) {
                // if no message set
                // add the contentView to the dialog_new body
                ((LinearLayout) layout.findViewById(R.id.content))
                        .removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.content))
                        .addView(contentView, 
                                new LayoutParams(
                                        LayoutParams.WRAP_CONTENT, 
                                        LayoutParams.WRAP_CONTENT));
            }
            dialog.setContentView(layout);
            return dialog;
        }



        //====================================================================================
        //创建表
        public CustomDialog create1() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog_new with the custom Theme
            final CustomDialog dialog = new CustomDialog(context, 
            		R.style.Dialog);
            dialog.setCanceledOnTouchOutside(false);
            View layout = inflater.inflate(R.layout.dialog_new, null);
            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            // set the dialog_new title
            ((TextView) layout.findViewById(R.id.title)).setText(title);
            etnametf = (EditText) layout.findViewById(R.id.etnametf);

            etremark = (EditText) layout.findViewById(R.id.etremark);

            if (positiveButtonText != null) {
                ((Button)layout.findViewById(R.id.positiveButton))
                        .setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    layout.findViewById(R.id.positiveButton)
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListener.onClick(
                                    		dialog, 
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else layout.findViewById(R.id.positiveButton).setVisibility(
                    View.GONE);

            if (negativeButtonText != null) {
                ((Button) layout.findViewById(R.id.negativeButton))
                        .setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    layout.findViewById(R.id.negativeButton)
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {


                                    //添加判断机制防止输入字符为空格或空
                                    //否则数据库不能创建table会闪退

                                    String beforeName = etnametf.getText().toString();
                                    String mark=etremark.getText().toString();
                                    //trim切除空字符串
                                    if (beforeName.trim().isEmpty()) {

                                        Toast.makeText(context, "请避免输入空或全空格", Toast.LENGTH_SHORT).show();

                                        positiveButtonClickListener.onClick(
                                                dialog,
                                                DialogInterface.BUTTON_POSITIVE);

                                    } else {

                                        String[] name_mark=new String[2];
                                        name_mark[0]=beforeName;
                                        name_mark[1]=mark;

                                        Mnn.getInstance().setName_mark(name_mark);

                                        negativeButtonClickListener.onClick(
                                                dialog,
                                                DialogInterface.BUTTON_NEGATIVE);
                                    }
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.negativeButton).setVisibility(
                        View.GONE);
            }

            // set the content message
            if (message != null) {
                ((TextView) layout.findViewById(
                		R.id.message)).setText(message);
            } else if (contentView != null) {
                // if no message set
                // add the contentView to the dialog_new body
                ((LinearLayout) layout.findViewById(R.id.content))
                        .removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.content))
                        .addView(contentView, 
                                new LayoutParams(
                                        LayoutParams.WRAP_CONTENT, 
                                        LayoutParams.WRAP_CONTENT));
            }
            dialog.setContentView(layout);
            return dialog;
        }


        //==============================================================================
        //修改表
        public CustomDialog create3(String name) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog_new with the custom Theme
            final CustomDialog dialog = new CustomDialog(context,
                    R.style.Dialog);
            dialog.setCanceledOnTouchOutside(false);
            View layout = inflater.inflate(R.layout.dialog_new, null);
            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            // set the dialog_new title
            ((TextView) layout.findViewById(R.id.title)).setText(title);
            etnametf = (EditText) layout.findViewById(R.id.etnametf);

            etremark = (EditText) layout.findViewById(R.id.etremark);

            //========================================

            //设置为相应的东西
            etnametf.setText(name);
            etnametf.setFocusable(true);
            Cursor c= Main_activity.dbRead.query(Traffic_db.TABLE_NAME_data,new String[]{"remark"},"name = ?",new String[]{name},null,null,null);
            c.moveToFirst();
            etremark.setText(c.getString(0));
            c.close();

            if (positiveButtonText != null) {
                ((Button)layout.findViewById(R.id.positiveButton))
                        .setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    layout.findViewById(R.id.positiveButton)
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListener.onClick(
                                            dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else layout.findViewById(R.id.positiveButton).setVisibility(
                    View.GONE);

            if (negativeButtonText != null) {
                ((Button) layout.findViewById(R.id.negativeButton))
                        .setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    layout.findViewById(R.id.negativeButton)
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {


                                    //添加判断机制防止输入字符为空格或空
                                    //否则数据库不能创建table会闪退

                                    String beforeName = etnametf.getText().toString();
                                    String mark=etremark.getText().toString();
                                    //trim切除空字符串
                                    if (beforeName.trim().isEmpty()) {

                                        Toast.makeText(context, "请避免输入空或全空格", Toast.LENGTH_SHORT).show();

                                        positiveButtonClickListener.onClick(
                                                dialog,
                                                DialogInterface.BUTTON_POSITIVE);

                                    } else {

                                        String[] name_mark=new String[2];
                                        name_mark[0]=beforeName;
                                        name_mark[1]=mark;

                                        Mnn.getInstance().setName_mark(name_mark);

                                        negativeButtonClickListener.onClick(
                                                dialog,
                                                DialogInterface.BUTTON_NEGATIVE);
                                    }
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.negativeButton).setVisibility(
                        View.GONE);
            }

            // set the content message
            if (message != null) {
                ((TextView) layout.findViewById(
                        R.id.message)).setText(message);
            } else if (contentView != null) {
                // if no message set
                // add the contentView to the dialog_new body
                ((LinearLayout) layout.findViewById(R.id.content))
                        .removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.content))
                        .addView(contentView,
                                new LayoutParams(
                                        LayoutParams.WRAP_CONTENT,
                                        LayoutParams.WRAP_CONTENT));
            }
            dialog.setContentView(layout);
            return dialog;
        }


//--------------------------------------------------------------------------------------------------
        //此处为设置自定义的间隔时间
        public CustomDialog create2() {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final CustomDialog dialog = new CustomDialog(context,
                    R.style.Dialog);

            dialog.setCanceledOnTouchOutside(false);
            View layout = inflater.inflate(R.layout.dialog_n_time, null);
            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

            ((TextView) layout.findViewById(R.id.title)).setText(title);
            etnametf = (EditText) layout.findViewById(R.id.etnametf);

            //此处设置仅限输入数字 ===================================================================
            etnametf.setKeyListener(new  DigitsKeyListener(false,true));

            if (positiveButtonText != null) {
                ((Button)layout.findViewById(R.id.positiveButton))
                        .setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    layout.findViewById(R.id.positiveButton)
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListener.onClick(
                                            dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.positiveButton).setVisibility(
                        View.GONE);
            }
            // “确定”
            if (negativeButtonText != null) {
                ((Button) layout.findViewById(R.id.negativeButton))
                        .setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    layout.findViewById(R.id.negativeButton)
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {


                                    //添加判断机制防止输入字符为空格或空
                                    String beforeName=etnametf.getText().toString();

                                    //trim切除空字符串
                                    if(beforeName.trim().isEmpty())
                                    {

                                        Toast.makeText(context,"请避免输入空或全空格",Toast.LENGTH_SHORT).show();

                                        positiveButtonClickListener.onClick(
                                                dialog,
                                                DialogInterface.BUTTON_POSITIVE);

                                    }else if(Float.valueOf(beforeName)<0.0499){

                                        Toast.makeText(context,"请避免小于0.05",Toast.LENGTH_SHORT).show();

                                        positiveButtonClickListener.onClick(
                                                dialog,
                                                DialogInterface.BUTTON_POSITIVE);

                                    }
                                    else {
                                        //强转类型为float型
                                        float name1 = Float.valueOf(beforeName);

                                        //设置所需时间
                                        Mnn. getInstance().setTime(name1);

                                        negativeButtonClickListener.onClick(
                                                dialog,
                                                DialogInterface.BUTTON_NEGATIVE);
                                    }


                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.negativeButton).setVisibility(
                        View.GONE);
            }



            // set the content message
            if (message != null) {
                ((TextView) layout.findViewById(
                        R.id.message)).setText(message);
            } else if (contentView != null) {
                // if no message set
                // add the contentView to the dialog_new body
                ((LinearLayout) layout.findViewById(R.id.content))
                        .removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.content))
                        .addView(contentView,
                                new LayoutParams(
                                        LayoutParams.WRAP_CONTENT,
                                        LayoutParams.WRAP_CONTENT));
            }
            dialog.setContentView(layout);
            return dialog;
        }

    }
 
}