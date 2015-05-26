package er_application;

import java.io.Serializable;
import java.util.Date;
import managers.PatientManager;

/**
 * A Nurse user of the ER
 * @author tammeli2
 *
 */
public class Nurse extends Staff implements Serializable{
	
	/**
	 * Automatic generated serial ID by Eclipse.
	 */
	private static final long serialVersionUID = -5763710549196001005L;
	
	/**
	 * Creates a new Nurse user.
	 * @param username username of this nurse
	 * @param password password of this username
	 */
	public Nurse(String username, String password){
		super(username, password);
	}

	/**
	 * Creates a new patient with given data and
	 * adds to the current patient list
	 * @param name name of this patient
	 * @param birthdate birthdate of this patient
	 * @param healthCard health card number of this patient
	 * @param arrivalTime arrival time of this patient
	 */
	public void recordPatient(String name, String birthdate, 
			String healthCard, Date arrivalTime, 
			PatientManager patientManager){
		Patient patient = new Patient(name, birthdate, 
				healthCard, arrivalTime);
		patientManager.add(patient);
	}
	
	/**
	 * Records the patient's vital signs.
	 * @param patient a Patient object
	 * @param vitalSigns String in format of: Temperature, 
	 * Blood pressure, Heart rate
	 */
	public void recordVitalSigns(Patient patient, String vitalSigns){ 
		// decide to use String for vitalSigns
		Record record = patient.getRecord();
		record.addVitalSigns(vitalSigns);
	}
	
	/**
	 * Record the check up time for the given Patient with the given Date
	 * @param patient Patient object
	 * @param date Date of the check up time
	 */
	public void recordCheckUpTime(Patient patient, Date date) {
		patient.setCheckUpTime(date);
	}
	
	/**
	 * Record the arrival time for the given Patient with the given Date
	 * @param patient Patient object
	 * @param date Date of the arrival time
	 */
	public void recordArrivalTime(Patient patient, Date date) {
		patient.setArrivalTime(date);
	}
}