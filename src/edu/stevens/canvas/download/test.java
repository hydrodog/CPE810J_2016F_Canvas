/**
 *  @author: Gaojian Chen 
 * 
 * 
 */
package edu.stevens.canvas.download;

public class test {
	public static void main(String[] args){
	Download d=	new Download("10300000000000133","10300000000046026","10300000000022347”,”Your-token”);
	d.printurl();
	d.get();
	double k=d.getGrade();
	System.out.println(k);
	}
}
//The output is:
//Output from Server:
//{“id":10300000001163980,"body":null,"url":null,"grade":"10","score”:10.0,"submitted_at":"2016-05-09T21:34:23Z","assignment_id":10300000000046026,"user_id":10300000000022347,"submission_type":"online_upload","workflow_state":"graded","grade_matches_current_submission":true,"graded_at":"2016-11-11T01:17:54Z","grader_id":10300000000021989,"attempt":1,"excused":false,"late":false,"preview_url":"https://canvas.instructure.com/courses/1030~133/assignments/1030~46026/submissions/22347?preview=1\u0026version=15"}
//User:10300000000022347 grade:10.0
//10.0
