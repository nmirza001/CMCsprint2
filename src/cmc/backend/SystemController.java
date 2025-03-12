package cmc.backend;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cmc.CMCException;

public class SystemController {
	private DatabaseController myDBController;
	
	// Construct a SystemController using the basic (no parameter)
	// DatabaseController as the underlying database access.
	public SystemController() {
		this.myDBController = new DatabaseController();
	}
	
	/**
	 * Verify whether the username and password provided match a user in the
	 * database.  Return a Boolean indicating yes or no.
	 * 
	 * TODO: how could we distinguish a DB error from a failed login?
	 * 
	 * @param username the username to check
	 * @param password the password to check for matching the username
	 * @return the matching User object if the username and password match
	 * a database entry, or null otherwise
	 * @throws CMCException
	 */
	public User login(String username, String password) throws CMCException {
		String[] userData = this.myDBController.getUser(username);
		if (userData == null)
			return null;
		
		User theUser = new User(userData[2], userData[3], userData[4].charAt(0), userData[0],
				userData[1]);
		
		String status = userData[5];
		if(status.length() != 1) throw new CMCException("User status in DB malformed.");
		theUser.setActivated(status.charAt(0));
		
		if (theUser.activated != 'Y' || !theUser.password.equals(password)) {
			return null;
		}
		else {
			return theUser;
		}
	}

	// this ADMIN ONLY method returns the list of all the users (and their data)
	// TODO: shouldn't this return a List of User objects?
	public List<String[]> getAllUsers() {
		List<String[]> usersList = this.myDBController.getAllUsers();
		return usersList;
	}
	
	// this ADMIN ONLY method attempts to add a user to the database with the
	// provided details
	public boolean addUser(String username, String password,
			String firstName, String lastName, boolean isAdmin) {
		char type = (isAdmin ? 'a' : 'u');
		try {
			return this.myDBController.addUser(username, password, type, firstName, lastName);
		} catch (CMCException e) {
			// TODO: should we let the calling class report the error more
			//       clearly by passing it on?
			return false;
		}
	}
	
	// this ADMIN ONLY method attempts to remove a user from the database
	// based on the provided username
	public boolean removeUser(String username) {
		try {
			return this.myDBController.removeUser(username);
		} catch (CMCException e) {
			// TODO: should we let the calling class report the error more
			//       clearly by passing it on?
			return false;
		}
	}
	
	// this REGULAR USER ONLY method searches for schools in the database
	// based on provided criteria (just state for now)
	public List<String[]> search(String state) {
		List<String[]> schoolList = this.myDBController.getAllSchools();
		
		List<String[]> filteredList = new ArrayList<String[]>();
		for (int i = 0; i < schoolList.size(); i++) {
			String[] school = schoolList.get(i);
			if (state.equals("") || school[1].equals(state) || school[1] == "")
				filteredList.add(school);
		}
		
		return filteredList;
	}
	
	// this REGULAR USER ONLY method attempts to add the provided school
	// to the list of saved schools for the provided username
	public boolean saveSchool(String user, String school) throws CMCException {
		return this.myDBController.saveSchool(user, school);
	}
	
	// this REGULAR USER ONLY method attempts to retrieve the list of saved
	// schools for the provided username
	public List<String> getSavedSchools(String user) {
		Map<String, List<String>> usersToSavedSchools = this.myDBController.getUserSavedSchoolMap();
		return usersToSavedSchools.get(user);
	}
	
	/**
	 * Adds a new university to the database by calling the database controller.
	 * @param uni University as string array
	 * @return {@code true} if the operation succeeded.
	 * @throws IllegalArgumentException if uni is null
	 * @author Roman Lefler
	 * @version Mar 12, 2025
	 */
	// TODO: Should be University object
	public boolean addNewUniversity(String[] uni) {
		if(uni == null) throw new IllegalArgumentException();
		
		int nNumStu = Integer.parseInt(uni[4]);
		double fPercentFemale = Double.parseDouble(uni[5]);
		double fSatVerbal = Double.parseDouble(uni[6]);
		double fSatMath = Double.parseDouble(uni[7]);
		double fExpenses = Double.parseDouble(uni[8]);
		double fPercFinAid = Double.parseDouble(uni[9]);
		int nNumAppl = Integer.parseInt(uni[10]);
		double fPercAdmitted = Double.parseDouble(uni[11]);
		double fPercEnrolled = Double.parseDouble(uni[12]);
		int nAcad = Integer.parseInt(uni[13]);
		int nSocial = Integer.parseInt(uni[14]);
		int nQuality = Integer.parseInt(uni[15]);
		return myDBController.addNewUniversity(uni[0], uni[1], uni[2], uni[3],
				nNumStu, fPercentFemale, fSatVerbal, fSatMath, fExpenses,
				fPercFinAid, nNumAppl, fPercAdmitted, fPercEnrolled,
				nAcad, nSocial, nQuality);
	}

}
