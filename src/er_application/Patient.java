package er_application;

import android.annotation.SuppressLint;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

/**
 * A Patient.
 * @author Huiyan Xu
 *
 */
public class Patient implements Serializable, Comparator<Patient>{
	
	private static final long serialVersionUID = 7788310762729355162L;
	private String name;
	private String birthdate; //in YYYY-MM-DD format
	private String healthCardNumber;
	private Record record;
	
	/**
	 * Creates a new Patient with given data.
	 * @param name name of this Patient
	 * @param birthdate in YYYY-MM-DD format
	 * @param healthCardNumber health card number of this Patient
	 * @param arrivalTime arrival time when patient come to see doctor
	 */
	public Patient(String name, String birthdate, 
			String healthCardNumber, Date arrivalTime) {
		this.name = name;
		this.birthdate = birthdate;
		this.healthCardNumber = healthCardNumber;
		// creates a new record with given arrivalTime
		this.record = new Record(arrivalTime);
	}
	
	/**
	 * Returns the record of this patient.
	 * @return Record of this patient
	 */
	public Record getRecord() {
		return this.record;
	}
	
	/**
	 * Returns a string representation of this patient with 
	 * personal informations and record.
	 * @return string representation of data
	 */
	public String getData() {
		String data = this.record.showRecord();
		String information = this.healthCardNumber + 
				"\n" + this.name + "\n" + this.birthdate + "\n";
		String allInfo = information + data + "\n";
		return allInfo;
	}
	
	public String getPersonalData(){
		return this.healthCardNumber + 
				" " + this.name + " " + this.birthdate + "\n";
	}
	
	/**
	 * Returns the health card number of this patient.
	 * @return healthCardNumber health card number of this patient
	 */
	public String getHealthCardNumber() {
		return healthCardNumber;
	}
	
	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	public int calculateUrgency() {
		String vitalSigns = this.getRecord().getAllVitalSigns();
		if (!vitalSigns.isEmpty()) {
			String[] vitalSignsArray = vitalSigns.split("~");
			int length = vitalSignsArray.length;
			String vitalSign = vitalSignsArray[length-1];
			String[] vitalSignArray = vitalSign.split(",");
			System.out.println(vitalSignArray);
			int urgency = 0;
    		int age = Integer.valueOf(this.birthdate.split("-")[0]) 
    			- (new Date()).getYear();
    		if (age < 2) {
    			urgency += 1;
    		}
    		if (Double.valueOf(vitalSignArray[0]) >= 39.0) {
    			urgency += 1;
    		}
    		if (Double.valueOf(vitalSignArray[1]) >= 140 
    				| Integer.valueOf(vitalSignArray[2]) >= 90) {
    			urgency += 1;
    		}
    		if (Double.valueOf(vitalSignArray[3]) >= 100 
    				| Integer.valueOf(vitalSignArray[3]) <= 50 ) {
    			urgency += 1;
    		}		
		return urgency;
		}
		return 0;
	}
	
	/**
	 * Set the check up time for the Patient
	 * @param checkupTime check up time of the Patient
	 */
	public void setCheckUpTime(Date checkupTime) {
		record.setCheckUpTime(checkupTime);
	}
	
	/**
	 * Set the arrival time for the Patient
	 * @param arrivalTime arrival time for the Patient
	 */
	public void setArrivalTime(Date arrivalTime) {
		record.setArrivalTime(arrivalTime);
	}
	
	/**
	 * Determines the higher urgency value between two Patients
	 * first check the urgency values
	 * if the urgency values are the same, then check the arrival time
	 * Returns 1 indicates I go first, -1 indicates you go first
	 */
	@Override
	public int compare(Patient me, Patient you) {
		int urgencyCompare = (Integer.valueOf(me.calculateUrgency()).compareTo(Integer.valueOf(you.calculateUrgency())));
		
		if (urgencyCompare != 0){
			return urgencyCompare;
		}
		if (me.getRecord().getArrivalTime().before(you.getRecord().getArrivalTime())){
			return 1;
		}
		return -1;
	}
}