/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.courseservice;

import com.courseservice.business.CourseServiceImpl;
import com.courseservice.exceptions.NonexistentCourseException;
import com.courseservice.exceptions.NonexistentSectionException;
import com.courseservice.models.Course;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import models.Section;

/**
 *
 * @author bjw
 */
@WebService(serviceName = "CourseService")
public class CourseService {
    private CourseServiceImpl impl;
    
    public CourseService() {
        this.impl = new CourseServiceImpl();
    }
    
    @WebMethod(operationName = "getCourse")
    public Course getCourse (
            @WebParam(name = "courseID") String courseID) 
            throws NonexistentCourseException {
        Course course;
        try {
            course = this.impl.getCourse(courseID); 
        } catch (NonexistentCourseException e) {
            throw e;
        }
        return course;
    }
    
    @WebMethod(operationName = "getAllCourses")
    public List<Course> getAllCourses() {
        return this.impl.getAllCourses();
    }
    
    @WebMethod(operationName = "getActiveCourses")
    public List<Course> getActiveCourses() {
        return this.impl.getActiveCourses();
    }
    
    @WebMethod(operationName = "getInactiveCourses")
    public List<Course> getInactiveCourses() {
        return this.impl.getInactiveCourses();
    }
    
    @WebMethod(operationName = "getSection")
    public Section getSection(
            @WebParam(name = "courseCode") String courseCode, 
            @WebParam(name = "sectionCode") String sectionCode, 
            @WebParam(name = "year") Integer year) 
            throws NonexistentSectionException {
        Section section;
        try {
            section = this.impl.getSection(courseCode, sectionCode, year);
        } catch (NonexistentSectionException e) {
            throw e;
        }
        return section;
    }
    
    @WebMethod(operationName = "getAllSectionsOfCourse")
    public List<Section> getAllSectionsOfCourse(
            @WebParam(name = "courseCode") String courseCode) {
        return this.impl.getAllSectionsOfCourse(courseCode);
    }
    
    @WebMethod(operationName = "getAllCurrentSectionsOfCourse")
    public List<Section> getAllCurrentSectionsOfCourse(
            @WebParam(name = "courseCode") String courseCode, 
            @WebParam(name = "year") Integer year) {
        return this.impl.getAllCurrentSectionsOfCourse(courseCode, year);
    }
    
    @WebMethod(operationName = "getCourseSkillCodes")
    public List<Integer> getCourseSkillCodes(
            @WebParam(name = "courseCode") String courseCode) {
        return this.impl.getCourseSkillCodes(courseCode);
    }
    
//    @WebMethod(operationName = "getCourseSkills")
//    public List<Skill> getCourseSkills(
//            @WebParam(name = "courseCode") String courseCode) {
//        // Not yet implemented
//    }
    
}
