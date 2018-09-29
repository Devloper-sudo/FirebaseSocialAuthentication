package developer.reign.com.firebasesocialauthentcation;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.widget.TextView;


public class MyProgressDialog {

    private Dialog mDialog;
    private TextView statusTV;

    public Dialog showProgressDialog(Context context, String status) {
        mDialog = new Dialog(context);
        mDialog.setContentView(R.layout.dialog_progress);
        mDialog.setCancelable(false);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        statusTV = (TextView) mDialog.findViewById(R.id.progress_status_tv);
        statusTV.setText(status);
        mDialog.show();
        return mDialog;
    }

    public void updateStatus(String status) {
        statusTV.setText(status);
    }

    public void removeDialog() {
        if (mDialog != null && mDialog.isShowing())
            mDialog.dismiss();
    }
}
