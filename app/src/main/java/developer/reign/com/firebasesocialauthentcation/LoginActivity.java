package developer.reign.com.firebasesocialauthentcation;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    public static final String LOGIN_INTENT = "login_intent";
    private FirebaseAuth auth;
    private EditText emailEt, passwordEt;
    private Button btn_login;
    private MyProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_acitivty);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            Intent intent = new Intent(this, TimeLineActivity.class);
            intent.putExtra(LOGIN_INTENT, auth.getCurrentUser().getEmail());
            startActivity(intent);
            finish();
        }

        progressDialog = new MyProgressDialog();


        emailEt = findViewById(R.id.email);
        passwordEt = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);


        btn_login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.btn_login:

                String email = emailEt.getText().toString().trim();
                final String password = passwordEt.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(this, "Invalid Passowrd", Toast.LENGTH_SHORT).show();
                    return;
                }


                progressDialog.showProgressDialog(LoginActivity.this, "Hang On...");

                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        progressDialog.removeDialog();
                        if (!task.isSuccessful()) {

                            if (password.length() < 6) {
                                passwordEt.setError(getString(R.string.minimum_password));
                            } else {
                                Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                            }

                        } else {
                            Toast.makeText(LoginActivity.this, "Log in  is SuccessFul", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, TimeLineActivity.class);
                            intent.putExtra(LOGIN_INTENT, task.getResult().getUser().getEmail());
                            startActivity(intent);
                            finish();
                        }

                    }
                });


                break;
        }
    }

    public void navigateSignUp(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    public void navigateForgotPassword(View view) {

        Intent intent = new Intent(this, ResetPasswordActivity.class);
        startActivity(intent);
        finish();
    }
}
