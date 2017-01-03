package com.itydl.handlercount;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static com.itydl.handlercount.R.id.btn_demo_decrease;
import static com.itydl.handlercount.R.id.btn_demo_increase;
import static com.itydl.handlercount.R.id.btn_demo_pause;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int MES_WHAT_INCREASE = 1;
    private static final int MES_WHAT_DECREASE = 2;
    TextView tvdemonumber;
    Button btndemoincrease;
    Button btndemodecrease;
    Button btndemopause;

    private int number = 0;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MES_WHAT_INCREASE:
                    if(number == 20){

                        //限制Button可操作性
                        btndemoincrease.setEnabled(false);
                        btndemodecrease.setEnabled(true);
                        btndemopause.setEnabled(false);

                        //移除消息
                        mHandler.removeMessages(MES_WHAT_INCREASE);
                        Toast.makeText(getApplicationContext(),"已经最大值",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    number ++;
                    tvdemonumber.setText(number+"");
                    //实现循环发送消息。执行完UI刷新后，再一次调用sendEmptyMessageDelayed方法，隔1s发送一条消息给自己。从而实现了隔1s值增加1
                    mHandler.sendEmptyMessageDelayed(MES_WHAT_INCREASE,1000);
                    break;
                case MES_WHAT_DECREASE:
                    if(number == 1){

                        //限制Button可操作性
                        btndemoincrease.setEnabled(true);
                        btndemodecrease.setEnabled(false);
                        btndemopause.setEnabled(false);

                        //停止移除消息
                        mHandler.removeMessages(MES_WHAT_DECREASE);
                        Toast.makeText(getApplicationContext(),"已经最小值",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    number --;
                    tvdemonumber.setText(number+"");
                    //发消息给自己，原理同上边
                    mHandler.sendEmptyMessageDelayed(MES_WHAT_DECREASE,1000);
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_demo);
        initView();

        number = Integer.parseInt(tvdemonumber.getText().toString());

        btndemoincrease.setOnClickListener(this);
        btndemodecrease.setOnClickListener(this);
        btndemopause.setOnClickListener(this);
    }

    private void initView() {
        tvdemonumber = (TextView) findViewById(R.id.tv_demo_number);
        btndemoincrease = (Button) findViewById(btn_demo_increase);
        btndemodecrease = (Button) findViewById(btn_demo_decrease);
        btndemopause = (Button) findViewById(btn_demo_pause);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case btn_demo_increase:

                //限制Button可操作性
                btndemoincrease.setEnabled(false);
                btndemodecrease.setEnabled(true);
                btndemopause.setEnabled(true);

                //清理掉减少扫按钮发送的消息
                mHandler.removeMessages(MES_WHAT_DECREASE);
                //发消息(增加)
                mHandler.sendEmptyMessage(MES_WHAT_INCREASE);
                break;
            case btn_demo_decrease:

                //限制Button可操作性
                btndemoincrease.setEnabled(true);
                btndemodecrease.setEnabled(false);
                btndemopause.setEnabled(true);

                //清理掉增加按钮发送的消息
                mHandler.removeMessages(MES_WHAT_INCREASE);
                //发消息(减少)
                mHandler.sendEmptyMessage(MES_WHAT_DECREASE);
                break;

            case btn_demo_pause:

                //限制Button可操作性
                btndemoincrease.setEnabled(true);
                btndemodecrease.setEnabled(true);
                btndemopause.setEnabled(false);

                //停止增加/减少(移除未处理的减少/增加的消息)
                mHandler.removeMessages(MES_WHAT_INCREASE);
                mHandler.removeMessages(MES_WHAT_DECREASE);
                break;

            default:
                break;
        }
    }
}
