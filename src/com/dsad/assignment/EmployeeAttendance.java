package com.dsad.assignment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class EmployeeAttendance {
	static EmployeeAttendance ea =new EmployeeAttendance();
	static EmpBT eb =new EmpBT();

	public void readInputFile() {
		try {
			File file = new File("EmpId.txt");
			BufferedReader br =new BufferedReader(new FileReader(file));
			ArrayList<Integer> al =new ArrayList<>();
			EmployeeNode root =new EmployeeNode();
			root=null;
			String x;                 
			while((x = br.readLine())!=null) {
				al.add(Integer.parseInt(x));
				root=eb.insert(root, Integer.parseInt(x));
			}
			br.close();
			// For checking the contents of the file
			/* for(int i=0;i<al.size();i++) {        
                      System.out.println(al.get(i));
                    }*/
			eb.printRedBlackTree(root);
			eb.printRedBlackTreeCount(root);
			eb.printRoot(root);
		}
		catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(NumberFormatException e) {
			e.printStackTrace();
		}                  
	}

	public void userChoice() {
		Scanner input = new Scanner(System.in);
		int choice=0;
		do {                                                  
			System.out.println("1. List the employees");
			System.out.println("2. Employee Count");
			System.out.println("3. Search for an Employee");
			System.out.println("4. Frequency of Entering in to the office of an Employee");
			System.out.println("5. Employee with highest frequency");
			System.out.println("6. Range of Employees present between given two ids and corresponding frequency");
			System.out.println("Press 0 to exit");
			choice=input.nextInt();                              
		}while(choice!=0);
		input.close();
	}

	public static void main(String[] args) {            
		System.out.println("Attendance Monitoring system opertations:");
		ea.readInputFile();
		//ea.userChoice();
	} 
}