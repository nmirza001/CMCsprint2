package controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	
	/**
	 * Used for when something shouldn't happen, i.e. a DB error when it should always work.
	 */
	private static final String SHOULDNT_HAPPEN = "If you're seeing this DatabaseController has a bug.";
	
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
	 * Gets all universities' emphases.
	 * Note that the caller should not assume that all universities
	 * are in the dictionary and if a university has no emphases
	 * it will not be in the dictionary and hence 'get' would
	 * return {@code null}.
	 * @return A map of university names to a list of emphases.
	 * @author Roman Lefler
	 * @version Mar 14, 2025
	 */
	private Map<String, List<String>> getUniversitiesEmphases() {
		String[][] emphases = database.university_getNamesWithEmphases();
		Map<String, List<String>> dict = new HashMap<String, List<String>>();
		for(String[] kv : emphases) {
			
			List<String> list = dict.get(kv[0]);
			if(list == null) {
				list = new ArrayList<>(3);
				dict.put(kv[0], list);
			}
			list.add(kv[1]);
		}
		return dict;
	}
	
	/**
	 * Gets the list of all the universities in the DB
	 * @return A list of universities
	 * @author Roman Lefler
	 * @version Mar 13, 2025
	 */
	public List<University> getAllSchools() {
		String[][] dbUniversityList = this.database.university_getUniversities();

		Map<String, List<String>> emphases = getUniversitiesEmphases();
		ArrayList<University> result = new ArrayList<>();
		for (String[] k : dbUniversityList) {
			University u = new University(k[0]);
			u.setState(k[1]);
			u.setLocation(k[2]);
			u.setControl(k[3]);
			u.setNumStudents(Integer.parseInt(k[4]));
			u.setPercentFemale(Double.parseDouble(k[5]));
			u.setSatVerbal(Double.parseDouble(k[6]));
			u.setSatMath(Double.parseDouble(k[7]));
			u.setExpenses(Double.parseDouble(k[8]));
			u.setPercentFinancialAid(Double.parseDouble(k[9]));
			u.setNumApplicants(Integer.parseInt(k[10]));
			u.setPercentAdmitted(Double.parseDouble(k[11]));
			u.setPercentEnrolled(Double.parseDouble(k[12]));
			u.setScaleAcademics(Integer.parseInt(k[13]));
			u.setScaleSocial(Integer.parseInt(k[14]));
			u.setScaleQualityOfLife(Integer.parseInt(k[15]));
			List<String> schoolEmphases = emphases.get(k[0]);
			if(schoolEmphases != null) {
				for(String e : schoolEmphases) u.addEmphasis(e);
			}
			
			result.add(u);
		}

		return result;
	}
	
	/**
	 * Gets a list of all possible emphases.
	 * @return A list of all emphases.
	 * @author Roman Lefler
	 * @version Mar 14, 2025
	 */
	public List<String> getAllEmphases() {
		// It's not clear to me why this returns a 2D array
		String[][] arr = database.university_getEmphases();
		List<String> list = new ArrayList<String>();
		for(int i = 0; i < arr.length; i++) {
			for(int j = 0; j < arr[i].length; j++) list.add(arr[i][j]);
		}
		
		return list;
	}
	
	/**
	 * Removes an emphasis from a university.
	 * 
	 * This method doesn't take a University object so that
	 * it doesn't get confused with the list maintained in
	 * the University object, which can be out of sync with the
	 * database.
	 * @param uniName University name to remove emphasis from.
	 * @param emphasis The emphasis to remove
	 * @return {@code true} is succeeded.
	 * @author Roman Lefler
	 * @version Mar 16, 2025
	 */
	private boolean removeEmphasis(String uniName, String emphasis) {
		int result = database.university_removeUniversityEmphasis(uniName, emphasis);
		return result > 0;
	}
	
	/**
	 * Adds an emphasis to a university.
	 * 
	 * This method doesn't take a University object so that
	 * it doesn't get confused with the list maintained in
	 * the University object, which can be out of sync with the
	 * database.
	 * @param uniName University name to add emphasis to.
	 * @param emphasis The emphasis to add
	 * @return {@code true} if succeeded.
	 * @author Roman Lefler
	 * @version Mar 16, 2025
	 */
	private boolean addEmphasis(String uniName, String emphasis) {
		int result = database.university_addUniversityEmphasis(uniName, emphasis);
		return result > 0;
	}
	
	/**
	 * Adds a new university to the database.
	 * @param u University with attributes to add.
	 * @return {@code true} if the operation succeeded.
	 * @see #editUniversity(University)
	 * @see #removeUniversity(University)
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
		
		if(result != 1) return false;
		
		String uniName = u.getName();
		for(String e : u.getEmphases()) {
			if(!addEmphasis(uniName, e)) throw new IllegalStateException(SHOULDNT_HAPPEN);
		}
		
		return true;
	}
	
	/**
	 * Removes a university from the database.
	 * @param u Removes a university by name (only the name is used)
	 * @return {@code true} if the operation succeeded.
	 * @throws IllegalArgumentException if u is {@code null}.
	 * @see #addNewUniversity(University)
	 * @see #editUniversity(University)
	 * @author Roman Lefler
	 * @version Mar 14, 2025
	 */
	public boolean removeUniversity(University u) {
		
		if(u == null) throw new IllegalArgumentException("u cannot be null.");
		
		String uniName = u.getName();
		// Since u's emphasis list can be out of sync with the database's
		// emphases, we must rely on database's reported emphases
		Map<String, List<String>> emphasesDict = getUniversitiesEmphases();
		List<String> emphases = emphasesDict.get(uniName);
		if(emphases != null) {
			for(String k : u.getEmphases()) {
				
				if(!removeEmphasis(uniName, k)) throw new IllegalStateException(SHOULDNT_HAPPEN);
			}
		}
		
		int result = database.university_deleteUniversity(u.getName());
		return result > 0;
	}
	
	/**
	 * Edits a university. The university must already be in
	 * the database.
	 * @param u University information
	 * @return {@code true} if successful.
	 * @see #addNewUniversity(University)
	 * @see #removeUniversity(University)
	 * @author Roman Lefler
	 * @version Mar 16, 2025
	 */
	public boolean editUniversity(University u) {
		
		// # Emphases:
		// Anything that is present in the database but not u
		// was removed.
		// Anything that is present in u but not in the database
		// was added.
		String uniName = u.getName();
		University old = University.find(getAllSchools(), uniName);
		List<String> oldE = old.getEmphases();
		List<String> newE = u.getEmphases();
		// Create a total set of all emphases
		Set<String> both = new HashSet<>(oldE);
		both.addAll(newE);
		// Iterate through the total set of all emphases
		for(String em : both) {
			if(oldE.contains(em)) {
				// In old but not new; Newly removed
				if(!newE.contains(em)) {
					if(!removeEmphasis(uniName, em)) throw new IllegalStateException(SHOULDNT_HAPPEN);
				}
			}
			// Not in old (and therefore must be in new); Newly added
			else {
				if(!addEmphasis(uniName, em)) throw new IllegalStateException(SHOULDNT_HAPPEN);
			}
		}
		
		// Here's the easy part
		int result = database.university_editUniversity(
				u.getName(), u.getState(), u.getLocation(), u.getControl(),
				u.getNumStudents(), u.getPercentFemale(), u.getSatVerbal(),
				u.getSatMath(), u.getExpenses(), u.getPercentFinancialAid(),
				u.getNumApplicants(), u.getPercentAdmitted(),
				u.getPercentEnrolled(), u.getScaleAcademics(),
				u.getScaleSocial(), u.getScaleQualityOfLife());
		
		return result == 1;
	}
	
}