package group0313.com.example.emergencyroom;

import er_application.Patient;
import er_application.Staff;
import managers.PatientManager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ShowRecordActivity extends Activity {
	
	private PatientManager patientManager;
	private Staff staff;
	private String usertype;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_record);
		
		Intent intent = getIntent();
		patientManager = (PatientManager) intent.getSerializableExtra("Patients");
		usertype = (String) intent.getSerializableExtra("Usertype");
		staff = (Staff) intent.getSerializableExtra("Staff");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_record, menu);
		return true;
	}
	
	/**
	 * Returns to the previous Activity
	 * @param view
	 */
	public void back(View view) {
		if (usertype.equals("N")){
			Intent intent_nurse = new Intent(this, NurseActivity.class);
			intent_nurse.putExtra("Nurse", staff);
			startActivity(intent_nurse);
		}
		else{
			Intent intent_physician = new Intent(this, PhysicianActivity.class);
			intent_physician.putExtra("Physician", staff);
			startActivity(intent_physician);
		}
	}
	
	/**
	 * Displays the patient's record
	 * @param view
	 */
	public void showRecord(View view){
		EditText healthCardField = (EditText) findViewById(R.id.health_card_text);
		String healthCard = healthCardField.getText().toString();
		
		TextView recordField = (TextView) findViewById(R.id.show_record_field);
		
		try{
			Patient patient = staff.getPatient(healthCard, patientManager);
			String data = patient.getData();
			
			recordField.setText(data);
		}
		catch (IllegalArgumentException e){
			recordField.setText("Patient " + healthCard + " cannot be found.");
		}
	}
}
