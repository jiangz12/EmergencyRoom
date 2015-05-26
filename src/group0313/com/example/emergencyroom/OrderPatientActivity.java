package group0313.com.example.emergencyroom;


import er_application.Staff;
import managers.PatientManager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

/**
 * An Activity for Physician and Nurse to get a all unchecked patients 
 * in urgency level order/
 * @author Huiyan Xu
 *
 */

public class OrderPatientActivity extends Activity {
	
	private PatientManager patientManager;
	private Staff staff;
	private String usertype;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_patient);
		
		Intent intent = getIntent();
		patientManager = (PatientManager) intent.getSerializableExtra("Patients");
		staff = (Staff) intent.getSerializableExtra("Staff");
		usertype = (String) intent.getSerializableExtra("Usertype");		
		
        TextView showOrder = (TextView) findViewById(R.id.show_field);
        showOrder.setText(patientManager.orderPatients());		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.order_patient, menu);
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
	
}
