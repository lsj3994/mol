package com.example.manageofleague;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;  //파이어 베이스 인증
    private DatabaseReference mDatabaseRef; //실시간 데이터 베이스
    private EditText mEtEmail, mEtpwd; //로그인 입력필드

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // xml 만 연결 가능
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();  // 파이어베이스 초기화
        //데이터베이스 초기화
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("ManageOfLeague");

        mEtEmail = findViewById(R.id.et_email);
        mEtpwd   = findViewById(R.id.et_pwd);


        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //로그인 시작
                String strEmail = mEtEmail.getText().toString();
                String strPwd   = mEtpwd.getText().toString();

                mFirebaseAuth.signInWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //로그인 성공
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish(); //현재 로그인 페이지 쓸일 없으므로 파괴.
                        } else {
                            Toast.makeText(LoginActivity.this, "로그인 실패..!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });




        Button btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //회원가입 화면으로                          앞은 본인 액티비티, 뒤는 옮겨갈 액티비
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                //회원가입 눌렀을때 가입 화면으로 이동
                startActivity(intent);
            }
        });
    }
}