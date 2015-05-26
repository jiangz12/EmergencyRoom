package group0313.com.example.emergencyroom;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

/**
 * The Main Activity to log into the Emergency Room Application
 * @author Zhen Jiang, Huiyan Xu, Melissa Tam, Huan Xu
 *
 */

public class MainActivity extends Activity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the 
    	// action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void selectNurse(View view) {
		Intent intent = new Intent(this, NurseLoginActivity.class);
		startActivity(intent);
	}
    
    public void selectDoctor(View view) {
		Intent intent = new Intent(this, PhysicianLoginActivity.class);
		startActivity(intent);
	}


}
