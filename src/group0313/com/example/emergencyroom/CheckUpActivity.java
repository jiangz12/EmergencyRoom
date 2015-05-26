package group0313.com.example.emergencyroom;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

import er_application.Nurse;
import er_application.Patient;
import managers.PatientManager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * An Activity for Nurse to check up patient.
 * @author Huiyan Xu
 *
 */

public class CheckUpActivity extends Activity {
	
	private PatientManager patientManager;
	private Nurse nurse;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkup);
		
		Intent intent = getIntent();
		patientManager = (PatientManager) intent.getSerializableExtra("Patients");
		nurse = (Nurse) intent.getSerializableExtra("Nurse");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.checkup, menu);
		return true;
	}
	
	/**
	 * Returns to the previous Activity
	 * @param view
	 */
	public void back(View view) {
		Intent intent_nurse = new Intent(this, NurseActivity.class);
		intent_nurse.putExtra("Nurse", nurse);
		startActivity(intent_nurse);
	}
	
	/**
	 * Check up this patient
	 * @param view
	 */
	public void checkUp(View view){
		EditText healthCardField = (EditText) findViewById(R.id.health_card_text);
		String healthCard = healthCardField.getText().toString();
		TextView messageField = (TextView) findViewById(R.id.message_field);
		
		try{
			Patient patient = nurse.getPatient(healthCard, patientManager);
			Date now = new Date();
			if (patient.getRecord().isCheckedUp()) {
				messageField.setText("Patient " + healthCard 
						+ " has already been checked up.");
			}
			else {
			nurse.recordCheckUpTime(patient, now);
			messageField.setText("Patient " + healthCard + " has checked up.");
			 FileOutputStream outputStream;
	         try {
	             outputStream = openFileOutput(NurseActivity.FILENAME, 
	                                           Context.MODE_PRIVATE);
	             patientManager.save(outputStream);	             
	         } catch (FileNotFoundException e) {
	             e.printStackTrace();
	         }
			healthCardField.setText(null);
			}
		}
		catch (IllegalArgumentException e){
			messageField.setText("Patient " + healthCard + " cannot be found.");
		}
		healthCardField.setText(null);
	}
}
