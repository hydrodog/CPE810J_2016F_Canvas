/**
 * 
 * @author: Gaojian Chen
 * @modifier -
 */
package edu.stevens.canvas.download;

/**
 * Parents of Download class
 * Providing parameter of Canvas API
 *
 */

public class Canvas {
	private String courseId;
	private String assignmentId;
	private String userId;
	private String token; 
	
public Canvas(String courseId,String assignmentId,String userId,String token){
	this.courseId = courseId;
	this.assignmentId = assignmentId;
	this.userId = userId;
	this.token = token;
}
public String getcouseId(){
	return courseId;
}

public String getassignmentId(){
	return assignmentId;
}
public String getuserId(){
	return userId;
}
public String gettoken(){
	return token;
}
}
