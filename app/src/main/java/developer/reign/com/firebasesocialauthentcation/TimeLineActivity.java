package developer.reign.com.firebasesocialauthentcation;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TimeLineActivity extends AppCompatActivity {


    private TextView email_tv;

    private Button btnChangeEmail, btnChangePassword, btnSendResetEmail, btnRemoveUser,
            changeEmail, changePassword, sendEmail, remove, signOut;

    private EditText oldEmail, newEmail, password, newPassword;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        auth = FirebaseAuth.getInstance();

        final FirebaseUser user = auth.getCurrentUser();


        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {


                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(TimeLineActivity.this, LoginActivity.class));
                    finish();

                }
            }
        }

        ;

        email_tv =

                findViewById(R.id.email_tv);

        btnChangeEmail = (Button)

                findViewById(R.id.change_email_button);

        btnChangePassword = (Button)

                findViewById(R.id.change_password_button);

        btnSendResetEmail = (Button)

                findViewById(R.id.sending_pass_reset_button);

        btnRemoveUser = (Button)

                findViewById(R.id.remove_user_button);

        changeEmail = (Button)

                findViewById(R.id.changeEmail);

        changePassword = (Button)

                findViewById(R.id.changePass);

        sendEmail = (Button)

                findViewById(R.id.send);

        remove = (Button)

                findViewById(R.id.remove);

        signOut = (Button)

                findViewById(R.id.sign_out);

        oldEmail = (EditText)

                findViewById(R.id.old_email);

        newEmail = (EditText)

                findViewById(R.id.new_email);

        password = (EditText)

                findViewById(R.id.password);

        newPassword = (EditText)

                findViewById(R.id.newPassword);


        oldEmail.setVisibility(View.GONE);
        newEmail.setVisibility(View.GONE);
        password.setVisibility(View.GONE);
        newPassword.setVisibility(View.GONE);
        changeEmail.setVisibility(View.GONE);
        changePassword.setVisibility(View.GONE);
        sendEmail.setVisibility(View.GONE);
        remove.setVisibility(View.GONE);


        Intent intent = getIntent();

        if (intent != null)

        {
            email_tv.setText(intent.getStringExtra(LoginActivity.LOGIN_INTENT));
        }


        btnChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldEmail.setVisibility(View.GONE);
                newEmail.setVisibility(View.VISIBLE);
                password.setVisibility(View.GONE);
                newPassword.setVisibility(View.GONE);
                changeEmail.setVisibility(View.VISIBLE);
                changePassword.setVisibility(View.GONE);
                sendEmail.setVisibility(View.GONE);
                remove.setVisibility(View.GONE);
            }
        });


        changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MyProgressDialog myProgressDialog = new MyProgressDialog();
                myProgressDialog.showProgressDialog(TimeLineActivity.this, "Changing Email...");

                if (user != null && !newEmail.getText().toString().trim().equals("")) {

                    user.updateEmail(newEmail.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                myProgressDialog.removeDialog();
                                Toast.makeText(TimeLineActivity.this, "EmailGot Updated Please sign in with new email id!", Toast.LENGTH_SHORT).show();
                                signOut();
                            } else {
                                myProgressDialog.removeDialog();

                                Toast.makeText(TimeLineActivity.this, "Failed to update email!", Toast.LENGTH_LONG).show();

                            }

                        }
                    });
                } else if (newEmail.getText().toString().trim().equals("")) {
                    newEmail.setError("Enter email");
                    myProgressDialog.removeDialog();
                }


            }
        });


        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldEmail.setVisibility(View.GONE);
                newEmail.setVisibility(View.GONE);
                password.setVisibility(View.GONE);
                newPassword.setVisibility(View.VISIBLE);
                changeEmail.setVisibility(View.GONE);
                changePassword.setVisibility(View.VISIBLE);
                sendEmail.setVisibility(View.GONE);
                remove.setVisibility(View.GONE);
            }
        });


        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MyProgressDialog myProgressDialog = new MyProgressDialog();
                myProgressDialog.showProgressDialog(TimeLineActivity.this, "Changing Password...");

                if (user != null && !newPassword.getText().toString().trim().equals("")) {
                    if (newPassword.getText().toString().trim().length() < 6) {
                        myProgressDialog.removeDialog();
                        newPassword.setError("Password too short, enter minimum 6 characters");

                    } else {
                        user.updatePassword(newPassword.getText().toString().trim())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            myProgressDialog.removeDialog();
                                            Toast.makeText(TimeLineActivity.this, "Password is updated, sign in with new password!", Toast.LENGTH_SHORT).show();
                                            signOut();

                                        } else {
                                            myProgressDialog.removeDialog();
                                            Toast.makeText(TimeLineActivity.this, "Failed to update password!", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });
                    }
                } else if (newPassword.getText().toString().trim().equals("")) {
                    myProgressDialog.removeDialog();
                    newPassword.setError("Enter password");

                }
            }

        });


        btnSendResetEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldEmail.setVisibility(View.VISIBLE);
                newEmail.setVisibility(View.GONE);
                password.setVisibility(View.GONE);
                newPassword.setVisibility(View.GONE);
                changeEmail.setVisibility(View.GONE);
                changePassword.setVisibility(View.GONE);
                sendEmail.setVisibility(View.VISIBLE);
                remove.setVisibility(View.GONE);
            }
        });

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MyProgressDialog myProgressDialog = new MyProgressDialog();
                myProgressDialog.showProgressDialog(TimeLineActivity.this, "Resting Email ...");

                if (!oldEmail.getText().toString().trim().equals("")) {
                    auth.sendPasswordResetEmail(oldEmail.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        myProgressDialog.removeDialog();
                                        Toast.makeText(TimeLineActivity.this, "Reset password email is sent!", Toast.LENGTH_SHORT).show();

                                    } else {
                                        myProgressDialog.removeDialog();

                                        Toast.makeText(TimeLineActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                } else {
                    myProgressDialog.removeDialog();
                    oldEmail.setError("Enter email");

                }
            }
        });

        btnRemoveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final MyProgressDialog myProgressDialog = new MyProgressDialog();
                myProgressDialog.showProgressDialog(TimeLineActivity.this, "Resting Email ...");

                if (user != null) {
                    user.delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        myProgressDialog.removeDialog();
                                        Toast.makeText(TimeLineActivity.this, "Your profile is deleted:( Create a account now!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(TimeLineActivity.this, LoginActivity.class));
                                        finish();

                                    } else {
                                        myProgressDialog.removeDialog();
                                        Toast.makeText(TimeLineActivity.this, "Failed to delete your account!", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
                startActivity(new Intent(TimeLineActivity.this,LoginActivity.class));
            }
        });


    }


    public void signOut() {
        auth.signOut();
    }

    public void navigateOnClick(View view) {

        startActivity(new Intent(this, ResetPasswordActivity.class));
    }
}
