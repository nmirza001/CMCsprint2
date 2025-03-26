package cmc.backend.controller;

import java.util.List;

import cmc.backend.entities.University;

/**
 * The UniversityController class handles all operations related to universities.
 * It provides methods to add, update, delete, and retrieve university information.
 */
public class UniversityController {
    
    private DBController dbController;
    
    /**
     * Constructs a new UniversityController with the default DBController.
     */
    public UniversityController() {
        this.dbController = new DBController();
    }
    
    /**
     * Constructs a new UniversityController with a provided DBController.
     * 
     * @param dbController the database controller to use
     */
    public UniversityController(DBController dbController) {
        this.dbController = dbController;
    }
    
    /**
     * Retrieves all universities from the database.
     * 
     * @return a list of all universities
     */
    public List<University> getAllUniversities() {
        return dbController.getAllUniversities();
    }
    
    /**
     * Finds a university by name.
     * 
     * @param name the name of the university to find
     * @return the university, or null if not found
     */
    public University findUniversityByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        
        String searchName = name.toUpperCase();
        List<University> universities = dbController.getAllUniversities();
        
        for (University university : universities) {
            if (university.getName().equals(searchName)) {
                return university;
            }
        }
        
        return null;
    }
    
    /**
     * Adds a new university to the database.
     * 
     * @param university the university to add
     * @return true if the university was successfully added
     * @throws IllegalArgumentException if university is null
     */
    public boolean addUniversity(University university) {
        if (university == null) {
            throw new IllegalArgumentException("University cannot be null");
        }
        
        // Check if a university with this name already exists
        if (findUniversityByName(university.getName()) != null) {
            return false; // University with this name already exists
        }
        
        return dbController.addUniversity(university);
    }
    
    /**
     * Updates an existing university in the database.
     * 
     * @param university the university with updated information
     * @return true if the university was successfully updated
     * @throws IllegalArgumentException if university is null
     */
    public boolean updateUniversity(University university) {
        if (university == null) {
            throw new IllegalArgumentException("University cannot be null");
        }
        
        // Check if the university exists
        if (findUniversityByName(university.getName()) == null) {
            return false; // University doesn't exist
        }
        
        return dbController.updateUniversity(university);
    }
    
    /**
     * Removes a university from the database.
     * 
     * @param universityName the name of the university to remove
     * @return true if the university was successfully removed
     * @throws IllegalArgumentException if universityName is null or empty
     */
    public boolean removeUniversity(String universityName) {
        if (universityName == null || universityName.trim().isEmpty()) {
            throw new IllegalArgumentException("University name cannot be null or empty");
        }
        
        University university = findUniversityByName(universityName);
        if (university == null) {
            return false; // University doesn't exist
        }
        
        return dbController.removeUniversity(university);
    }
    
    /**
     * Gets a list of all emphases available in the system.
     * 
     * @return a list of all emphases
     */
    public List<String> getAllEmphases() {
        return dbController.getAllEmphases();
    }
    
    /**
     * Gets detailed information about a university.
     * 
     * @param universityName the name of the university
     * @return a string containing detailed university information
     */
    public String getUniversityDetails(String universityName) {
        University university = findUniversityByName(universityName);
        
        if (university == null) {
            return universityName + " is not found in the database.";
        }
        
        StringBuilder details = new StringBuilder();
        details.append("University Name: ").append(university.getName()).append("\n");
        details.append("State: ").append(university.getState()).append("\n");
        details.append("Location: ").append(university.getLocation()).append("\n");
        details.append("Control: ").append(university.getControl()).append("\n");
        details.append("Number of Students: ").append(university.getNumStudents()).append("\n");
        details.append("Percent Female: ").append(university.getPercentFemale()).append("%\n");
        details.append("SAT Verbal: ").append(university.getSatVerbal()).append("\n");
        details.append("SAT Math: ").append(university.getSatMath()).append("\n");
        details.append("Expenses: $").append(university.getExpenses()).append("\n");
        details.append("Percent Financial Aid: ").append(university.getPercentFinancialAid()).append("%\n");
        details.append("Number of Applicants: ").append(university.getNumApplicants()).append("\n");
        details.append("Percent Admitted: ").append(university.getPercentAdmitted()).append("%\n");
        details.append("Percent Enrolled: ").append(university.getPercentEnrolled()).append("%\n");
        details.append("Academic Scale (1-5): ").append(university.getScaleAcademics()).append("\n");
        details.append("Social Scale (1-5): ").append(university.getScaleSocial()).append("\n");
        details.append("Quality of Life Scale (1-5): ").append(university.getScaleQualityOfLife()).append("\n");
        
        details.append("Emphases: ");
        List<String> emphases = university.getEmphases();
        if (emphases == null || emphases.isEmpty()) {
            details.append("None");
        } else {
            details.append(String.join(", ", emphases));
        }
        
        return details.toString();
    }
}