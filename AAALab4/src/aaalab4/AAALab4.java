/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aaalab4;

import java.util.Scanner;

/**
 *
 * @author Robert
 */
public class AAALab4 {

    /**
     * @param args the command line arguments
     */
    public static int[] d  = {1,3,5,7,11,13,17,21};
    public static void main(String[] args) {
        // TODO code application logic here
    // Scanner s = new Scanner(System.in);
    // System.out.println("Change:");
     //int j = s.nextInt();
     //change(13,d.length);
     for (int i=0;i<201;i=i+10){
       if (i!=0){
           long start = System.nanoTime();
           change(i,d.length);
           long end = System.nanoTime()-start;
           System.out.println(end);
       }else System.out.println(0);
     }
    }
   public static void change(int C,int n){
    int[][] c = new int[n][C+1];
    for (int i =0;i<n;i++)
        c[i][0]=0;
    for(int i=0;i<n;i++)
        for(int j=1;j<C+1;j++){
         if ((i==0)&&(j<d[0])){
           c[i][j]= 100000;
         } else {
           if (i==0){
            c[i][j]= 1+c[0][j-d[0]];
           } else{
               int ne = c[i-1][j];
               int now;
               if (j>=d[i]){
                now = 1+c[i][j-d[i]];}
               else now = 10000;
               
               c[i][j]=Integer.min(ne,now);
           }
         }
       }  
       //System.out.println(c[n-1][C]);
   }
    
}
