package cmc.backend.controller;

import java.util.ArrayList;
import java.util.List;

import cmc.CMCException;
import cmc.backend.account.Account;
import cmc.backend.account.Admin;
import cmc.backend.account.User;

/**
 * The AccountController class handles all operations related to user accounts.
 * It serves as a bridge between the user interface and the database controller.
 */
public class AccountController {
    
    private DBController dbController;
    
    /**
     * Constructs a new AccountController with the default DBController.
     */
    public AccountController() {
        this.dbController = new DBController();
    }
    
    /**
     * Constructs a new AccountController with a provided DBController.
     * 
     * @param dbController the database controller to use
     */
    public AccountController(DBController dbController) {
        this.dbController = dbController;
    }
    
    /**
     * Authenticates a user with username and password.
     * 
     * @param username the username
     * @param password the password
     * @return the authenticated Account, or null if authentication fails
     * @throws CMCException if there is a database error
     */
    public Account login(String username, String password) throws CMCException {
        String[] userData = dbController.getUserData(username);
        
        if (userData == null) {
            return null; // User not found
        }
        
        // Check if user is active
        String status = userData[5];
        if (status.length() != 1 || status.charAt(0) != 'Y') {
            return null; // User is not active
        }
        
        // Create the appropriate account type
        Account account;
        char type = userData[4].charAt(0);
        
        if (type == 'a') {
            account = new Admin(userData[2], userData[3], userData[0], userData[1]);
        } else {
            account = new User(userData[2], userData[3], userData[0], userData[1]);
        }
        
        // Authenticate with password
        if (!account.authenticate(password)) {
            return null; // Password doesn't match
        }
        
        return account;
    }
    
    /**
     * Adds a new user to the system.
     * 
     * @param username the username
     * @param password the password
     * @param firstName the first name
     * @param lastName the last name
     * @param isAdmin true if this is an admin account
     * @return true if the user was successfully added
     */
    public boolean addUser(String username, String password, String firstName, 
                          String lastName, boolean isAdmin) {
        char type = isAdmin ? 'a' : 'u';
        
        try {
            return dbController.addUser(username, password, type, firstName, lastName);
        } catch (CMCException e) {
            return false;
        }
    }
    
    /**
     * Removes a user from the system.
     * 
     * @param username the username to remove
     * @return true if the user was successfully removed
     */
    public boolean removeUser(String username) {
        try {
            return dbController.removeUser(username);
        } catch (CMCException e) {
            return false;
        }
    }
    
    /**
     * Gets all users in the system.
     * 
     * @return a list of all user accounts
     */
    public List<Account> getAllUsers() {
        List<String[]> userDataList = dbController.getAllUserData();
        List<Account> accounts = new ArrayList<>();
        
        for (String[] userData : userDataList) {
            char type = userData[4].charAt(0);
            boolean isActive = userData[5].charAt(0) == 'Y';
            
            Account account;
            if (type == 'a') {
                account = new Admin(userData[2], userData[3], userData[0], userData[1]);
            } else {
                account = new User(userData[2], userData[3], userData[0], userData[1]);
            }
            
            // Set active status
            account.setActive(isActive);
            accounts.add(account);
        }
        
        return accounts;
    }
    
    /**
     * Gets a specific user from the system.
     * 
     * @param username the username to look up
     * @return the user account, or null if not found
     */
    public Account getUser(String username) {
        String[] userData = dbController.getUserData(username);
        
        if (userData == null) {
            return null;
        }
        
        Account account;
        char type = userData[4].charAt(0);
        boolean isActive = userData[5].charAt(0) == 'Y';
        
        if (type == 'a') {
            account = new Admin(userData[2], userData[3], userData[0], userData[1]);
        } else {
            account = new User(userData[2], userData[3], userData[0], userData[1]);
        }
        
        account.setActive(isActive);
        return account;
    }
    
    /**
     * Updates a user's profile information.
     * 
     * @param username the username of the user to update
     * @param firstName the new first name (or null to keep current)
     * @param lastName the new last name (or null to keep current)
     * @param password the new password (or null to keep current)
     * @return true if the update was successful
     */
    public boolean updateUser(String username, String firstName, String lastName, String password) {
        String[] userData = dbController.getUserData(username);
        
        if (userData == null) {
            return false; // User not found
        }
        
        // Apply updates only to fields that were provided
        String updatedFirstName = (firstName != null) ? firstName : userData[0];
        String updatedLastName = (lastName != null) ? lastName : userData[1];
        String updatedPassword = (password != null) ? password : userData[3];
        
        try {
            return dbController.updateUser(username, updatedFirstName, updatedLastName, 
                    updatedPassword, userData[4].charAt(0), userData[5].charAt(0));
        } catch (CMCException e) {
            return false;
        }
    }
    
    /**
     * Deactivates a user account.
     * 
     * @param username the username to deactivate
     * @return true if the deactivation was successful
     */
    public boolean deactivateUser(String username) {
        try {
            return dbController.deactivateUser(username);
        } catch (CMCException e) {
            return false;
        }
    }
    
    /**
     * Activates a user account.
     * 
     * @param username the username to activate
     * @return true if the activation was successful
     */
    public boolean activateUser(String username) {
        String[] userData = dbController.getUserData(username);
        
        if (userData == null) {
            return false; // User not found
        }
        
        try {
            return dbController.updateUser(username, userData[0], userData[1], 
                    userData[3], userData[4].charAt(0), 'Y');
        } catch (CMCException e) {
            return false;
        }
    }
}