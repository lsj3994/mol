package com.example.manageofleague;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;  //파이어 베이스 인증
    private DatabaseReference mDatabaseRef; //실시간 데이터 베이스
    private EditText mEtEmail, mEtpwd, mEtpwd2; //회원가입 이메일 비밀번호
    private Button mBtnRegister;   // 회원가입 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mFirebaseAuth = FirebaseAuth.getInstance();  // 파이어베이스 초기화
        // mDatabaseRef = FirebaseDatabase.getInstance().getReference("MansgeOfLeague");  //데이터베이스 초기화
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("ManageOfLeague");


        mEtEmail = findViewById(R.id.et_email);
        mEtpwd   = findViewById(R.id.et_pwd);
        mEtpwd2  = findViewById(R.id.et_pwd2);
        mBtnRegister = findViewById(R.id.btn_register);

        //버튼 이벤트
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //회원가입 처리 시작
                String strEmail = mEtEmail.getText().toString();
                String strPwd   = mEtpwd.getText().toString();
                String strPwd2  = mEtpwd2.getText().toString();

                if (strPwd != strPwd2) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("입력하신 패스워드가 틀립니다.")
                            .setTitle("패스워드 확인");

                    // 다이얼로그 버튼 추가
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // OK 버튼을 누르면 실행할 코드
                        }
                    });
//                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            // Cancel 버튼을 누르면 실행할 코드
//                        }
//                    });

                    // 다이얼로그 생성
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return;
                }

                //FirebaseAuth 진행
                mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // task 회원처리 하고 결과값을 넘겨줌
                        if (task.isSuccessful()) {

                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                            //UserAccount.java 에서 가져옴
                            UserAccount account = new UserAccount();
                            account.setIdToken(firebaseUser.getUid());
                            //firebase에서 email 을 찾아서 가져옴
                            account.setEmailId(firebaseUser.getEmail());
                            account.setPassword(strPwd);
                            //setValue : database에 insert 행위
                            mDatabaseRef.child("UserAcount").child(firebaseUser.getUid()).setValue(account);

                            Toast.makeText(RegisterActivity.this, "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
                        } else  {
                            Toast.makeText(RegisterActivity.this, "회원가입에 실패하셨습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
}