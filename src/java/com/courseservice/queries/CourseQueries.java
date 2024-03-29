package com.courseservice.queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.courseservice.models.Course;

public class CourseQueries {
	// Instance Variables
	private Connection connection;
	
	// Constructors
	public CourseQueries (Connection connection) {
		this.connection = connection;
	}
	
	// Queries
        /**
         * Gets a course by its code.
         * @param courseCode
         * @return 
         */
        public Course getCourse(String courseCode) 
                throws SQLException {
            List<Course> list = null;
            PreparedStatement stmt = connection.prepareStatement(
                " SELECT * " +
                " FROM course" +
                " WHERE course_code = ?");
            stmt.setString(1, courseCode);
            list = getListOfCourses(stmt);
            if (list.size() > 0)
                return list.get(0);
            else
                return null;
        }
        
	/**
	 * All courses.
	 */
	public List<Course> getAllCourses() 
		throws SQLException {
		List<Course> list = null;
		PreparedStatement stmt = connection.prepareStatement(
			" SELECT * " +
			" FROM course");
		list = getListOfCourses(stmt);
		
		return list;
	}
        
        /**
         * All active courses.
         * @throws java.sql.SQLException
         */
        
        public List<Course> getActiveCourses()
                throws SQLException {
            List<Course> list = null;
            PreparedStatement stmt = connection.prepareStatement(
                    " SELECT * " +
                    " FROM course " +
                    " WHERE status = 'active'"
            );
            list = getListOfCourses(stmt);
            return list;
        }
        
        /**
         * All inactive courses.
         * @throws java.sql.SQLException
         */
        
        public List<Course> getInactiveCourses() 
                throws SQLException {
            List<Course> list = null;
            PreparedStatement stmt = connection.prepareStatement(
                    " SELECT * " +
                    " FROM course " +
                    " WHERE status = 'inactive'"
            );
            list = getListOfCourses(stmt);
            return list;
        }

	/**
	 * All courses currently being offered.
	 */
	public List<Course> getCoursesOfferedInYear(int year) 
			throws SQLException {
		List<Course> list = null;
		PreparedStatement stmt = connection.prepareStatement(
			" SELECT * " +
			" FROM course NATURAL JOIN section" +
			" WHERE year = ?");
		stmt.setInt(1,  year);
		list = getListOfCourses(stmt);
		
		return list;
	}
	
	/**
	 * All courses that provide a certain skill.
	 */
	public List<Course> getCoursesProvidingSkill(String skillCode) 
			throws SQLException {
		List<Course> list = null;
		PreparedStatement stmt = connection.prepareStatement(
			" SELECT * " +
			" FROM course NATURAL JOIN course_skill" +
			" WHERE skill_code = ?");
		stmt.setString(1,  skillCode);
		list = getListOfCourses(stmt);
		
		return list;
	}
	
	
	/**
	 * All courses that prepare for a certificate.
	 */
	public List<Course> getCoursesPreparingForCertificate(String certCode) 
			throws SQLException {
		List<Course> list = null;
		PreparedStatement stmt = connection.prepareStatement(
			" SELECT * " +
			" FROM course NATURAL JOIN prepares_for" +
			" WHERE certificate_code = ?");
		stmt.setString(1,  certCode);
		list = getListOfCourses(stmt);
		
		return list;
	}
	
	
	/**
	 * All courses that prepare for using a particular tool.
	 */
	public List<Course> getCoursesForTool(String toolCode) 
			throws SQLException {
		List<Course> list = null;
		PreparedStatement stmt = connection.prepareStatement(
			" SELECT * " +
			" FROM course NATURAL JOIN prepares_for NATURAL JOIN certificate" +
			" WHERE tool_code = ?");
		stmt.setString(1,  toolCode);
		list = getListOfCourses(stmt);
		
		return list;
	}
	
	/**
	 * All courses offered by a company.
	 */
	public List<Course> getCoursesOfferedByCompany(String companyCode) 
			throws SQLException {
		List<Course> list = null;
		PreparedStatement stmt = connection.prepareStatement(
			" SELECT * " +
			" FROM course NATURAL JOIN section NATURAL JOIN" +
			"      provides" +
			" WHERE company_code = ?");
		stmt.setString(1,  companyCode);
		list = getListOfCourses(stmt);
		
		return list;
	}
	
	/**
	 * All courses taken by a person.
	 */
	public List<Course> getCoursesAttended(String personCode) 
			throws SQLException {
		List<Course> list = null;
		PreparedStatement stmt = connection.prepareStatement(
			" SELECT * " +
			" FROM course NATURAL JOIN section NATURAL JOIN" +
			"      attended" +
			" WHERE person_code = ?");
		stmt.setString(1,  personCode);
		list = getListOfCourses(stmt);
		
		return list;
	}
	
	/**
	 * All courses useful for a job profile.
	 */
	public List<Course> getCoursesUsefulForJobProfile(String jobProfileCode) 
			throws SQLException {
		List<Course> list = null;
		PreparedStatement stmt = connection.prepareStatement(
			" SELECT * " +
			" FROM course NATURAL JOIN course_skill NATURAL JOIN" +
			"      job_profile" +
			" WHERE job_profile_code = ?");
		stmt.setString(1,  jobProfileCode);
		list = getListOfCourses(stmt);
		
		return list;
	}
	
	/**
	 * Get courses that fullfill missing skills.
	 */
	public List<Course> getCoursesForMissingSkills(String jobProfileCode) 
			throws SQLException {
		List<Course> list = null;
		PreparedStatement stmt = connection.prepareStatement(
			" WITH " +
		    "   skills_job as " + 
			"     (SELECT skill_code " +
			"      FROM job_skill " +
			"      WHERE job_code = ?), " +
			"   skills_job_profile as " +
			"     (SELECT skill_code " +
			"      FROM job_profile_skill NATURAL JOIN job " +
			"      WHERE job_code = ?), " +
			"   skills_person as " +
			"     (SELECT skill_code " +
			"      FROM person_skill " +
			"      WHERE person_code = ?), " +
			"	missing_skills as ( (SELECT * FROM skills_job " +
			"                        INTERSECT" +
			"                         SELECT * FROM skills_job_profile ) " +
            "       			     MINUS " +
            "        			     SELECT * FROM skills_person )" +
			" SELECT *" +
			" FROM (SELECT DISTINCT course_code " +
			"       FROM course_skill NATURAL JOIN missing_skills) " +
			"      NATURAL JOIN course"
		);
		stmt.setString(1,  jobProfileCode);
		stmt.setString(2,  jobProfileCode);
		stmt.setString(3,  jobProfileCode);		
		list = getListOfCourses(stmt);
		
		return list;
	}
	
	public List<Course> getCoursesRequiredForCertificateForJob(String personCode,
															   String jobCode,
															   String jobProfileCode)
			throws SQLException {
		List<Course> list = null;
		PreparedStatement stmt = connection.prepareStatement(
			" WITH " + 
			"   job_certificates as " +
			"     (SELECT certificate_code " +
			"      FROM job_certificate " +
			"      WHERE job_code = ?), " +
			"   job_profile_certificates as " +
			"     (SELECT certificate_code " +
			"      FROM job_profile_certificate " +
			"      WHERE job_profile_code = ?)," +
			"   person_certificates as " +
			"     (SELECT certificate_code " +
			"      FROM earns " +
			"      WHERE person_code = ?) " +
			" SELECT * " + 
			" FROM certificate NATURAL JOIN (SELECT * FROM person_certificates MINUS " +
			"                                SELECT * FROM job_certificates MINUS " +
			"                                SELECT * FROM job_profile_certificates) " +
			"      NATURAL JOIN prepares_for NATURAL JOIN course"
		);
	stmt.setString(1, jobCode);
	stmt.setString(2, jobProfileCode);
	stmt.setString(3, personCode);
	list = getListOfCourses(stmt);
	return list;
	}
	
	// Insertions
	public int addCourse(Course course) throws SQLException {
		int count = 0;
		PreparedStatement stmt = connection.prepareStatement(
			" INSERT INTO course " +
			"   VALUES (?, ?, ?, ?, ?, ?) "
		);
		stmt.setString(1, course.getCourseCode());
		stmt.setString(2, course.getCourseTitle());
		stmt.setString(3, course.getCourseDescription());
		stmt.setString(4, course.getCourseLevel());
		stmt.setString(5, course.getStatus());
		stmt.setDouble(6, course.getRetailPrice());
		count = stmt.executeUpdate();
		return count;
	}
	
	// Updates
	public int updateCourse(Course course) throws SQLException {
		int count = 0;
		PreparedStatement stmt = connection.prepareStatement(
			" UPDATE course " +
			" SET course_title = ? " +
			"     course_description = ? " +
			"     course_level = ? " +
			"     status = ? " +
			"     retail_price = ? " +
			" WHERE company_code = ? "
		);
		
		stmt.setString(1, course.getCourseTitle());
		stmt.setString(2, course.getCourseDescription());
		stmt.setString(3, course.getCourseLevel());
		stmt.setString(4, course.getStatus());
		stmt.setDouble(5, course.getRetailPrice());
		stmt.setString(6, course.getCourseCode());
		count = stmt.executeUpdate();
		return count;
	}
	
	// Helper Functions
	private List<Course> getListOfCourses(PreparedStatement stmt) 
			throws SQLException {
		// Create an empty Course list
		List<Course> list = new ArrayList<Course>();
		// Execute the query
		ResultSet results = stmt.executeQuery();
		// Walk through the results...
		while (results.next()) {
			// Create a new Course from the results
			// and add it to the list.
			list.add( new Course(
				results.getString("course_code"),
				results.getString("course_title"),
				results.getClob("course_description").toString(),
				results.getString("course_level"),
				results.getString("status"),
				results.getDouble("retail_price"))
			);
		}
		return list;
	}
}
