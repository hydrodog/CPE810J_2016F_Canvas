package edu.stevens.canvas.graph;

import java.util.*;

/**
 * The SummaryStat Class calculates the following statistics: 
 * (1) mean: the average of all the scores in a certain task.
 * (2) mode: the most frequently appeared score(s) in a certain task.
 * (3) median: The "median" is the "middle" value in the list of scores.
 * (4) variance: variance is the expectation of the squared deviation of a random variable from its mean,
 *     and it informally measures how far a set of numbers are spread out from their mean.
 * (5) standard deviation: it is the square root of variance. A low standard deviation indicates that
 *     the scores tend to be close to the mean (also called the expected value) of the scores,
 *     while a high standard deviation indicates that the scores are spread out over a wider range of values.
 * (6) lowest score and highest score. 
 * 
 * @author Tao Feng
 *
 */

public class SummaryStat {
	private StringBuilder sb;
	
	public SummaryStat(ArrayList<Double> grade) {
		// create a string builder to store the result
		sb = new StringBuilder("");
		
		// count
		sb.append(count(grade) + " students participate in the exam" + "\r\n" + "\r\n");
		
		// max
		sb.append("Highest score: " + max(grade) + "\r\n" + "\r\n");
		
		// min
		sb.append("Lowest score: " + min(grade) + "\r\n" + "\r\n");
		
		// average
		sb.append("Average: " + average(grade) + "\r\n" + "\r\n");
		
		// variance
		sb.append("Variance: " + variance(grade) + "\r\n" + "\r\n");
		
		// standard deviation 
		sb.append("Standard Deviation: " + StdDev(grade) + "\r\n" + "\r\n");
		
		// median
		sb.append("Median: " + median(grade) + "\r\n" + "\r\n");
		
		// mode
		sb.append("Mode(s): " + mode(grade) + "\r\n" + "\r\n");
	}
	
	// count the number of student
	public static int count(ArrayList<Double> list) {
		return list.size();
	}
	
	// calculate the sum of grade
	public static double sum(ArrayList<Double> list) {
		double sum = 0;
		
		for (int i = 0; i < list.size(); i++){
		   sum = sum + list.get(i);
		}
		
		return sum;
	}
	
	// calculate the average of grade
	public static double average(ArrayList<Double> list) {
		double average = sum(list) / list.size();
		
		return average;
	}
	
	// calculate the median of grade
	public static double median(ArrayList<Double> list) {
		Collections.sort(list);
		
		int middle = list.size() / 2;
		
		if (list.size() % 2 == 0) {
			return (list.get(middle - 1) + list.get(middle)) / 2.0;
		}
		else {
			return list.get((list.size() - 1) / 2);
		}
	}
	
	// calculate the mode of grade
	public static ArrayList<Integer> mode(final ArrayList<Double> list) {
		// create an arraylist to store the mode(s)
		final ArrayList<Integer> modes = new ArrayList<Integer>();
		
		// create a map to store the grade and its frequency
		final Map<Integer, Integer> countMap = new HashMap<Integer, Integer>();

		int max = -1;

		for (final double n : list) {
			int n2 = (int) n;
			int count = 0;
			if (countMap.containsKey(n2)) {
				count = countMap.get(n2) + 1;
			}
			else {
				count = 1;
			}
			
			countMap.put(n2, count);
			
			if (count > max) {
				max = count;
			}
		}

		for (final Map.Entry<Integer, Integer> tuple : countMap.entrySet()) {
			if (tuple.getValue() == max) {
				modes.add(tuple.getKey());
			}
		}
		return modes;
	}
	
	
	// calculate the variance of grade
	public static double variance(ArrayList<Double> list) {
		double var = 0;
		
		for (int i = 0; i < list.size(); i++) {
			var += (list.get(i) - average(list)) * (list.get(i) - average(list));
		}
		
		return var / (list.size());
	}
	
	// calculate the standard deviation of grade
	public static double StdDev(ArrayList<Double> list) {
		double var = 0;
		
		for (int i = 0; i < list.size(); i++) {
			var += (list.get(i) - average(list)) * (list.get(i) - average(list));
		}
		
		return Math.sqrt(var / (list.size()));
	}
	
	// find the max of grade
	public static double max(ArrayList<Double> list) {
		double max = Integer.MIN_VALUE;
		
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) > max) {
				max = list.get(i);
			}
		}
		
		return max;
	}
	
	// find the min of grade
	public static double min(ArrayList<Double> list) {
		double min = Integer.MAX_VALUE;
		
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) < min) {
				min = list.get(i);
			}
		}
		
		return min;
	}
	
	// return the result
	public StringBuilder getStr() {
		return sb;
	}
}