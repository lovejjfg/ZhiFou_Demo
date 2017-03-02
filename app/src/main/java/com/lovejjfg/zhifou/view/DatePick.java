package com.lovejjfg.zhifou.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import com.lovejjfg.sview.SupportActivity;
import com.lovejjfg.zhifou.R;
import com.lovejjfg.zhifou.constant.Constants;
import com.lovejjfg.zhifou.util.DateUtils;
import com.lovejjfg.zhifou.util.JumpUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePick extends SupportActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_pick);
        final boolean isFirst = getIntent().getBooleanExtra(Constants.FIRST, true);
//        initDialog();
        final DatePicker mDatePicker = (DatePicker) findViewById(R.id.date_picker);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (mDatePicker != null) {
            mDatePicker.init(year, month, day, (view, year1, monthOfYear, dayOfMonth) -> {
                int chooseMonth = monthOfYear + 1;
                int chooseDay = dayOfMonth + 1;
                Log.e("截止：", year1 + "年" + +chooseMonth + "月" + dayOfMonth + "日");
                String date = String.format("%d%2d%2d", year1, chooseMonth, chooseDay).replace(" ", "0");
                String showDate = String.format("%d%2d%2d", year1, chooseMonth, dayOfMonth).replace(" ", "0");
                if (!DateUtils.isMoreThanToday(date)) {
                    Intent i = new Intent();
                    i.putExtra(Constants.DATE, date);
                    i.putExtra(Constants.SHOW_DATE, showDate);
                    setResult(200, i);
                    if (isFirst) {
                        JumpUtils.jumpToSpecifiedDate(DatePick.this, date, showDate);
                    }
                    finishAfterTransition();
                    Log.e("DATE-->", String.format("%d-%2d-%2d", year1, chooseMonth, dayOfMonth).replace(" ", "0"));
                } else {
                    Log.e("DATE-->", "大于了今天");
                    showToast("大于了今天");
                }
            });

            Date today = new Date();
            Calendar cal = new GregorianCalendar();
            cal.setTime(today);
            cal.add(Calendar.DAY_OF_MONTH, -30);
            Date today30 = cal.getTime();
            cal.add(Calendar.DAY_OF_MONTH, -60);
            Date today60 = cal.getTime();
            cal.add(Calendar.DAY_OF_MONTH, -90);
            Date today90 = cal.getTime();

            GregorianCalendar gregorianCalendar = new GregorianCalendar(2016, 9, 1);
            long millisecond = gregorianCalendar.getTime().getTime();
            long maxDate = System.currentTimeMillis();
            long i = 30 * 24 * 60 * 60 * 100;
            mDatePicker.setMaxDate(today.getTime());
            mDatePicker.setMinDate(today30.getTime());
        }

    }
}
