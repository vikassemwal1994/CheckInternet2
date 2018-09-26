package info.androidhive.checkinternet;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class CustomDialogClass extends Dialog implements
    android.view.View.OnClickListener {

  public Activity c;
  public Dialog d;
  public Button yes, no;
  public TextView txt_dia;
  public String msg;

  public CustomDialogClass(Activity a, String msg) {
    super(a);
    // TODO Auto-generated constructor stub
    this.msg = msg;
    this.c = a;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.custom_dialog);
    TextView tv = (TextView) findViewById(R.id.tv);
    tv.setBackgroundColor(Color.TRANSPARENT);

  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      /*case R.id.btn_yes:
        dismiss();

        break;
      default:
        break;*/
    }
    dismiss();
  }
}