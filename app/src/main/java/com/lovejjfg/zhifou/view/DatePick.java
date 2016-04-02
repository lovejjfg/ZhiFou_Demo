package com.lovejjfg.zhifou.view;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.DatePicker;

import com.lovejjfg.zhifou.R;
import com.lovejjfg.zhifou.constant.Constants;
import com.lovejjfg.zhifou.util.DateUtils;
import com.lovejjfg.zhifou.util.JumpUtils;

import java.util.Calendar;

public class DatePick extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_pick);
        final boolean isFirst = getIntent().getBooleanExtra(Constants.FIRST, true);
//        initDialog();
        DatePicker mDatePicker = (DatePicker) findViewById(R.id.date_picker);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        mDatePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int chooseMonth = monthOfYear + 1;
                Log.e("截止：", year + "年" + +chooseMonth + "月" + dayOfMonth + "日");
                String date = String.format("%d%2d%2d", year, chooseMonth, dayOfMonth).replace(" ", "0");
                if (!DateUtils.isMoreThanToday(date)) {
                    Intent i = new Intent();
                    i.putExtra(Constants.DATE, date);
                    setResult(200, i);
                    if (isFirst) {
                        JumpUtils.jumpToSpecifiedDate(DatePick.this, date);
                    }
                    finishAfterTransition();
                    Log.e("DATE-->", String.format("%d-%2d-%2d", year, chooseMonth, dayOfMonth).replace(" ", "0"));
                } else {
                    Log.e("DATE-->", "大于了今天");
                }
            }
        });

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("正在获取微信授权！！");
        dialog.show();
    }
}
