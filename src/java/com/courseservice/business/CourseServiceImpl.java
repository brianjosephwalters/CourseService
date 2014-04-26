/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.courseservice.business;

import com.courseservice.db.CourseDB;
import com.courseservice.exceptions.NonexistentCourseException;
import com.courseservice.exceptions.NonexistentSectionException;
import com.courseservice.models.Course;
import com.courseservice.queries.CourseQueries;
import com.courseservice.queries.CourseSkillQueries;
import java.sql.SQLException;
import java.util.List;
import models.Section;
import models.queries.SectionQueries;

/**
 *
 * @author bjw
 */
public class CourseServiceImpl {
    
    public Course getCourse(String courseCode) 
            throws NonexistentCourseException {
        CourseQueries queries = new CourseQueries(CourseDB.dbConnection());
        Course course = null;
        try {
            course = queries.getCourse(courseCode);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (course == null) {
            throw new NonexistentCourseException(courseCode);
        }
        return course;
    }
    
    public List<Course> getAllCourses() {
        CourseQueries queries = new CourseQueries(CourseDB.dbConnection());
        List<Course> list = null;
        try {
            list = queries.getAllCourses();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<Course>  getActiveCourses() {
        CourseQueries queries = new CourseQueries(CourseDB.dbConnection());
        List<Course> list = null;
        try {
            list = queries.getActiveCourses();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list; 
    }
    
    public List<Course> getInactiveCourses() {
        CourseQueries queries = new CourseQueries(CourseDB.dbConnection());
        List<Course> list = null;
        try {
            list = queries.getInactiveCourses();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list; 
    }
    
    public Section getSection(String courseCode, String sectionCode, Integer year) 
            throws NonexistentSectionException {
        SectionQueries queries = new SectionQueries(CourseDB.dbConnection());
        Section section = null;
        try {
            section = queries.getSection(courseCode, sectionCode, year);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (section == null) {
            throw new NonexistentSectionException(courseCode, sectionCode, year);
        }
        return section;
    }
    
    public List<Section> getAllSectionsOfCourse(String courseCode) {
        SectionQueries queries = new SectionQueries(CourseDB.dbConnection());
        List<Section> list = null;
        try {
            list = queries.getAllSectionsOfCourse(courseCode);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list; 
    }
    
    public List<Section> getAllCurrentSectionsOfCourse(String courseCode, Integer year) {
        SectionQueries queries = new SectionQueries(CourseDB.dbConnection());
        List<Section> list = null;
        try {
            list = queries.getAllCurrentSectionsOfCourse(courseCode, year);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list; 
    }  
    
    public List<Integer> getCourseSkillCodes(String courseCode) {
        CourseSkillQueries queries = new CourseSkillQueries(CourseDB.dbConnection());
        List<Integer> list = null;
        try {
            list = queries.getCourseSkillCodes(courseCode);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
