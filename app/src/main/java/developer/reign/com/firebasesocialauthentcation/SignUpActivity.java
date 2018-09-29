package developer.reign.com.firebasesocialauthentcation;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {


    private EditText emailEt, passwordEt;
    private Button btnSignUp;
    private FirebaseAuth auth;
    private MyProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
        emailEt = findViewById(R.id.email);
        passwordEt = findViewById(R.id.password);

        btnSignUp = findViewById(R.id.sign_up_button);


        progressDialog = new MyProgressDialog();

    }

    public void registerToFirebase(View view) {


        String email = emailEt.getText().toString().trim();
        final String password = passwordEt.getText().toString().trim();

        progressDialog.showProgressDialog(this, "Hang On...");
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                Toast.makeText(SignUpActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                progressDialog.removeDialog();
                // If sign in fails, display a message to the user. If sign in succeeds
                // the auth state listener will be notified and logic to handle the
                // signed in user can be handled in the listener.
                if (!task.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this, "Authentication failed." + task.getException(),
                            Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(SignUpActivity.this, TimeLineActivity.class);
                    intent.putExtra(LoginActivity.LOGIN_INTENT, task.getResult().getUser().getEmail());
                    startActivity(intent);
                    finish();

                }
            }
        });


    }

    public void navigateOnClick(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();

    }
}
