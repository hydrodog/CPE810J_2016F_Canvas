
import java.util.*;

public class SummaryStat {

    public static double count(ArrayList<Integer> list) {
        return list.size();
    }


    public static double sum(ArrayList<Integer> list) {

        double sum = 0;
        for (int i = 0; i < list.size(); i++){
           sum = sum + list.get(i);
        }

        return sum;
    }


    public static double average(ArrayList<Integer> list) {
        double average = sum(list) / list.size();
        return average;
    }




    public static double median(ArrayList<Integer> list) {
        Collections.sort(list);
        int middle =list.size()/2;
        if (list.size()%2 == 0) {
        return (list.get(middle-1) + list.get(middle)) / 2.0;
        } else {
        return list.get((list.size()-1)/2);
        }
    }

    //http://stackoverflow.com/questions/4191687/how-to-calculate-mean-median-mode-and-range-from-a-set-of-numbers
    public static ArrayList<Integer> mode(final ArrayList<Integer> list) {
        final ArrayList<Integer> modes = new ArrayList<Integer>();
        final Map<Integer, Integer> countMap = new HashMap<Integer, Integer>();

        int max = -1;

        for (final int n : list) {
            int count = 0;

            if (countMap.containsKey(n)) {
                count = countMap.get(n) + 1;
            } else {
                count = 1;
            }

            countMap.put(n, count);

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




    public static double variance(ArrayList<Integer> list) {
        double sumMinusAverage = sum(list) - average(list);

        return sumMinusAverage * sumMinusAverage / (list.size()-1);
    }

    public static double StdDev(ArrayList<Integer> list) {
        double sumMinusAverage = sum(list) - average(list);

        return Math.sqrt(sumMinusAverage * sumMinusAverage / (list.size()-1));
    }

    public static double max(ArrayList<Integer> list) {
        int max = Integer.MIN_VALUE;
        for(int i=0; i<list.size(); i++){
            if(list.get(i) > max){
                max = list.get(i);
            }
        }
        return max;
    }


    public static double min(ArrayList<Integer> list) {
        int min = Integer.MAX_VALUE;
        for(int i=0; i<list.size(); i++){
            if(list.get(i) < min){
                min = list.get(i);
            }
        }
        return min;
    }



    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<Integer>();

        //simulates 100 ramdon int numbers
        //still need to determine how to input data for further work
        int pick;
        Random rand = new Random();

         for (int j = 0; j<100; j++)
        {
            pick = rand.nextInt(100);
            list.add(pick);
        }

        System.out.println();
        System.out.println(count(list)+" students participate in the exam");
        System.out.println("The highest score is " +  max(list));
        System.out.println("The lowest score is " +  min(list));
        System.out.println("The average is: " + average(list));
        System.out.println("The variance is: " + variance(list));
        System.out.println("The standard deviation is: " +  StdDev(list));
        System.out.println("The median  is: " +  median(list));
        System.out.println("The mode(s): " +  mode(list));

        System.out.println("The sorted score from low to high is: " );
          for(int counter: list){
            System.out.print(counter+" ");
        }

    }

}


