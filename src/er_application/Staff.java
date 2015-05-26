package er_application;

import java.io.Serializable;
import managers.PatientManager;

/**
 * A staff of the hospital.
 * @author tammeli2
 *
 */
public class Staff implements Serializable {
	
	/**
	 * Generated serial ID by Eclipse
	 */
	private static final long serialVersionUID = -2956272673619270642L;
	private String username;
	private String password;
	
	/**
	 * Creates a new Staff user.
	 * @param username username of this staff member
	 * @param password password of this staff member
	 */
	public Staff (String username, String password){
		this.username = username;
		this.password = password;
	}
	
	/**
	 * Returns username of this staff member.
	 * @return username of this staff member
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Returns password of this staff member.
	 * @return password of this staff member
	 */
	public String getPassword(){
		return password;
	}
	
	/**
	 * Returns the patient with corresponding health card number.
	 * @param healthCardNumber of the patient
	 * @return the patient with given health card number
	 */
	public Patient getPatient(String healthCardNumber, PatientManager patientManager){
		return patientManager.get(healthCardNumber);
	}

}
