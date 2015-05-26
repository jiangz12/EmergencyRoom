package group0313.com.example.emergencyroom;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

import managers.PatientManager;

import er_application.Nurse;
import er_application.Patient;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class UpdateRecordActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_record);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.update_record, menu);
		return true;
	}
	
	/**
	 * Returns True iff vitalSign is in correct format x,x,x,x
	 * @param vitalSign
	 * @return true if vital sign is in correct format
	 */
	public boolean checkVitalSigns(String vitalSigns) {
		String[] vitalSignList = vitalSigns.split(",");
	    for (int i = 0; i < vitalSignList.length; i ++) {
			try{
				isNumber(vitalSignList[i]);
			} catch (NumberFormatException e) {
				return false;
			}
		}
		return true;
	}
	
	public boolean isNumber(String string) throws NumberFormatException{
		if (string.matches("\\d+\\.?\\d*") || string.matches("")) {
			return true;
		} else {
			throw new NumberFormatException();
		}
	}

	/**
	 * Updates a patient's vital signs and save it to patientmanager.
	 * Checks for existing patient by healthCardNumber.
	 * @param view
	 */
	 @SuppressLint("NewApi")
	public void updatePatient(View view) {
		 Intent intent = getIntent();
		 
		 Nurse nurse = (Nurse) intent.getSerializableExtra("Nurse");
		 PatientManager patientmanager = (PatientManager) intent.getSerializableExtra("Patients");
		 
		 EditText temperatureText = 
				 (EditText) findViewById(R.id.temperature_field);
		 String temperature = temperatureText.getText().toString();
		 
		 EditText systoleText = 
				 (EditText) findViewById(R.id.systole_field);
		 String systole = systoleText.getText().toString();
		 
		 EditText diastoleText = 
				 (EditText) findViewById(R.id.diastole_field);
		 String diastole = diastoleText.getText().toString();
		 
		 EditText heartRateText = 
				 (EditText) findViewById(R.id.heart_rate_field);
		 String  heartRate = heartRateText.getText().toString();
		 
		 EditText healthCardNumberText = 
				 (EditText) findViewById(R.id.search_card_field);
		 String healthCardNumber = healthCardNumberText.getText().toString();
		 
		 Patient patient = patientmanager.get(healthCardNumber);
		 String checkEmptyVitalSigns = temperature + systole + diastole + heartRate;
		 String checkNonintVitalSigns = temperature + "," + systole + "," + diastole + "," + heartRate;
		 if (patient != null){
			 String allVitalSigns = patient.getRecord().getAllVitalSigns();
			 String vitalSigns = "";
			 if (!allVitalSigns.isEmpty()) {
				 String[] vitalSignsArray = allVitalSigns.split("~");
				 String lastVitalSigns = vitalSignsArray[vitalSignsArray.length - 1];
				 String[] vitalSignArray = lastVitalSigns.split(",");
				 if (temperature.isEmpty()) {
					 vitalSigns += vitalSignArray[0] + ",";
				 } else {
					 vitalSigns += temperature +  ",";
				 }
				 if (systole.isEmpty()) {
					 vitalSigns += vitalSignArray[1] + ",";
				 } else {
					 vitalSigns += systole +  ",";
				 }
				 if (diastole.isEmpty()) {
					 vitalSigns += vitalSignArray[2] + ",";
				 } else {
					 vitalSigns += diastole +  ",";
				 }
				 if (heartRate.isEmpty()) {
					 vitalSigns += vitalSignArray[3];
				 } else {
					 vitalSigns += heartRate;
				 }
				 if ((patient.getRecord().isCheckedUp() && 
					 (temperature.isEmpty() || systole.isEmpty() || diastole.isEmpty() 
					 || heartRate.isEmpty()))){
					 TextView uncompletenessError = (TextView) findViewById(R.id.errortext);
					 uncompletenessError.setText("You have to enter all vital signs.");
				 }
				 else if (checkEmptyVitalSigns.isEmpty() || !checkVitalSigns(checkNonintVitalSigns)) {
					 TextView nullError = (TextView) findViewById(R.id.errortext);
					 nullError.setText("You have to enter at least one and correct vital sign.");
				 } 
				 else {
					 nurse.recordVitalSigns(patient, vitalSigns);
					 if (patient.getRecord().isCheckedUp()) {
						 Date now = new Date();
						 nurse.recordArrivalTime(patient, now);
					 }
					 nurse.recordCheckUpTime(patient, null);
					 
					 intent.putExtra("Patients", patientmanager);
					 intent.putExtra("Nurse", nurse);
					 
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
			 } else {
				 if ((temperature.isEmpty() || systole.isEmpty() || diastole.isEmpty() 
						 || heartRate.isEmpty())) {
					 TextView uncompletenessError = (TextView) findViewById(R.id.errortext);
					 uncompletenessError.setText("You have to enter all vital signs.");
				 } else {
					 vitalSigns += checkNonintVitalSigns;
					 if (!checkVitalSigns(checkNonintVitalSigns)) {
						 TextView nullError = (TextView) findViewById(R.id.errortext);
						 nullError.setText("You have to enter correct vital signs.");
					 } else {
						 nurse.recordVitalSigns(patient, vitalSigns);
						 if (patient.getRecord().isCheckedUp()) {
							 Date now = new Date();
							 nurse.recordArrivalTime(patient, now);
						 }
						 nurse.recordCheckUpTime(patient, null);
						 
						 intent.putExtra("Patients", patientmanager);
						 intent.putExtra("Nurse", nurse);
						 
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
			 } 
		 }
		 else{
			 TextView Error = (TextView) findViewById(R.id.errortext);
			 Error.setText("Cannot find patient with health card number: " 
			 + healthCardNumber + ".");
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