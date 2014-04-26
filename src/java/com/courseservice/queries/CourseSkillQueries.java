/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.courseservice.queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bjw
 */
public class CourseSkillQueries {
    private Connection connection;
    
    public CourseSkillQueries(Connection connection) {
        this.connection = connection;
    }
    
    public List<Integer> getCourseSkillCodes(String courseCode)
            throws SQLException {
        List<Integer> list = new ArrayList<Integer>();
        PreparedStatement stmt = connection.prepareStatement(
            " SELECT skill_code " +
            " FROM course_skill" +
            " WHERE course_code = ?");
        stmt.setString(1, courseCode);
        ResultSet results = stmt.executeQuery();
        while (results.next()) {
            list.add(results.getInt("skill_code"));
        }
        return list;
    }
}
