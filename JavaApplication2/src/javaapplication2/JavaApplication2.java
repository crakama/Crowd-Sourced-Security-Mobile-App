/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;

import java.util.Scanner;

/**
 *
 * @author admin cate rakama
 */
public class JavaApplication2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
          int num=0;
    int fact=1;
    System.out.println("Enter a number: ");
    Scanner input = new Scanner(System.in);
        //int nextInt = input.nextInt();
          //num=nextInt;
       num = input.nextInt();
   while (true && num < 0) {
      System.out.println("You entered a negative integer please Input a positive integer:");
      input = new Scanner(System.in);
      int nextInt = input.nextInt();
          num=nextInt;
    }
	
    
    for (int i=2;i<=num; i++){
        fact=fact*i;
    }

    System.out.println("Factorial: "+fact);// TODO code application logic here
    }
    
}
