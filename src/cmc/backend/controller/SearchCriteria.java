package cmc.backend.controller;

import java.util.ArrayList;
import java.util.List;

/**
 * The SearchCriteria class represents the search parameters for university searches.
 * It provides getters and setters for all possible search criteria.
 */
public class SearchCriteria {
    
    // String criteria
    private String name;
    private String state;
    private String location;
    private String control;
    
    // Numeric range criteria (min/max)
    private int minStudents;
    private int maxStudents;
    private double minPercentFemale;
    private double maxPercentFemale;
    private double minSatVerbal;
    private double maxSatVerbal;
    private double minSatMath;
    private double maxSatMath;
    private double minExpenses;
    private double maxExpenses;
    private double minPercentFinancialAid;
    private double maxPercentFinancialAid;
    private int minApplicants;
    private int maxApplicants;
    private double minPercentAdmitted;
    private double maxPercentAdmitted;
    private double minPercentEnrolled;
    private double maxPercentEnrolled;
    private int minAcademicScale;
    private int maxAcademicScale;
    private int minSocialScale;
    private int maxSocialScale;
    private int minQualityOfLifeScale;
    private int maxQualityOfLifeScale;
    
    // List criteria
    private List<String> emphases;
    
    /**
     * Default constructor.
     */
    public SearchCriteria() {
        // Initialize empty emphases list
        this.emphases = new ArrayList<>();
        
        // Default all numeric minimums to -1 (no limit)
        this.minStudents = -1;
        this.minPercentFemale = -1;
        this.minSatVerbal = -1;
        this.minSatMath = -1;
        this.minExpenses = -1;
        this.minPercentFinancialAid = -1;
        this.minApplicants = -1;
        this.minPercentAdmitted = -1;
        this.minPercentEnrolled = -1;
        this.minAcademicScale = -1;
        this.minSocialScale = -1;
        this.minQualityOfLifeScale = -1;
        
        // Default all numeric maximums to -1 (no limit)
        this.maxStudents = -1;
        this.maxPercentFemale = -1;
        this.maxSatVerbal = -1;
        this.maxSatMath = -1;
        this.maxExpenses = -1;
        this.maxPercentFinancialAid = -1;
        this.maxApplicants = -1;
        this.maxPercentAdmitted = -1;
        this.maxPercentEnrolled = -1;
        this.maxAcademicScale = -1;
        this.maxSocialScale = -1;
        this.maxQualityOfLifeScale = -1;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the control
     */
    public String getControl() {
        return control;
    }

    /**
     * @param control the control to set
     */
    public void setControl(String control) {
        this.control = control;
    }

    /**
     * @return the minStudents
     */
    public int getMinStudents() {
        return minStudents;
    }

    /**
     * @param minStudents the minStudents to set
     */
    public void setMinStudents(int minStudents) {
        this.minStudents = minStudents;
    }

    /**
     * @return the maxStudents
     */
    public int getMaxStudents() {
        return maxStudents;
    }

    /**
     * @param maxStudents the maxStudents to set
     */
    public void setMaxStudents(int maxStudents) {
        this.maxStudents = maxStudents;
    }

    /**
     * @return the minPercentFemale
     */
    public double getMinPercentFemale() {
        return minPercentFemale;
    }

    /**
     * @param minPercentFemale the minPercentFemale to set
     */
    public void setMinPercentFemale(double minPercentFemale) {
        this.minPercentFemale = minPercentFemale;
    }

    /**
     * @return the maxPercentFemale
     */
    public double getMaxPercentFemale() {
        return maxPercentFemale;
    }

    /**
     * @param maxPercentFemale the maxPercentFemale to set
     */
    public void setMaxPercentFemale(double maxPercentFemale) {
        this.maxPercentFemale = maxPercentFemale;
    }

    /**
     * @return the minSatVerbal
     */
    public double getMinSatVerbal() {
        return minSatVerbal;
    }

    /**
     * @param minSatVerbal the minSatVerbal to set
     */
    public void setMinSatVerbal(double minSatVerbal) {
        this.minSatVerbal = minSatVerbal;
    }

    /**
     * @return the maxSatVerbal
     */
    public double getMaxSatVerbal() {
        return maxSatVerbal;
    }

    /**
     * @param maxSatVerbal the maxSatVerbal to set
     */
    public void setMaxSatVerbal(double maxSatVerbal) {
        this.maxSatVerbal = maxSatVerbal;
    }

    /**
     * @return the minSatMath
     */
    public double getMinSatMath() {
        return minSatMath;
    }

    /**
     * @param minSatMath the minSatMath to set
     */
    public void setMinSatMath(double minSatMath) {
        this.minSatMath = minSatMath;
    }

    /**
     * @return the maxSatMath
     */
    public double getMaxSatMath() {
        return maxSatMath;
    }

    /**
     * @param maxSatMath the maxSatMath to set
     */
    public void setMaxSatMath(double maxSatMath) {
        this.maxSatMath = maxSatMath;
    }

    /**
     * @return the minExpenses
     */
    public double getMinExpenses() {
        return minExpenses;
    }

    /**
     * @param minExpenses the minExpenses to set
     */
    public void setMinExpenses(double minExpenses) {
        this.minExpenses = minExpenses;
    }

    /**
     * @return the maxExpenses
     */
    public double getMaxExpenses() {
        return maxExpenses;
    }

    /**
     * @param maxExpenses the maxExpenses to set
     */
    public void setMaxExpenses(double maxExpenses) {
        this.maxExpenses = maxExpenses;
    }

    /**
     * @return the minPercentFinancialAid
     */
    public double getMinPercentFinancialAid() {
        return minPercentFinancialAid;
    }

    /**
     * @param minPercentFinancialAid the minPercentFinancialAid to set
     */
    public void setMinPercentFinancialAid(double minPercentFinancialAid) {
        this.minPercentFinancialAid = minPercentFinancialAid;
    }

    /**
     * @return the maxPercentFinancialAid
     */
    public double getMaxPercentFinancialAid() {
        return maxPercentFinancialAid;
    }

    /**
     * @param maxPercentFinancialAid the maxPercentFinancialAid to set
     */
    public void setMaxPercentFinancialAid(double maxPercentFinancialAid) {
        this.maxPercentFinancialAid = maxPercentFinancialAid;
    }

    /**
     * @return the minApplicants
     */
    public int getMinApplicants() {
        return minApplicants;
    }

    /**
     * @param minApplicants the minApplicants to set
     */
    public void setMinApplicants(int minApplicants) {
        this.minApplicants = minApplicants;
    }

    /**
     * @return the maxApplicants
     */
    public int getMaxApplicants() {
        return maxApplicants;
    }

    /**
     * @param maxApplicants the maxApplicants to set
     */
    public void setMaxApplicants(int maxApplicants) {
        this.maxApplicants = maxApplicants;
    }

    /**
     * @return the minPercentAdmitted
     */
    public double getMinPercentAdmitted() {
        return minPercentAdmitted;
    }

    /**
     * @param minPercentAdmitted the minPercentAdmitted to set
     */
    public void setMinPercentAdmitted(double minPercentAdmitted) {
        this.minPercentAdmitted = minPercentAdmitted;
    }

    /**
     * @return the maxPercentAdmitted
     */
    public double getMaxPercentAdmitted() {
        return maxPercentAdmitted;
    }

    /**
     * @param maxPercentAdmitted the maxPercentAdmitted to set
     */
    public void setMaxPercentAdmitted(double maxPercentAdmitted) {
        this.maxPercentAdmitted = maxPercentAdmitted;
    }

    /**
     * @return the minPercentEnrolled
     */
    public double getMinPercentEnrolled() {
        return minPercentEnrolled;
    }

    /**
     * @param minPercentEnrolled the minPercentEnrolled to set
     */
    public void setMinPercentEnrolled(double minPercentEnrolled) {
        this.minPercentEnrolled = minPercentEnrolled;
    }

    /**
     * @return the maxPercentEnrolled
     */
    public double getMaxPercentEnrolled() {
        return maxPercentEnrolled;
    }

    /**
     * @param maxPercentEnrolled the maxPercentEnrolled to set
     */
    public void setMaxPercentEnrolled(double maxPercentEnrolled) {
        this.maxPercentEnrolled = maxPercentEnrolled;
    }

    /**
     * @return the minAcademicScale
     */
    public int getMinAcademicScale() {
        return minAcademicScale;
    }

    /**
     * @param minAcademicScale the minAcademicScale to set
     */
    public void setMinAcademicScale(int minAcademicScale) {
        this.minAcademicScale = minAcademicScale;
    }

    /**
     * @return the maxAcademicScale
     */
    public int getMaxAcademicScale() {
        return maxAcademicScale;
    }

    /**
     * @param maxAcademicScale the maxAcademicScale to set
     */
    public void setMaxAcademicScale(int maxAcademicScale) {
        this.maxAcademicScale = maxAcademicScale;
    }

    /**
     * @return the minSocialScale
     */
    public int getMinSocialScale() {
        return minSocialScale;
    }

    /**
     * @param minSocialScale the minSocialScale to set
     * */