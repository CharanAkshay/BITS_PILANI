package com.dsad.assignment;

public class EmployeeNode {

	int empID,attCount;
	EmployeeNode left,right;
	EmployeeNode parent;
	Color color;
	boolean isNullLeaf;

	public enum Color{
		RED,
		BLACK
	}                   
}

