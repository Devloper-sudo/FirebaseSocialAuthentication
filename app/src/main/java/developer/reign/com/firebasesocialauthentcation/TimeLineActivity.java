package developer.reign.com.firebasesocialauthentcation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

public class TimeLineActivity extends AppCompatActivity {


    private TextView email_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);
        email_tv = findViewById(R.id.email_tv);

        Intent intent = getIntent();

        if (intent != null) {
            email_tv.setText(intent.getStringExtra(LoginActivity.LOGIN_INTENT));
        }

    }


    public void navigateOnClick(View view) {

        startActivity(new Intent(this, ResetPasswordActivity.class));
    }
}
