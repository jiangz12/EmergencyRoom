package group0313.com.example.emergencyroom;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import er_application.Patient;
import er_application.Physician;
import managers.PatientManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * An Activity for Physicians to add new
 * prescription(s) to a Patient's record
 * @author tammeli2	
 *
 */
public class PrescriptionActivity extends Activity {
	
	private PatientManager patientManager;
	private Physician physician;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prescription);
		
		Intent intent = getIntent();

		physician = (Physician) intent.getSerializableExtra("Physician");
		patientManager = (PatientManager) intent.getSerializableExtra("Patients");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.prescription, menu);
		return true;
	}
	
	/**
	 * Returns to Physician's main page
	 * @param view
	 */
	public void back (View view){
		Intent intent = new Intent(this, PhysicianActivity.class);
		intent.putExtra("Physician", physician);
    	startActivity(intent);
	}
	
	/**
	 * Adds the given prescription to patient's record
	 * @param view
	 */
	public void addPrescription(View view){
		EditText healthCardField = (EditText) findViewById(R.id.health_card_text);
		String healthCard = healthCardField.getText().toString();
		
		EditText prescriptionField = (EditText) findViewById(R.id.prescription_text);
		String prescription = prescriptionField.getText().toString();
		
		TextView messageField = (TextView) findViewById(R.id.message_field);
		
		try{
			Patient patient = patientManager.get(healthCard);
			physician.recordPrescription(patient, prescription);
			
			messageField.setText("Added prescription to patient " + healthCard + ".");
			 FileOutputStream outputStream;
	         try {
	             outputStream = openFileOutput(PhysicianActivity.FILENAME, 
	                                           Context.MODE_PRIVATE);
	             patientManager.save(outputStream);
	             
	         } catch (FileNotFoundException e) {
	             e.printStackTrace();
	         }
			healthCardField.setText(null);
			prescriptionField.setText(null);
		}
		catch (IllegalArgumentException e){
			messageField.setText("Patient " + healthCard + " cannot be found.");
		}
	}

}

