package managers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import er_application.Staff;

/**
 * A class that manages Staffs, include Physicians and Nurses.
 * @author Huiyan Xu
 *
 */

public class StaffManager implements Serializable, Manager<Staff>{
	
	/**
	 * Constructs a new StaffManager that manages a collection of Staff
	 * stored in directory dir in file named fileName
	 * @param dir the directory in which the data file is stored
	 * @param fileName the data file containing Person information
	 * @throws IOException
	 */
	private static final long serialVersionUID = -8818833004417489669L;
	private Map<String, Staff> staffs;

	public StaffManager(File dir, String fileName) throws IOException {
		this.staffs = new HashMap<String, Staff>();
		File file = new File(dir, fileName);
		if (file.exists()) {
			this.load(file.getPath());
		} else {
			file.createNewFile(); 			
		}
	}
	
	/**
	 * Gets the Staffs managed by this StaffManager
	 * @return a map of Staff username to Staff object
	 */
	public Map<String, Staff> getStaffs() {
		return staffs;
	}
	
	/**
	 * Adds a staff to this StaffManager.
	 * @param staff Staff object
	 */
	public void add(Staff staff) {
		staffs.put(staff.getUsername(), staff);	
	}
	
	/**
	 * Returns the staff with the given username
	 * @param username the username of the Staff
	 * @return staff with the given username
	 */
	@Override
	public Staff get(String username) {
		return staffs.get(username);
	}
	
	/**
	 * Matches the user's password to the given password
	 * @param username username of the Staff
	 * @param password password of the Staff
	 * @return true iff the given password matches the user's passowrd
	 */
	public boolean matchPassword(String username, String password) {
		if (staffs.get(username).getPassword().equals(password)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Saves the Staff objects to file outputStream.
	 * @param outputStream the output stream to 
	 * write the Staff data to file.
	 */
	@Override
	public void save(FileOutputStream outputStream) {
        try {
        	for (Staff staff : staffs.values()) {
        		outputStream.write((staff.getUsername()+ "," 
        	+ staff.getPassword() + "\n").getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	/**
	 * Loads the map of Staffs from the file at path filePath.
	 * @param filePath the file path of the data file.
	 * @throws FileNotFoundException
	 */
	@Override
	public void load(String filePath) throws FileNotFoundException {
		Scanner scanner = new Scanner(new FileInputStream(filePath));
		String [] record;
        while(scanner.hasNextLine()) {
        	record = scanner.nextLine().split(",");
        	String username = record[0];
        	String password = record[1];
        	Staff staff = new Staff(username, password);
        	staffs.put(username, staff);
        }
        scanner.close();
	}
}
