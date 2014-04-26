package com.courseservice.exceptions;

/**
 *
 * @author bjw
 */
public class NonexistentCourseException extends Exception {
    private String courseCode;
    
    public NonexistentCourseException(String courseCode) {
        this.courseCode = courseCode;
    }
    
    public String getCourseCode() {
        return this.courseCode;
    }
    
    @Override
    public String getMessage() {
        return "Course does not exist: " + this.courseCode;
    }
}
