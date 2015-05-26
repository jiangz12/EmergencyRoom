package group0313.com.example.emergencyroom;

import java.io.IOException;

import managers.PatientManager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import er_application.Nurse;

/**
 * An Activity that runs the Nurse functions.
 * @author Zhen Jiang, Huiyan Xu, Melissa Tam, Huan Xu
 *
 */

public class NurseActivity extends Activity {
	public static final String FILENAME = "Patients.txt";
	private PatientManager patientmanager;
	private Nurse nurse;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nurse);

		Intent intent = getIntent();

		nurse = (Nurse) intent.getSerializableExtra("Nurse");
		
        TextView newlyRegistered = 
        		(TextView) findViewById(R.id.welcome_nurse);
		newlyRegistered.setText("Welcome " + nurse.getUsername() 
				+ ".\nPlease select an activity:");		
		
		try {
			patientmanager = new PatientManager
					(this.getApplicationContext().getFilesDir(), FILENAME);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.nurse, menu);
		return true;
	}
	
	/**
	 * Runs the Main Activity.
	 * @param view
	 */
	public void back(View view) {
		Intent intent = new Intent(this, NurseLoginActivity.class);
		startActivity(intent);
	}
	
	public void registerPatient(View view) {
		Intent intent_register = new Intent(this, RegisterPatientActivity.class);
		intent_register.putExtra("Nurse", nurse);
		intent_register.putExtra("Patients", patientmanager);
		startActivity(intent_register);
	}
	
	public void updateRecord(View view) {
		Intent intent_update = new Intent(this, UpdateRecordActivity.class);
		intent_update.putExtra("Nurse", nurse);
		intent_update.putExtra("Patients", patientmanager);
		startActivity(intent_update);
	}
	
	public void showRecord(View view) {
		Intent intent_show = new Intent(this, ShowRecordActivity.class);
		intent_show.putExtra("Staff", nurse);
		intent_show.putExtra("Patients", patientmanager);
		intent_show.putExtra("Usertype", "N");
		startActivity(intent_show);
	}
	
	public void checkUp(View view) {
		Intent intent_checkup = new Intent(this, CheckUpActivity.class);
		intent_checkup.putExtra("Nurse", nurse);
		intent_checkup.putExtra("Patients", patientmanager);
		startActivity(intent_checkup);	
	}
	
	public void orderPatient(View view) {
		Intent intent_order = new Intent(this, OrderPatientActivity.class);
		intent_order.putExtra("Staff", nurse);
		intent_order.putExtra("Patients", patientmanager);
		intent_order.putExtra("Usertype", "N");
		startActivity(intent_order);			
	}
}