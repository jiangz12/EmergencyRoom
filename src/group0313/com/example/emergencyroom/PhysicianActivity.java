package group0313.com.example.emergencyroom;

import java.io.IOException;

import managers.PatientManager;
import er_application.Physician;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

/**
 * A main activity for Physician
 * @author tammeli2
 *
 */
public class PhysicianActivity extends Activity {
	
	public static final String FILENAME = "Patients.txt";
	private PatientManager patientManager;
	private Physician physician;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_physician);

		Intent intent = getIntent();

		physician = (Physician) intent.getSerializableExtra("Physician");
		
        TextView message = (TextView) findViewById(R.id.welcome_message);
		message.setText("Welcome " + physician.getUsername() 
				+ ".\nPlease select an activity:");		
		
		try {
			patientManager = new PatientManager
					(this.getApplicationContext().getFilesDir(), FILENAME);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.physician, menu);
		return true;
	}
	
	/**
	 * Returns to the login page
	 * @param view
	 */
	public void back (View view){
		Intent intent = new Intent(this, PhysicianLoginActivity.class);
    	intent.putExtra("patients", patientManager);
    	startActivity(intent);
	}
	
	/**
	 * Starts the PrescriptionActivity
	 * @param view
	 */
	public void addPrescription(View view){
		Intent intent = new Intent(this, PrescriptionActivity.class);
		intent.putExtra("Physician", physician);
    	intent.putExtra("Patients", patientManager);
    	startActivity(intent);
	}
	
	/**
	 * Starts the RecordActivity
	 * @param view
	 */
	public void showRecord(View view){
		Intent intent = new Intent(this, ShowRecordActivity.class);
		intent.putExtra("Staff", physician);
    	intent.putExtra("Patients", patientManager);
    	intent.putExtra("Usertype", "P");
    	startActivity(intent);
	}
	
	public void orderPatient(View view) {
		Intent intent_order = new Intent(this, OrderPatientActivity.class);
		intent_order.putExtra("Staff", physician);
		intent_order.putExtra("Patients", patientManager);
		intent_order.putExtra("Usertype", "P");
		startActivity(intent_order);			
	}
}
