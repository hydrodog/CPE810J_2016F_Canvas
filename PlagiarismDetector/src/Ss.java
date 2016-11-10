package Plagiarism;

/*
 * author: Yu Yu
 * modified:
 */


import java.util.ArrayList;  
import java.util.List;  
  
 
  
public class Ss {  
  
    public static void main(String[] args) {  
        Ss ss = new Ss();  
        ss.getSameStr("12", "123pdg123");                                                                           
    }  
      
    public List<String> getSameStr(String str1, String str2){  
        char[] arrchar1 = str1.toCharArray();  
        char[] arrchar2 = str2.toCharArray();  
        int[][] arr = new int[arrchar1.length][arrchar2.length];  
        int len = arrchar1.length < arrchar2.length ? arrchar1.length:arrchar2.length;  
        int maxarr[] = new int[len];  
        int maxindex[] = new int[len];  
        for(int i = 0; i < arrchar1.length ; i++){  
            for(int j = 0; j < arrchar2.length ; j++){  
                if(arrchar2[j] == arrchar1[i]){  
                    if(i == 0 || j == 0){  
                        arr[i][j] = 1;  
                        if(maxarr[0] < 1){  
                            maxarr[0] = 1;  
                            maxindex[0] = i;  
                        }  
                    }else{  
                        arr[i][j] = arr[i-1][j-1] + 1;  
                         
                       if(maxarr[0] < arr[i][j]){  
                            maxarr[0] = arr[i][j];  
                            maxindex[0] = i;  
                            for(int num = 1; num < maxarr.length; num++){  
                                if(maxarr[num] == 0){  
                                    break;  
                                }else{  
                                    maxarr[num] = 0;  
                                    maxindex[num] = 0;  
                                }  
                            }  
                        }else if (maxarr[0] == arr[i][j]){  
                            
                            int num = 0;  
                            for(int max : maxarr){  
                                if(max == 0){  
                                    maxarr[num] = arr[i][j];  
                                    maxindex[num] = i;  
                                    break;  
                                }  
                                num++;  
                            }  
                        }  
                    }  
                }else{  
                    arr[i][j] = 0;  
                }  
            }  
        }  
        for(int i = 0;i < arr.length ; i++){  
            for(int j = 0;j < arr[i].length;j++){  
                System.out.print("   " + arr[i][j]);  
            }  
            System.out.println("");  
        }  
          
        List<String> list = new ArrayList<String>();  
          
          
        for(int i = 0; i< maxarr.length ;i++){  
            if(maxarr[i] == 0){  
                break;  
            }  
            int num = maxindex[i] - (maxarr[i] -1);  
            String str = "";  
            for(int k = 0;k<maxarr[i];k++){  
                char tempchar = arrchar1[num];  
                str += String.valueOf(tempchar);  
                num++;  
            }  
            System.out.println(str);  
            list.add(str);  
        }  
                  
        return list;  
    }  
}