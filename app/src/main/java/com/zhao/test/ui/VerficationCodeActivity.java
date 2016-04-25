package com.zhao.test.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhao.myutils.base.BaseActivity;
import com.zhao.test.R;

import java.util.Random;

/**
 * 验证码工具类
 */
public class VerficationCodeActivity extends BaseActivity {

    private String numStrTmp = "";
    private String numStr = "";
    private int[] numArray = new int[4];
    private int[] colorArray = new int[6];

    private TextView tvHideA;
    private TextView tvHideB;
    private TextView tvHideC;
    private TextView tvHideD;
    private TextView tvHideE;
    private TextView tvHideF;
    private TextView tvHideG;
    private ImageView ivNumA;
    private ImageView ivNumB;
    private ImageView ivNumC;
    private ImageView ivNumD;
    private ImageView ivNumE;
    private ImageView ivNumF;
    private ImageView ivNumG;
    private Button btnCheck;
    private TextView tvCheck;
    private EditText etCheck;

    // 计算类型的验证码
    private String[] strVerify = new String[3];
    private int[] intVerify = new int[3];
    private int intResult = -100;
    private EditText etVerify;
    private Button btnVerify;
    private TextView tvVerify;

    @Override
    public int setLayoutResId() {
        return R.layout.activity_verfication_code;
    }

    @Override
    protected void initView(Bundle bundle) {

        tvHideA = (TextView) findViewById(R.id.tvHideA);
        tvHideB = (TextView) findViewById(R.id.tvHideB);
        tvHideC = (TextView) findViewById(R.id.tvHideC);
        tvHideD = (TextView) findViewById(R.id.tvHideD);
        ivNumA = (ImageView) findViewById(R.id.ivNumA);
        ivNumB = (ImageView) findViewById(R.id.ivNumB);
        ivNumC = (ImageView) findViewById(R.id.ivNumC);
        ivNumD = (ImageView) findViewById(R.id.ivNumD);
        // 计算类型的验证码
        tvHideE = (TextView) findViewById(R.id.tvHideE);
        tvHideF = (TextView) findViewById(R.id.tvHideF);
        tvHideG = (TextView) findViewById(R.id.tvHideG);
        ivNumE = (ImageView) findViewById(R.id.ivNumE);
        ivNumF = (ImageView) findViewById(R.id.ivNumF);
        ivNumG = (ImageView) findViewById(R.id.ivNumG);

        etVerify = (EditText) findViewById(R.id.etVerify);
        btnVerify = (Button) findViewById(R.id.btnVerify);
        tvVerify = (TextView) findViewById(R.id.tvVerify);

        tvCheck = (TextView) findViewById(R.id.tvCheck);
        etCheck = (EditText) findViewById(R.id.etCheck);
        btnCheck = (Button) findViewById(R.id.btnCheck);
    }

    @Override
    protected void initListener() {
        setOnClickListener(btnCheck);
        setOnClickListener(btnVerify);
    }

    @Override
    protected void initData(Bundle bundle) {
        setNum();
        setVerify();
    }

    /**
     * 计算类型的验证码
     */
    private void setVerify() {
        initStrVerify();
        tvHideE.setText(strVerify[0]);
        tvHideE.setTextColor(randomColor());
        tvHideG.setText(strVerify[1]);
        tvHideG.setTextColor(randomColor());
        tvHideF.setText(strVerify[2]);
        tvHideF.setTextColor(randomColor());

        // Num 1
        Matrix matrixE = new Matrix();
        matrixE.reset();
        matrixE.setRotate(randomAngle());
        Bitmap bmNumE = Bitmap.createBitmap(getBitmapFromView(tvHideE, 210, 180), 0, 0, 210, 180, matrixE, true);
        ivNumE.setImageBitmap(bmNumE);

        //Operator
        Matrix matrixF = new Matrix();
        matrixF.reset();
        matrixF.setRotate(randomAngle());
        Bitmap bmNumF = Bitmap.createBitmap(getBitmapFromView(tvHideF, 210, 180), 0, 0, 210, 180, matrixF, true);
        ivNumF.setImageBitmap(bmNumF);

        // Num2
        Matrix matrixG = new Matrix();
        matrixG.reset();
        matrixG.setRotate(randomAngle());
        Bitmap bmNumG = Bitmap.createBitmap(getBitmapFromView(tvHideG, 210, 180), 0, 0, 210, 180, matrixG, true);
        ivNumG.setImageBitmap(bmNumG);
    }

    private void initStrVerify() {
        // 获得两个不相等运算数值：0-9
        do {
            intVerify[0] = new Random().nextInt(10);
            intVerify[1] = new Random().nextInt(10);
        } while (intVerify[0] == intVerify[1]);
        // 获得运算符号：+，-，x
        intVerify[2] = new Random().nextInt(3);
        if (intVerify[2] == 0) {
            intResult = intVerify[0] + intVerify[1];
            strVerify[2] = "加上";
        } else if (intVerify[2] == 1) {
            intResult = intVerify[0] - intVerify[1];
            strVerify[2] = "减去";
        } else if (intVerify[2] == 2) {
            intResult = intVerify[0] * intVerify[1];
            strVerify[2] = "乘以";
        } else {
            intResult = -100;
            strVerify[2] = "呵呵";
        }
        strVerify[0] = numToCharacter(intVerify[0]);
        strVerify[1] = numToCharacter(intVerify[1]);
    }

    private String numToCharacter(int num) {
        switch (num) {
            case 0:
                return "零";
            case 1:
                return "一";
            case 2:
                return "二";
            case 3:
                return "三";
            case 4:
                return "四";
            case 5:
                return "五";
            case 6:
                return "六";
            case 7:
                return "七";
            case 8:
                return "八";
            case 9:
                return "九";
            default:
                return "错";
        }
    }

    @Override
    protected void treatClickEvent(View view) {
        super.treatClickEvent(view);
        if (view == btnCheck) {
            if (etCheck.getText().toString().trim().length() > 0) {
                tvCheck.setVisibility(View.VISIBLE);
                if (numStr.equals(etCheck.getText().toString())) {
                    tvCheck.setText("输入正确");
                    tvCheck.setTextColor(Color.GREEN);
                } else {
                    tvCheck.setText("输入错误");
                    tvCheck.setTextColor(Color.RED);
                }
            } else {
                setNum();
                tvCheck.setVisibility(View.GONE);
            }
        } else if (view == btnVerify) {
            if (etVerify.getText().toString().trim().length() > 0) {
                tvVerify.setVisibility(View.VISIBLE);
                if (etVerify.getText().toString().equals(String.valueOf(intResult))) {
                    tvVerify.setText("正确");
                    tvVerify.setTextColor(Color.GREEN);
                } else {
                    tvVerify.setText("错误");
                    tvVerify.setTextColor(Color.RED);
                }
            } else {
                setVerify();
                tvVerify.setVisibility(View.GONE);
            }
        }
    }

    public void initNum() {
        numStr = "";
        numStrTmp = "";
        for (int i = 0; i < numArray.length; i++) {
            int numIntTmp = new Random().nextInt(10);
            numStrTmp = String.valueOf(numIntTmp);
            numStr = numStr + numStrTmp;
            numArray[i] = numIntTmp;
        }
    }

    public void setNum() {
        initNum();
        tvHideA.setText("" + numArray[0]);
        tvHideA.setTextColor(randomColor());
        tvHideB.setText("" + numArray[1]);
        tvHideB.setTextColor(randomColor());
        tvHideC.setText("" + numArray[2]);
        tvHideC.setTextColor(randomColor());
        tvHideD.setText("" + numArray[3]);
        tvHideD.setTextColor(randomColor());

        // Num 1
        Matrix matrixA = new Matrix();
        matrixA.reset();
        matrixA.setRotate(randomAngle());
        Bitmap bmNumA = Bitmap.createBitmap(getBitmapFromView(tvHideA, 120, 150), 0, 0, 120, 150, matrixA, true);
        ivNumA.setImageBitmap(bmNumA);
        // Num 2
        Matrix matrixB = new Matrix();
        matrixB.reset();
        matrixB.setRotate(randomAngle());
        Bitmap bmNumB = Bitmap.createBitmap(getBitmapFromView(tvHideB, 120, 150), 0, 0, 120, 150, matrixB, true);
        ivNumB.setImageBitmap(bmNumB);
        // Num 3
        Matrix matrixC = new Matrix();
        matrixC.reset();
        matrixC.setRotate(randomAngle());
        Bitmap bmNumC = Bitmap.createBitmap(getBitmapFromView(tvHideC, 120, 150), 0, 0, 120, 150, matrixC, true);
        ivNumC.setImageBitmap(bmNumC);
        // Num 4
        Matrix matrixD = new Matrix();
        matrixD.reset();
        matrixD.setRotate(randomAngle());
        Bitmap bmNumD = Bitmap.createBitmap(getBitmapFromView(tvHideD, 120, 150), 0, 0, 120, 150, matrixD, true);
        ivNumD.setImageBitmap(bmNumD);
    }

    public int randomAngle() {
        return 20 * (new Random().nextInt(5) - new Random().nextInt(3));
    }

    public int randomColor() {
        colorArray[0] = 0xFF000000; // BLACK
        colorArray[1] = 0xFFFF00FF; // MAGENTA
        colorArray[2] = 0xFFFF0000; // RED
        colorArray[3] = 0xFF00FF00; // GREEN
        colorArray[4] = 0xFF0000FF; // BLUE
        colorArray[5] = 0xFF00FFFF; // CYAN
        //colorArray[6] = 0xFFFFFF00; // YELLOW:看不清楚

        int randomColorId = new Random().nextInt(6);
        return colorArray[randomColorId];
    }

    public static Bitmap getBitmapFromView(View view, int width, int height) {
        int widthSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
        view.measure(widthSpec, heightSpec);
        view.layout(0, 0, width, height);
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

}