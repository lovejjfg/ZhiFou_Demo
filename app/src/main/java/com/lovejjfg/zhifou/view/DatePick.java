package com.lovejjfg.zhifou.view;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.DatePicker;

import com.lovejjfg.zhifou.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DatePick extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_pick);
//        initDialog();
        DatePicker mDatePicker = (DatePicker) findViewById(R.id.date_picker);
        String currentDate = getCurrentDate();
        String[] date = currentDate.split("-");
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        mDatePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int chooseMonth = monthOfYear + 1;
                Log.e("截止：", year + "年" + +chooseMonth + "月" + dayOfMonth + "日");
            }
        });

    }

    private void initDialog() {
        String currentDate = getCurrentDate();
        String[] date = currentDate.split("-");
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                int x = 0;
                int beginMonth = 2;
                int beginDay = 3;
                int beginYear = 2015;
                Log.e("开始：", beginYear + "年" + beginMonth + "月" + beginDay + "日");
                int chooseMonth = monthOfYear + 1;

                Log.e("截止：", year + "年" + +chooseMonth + "月" + dayOfMonth + "日");
                if (year < beginYear) {
                    Log.e("异常：", "所选年份非法");
                    return;
                }
                if (year == beginYear && beginMonth > chooseMonth) {
                    Log.e("异常：", "所选月份非法");
                    return;
                }
                if (year == beginYear && chooseMonth == beginMonth && dayOfMonth < beginDay) {
                    Log.e("异常：", "所选日期非法");
                    return;
                }
                x = (year - beginYear) * 12;
                x += dayOfMonth > beginDay ? chooseMonth - beginMonth : chooseMonth - beginMonth - 1;
                Log.e("期数:", String.format("一共%d期", x));
                finish();
            }
        }, Integer.parseInt(date[0]), Integer.parseInt(date[1]) - 1, Integer.parseInt(date[2]));

        Log.e("currentDate", currentDate);
        datePickerDialog.show();
        datePickerDialog.setCanceledOnTouchOutside(true);
        datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                DatePick.this.finish();
            }
        });
        datePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (!DatePick.this.isFinishing()) {
                    DatePick.this.finish();
                }
            }
        });
    }

    public static String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(c.getTime());
    }
}
