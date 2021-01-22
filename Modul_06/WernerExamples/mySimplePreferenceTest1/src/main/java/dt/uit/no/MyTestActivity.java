package dt.uit.no;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;

import dt.hin.no.R;

public class MyTestActivity extends AppCompatActivity  implements SharedPreferences.OnSharedPreferenceChangeListener {

	private SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//Gir shared prefs-fil tilgjengelig fra alle aktiviteter med standard filnavn: /data/data/shared_prefs/no.hin.dt.mysimplepreferencetest1/no.hin.dt_mysimplepreferencetest1.xml:
		//prefs = PreferenceManager.getDefaultSharedPreferences(this);

		//Bestemmer selv navn og tilgang (legges under /data/data/shared_prefs/no.hin.dt.mysimplepreferencetest1/):
		prefs = this.getSharedPreferences("minpref", Activity.MODE_PRIVATE);

		//Preferencesfil for DENNE aktiviteten (navn på sharedprefs-fil: /data/data/shared_prefs/dt.uit.no.MyTestActivity.xml)
		//prefs = this.getPreferences(Activity.MODE_PRIVATE);

        // Bruker verdier som måtte ligge i prefs:
        String str = prefs.getString("MY_TEXT", "");
		EditText editText = (EditText)findViewById(R.id.etInput);
		editText.setText(str);

		boolean backColorState = prefs.getBoolean("BACKGROUND_COLOR", true);
		this.changeBackgroundColor(backColorState);

		boolean textColorState = prefs.getBoolean("TEXTBACKGROUND_COLOR", true);
		this.changeTextBackgroundColor(textColorState);

		CheckBox saveOnExit = (CheckBox)findViewById(R.id.cbSaveOnExit);
		boolean save = prefs.getBoolean("SAVE_ON_EXIT", false);
		saveOnExit.setChecked(save);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mainmenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_end:
				//this.writeToSharedPreference();
				this.finish();
				break;

			default:
				// If we got here, the user's action was not recognized.
				// Invoke the superclass to handle it.
				return super.onOptionsItemSelected(item);
		}
		return true;
	}

	@Override
	protected void onStop() {
		this.writeToSharedPreference();
		super.onStop();
	}

	//Lagrer i preferansefil:
	private void writeToSharedPreference(){
		SharedPreferences.Editor editor = prefs.edit();

		EditText editText = (EditText)findViewById(R.id.etInput);
		Switch colorSwitch = (Switch)findViewById(R.id.swBackgroundColor);
		Switch textColorSwitch = (Switch)findViewById(R.id.swTextBackgroundColor);
		CheckBox saveOnExit = (CheckBox)findViewById(R.id.cbSaveOnExit);

		if (saveOnExit.isChecked()) {
			editor.putString("MY_TEXT", editText.getText().toString());
			editor.putBoolean("BACKGROUND_COLOR", colorSwitch.isChecked());
			editor.putBoolean("TEXTBACKGROUND_COLOR", textColorSwitch.isChecked());
			editor.putBoolean("SAVE_ON_EXIT", saveOnExit.isChecked());
		} else {
			//Tømmer/setter til default:
			editor.putString("MY_TEXT", "");
			editor.putBoolean("BACKGROUND_COLOR", true);
			editor.putBoolean("TEXTBACKGROUND_COLOR", true);
			editor.putBoolean("SAVE_ON_EXIT", false);
		}
		editor.commit();
	}

	//Event: Endrer bakgrunnsfargen:
	public void doSwitchBackgroundColor(View view) {
		Switch colorSwitch = (Switch)findViewById(R.id.swBackgroundColor);
		this.changeBackgroundColor(colorSwitch.isChecked());
	}

	//Event: Endrer tekstbakgrunnsfargen:
	public void doSwitchTextBackgroundColor(View view) {
		Switch textColorSwitch = (Switch)findViewById(R.id.swTextBackgroundColor);
		this.changeTextBackgroundColor(textColorSwitch.isChecked());
	}

	private void changeBackgroundColor(boolean switchState) {
		LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linLayMain);
		if (switchState) {
			linearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.myColor1));
		} else {
			linearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.myColor2));
		}
	}

	private void changeTextBackgroundColor(boolean switchState) {
		EditText editText = (EditText)findViewById(R.id.etInput);
		if (switchState) {
			editText.setBackgroundColor(ContextCompat.getColor(this, R.color.myTextBackgroundColor1));
		} else {
			editText.setBackgroundColor(ContextCompat.getColor(this, R.color.myTextBackgroundColor2));
		}
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

	}
}
