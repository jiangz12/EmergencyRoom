package er_application;

import java.io.Serializable;

/**
 * A Physician.
 * @author tammeli2
 */
public class Physician extends Staff implements Serializable{

	/**
	 * Automatic generated serial ID
	 */
	private static final long serialVersionUID = -8089887039897851888L;


	/**
	 * Creates a new Physician user 
	 * with username and password.
	 * @param username
	 * @param password
	 */
	public Physician(String username, String password) {
		super(username, password);
	}
	
	/**
	 * Adds new prescription to the patient's record.
	 * @param patient a Patient
	 * @param prescription, prescribed medicine for patient
	 *  and instructions of use
	 */
	public void recordPrescription(Patient patient, String prescription){
		Record record = patient.getRecord();
		record.addPrescription(prescription);
	}
}
