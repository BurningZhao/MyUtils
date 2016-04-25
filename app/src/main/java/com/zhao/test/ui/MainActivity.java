package com.zhao.test.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zhao.myutils.base.BaseActivity;
import com.zhao.test.R;

public class MainActivity extends BaseActivity {

    private Button mVrificationcodeBtn;
    @Override
    public int setLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle bundle) {
        mVrificationcodeBtn = (Button) findViewById(R.id.verification_code);
    }

    @Override
    protected void initListener() {
        setOnClickListener(mVrificationcodeBtn);
    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    protected void treatClickEvent(View view) {
        super.treatClickEvent(view);
        switch (view.getId()) {
            case R.id.verification_code:
                Intent intent = new Intent(this, VerficationCodeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
