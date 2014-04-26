package com.courseservice.exceptions;

/**
 *
 * @author bjw
 */
public class NonexistentSectionException extends Exception {
    private String courseCode;
    private String sectionCode;
    private Integer year;
    
    public NonexistentSectionException(String courseCode, String sectionCode, Integer year) {
        this.courseCode = courseCode;
        this.sectionCode = sectionCode;
        this.year = year;
    }
    
    public String getCourseCode() {
        return this.courseCode;
    }
    
    public String getSectionCode() {
        return this.sectionCode;
    }
    
    public Integer getYear() {
        return this.year;
    }
    
    @Override
    public String getMessage() {
        return "Section does not exist: " + this.courseCode + ", " +
                                            this.sectionCode + ", " + 
                                            this.year;
    }
}
