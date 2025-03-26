package cmc.backend.entities;

import java.util.Date;

/**
 * Represents a saved school with a timestamp for when it was saved.
 * This class creates the relationship between a user and the universities they save.
 */
public class SavedSchool {
    
    private University university;
    private Date savedDate;
    
    /**
     * Constructs a new SavedSchool with the given university and the current date/time.
     * 
     * @param university the university to save
     */
    public SavedSchool(University university) {
        this.university = university;
        this.savedDate = new Date(); // Current date/time
    }
    
    /**
     * Constructs a new SavedSchool with the given university and date.
     * 
     * @param university the university to save
     * @param savedDate the date when the university was saved
     */
    public SavedSchool(University university, Date savedDate) {
        this.university = university;
        this.savedDate = savedDate;
    }
    
    /**
     * Gets the university associated with this saved school.
     * 
     * @return the university
     */
    public University getUniversity() {
        return university;
    }
    
    /**
     * Gets the date when this school was saved.
     * 
     * @return the saved date
     */
    public Date getSavedDate() {
        return savedDate;
    }
    
    /**
     * Gets the name of the university.
     * 
     * @return the university name
     */
    public String getUniversityName() {
        return university.getName();
    }
    
    /**
     * Gets the location of the university.
     * 
     * @return the university location
     */
    public String getLocation() {
        return university.getLocation();
    }
}