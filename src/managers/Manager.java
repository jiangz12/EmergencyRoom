package managers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * A manager that handles object of Type T.
 * @author Huiyan Xu
 * @param <T> the type of object under management
 */

public interface Manager<T>{
	
	/**
	 * Adds T to collection of T.
	 */
	public void add(T object);
	
	/**
	 * Gets T from collection of T.
	 */
	public T get(String name);
	
	/**
	 * Saves collection of T to file.
	 */
	public void save(FileOutputStream outputStream);
	
	/**
	 * Loads file into collection of T.
	 */
	public void load(String filePath) throws FileNotFoundException;

}
