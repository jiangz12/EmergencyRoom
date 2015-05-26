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
import er_application.Nurse;
import er_application.Staff;

/**
 * An Activity for Nurse to log into the Emergency Room Application
 * @author Zhen Jiang, Huiyan Xu, Melissa Tam, Huan Xu
 *
 */

public class NurseLoginActivity extends Activity {
	
	public static final String FILENAME = "Nurses.txt";
	private StaffManager nursemanager;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_login);
        try {
			nursemanager = new StaffManager(
					this.getApplicationContext().getFilesDir(),
					FILENAME);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the 
    	// action bar if it is present.
        getMenuInflater().inflate(R.menu.nurse_login, menu);
        return true;
    }
    
    /**
     * Registers a new nurse and log the nurse into the application.
     * @param view
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public void register(View view){
    	Intent intent_nurse = new Intent(this, NurseActivity.class);
    	
    	EditText userNameText = 
    			(EditText) findViewById(R.id.nurse_name_field);
    	String username = userNameText.getText().toString();
    	
    	EditText passwordText = 
    			(EditText) findViewById(R.id.nurse_password_field);
    	String password = passwordText.getText().toString();
    	
    	if (username.isEmpty() || password.isEmpty()) {
    		
    		TextView nullError = (TextView) findViewById(R.id.error);
    		nullError.setText("Please enter username or password!");
    	} else {
    		if (nursemanager.getStaffs().containsKey(username)) {
    			
    		    TextView registerError = (TextView) findViewById(R.id.error);
    		    registerError.setText("Sorry, the username you register " +
    		    		"has been used.\nPlease try another username!");
    	    } else {
    	    	Nurse nurse = new Nurse(username, password);
    	    	
    	    	intent_nurse.putExtra("Nurse", nurse);
    		    nursemanager.add(nurse);
    		
    		    FileOutputStream outputStream;
                try {
                    outputStream = openFileOutput(NurseLoginActivity.FILENAME, 
                                                  Context.MODE_PRIVATE);
                    nursemanager.save(outputStream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
        	    startActivity(intent_nurse);
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
    	Intent intent_nurse = new Intent(this, NurseActivity.class);
    	
    	EditText userNameText = (EditText) findViewById(R.id.nurse_name_field);
    	String username = userNameText.getText().toString();
    	
    	EditText passwordText = (EditText) findViewById(R.id.nurse_password_field);
    	String password = passwordText.getText().toString();
    	

    	if (!nursemanager.getStaffs().containsKey(username) || 
    			!nursemanager.matchPassword(username, password)) {
    		
    		TextView loginError = (TextView) findViewById(R.id.error);
    		loginError.setText("Sorry, you entered wrong " +
    				"username or wrong password.\nPlease try again!");
    	}
    	else {
    		Staff staffNurse = nursemanager.get(username);
    		Nurse nurse = new Nurse(staffNurse.getUsername(), staffNurse.getPassword());
    		intent_nurse.putExtra("Nurse", nurse);
    		
    		startActivity(intent_nurse);
    	}
    	
    }
    
    public void back(View view) {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
}
