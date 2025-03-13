package cmc.backend;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cmc.CMCException;
import cmc.backend.entities.University;
import dblibrary.project.csci230.*;

/**
 * The DatabaseController class is the primary interaction class with the
 * database library.  It currently just calls the lower-level methods and
 * forwards the result (possibly throwing some exceptions along the way).
 * 
 * @author Sally Sparrow
 */
public class DatabaseController {
	private UniversityDBLibrary database;

	// The default constructor that connects to the underlying
	// UniversityDBLibrary object using your team's info.
	public DatabaseController() {
		this.database = new UniversityDBLibrary("dei", "Csci230$");
	}

	// add a user to the db
	// TODO: it would be nice if this could take a User object instead
	// (so "higher-abstraction" classes don't have to worry about the order
	//  of properties)
	public boolean addUser(String username, String password, char type,
			String firstName, String lastName) throws CMCException {
		int result = this.database.user_addUser(firstName, lastName, username, password, type);
		
		if (result == -1) {
			throw new CMCException("Error adding user to the DB");
		}
		else {
			return true;
		}
	}
	
	// remove a user from the db
	public boolean removeUser(String username) throws CMCException {
		
		Map<String, List<String>> schoolMap = getUserSavedSchoolMap();
		List<String> schools = schoolMap.get(username);
		if(schools != null) {
			for(String s : schools) database.user_removeSchool(username, s);
		}
		
		int result = this.database.user_deleteUser(username);
		
		if (result != 1) {
			// TODO: How can we tell the difference?
			throw new CMCException("Error removing user \"" + username +
					"\" from the DB.  Not present?  DB error?");
		}
		else {
			return true;
		}
	}
	
	// get a user; null if not in DB
	public String[] getUser(String username) {
		String[][] databaseUserStrings = this.database.user_getUsers();
		
		for (String[] singleUser : databaseUserStrings) {
			String thisUsername = singleUser[2];
			if (thisUsername.equals(username)) {
				return singleUser;
			}
		}
		
		return null;
	}
	
	// get the list of all the users in the DB
	public List<String[]> getAllUsers() {
		String[][] dbUserList = this.database.user_getUsers();
		
		ArrayList<String[]> result = new ArrayList<String[]>();
		for (String[] user : dbUserList) {
			result.add(user);
		}
		
		return result;
	}
	
	// deactivate a user in the database
	// This is messy, and it would be much cleaner to do
	// an editUser with an updated User object!
	public boolean deactivateUser(String username) throws CMCException {
		String[] theUser = getUser(username);
		if (theUser == null)
			return false;
		int result = this.database.user_editUser(theUser[2], theUser[0], theUser[1], theUser[3], theUser[4].charAt(0), 'N');
		if (result == -1) {
			throw new CMCException("Error editing user (to deactivate) in the DB");
		}
		else {
			return true;
		}
	}
	
	// get the list of all the universities in the DB
	public List<String[]> getAllSchools() {
		String[][] dbUniversityList = this.database.university_getUniversities();

		ArrayList<String[]> result = new ArrayList<String[]>();
		for (String[] school : dbUniversityList) {
			result.add(school);
		}

		return result;
	}
	
	// save a school to a particular user's list
	// TODO: It feels like we should be able to do this as part of
	//       "updating" a user in the DB.
	public boolean saveSchool(String username, String schoolName) throws CMCException {
		
		Map<String, List<String>> schools = getUserSavedSchoolMap();
		List<String> userSchools = schools.get(username);
		// userSchools will be null if there are no saved schools
		if(userSchools != null && userSchools.contains(schoolName)) return false;
		
		int result = this.database.user_saveSchool(username, schoolName);
		if (result != 1) {
			String msg = String.format("(%d) Error saving school \"%s\" to user \"%s\" in the DB.",
					result, schoolName, username);
			throw new Error(msg + " Already present?  DB error?");
		}
		else {
			return true;
		}
	}
	
	// get the mapping from users to their saved universities in the DB
	// e.g., peter -> {CSBSJU, HARVARD}
	//       juser -> {YALE, AUGSBURG, STANFORD}
	public Map<String, List<String>> getUserSavedSchoolMap() {
		String[][] dbMapping = this.database.user_getUsernamesWithSavedSchools();

		HashMap<String, List<String>> result = new HashMap<String, List<String>>();
		
		for (String[] entry : dbMapping) {
			String user = entry[0];
			String school = entry[1];
			
			if (!result.containsKey(user))
				result.put(user, new ArrayList<String>());
			
			result.get(user).add(school);
		}

		return result;
	}
	
	/**
	 * Adds a new university to the database.
	 * @return {@code true} if the operation succeeded.
	 * @author Roman Lefler
	 * @version Mar 13, 2025
	 */
	public boolean addNewUniversity(University u) {
		int result = database.university_addUniversity(
				u.getName(), u.getState(), u.getLocation(), u.getControl(),
				u.getNumStudents(), u.getPercentFemale(), u.getSatVerbal(),
				u.getSatMath(), u.getExpenses(), u.getPercentFinancialAid(),
				u.getNumApplicants(), u.getPercentAdmitted(),
				u.getPercentEnrolled(), u.getScaleAcademics(),
				u.getScaleSocial(), u.getScaleQualityOfLife());
		
		return result == 1;
	}
	
}