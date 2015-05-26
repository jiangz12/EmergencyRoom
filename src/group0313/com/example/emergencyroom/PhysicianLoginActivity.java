package group0313.com.example.emergencyroom;

import java.io.FileNotFoundException;

import java.io.FileOutputStream;
import java.io.IOException;

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

import managers.StaffManager;
import er_application.Physician;
import er_application.Staff;

/**
 * The Main Activity for Physician to log into the Emergency Room Application
 * @author Zhen Jiang, Huiyan Xu, Melissa Tam, Huan Xu
 *
 */

public class PhysicianLoginActivity extends Activity {
	
	public static final String FILENAME = "Physicians.txt";
	private StaffManager physicianmanager;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physician_login);
        try {
			physicianmanager = new StaffManager(this.getApplicationContext().getFilesDir(), FILENAME);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the 
    	// action bar if it is present.
        getMenuInflater().inflate(R.menu.physician_login, menu);
        return true;
    }
    
    /**
     * Registers a new nurse and log the nurse into the application.
     * @param view
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public void register(View view){
    	Intent intent_physician = new Intent(this, PhysicianActivity.class);
    	
    	EditText userNameText = 
    			(EditText) findViewById(R.id.physician_name_field);
    	String username = userNameText.getText().toString();
    	
    	EditText passwordText = 
    			(EditText) findViewById(R.id.physician_password_field);
    	String password = passwordText.getText().toString();
    	
    	if (username.isEmpty() || password.isEmpty()) {
    		
    		TextView nullError = (TextView) findViewById(R.id.error);
    		nullError.setText("Please enter your username and password!");
    	} else {
    		if (physicianmanager.getStaffs().containsKey(username)) {
    			
    		    TextView registerError = (TextView) findViewById(R.id.error);
    		    registerError.setText("Sorry, this username " +
    		    		"is already registered.\nPlease try another username!");
    	    } else {
    	    	Physician physician = new Physician(username, password);
    	    	
    	    	intent_physician.putExtra("Physician", physician);
    		    physicianmanager.add(physician);
    		
    		    FileOutputStream outputStream;
                try {
                    outputStream = openFileOutput(PhysicianLoginActivity.FILENAME, 
                                                  Context.MODE_PRIVATE);
                    physicianmanager.save(outputStream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
        	    startActivity(intent_physician);
    	    }
    	}
    }
    
    /**
     * Log into the application under the given username iff 
     * password matches with the username.
     * Otherwise, displays a error message and stays on this activity.
     * @param view
     */
    public void login(View view) {
    	Intent intent_physician = new Intent(this, PhysicianActivity.class);
    	
    	EditText userNameText = (EditText) findViewById(R.id.physician_name_field);
    	String username = userNameText.getText().toString();
    	
    	EditText passwordText = (EditText) findViewById(R.id.physician_password_field);
    	String password = passwordText.getText().toString();
    	

    	if (!physicianmanager.getStaffs().containsKey(username) || 
    			!physicianmanager.matchPassword(username, password)) {
    		
    		TextView loginError = (TextView) findViewById(R.id.error);
    		loginError.setText("Sorry, you entered wrong " +
    				"username or wrong password.\nPlease try again!");
    	}
    	else {
    		Staff staffPhysician = physicianmanager.get(username);
    		Physician physician = new Physician(staffPhysician.getUsername(),
    				staffPhysician.getPassword());
    		intent_physician.putExtra("Physician", physician);
    		
    		startActivity(intent_physician);
    	}
    	
    }
    
    public void back(View view) {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
}
