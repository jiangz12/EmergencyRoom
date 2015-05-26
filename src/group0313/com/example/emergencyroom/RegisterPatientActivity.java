package group0313.com.example.emergencyroom;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

import er_application.Nurse;
import managers.PatientManager;
import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterPatientActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_patient);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register_patient, menu);
		return true;
	}
	
	/**
	 * Registers a new patient and saves it to patientmanager.
	 * Checks for a correct format.
	 * @param view
	 */
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public void register_patient(View view) {
		Intent intent = getIntent();
		
		PatientManager patientmanager = (PatientManager) intent.getSerializableExtra("Patients");
		
	    EditText nameText = (EditText) findViewById(R.id.patient_name_field);
	    String name = nameText.getText().toString();
	    	
	    EditText birthdateText = 
	    		(EditText) findViewById(R.id.birth_date_field);
	    String birthdate = birthdateText.getText().toString();
	    	
	    EditText healthCardNumberText = 
	    		(EditText) findViewById(R.id.health_card_field);
	    String healthCardNumber = healthCardNumberText.getText().toString();
	    
	    if (name.isEmpty() || healthCardNumber.isEmpty() || !checkBirth(birthdate)) {
	    	
	    	TextView registerError = 
	    			(TextView) findViewById(R.id.errortext);
	    	registerError.setText("Please enter Patient Name and " +
	    			"Health Card Number\nBirthdate in format YYYY-MM-DD.");
	    } 
	    else if (patientmanager.getPatients().containsKey(healthCardNumber)) {
	    	TextView sameNumberError = 
	    			(TextView) findViewById(R.id.errortext);
	    	sameNumberError.setText("This Health Card Number " +
	    			"already exists.");
	    }
	    else {
		    Date arrivaltime = new Date();
		    
		    Nurse nurse = (Nurse) intent.getSerializableExtra("Nurse");
		    
		    nurse.recordPatient(name, birthdate, healthCardNumber, 
		    		arrivaltime, patientmanager);
		    TextView successRegistered = 
		    		(TextView) findViewById(R.id.errortext);
			successRegistered.setText("The patient with HealthCardNumber: " 
		    		+ healthCardNumber + " has been successfully registered!");
		    
		    intent.putExtra("Patients", patientmanager);
    		
		    FileOutputStream outputStream;
            try {
                outputStream = openFileOutput(NurseActivity.FILENAME, 
                                              Context.MODE_PRIVATE);
                patientmanager.save(outputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
		    startActivity(intent);
	    }
	 }
	
	/**
	 * Returns True iff birthDate is in correct format (YYYY-MM-DD) 
	 * and is a valid date.
	 * @param birthDate
	 * @return true if birthDate is in correct format and is valid; 
	 * otherwise, false
	 */
	public boolean checkBirth(String birthDate) {
		if (birthDate.length() != 10) {
			return false;
		}
		else {
			String[] birthDateList = birthDate.split("-");
			if (birthDateList.length != 3) {
				return false;
			}
			else {
				for (int i = 0; i <= 2; i++) {
					try {
						Integer.parseInt(birthDateList[i]);
					} 
					catch (NumberFormatException e) {
						return false;
					}
					}
				if (Integer.valueOf(birthDateList[1]) < 1 
						| Integer.valueOf(birthDateList[1]) > 12) {
					return false;
				}
				if (Integer.valueOf(birthDateList[2]) < 1 
						| Integer.valueOf(birthDateList[2]) > 31) {
					return false;
				}
			return true;
			}
		}
		}
	
	public void back(View view) {
		Intent intent = getIntent();
		Nurse nurse = (Nurse) intent.getSerializableExtra("Nurse");
		Intent intent_nurse = new Intent(this, NurseActivity.class);
		intent_nurse.putExtra("Nurse", nurse);
		startActivity(intent_nurse);
	}

}
