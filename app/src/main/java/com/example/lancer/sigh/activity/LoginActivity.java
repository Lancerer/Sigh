package com.example.lancer.sigh.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lancer.sigh.MainActivity;
import com.example.lancer.sigh.R;
import com.example.lancer.sigh.model.LoginBean;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private EditText etPhone;
    private EditText etPwd;
    private TextView tvMsg;
    private Button btSubmit;
    private TextView tvVerify;
    private boolean flag = true;
    private Toolbar toolbarLogin;
    private CheckBox cbAuto;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    public void initView() {
        etPhone = findViewById(R.id.et_phone);
        etPwd = findViewById(R.id.et_pwd);
        tvMsg = findViewById(R.id.tv_msg);
        btSubmit = findViewById(R.id.bt_submit);
        tvVerify = findViewById(R.id.tv_verify);
        toolbarLogin = findViewById(R.id.toolbar_login);
        cbAuto = findViewById(R.id.cb_auto);
    }

    @Override
    public void initData() {
        setSupportActionBar(toolbarLogin);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Bmob.initialize(this, "96dd19703b81e648eafe9b48bf6527c6");
        //todo 自动登录
        sp = getSharedPreferences("config", MODE_PRIVATE);
        boolean auto = sp.getBoolean("auto", false);
        if (auto) {
            cbAuto.setChecked(true);
            startActivity(MainActivity.class);
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return true;
    }

    @Override
    public int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initListener() {
        btSubmit.setOnClickListener(this);
        tvMsg.setOnClickListener(this);
        tvVerify.setOnClickListener(this);
        cbAuto.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_submit:
                login();
                break;
            case R.id.tv_msg:
                sendMsg();
                break;
            case R.id.tv_verify:
                //TODO 发送验证码
                initDialog();
                break;
            default:
                break;
        }
    }

    private void initDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("确认手机号码");
        builder.setMessage("我们将发送验证码到号码：" + etPhone.getText().toString());
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    private void sendMsg() {
        if (flag) {
            etPwd.setHint("验证码");
            tvVerify.setVisibility(View.VISIBLE);
            flag = false;
        } else {
            etPwd.setHint("密码");
            tvVerify.setVisibility(View.GONE);
            flag = true;
        }


    }

    private void login() {
        String phone = etPhone.getText().toString();
        String pwd = etPwd.getText().toString();
        if (!(TextUtils.isEmpty(phone) || TextUtils.isEmpty(pwd))) {
            if (phone.matches("[1][34578]\\d{9}")) {
                LoginBean loginBean = new LoginBean();
                loginBean.setPhone(phone);
                loginBean.setPwd(pwd);
                loginBean.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            Toast.makeText(LoginActivity.this, "success", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "没有该手机号码", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "密码不能为空，请输入密码", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            sp.edit().putBoolean("auto", true).commit();
        } else {
            sp.edit().putBoolean("auto", false).commit();
        }
    }
}
