package com.example.search;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class loginActivity extends AppCompatActivity {

    private Button btn_login,btn_click;
    private String userName,ps,spPs,userPs;
    private TextView xx_register,xx_find_psw;
    private EditText et_user_name,et_ps;
    private CheckBox rememberPass;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        rememberPass=(CheckBox)findViewById(R.id.RememberPass);
        boolean isRemember=pref.getBoolean("remember_password",false);
        if (isRemember){
            String account=pref.getString("account","");
            String psw=pref.getString("password","");
            et_user_name.setText(account);
            et_ps.setText(psw);
            rememberPass.setChecked(true);
        }//记住密码
    }

    private void init(){
        btn_login=findViewById(R.id.btn_login);
        et_user_name=findViewById(R.id.user_name);
        et_ps=findViewById(R.id.user_pw);
        btn_login=findViewById(R.id.btn_login);
        xx_find_psw=findViewById(R.id.tv_find_psw);
        xx_register=findViewById(R.id.tv_register);
        btn_click=findViewById(R.id.btn_click);
        btn_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account=et_user_name.getText().toString();
                String psw=et_ps.getText().toString();
                if (account.equals("lazyair") && psw.equals("ai244855252")){
                    editor=pref.edit();
                    if (rememberPass.isChecked()){
                        editor.putString("account",account);
                        editor.putString("psw",psw);
                    }else{
                        editor.clear();
                    }
                    editor.apply();
                }
            }
        });
        xx_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(loginActivity.this,register.class);
                startActivityForResult(intent,1);
            }

        });

        xx_find_psw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(loginActivity.this,"暂时未开放",Toast.LENGTH_SHORT).show();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName=et_user_name.getText().toString().trim();
                ps=et_ps.getText().toString().trim();
                String md5ps=md5utils.md5(ps);
                spPs=readPs(userName);
                if(TextUtils.isEmpty(userName)){
                    Toast.makeText(loginActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(ps)){
                    Toast.makeText(loginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                    // md5Psw.equals(); 判断，输入的密码加密后，是否与保存在SharedPreferences中一致
                }else if(md5ps.equals(spPs)){
                    //一致登录成功
                    Toast.makeText(loginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    //保存登录状态，在界面保存登录的用户名 定义个方法 saveLoginStatus boolean 状态 , userName 用户名;
                    saveLoginStatus(true, userName, ps);
                    //登录成功后关闭此页面进入主页
                    Intent data=new Intent();
                    //datad.putExtra( ); name , value ;
                    data.putExtra("isLogin",true);
                    //RESULT_OK为Activity系统常量，状态码为-1
                    // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
                    setResult(RESULT_OK,data);
                    //销毁登录界面
                    loginActivity.this.finish();
                    //跳转到主界面，登录成功的状态传递到 MainActivity 中
                    startActivity(new Intent(loginActivity.this, MainActivity.class));
                    return;
                }else if((spPs!=null&&!TextUtils.isEmpty(spPs)&&!md5ps.equals(spPs))){
                    Toast.makeText(loginActivity.this, "输入的用户名和密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    Toast.makeText(loginActivity.this, "此用户名不存在", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    private String readPs(String userName){
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        return sp.getString(userName,"");
    }



    private void saveLoginStatus(boolean status,String userName,String ps){
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取编辑器
        SharedPreferences.Editor editor=sp.edit();
        //存入boolean类型的登录状态
        editor.putBoolean("isLogin", status);
        //存入登录状态时的用户名
        editor.putString("loginUserName", userName);
        editor.putString("name",userName);
        editor.putString("psw",ps);
        //提交修改
        editor.commit();
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            //是获取注册界面回传过来的用户名
            // getExtra().getString("***");
            String userName = data.getStringExtra("userName");
            if (!TextUtils.isEmpty(userName)) {
                //设置用户名到 et_user_name 控件
                et_user_name.setText(userName);
                //et_user_name控件的setSelection()方法来设置光标位置
                et_user_name.setSelection(userName.length());
            }
        }
    }


}