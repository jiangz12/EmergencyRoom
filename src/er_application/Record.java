package er_application;

import android.annotation.SuppressLint;
import java.io.Serializable;
import java.util.Date;

/**
 * A Record of a Patient.
 * @author Jiang Zhen, Huiyan Xu
 *
 */
public class Record implements Serializable{
	
	private static final long serialVersionUID = 7243734301184365922L;
	private Date arrivalTime;
	private String allVitalSigns;
	private String allPrescriptions;
	private Date checkUpTime;
	
	/**
	 * Creates a new Record with given arrivalTime.
	 * @param arrivalTime when patient arrived
	 */
	public Record(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
		this.allVitalSigns = "";
		this.allPrescriptions = "";
		this.checkUpTime = null;
	}

	/**
	 * Returns the arrival time.
	 * @return arrivalTime recorded in this record
	 */
	public Date getArrivalTime() {
		return arrivalTime;
	}
	
	/**
	 * Adds vitalSign to this record.
	 * @param vitalSign string of temperature, blood pressure, heart rate
	 */
	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	public void addVitalSigns(String vitalSign) {
		Date presenttime = new Date();
		String time = "" + (presenttime.getYear() + 1900) 
				+ "-" + (presenttime.getMonth() + 1) + "-" 
				+ presenttime.getDate() + " " + presenttime.getHours() + "-"
				+ presenttime.getMinutes() + "-" + presenttime.getSeconds();
		if (allVitalSigns.isEmpty()) {
			allVitalSigns = allVitalSigns + time + "~" + vitalSign;
		}
		else {
			allVitalSigns = allVitalSigns + "\n             " + 
		    time + "~" + vitalSign;
		}
	}
	
	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	public void addPrescription(String prescription) {
		Date present = new Date();
		String time = "" + (present.getYear() + 1900) 
				+ "-" + (present.getMonth() + 1) + "-" 
				+ present.getDate() + " " + present.getHours() + "-"
				+ present.getMinutes() + "-" + present.getSeconds();	
		if (allPrescriptions.isEmpty()) {
			allPrescriptions = allPrescriptions + time + "~" + prescription;
		}
		else {
			allPrescriptions = allPrescriptions + "\n              " +
		time + "~" + prescription;
		}
	}
	
	@SuppressLint("NewApi")
	public void addPrescriptions(String time, String prescription) {
		if (allPrescriptions.isEmpty()) {
			allPrescriptions = allPrescriptions + time 
					+ "~" + prescription;
		}
		else {
			allPrescriptions = allPrescriptions + "\n              " + 
		time + "~" + prescription;
		}
	}
	
	/**
	 * Adds vitalSigns to this record with given time.
	 * @param time when vital signs were taken
	 * @param vitalSign string of temperature, blood pressure, heart rate
	 */
	@SuppressLint("NewApi")
	public void addVitalSigns(String time, String vitalSign) {
		if (allVitalSigns.isEmpty()) {
			allVitalSigns = allVitalSigns + time + 
			"~" + vitalSign;
		}
		else {
			allVitalSigns = allVitalSigns + "\n             " + 
		    time + "~" + vitalSign;
		}
		
	}
	
	/**
	 * Returns the record of allVitalSigns
	 * @return the record of allVitalSigns
	 */
	public String getAllVitalSigns() {
		return this.allVitalSigns;
	}
	
	/**
	 * Setup the check up time for the Patient
	 * @param checkupTime check up time of the Patient
	 */
	public void setCheckUpTime(Date checkupTime) {
		this.checkUpTime = checkupTime;
	}
	
	/**
	 * Setup the arrival time for the Patient
	 * @param arrivalTime arrival time of the Patient
	 */
	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	
	/**
	 * Returns the record of allPrescriptions
	 * @return the record of allPrescriptions
	 */
	public String getPrescription() {
		return this.allPrescriptions;
		
	}
	
	/**
	 * Determines whether if the Patient has been checked up on
	 * @return true iff the Patient is checked up on
	 */
	public boolean isCheckedUp() {
		if (checkUpTime != null && arrivalTime.before(checkUpTime)){
			return true;
		}
		return false;
	}

	/**
	 * Returns a string representation of this record 
	 * with arrival time and vital signs
	 * @return string representation of Record
	 */
	@SuppressWarnings("deprecation")
	public String showRecord() {		
		String arrival = "" + (arrivalTime.getYear() + 1900) + "-" 
		+ (arrivalTime.getMonth() + 1) + "-"
		+ arrivalTime.getDate() + " " + arrivalTime.getHours() + "-"
		+ arrivalTime.getMinutes() + "-" + arrivalTime.getSeconds();
		
		String checkUp = "";
		if (checkUpTime != null) {
			checkUp += (checkUpTime.getYear() + 1900) + "-" 
				+ (checkUpTime.getMonth() + 1) + "-"
				+ checkUpTime.getDate() + " " + checkUpTime.getHours() + "-"
				+ checkUpTime.getMinutes() + "-" + checkUpTime.getSeconds();
		}
		return "Arrival time: " + arrival + "\nCheckUp time: " + checkUp 
				+ "\nPrescription: " + allPrescriptions	
				+ "\nVital signs: " +  allVitalSigns;
	}
}