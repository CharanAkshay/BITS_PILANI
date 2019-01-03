package com.dsad.assignment;

import java.util.Optional;
import com.dsad.assignment.EmployeeNode;
import com.dsad.assignment.EmployeeNode.Color;

public class EmpBT {

	private static EmployeeNode createNullLeafNode(EmployeeNode parent) {
		EmployeeNode leaf = new EmployeeNode();
		leaf.color = Color.BLACK;
		leaf.isNullLeaf = true;
		leaf.parent = parent;
		return leaf;
	}

	private static EmployeeNode createBlackNode(int id) {
		EmployeeNode node = new EmployeeNode();
		node.empID = id;
		node.attCount=node.attCount+1;
		node.color = Color.BLACK;
		node.left = createNullLeafNode(node);
		node.right = createNullLeafNode(node);
		return node;
	}

	private static EmployeeNode createRedNode(EmployeeNode parent, int id) {
		EmployeeNode node = new EmployeeNode();
		node.empID = id;
		node.attCount=node.attCount+1;
		node.color = Color.RED;
		node.parent = parent;
		node.left = createNullLeafNode(node);
		node.right = createNullLeafNode(node);
		return node;
	}

	/**
	 * Main insert method of red black tree.
	 */

	 public EmployeeNode insert(EmployeeNode root, int data) {
		return insert(null, root, data);
	}

	/**
	 * Main print method of red black tree.
	 */

	 public void printRedBlackTree(EmployeeNode root) {
		 printRedBlackTree(root, 0);
	 }

	 public void printRedBlackTreeCount(EmployeeNode root) {
		 printRedBlackTreeCount(root, 0);
	 }

	 private void rightRotate(EmployeeNode root, boolean changeColor) {
		 EmployeeNode parent = root.parent;
		 root.parent = parent.parent;
		 if(parent.parent != null) {
			 if(parent.parent.right == parent) {
				 parent.parent.right = root;
			 } else {
				 parent.parent.left = root;
			 }
		 }
		 EmployeeNode right = root.right;
		 root.right = parent;
		 parent.parent = root;
		 parent.left = right;
		 if(right != null) {
			 right.parent = parent;
		 }
		 if(changeColor) {
			 root.color = Color.BLACK;
			 parent.color = Color.RED;
		 }
	 }

	 private void leftRotate(EmployeeNode root, boolean changeColor) {
		 EmployeeNode parent = root.parent;
		 root.parent = parent.parent;
		 if(parent.parent != null) {
			 if(parent.parent.right == parent) {
				 parent.parent.right = root;
			 } else {
				 parent.parent.left = root;
			 }
		 }
		 EmployeeNode left = root.left;
		 root.left = parent;
		 parent.parent = root;
		 parent.right = left;
		 if(left != null) {
			 left.parent = parent;
		 }
		 if(changeColor) {
			 root.color = Color.BLACK;
			 parent.color = Color.RED;
		 }
	 }

	 private Optional<EmployeeNode> findSiblingNode(EmployeeNode root) {
		 EmployeeNode parent = root.parent;
		 if(isLeftChild(root)) {
			 return Optional.ofNullable(parent.right.isNullLeaf ? null : parent.right);
		 } else {
			 return Optional.ofNullable(parent.left.isNullLeaf ? null : parent.left);
		 }
	 }

	 private boolean isLeftChild(EmployeeNode root) {
		 EmployeeNode parent = root.parent;
		 if(parent.left == root) {
			 return true;
		 } else {
			 return false;
		 }
	 }

	 private EmployeeNode insert(EmployeeNode parent, EmployeeNode root, int id) {
		 if(root  == null || root.isNullLeaf) {
			 //if parent is not null means tree is not empty
			 //so create a red leaf node
			 if(parent != null) {
				 return createRedNode(parent, id);
			 } else { //otherwise create a black root node if tree is empty
				 return createBlackNode(id);
			 }
		 }

		 //duplicate insertion is not allowed for this tree.
		 if(root.empID == id) {
			 //throw new IllegalArgumentException("Duplicate data " + data);
			 root.attCount=root.attCount+1;
			 return root ;
		 }
		 //if we go on left side then isLeft will be true
		 //if we go on right side then isLeft will be false.
		 boolean isLeft;
		 if(root.empID > id) {
			 EmployeeNode left = insert(root, root.left, id);
			 //if left becomes root parent means rotation
			 //happened at lower level. So just return left
			 //so that nodes at upper level can set their
			 //child correctly
			 if(left == root.parent) {
				 return left;
			 }
			 //set the left child returned to be left of root node
			 root.left = left;
			 //set isLeft to be true
			 isLeft = true;
		 } else {
			 EmployeeNode right = insert(root, root.right, id);
			 //if right becomes root parent means rotation
			 //happened at lower level. So just return right
			 //so that nodes at upper level can set their
			 //child correctly
			 if(right == root.parent) {
				 return right;
			 }
			 //set the right child returned to be right of root node
			 root.right = right;
			 //set isRight to be true
			 isLeft = false;
		 }
		 if(isLeft) {
			 //if we went to left side check to see Red-Red conflict
			 //between root and its left child
			 if(root.color == Color.RED && root.left.color == Color.RED) {
				 //get the sibling of root. It is returning optional means
				 //sibling could be empty
				 Optional<EmployeeNode> sibling = findSiblingNode(root);
				 //if sibling is empty or of BLACK color
				 if(!sibling.isPresent() || sibling.get().color == Color.BLACK) {
					 //check if root is left child of its parent
					 if(isLeftChild(root)) {
						 //this creates left left situation. So do one right rotate
						 rightRotate(root, true);
					 } else {
						 //this creates left-right situation so do one right rotate followed
						 //by left rotate
						 //do right rotation without change in color. So sending false.
						 //when right rotation is done root becomes right child of its left
						 //child. So make root = root.parent because its left child before rotation
						 //is new root of this subtree.
						 rightRotate(root.left, false);
						 //after rotation root should be root's parent
						 root = root.parent;
						 //then do left rotate with change of color
						 leftRotate(root, true);
					 }
				 } else {
					 //we have sibling color as RED. So change color of root
					 //and its sibling to Black. And then change color of their
					 //parent to red if their parent is not a root.
					 root.color = Color.BLACK;
					 sibling.get().color = Color.BLACK;
					 //if parent's parent is not null means parent is not root.
					 //so change its color to RED.
					 if(root.parent.parent != null) {
						 root.parent.color = Color.RED;
					 }
				 }
			 }
		 } else {
			 //this is mirror case of above. So same comments as above.
			 if(root.color == Color.RED && root.right.color == Color.RED) {
				 Optional<EmployeeNode> sibling = findSiblingNode(root);
				 if(!sibling.isPresent() || sibling.get().color == Color.BLACK) {
					 if(!isLeftChild(root)) {
						 leftRotate(root, true);
					 } else {
						 leftRotate(root.right, false);
						 root = root.parent;
						 rightRotate(root, true);
					 }
				 } else {
					 root.color = Color.BLACK;
					 sibling.get().color = Color.BLACK;
					 if(root.parent.parent != null) {
						 root.parent.color = Color.RED;
					 }
				 }
			 }
		 }
		 return root;
	 }

	 private void printRedBlackTree(EmployeeNode root, int space) {
		 if(root == null || root.isNullLeaf) {
			 return;
		 }
		 printRedBlackTree(root.right, space + 5);
		 for(int i=0; i < space; i++) {
			 System.out.print(" ");
		 }
		 System.out.println(root.empID + " " + (root.color == Color.BLACK ? "B" : "R"));
		 printRedBlackTree(root.left, space + 5);
	 }
	 
	 private void printRedBlackTreeCount(EmployeeNode root, int space) {
		 if(root == null || root.isNullLeaf) {
			 return;
		 }
		 printRedBlackTreeCount(root.right, space + 5);
		 for(int i=0; i < space; i++) {
			 System.out.print(" ");
		 }
		 System.out.println(root.attCount + " " + (root.color == Color.BLACK ? "B" : "R"));
		 printRedBlackTreeCount(root.left, space + 5);
	 }
	 
	 public void printRoot(EmployeeNode root) {
		 System.out.println(root.empID);
	 }
	 private int countNodes() {
		 return 0;   
	 }
	 
	 EmployeeNode readEmployees(EmployeeNode emp, int id) {
		 return emp;        
	 }
	 
	 int getHeadCount(EmployeeNode emp) {         
		 return 0;
	 }
	 
	 boolean searchID(EmployeeNode emp,int id) {
		 return false;      
	 }
	 
	 int howOften(EmployeeNode emp, int id) {
		 return 0;
	 }
	 
	 EmployeeNode frequentVisitor(EmployeeNode emp) {
		 return emp;
	 }
	 
	 void pritnRangePresent(EmployeeNode emp, int id1, int id2) {
	 }
}


