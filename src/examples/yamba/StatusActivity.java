package examples.yamba;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class StatusActivity extends Activity implements OnClickListener {
	private final static String TAG = "StatusActivity";
	private EditText editStatus;
	private Button buttonTweet;
	private TextView textCount;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_status);

		editStatus = (EditText) findViewById(R.id.editStatus);
		buttonTweet = (Button) findViewById(R.id.buttonTweet);
		buttonTweet.setOnClickListener(this);
		textCount = (TextView) findViewById(R.id.textCount);
		textCount.getTextColors().getDefaultColor();

		editStatus.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				int charsLeft = 140 - editStatus.length();
				textCount.setText(Integer.toString(charsLeft));
				textCount.setTextColor(getColorBasedOnCharsLeft(charsLeft));
			}

			private int getColorBasedOnCharsLeft(int charsLeft) {
				int green = Color.rgb(49, 158, 12);
				int yellow = Color.rgb(157, 150, 12);
				int red = Color.rgb(157, 20, 12);

				int color = green;

				if (charsLeft < 10) {
					color = yellow;
				}
				if (charsLeft < 0) {
					color = red;
				}

				return color;
			}
		});
	}

	@Override
	public void onClick(View v) {
		String status = editStatus.getText().toString();
		Log.d(TAG, "onClick with status: " + status);

		new PostTask(this).execute(status);
	}

}
