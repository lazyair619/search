package com.example.search;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class register extends AppCompatActivity {
    private Button btn_register;
    private EditText et_user_name,et_ps,et_ps_again;
    private String userName,ps,psAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resiger);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }

    private void init(){
        btn_register=findViewById(R.id.btn_register);
        et_user_name=findViewById(R.id.et_user_name);
        et_ps=findViewById(R.id.et_psw);
        et_ps_again=findViewById(R.id.et_psw_again);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditSting();
                if (TextUtils.isEmpty(userName)){
                    Toast.makeText(register.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(ps)){
                    Toast.makeText(register.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(psAgain)){
                    Toast.makeText(register.this, "请再次输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }else if(!ps.equals(psAgain)){
                    Toast.makeText(register.this, "输入两次的密码不一样", Toast.LENGTH_SHORT).show();
                    return;
                }else if (isExistUserName(userName)){//如果找到了就执行
                    Toast.makeText(register.this, "此账户名已经存在", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Toast.makeText(register.this, "注册成功", Toast.LENGTH_SHORT).show();
                    saveRegisterInfo(userName, ps);
                    Intent intent = new Intent(register.this, MainActivity.class);
                    finish();
                }
            }
        });
    }

    private void getEditSting(){
        userName=et_user_name.getText().toString().trim();
        ps=et_ps.getText().toString().trim();
        psAgain=et_ps_again.getText().toString().trim();
    }

    private boolean isExistUserName(String userName){
        boolean has_userName=false;
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        String spps=sp.getString(userName,"");
        if (!TextUtils.isEmpty(spps)){
            has_userName=true;
        }
        return has_userName;
    }

    private void saveRegisterInfo(String userName,String ps){
        String md5Psw =md5utils.md5(ps);//把密码用MD5加密
        //loginInfo表示文件名, mode_private SharedPreferences sp = getSharedPreferences( );
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取编辑器， SharedPreferences.Editor  editor -> sp.edit();
        SharedPreferences.Editor editor=sp.edit();
        //以用户名为key，密码为value保存在SharedPreferences中
        //key,value,如键值对，editor.putString(用户名，密码）;
        editor.putString(userName, md5Psw);
        //提交修改 editor.commit();
        editor.commit();
    }
}