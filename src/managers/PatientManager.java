package managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Date;

import android.annotation.SuppressLint;
import er_application.Patient;

/**
 * A class that manages Patients. PatientManager knows how to read Patient
 * objects from a file and how to write its collection of Patients to a file.
 * 
 * @author xuhuan2
 *
 */

public class PatientManager implements Serializable, Manager<Patient>{
	
	private static final long serialVersionUID = 5954764426676822357L;
	private Map<String, Patient> patients; //Patients by their HealCardNumber
	
	/**
	 * Constructs a new PatientManager that manages a collection of Patients
	 * stored in directory dir in file named fileName
	 * @param dir the directory in which the data file is stored
	 * @param fileName the data file containing Person information
	 * @throws IOException
	 */
	public PatientManager(File dir, String fileName) throws IOException {
		this.patients = new HashMap<String, Patient>();
		
		//Load the Patient list using stored data, if it exists.
		File file = new File(dir, fileName);
		if (file.exists()) {
			this.load(file.getPath());
		} else {
			file.createNewFile(); 
			//create a new file if the file doesn't exist
		}
	}
	
	/**
	 * Adds a patient to this PatientManager.
	 * @param patient Patient object
	 */
	@Override
	public void add(Patient patient) {
		patients.put(patient.getHealthCardNumber(), patient);
	}
	
	/**
	 * Gets the Patients managed by this PatientManager
	 * @return a map of Patient HealthCardNumber to Patient object
	 */
	public Map<String, Patient> getPatients() {
		return patients;
	}
	
	/**
	 * Returns the patient with the given healthCardNumber
	 * @param healthCardNumber the health card number of the patient
	 * @return patient with this health card number
	 */
	@Override
	public Patient get(String healthCardNumber) {
		return patients.get(healthCardNumber);
	}
	
	/**
	 * Saves the Patient objects to file outputStream.
	 * @param outputStream the output stream to 
	 * write the Patient data to file.
	 */
	@Override
	public void save(FileOutputStream outputStream) {
		try{
			for (Patient p: patients.values()) {
				String write = p.getData();
				String n = "\n";
				byte[] nb = n.getBytes();
				outputStream.write((write).getBytes());
				outputStream.write(nb);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads the map of Patients from the file at path filePath.
	 * @param filePath the file path of the data file.
	 * @throws FileNotFoundException
	 */
	@Override
	@SuppressLint("NewApi")
	public void load(String filePath) throws FileNotFoundException {
		
		Scanner scanner = new Scanner(new FileInputStream(filePath));
		scanner.useDelimiter("\n");
        String [] record_arrivaltime, record_checkuptime, record, record1;
        String recordShort, thisline = null;
        
        while(scanner.hasNextLine()) {
        	String healthCardNumber = scanner.nextLine();
        	String name = scanner.nextLine();
        	String dob = scanner.nextLine();
        	record_arrivaltime = scanner.nextLine().split(": ");
        	record_checkuptime = scanner.nextLine().split(": ");
        	Date arrivalTime = dateGetterHelper(record_arrivaltime[1]);
        	
        	Patient patient = new Patient(name, dob, 
        			healthCardNumber, arrivalTime);
        	
        	if (record_checkuptime.length == 2) {
        	    Date checkupTime = dateGetterHelper(record_checkuptime[1]);
        	    patient.setCheckUpTime(checkupTime);
        	}
        	
        	record = scanner.nextLine().split(": ");
        	if (!(record.length == 1)) {
        		record1 = record[1].split("~");
        		patient.getRecord().addPrescriptions(record1[0], record1[1]);
        		thisline = scanner.nextLine().toString();
        		while (!thisline.contains(": ")) {
        			recordShort = thisline.substring(14);
        			record1 = recordShort.split("~");
        			patient.getRecord().addPrescriptions(record1[0], record1[1]);
        			thisline = scanner.nextLine().toString();
        		}
        	}
        	else {
        		thisline = scanner.nextLine().toString();
        	}
        	record = thisline.split(": ");
        	if (!(record.length == 1)) {
        		record1 = record[1].split("~");
        		patient.getRecord().addVitalSigns(record1[0], record1[1]);
        		thisline = scanner.nextLine().toString();
        		while (scanner.hasNextLine() & (! thisline.isEmpty())) {
            		recordShort = thisline.substring(13);
            		record1 = recordShort.split("~");
            		patient.getRecord().addVitalSigns(record1[0], record1[1]);
            		thisline = scanner.nextLine().toString();
            	}
        	}
        	else {
            	if (scanner.hasNextLine()) {
            		scanner.nextLine();
            	}
        	}
        	patients.put(healthCardNumber, patient);
        }
        scanner.close();
    }
	
	/**
	 * A helper function that takes in info about 
	 * a particular date and returns a Date
	 * constructed using built-in function
	 * @param info the information of the given date
	 * @return the Date with the given values
	 */
	@SuppressWarnings("deprecation")
	private Date dateGetterHelper(String info) {
		String [] date, date1, date2;
		date = info.split(" ");
    	date1 = date[0].split("-");
    	date2 = date[1].split("-");
    	Date time = new Date(Integer.valueOf(date1[0])-1900,
    			Integer.valueOf(date1[1])-1, Integer.valueOf(date1[2]), 
    			Integer.valueOf(date2[0]), Integer.valueOf(date2[1]), 
    			Integer.valueOf(date2[2]));
		return time;
	}
	
	/**
	 * Returns a list of patients that have 
	 * not been seen by a physician
	 * @return list of patients not seen by physician
	 */
	private List<Patient> notCheckedPatients(){
		List<Patient> notChecked = new ArrayList<Patient>();
		for (Patient patient : patients.values()){
			if (!patient.getRecord().isCheckedUp()){
				notChecked.add(patient);
			}
		}
		return notChecked;
	}
	
	/**
	 * Returns a list of patients ordered
	 * by urgency level, then by arrival time.
	 * @return ordered list of patients by urgency levels
	 */
	private List<Patient> orderUrgency(){
		List <Patient> orderedPatients = this.notCheckedPatients();
		Comparator<Patient> comparator = new Patient(null, null, null, null);
		Collections.sort(orderedPatients, comparator);
		return orderedPatients;
	}
	
	public String orderPatients(){
		List <Patient> orderedPatients = this.orderUrgency();
		String list = "";
		for (int i = orderedPatients.size() - 1; i >= 0; i --) {
			list += ((Patient) (orderedPatients.toArray())[i]).getPersonalData();
		}
		return list;
	}


}