package assignment3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CatCafe implements Iterable<Cat> {

	public CatNode root;

	public CatCafe() {
	}

	public CatCafe(CatNode dNode) {
		this.root = dNode;
	}

	// Constructor that makes a shallow copy of a assignment3.CatCafe
	// New CatNode objects, but same assignment3.Cat objects
	public CatCafe(CatCafe cafe) {
		if (cafe.root != null) {
			copy(cafe.root);
		}
		else {
			root = null;
		}
	}

	// add a cat to the cafe database
	public void hire(Cat c) {
		if (root == null)
			root = new CatNode(c);
		else
			root = root.hire(c);
	}

	// removes a specific cat from the cafe database
	public void retire(Cat c) {
		if (root != null)
			root = root.retire(c);
	}

	// get the oldest hire in the cafe
	public Cat findMostSenior() {
		if (root == null)
			return null;

		return root.findMostSenior();
	}

	// get the newest hire in the cafe
	public Cat findMostJunior() {
		if (root == null)
			return null;

		return root.findMostJunior();
	}

	// returns a list of cats containing the top numOfCatsToHonor cats
	// in the cafe with the thickest fur. Cats are sorted in descending
	// order based on their fur thickness.
	public ArrayList<Cat> buildHallOfFame(int numOfCatsToHonor) {
		// Create a list of cats from the cafe
		ArrayList<Cat> catsList = new ArrayList<>();
		for (Cat cat : this) {
			catsList.add(cat);
		}

		// Return an empty list if there are no cats in the cafe
		if (catsList.isEmpty()) {
			return new ArrayList<Cat>();
		}

		// Check if the numOfCatsToHonor value is greater than or equal to the number of cats in the cafe
		if (numOfCatsToHonor >= catsList.size()) {
			numOfCatsToHonor = catsList.size();
		}

		// Sort the list of cats by their fur thickness
		catsList.sort((cat1, cat2) -> Integer.compare(cat2.getFurThickness(), cat1.getFurThickness()));

		// Create a new list
		ArrayList<Cat> tempList = new ArrayList<>();

		// Add the desired numofCats to the tempList
		for (int i = 0; i < numOfCatsToHonor; i++) {
			tempList.add(catsList.get(i));
		}

		return tempList;
	}


	// Returns the expected grooming cost the cafe has to incur in the next numDays days
	public double budgetGroomingExpenses(int numDays) {
		// Return 0 if the numDays value is less than or equal to zero
		if (numDays <= 0) {
			return 0;
		}

		double totalCost = 0;

		for (Cat cat: this) {
			// Ignore cats with negative values for getDaysToNextGrooming()
			if (cat.getDaysToNextGrooming() <= numDays) {
				totalCost += cat.getExpectedGroomingCost();
			}
		}

		return totalCost;
	}


	// returns a list of list of Cats.
	// The cats in the list at index 0 need be groomed in the next week.
	// The cats in the list at index i need to be groomed in i weeks.
	// Cats in each sublist are listed in from most senior to most junior.
	public ArrayList<ArrayList<Cat>> getGroomingSchedule() {
		ArrayList<ArrayList<Cat>> groomingSchedule = new ArrayList<>();

		for (Cat cat : this) {
			// Skip cat if it has a negative value for getDaysToNextGrooming()
			if (cat.getDaysToNextGrooming() < 0) {
				continue;
			}
			else {
				int week = cat.getDaysToNextGrooming() / 7;

				// Check if the week index exists
				if (week >= groomingSchedule.size()) {
					// If the index does not exist create new list
					for (int i = groomingSchedule.size(); i <= week; i++) {
						groomingSchedule.add(new ArrayList<Cat>());
					}
				}

				// Add the cat to the correct week
				groomingSchedule.get(week).add(cat);
			}
		}

		return groomingSchedule;
	}



	public Iterator<Cat> iterator() {
		return new CatCafeIterator();
	}


	public class CatNode {
		public Cat catEmployee;
		public CatNode junior;
		public CatNode senior;
		public CatNode parent;

		public CatNode(Cat c) {
			this.catEmployee = c;
			this.junior = null;
			this.senior = null;
			this.parent = null;
		}

		// add the c to the tree rooted at this and returns the root of the resulting tree
		public CatNode hire (Cat c) {

			if (c != null) {
				CatNode newCat = new CatNode(c);

				root = addCat(this, newCat);

				// if the maxheap property is not maintained
				if (!isMaxHeap(root)) {
					root = upHeap(newCat);
				}

				return root;
			}

			return null;

		}

		// remove c from the tree rooted at this and returns the root of the resulting tree
		public CatNode retire(Cat c) {

			if (c != null && root != null) {
				CatNode keyNode = getCat(this, c);

				// If the cat is not under the current node check the whole tree
				if (keyNode == null) {

					keyNode = getCat(CatCafe.this.root, c);
					if (keyNode == null) {
						// The cat does not exist
						return this;
					}

				}

				// Remove the node
				CatNode head = removeCat(this, keyNode);
				while (!isMaxHeap(head)) {
					CatNode brokenNode = findHeapBreak(head);
					head = downHeap(brokenNode);
				}

				return head;
			}
			else {
				return null;
			}

		}


		// find the cat with highest seniority in the tree rooted at this
		public Cat findMostSenior() {

			return getOldest(this);

		}

		// find the cat with lowest seniority in the tree rooted at this
		public Cat findMostJunior() {

			return getYoungest(this);
		}

		// Feel free to modify the toString() method if you'd like to see something else displayed.
		public String toString() {
			String result = this.catEmployee.toString() + "\n";
			if (this.junior != null) {
				result += "junior than " + this.catEmployee.toString() + " :\n";
				result += this.junior.toString();
			}
			if (this.senior != null) {
				result += "senior than " + this.catEmployee.toString() + " :\n";
				result += this.senior.toString();
			} /*
			if (this.parent != null) {
				result += "parent of " + this.catEmployee.toString() + " :\n";
				result += this.parent.catEmployee.toString() +"\n";
			}*/
			return result;
		}
	}


	private class CatCafeIterator implements Iterator<Cat> {
		// HERE YOU CAN ADD THE FIELDS YOU NEED

		private CatNode current;
		private CatCafe tempCafe;

		private CatCafeIterator() {

			if (root == null) {
				return;
			}

			tempCafe = new CatCafe(CatCafe.this);
			current = getCat(tempCafe.root, getYoungest(tempCafe.root));
		}

		public Cat next(){

			if (current == null || !hasNext()) {
				throw new NoSuchElementException();
			}

			Cat currentCat = current.catEmployee;

			tempCafe.retire(current.catEmployee);

			if (tempCafe.root != null) {
				current = getCat(tempCafe.root, getYoungest(tempCafe.root));;
			}

			return currentCat;
		}

		public boolean hasNext() {

			if (root == null) {
				return false;
			}
			else {
				Boolean bool = tempCafe.root != null;
				return bool;
			}


		}

	}

	public static void main(String[] args) {
		Cat B = new Cat("Buttercup", 45, 53, 5, 85.0);
		Cat C = new Cat("Chessur", 8, 23, 2, 250.0);
		Cat J = new Cat("Jonesy", 0, 21, 12, 30.0);
		Cat JJ = new Cat("JIJI", 156, 17, 1, 30.0);
		Cat JTO = new Cat("J. Thomas O'Malley", 21, 10, 9, 20.0);
		Cat MrB = new Cat("Mr. Bigglesworth", 71, 0, 31, 55.0);
		Cat MrsN = new Cat("Mrs. Norris", 100, 68, 15, 115.0);
		Cat T = new Cat("Toulouse", 180, 37, 14, 25.0);

		Cat BC = new Cat("Blofeld's cat", 6, 72, 18, 120.0);
		Cat L = new Cat("Lucifer", 10, 44, 20, 50.0);
	}


	// Helper functions for hire
	public CatNode addCat(CatNode root, CatNode newCat) {

		if (root == null) {
			root = newCat;
		}
		else if (root.catEmployee.compareTo(newCat.catEmployee) < 0) {
			root.senior = addCat(root.senior, newCat);
			root.senior.parent = root;
		}
		else if (root.catEmployee.compareTo(newCat.catEmployee) > 0) {
			root.junior = addCat(root.junior, newCat);
			root.junior.parent = root;
		}

		return root;
	}

	public Boolean isMaxHeap(CatNode root) {

		if (root == null) {
			return true;
		}
		else {

			// If either junior / senior child is less than root, return false
			if (root.junior != null && (root.catEmployee.getFurThickness() < root.junior.catEmployee.getFurThickness() )) {
				return false;
			}

			if (root.senior != null && (root.catEmployee.getFurThickness() < root.senior.catEmployee.getFurThickness())) {
				return false;
			}

			// Returns false if it isnt a maxHeap
			return isMaxHeap(root.junior) && isMaxHeap((root.senior));

		}
	}

	public CatNode upHeap(CatNode node) {

		do {
			rotateTree(node.parent, node);
		} while (node.parent != null && node.catEmployee.getFurThickness() > node.parent.catEmployee.getFurThickness());

		if (node.parent == null) {
			return node;
		}
		return this.root;
	}

	public void rotateTree(CatNode parent, CatNode child) {

		// Check if child is the senior or junior of its parent
		if (child.parent.senior != null && (child.parent.senior.catEmployee.equals(child.catEmployee))) {

			parent.senior = child.junior;

			// If the child has a junior, set its parent to the parent node
			if (child.junior != null) {
				child.junior.parent = parent;
			}

			child.junior = parent;

		}
		else if (child.parent.junior != null && (child.parent.junior.catEmployee.equals(child.catEmployee))) {

			parent.junior = child.senior;

			// If the child has a senior, set its parent to the parent node
			if (child.senior != null) {
				child.senior.parent = parent;
			}

			child.senior = parent;

		}

		// Check if the parent is the senior or junior of its parent
		if (parent.parent != null && parent.parent.junior != null && parent.parent.junior.equals(parent)) {

			parent.parent.junior = child;
		}
		else if (parent.parent != null && parent.parent.senior != null){

			parent.parent.senior = child;
		}

		child.parent = parent.parent;
		parent.parent = child;

	}


	// Helper functions for retire
	public CatNode getCat(CatNode root, Cat key) {
		return inorder(root, key);
	}

	// Inorder traversal to find relevant node
	public CatNode inorder(CatNode node, Cat key) {
		if (node == null || key == null) {
			return null;
		}

		CatNode junior = inorder(node.junior, key);
		if (junior != null) {
			return junior;
		}
		if (node.catEmployee.equals(key)) {
			return node;
		}

		CatNode senior = inorder(node.senior, key);
		if (senior != null) {
			return senior;
		}
		return null;
	}

	public CatNode removeCat(CatNode root, CatNode keyNode) {
		// Return null if the root node is null
		if (root == null) {
			return null;
		}

		// Check if the root node is the node to be removed
		if (root.catEmployee.equals(keyNode.catEmployee)) {
			// If the node to be removed has no children, set the root to null
			if (keyNode.senior == null && keyNode.junior == null) {
				root = null;
			}
			// If the node to be removed has only a junior child, set the root to the junior child and set the parent pointer of the junior child
			else if (keyNode.senior == null) {
				root = root.junior;
				root.parent = keyNode.parent;
			}
			// If the node to be removed has only a senior child, set the root to the senior child and set the parent pointer of the senior child
			else if (keyNode.junior == null) {
				root = root.senior;
				root.parent = keyNode.parent;
			}
			// If the node to be removed has both a junior and senior child,
			else {

				CatNode parent = root.parent;

				CatNode oldestCat = getCat(keyNode, getOldest(keyNode.junior));

				oldestCat.senior = keyNode.senior;

				keyNode.senior.parent = oldestCat;

				if (parent != null) {
					if (parent.junior != null && (root.parent == null) ? false : (root.parent.junior == root)) {
						parent.junior = root.junior;
					}
					else if (parent.senior != null) {
						parent.senior = root.junior;
					}
				}
				else {
					root.junior.parent = null;
				}

				root = root.junior;
				root.parent = parent;
			}
		}
		// If the root node is not the node to be removed, recursively remove the node in the junior and senior subtrees
		else {
			root.junior = removeCat(root.junior, keyNode);
			root.senior = removeCat(root.senior, keyNode);
		}
		return root;
	}


	public CatNode downHeap(CatNode node) {

		rotateTree(node.parent, node);

		CatNode current = node;

		while (current.parent != null) {
			current = current.parent;
		}

		return current;
	}


	public CatNode findHeapBreak(CatNode root) {
		if (root == null) {
			return null;
		}

		if (root.parent != null && root.parent.catEmployee.getFurThickness() < root.catEmployee.getFurThickness()) {

			CatNode sibling = compareSibling(root);

			return (sibling == null || root.catEmployee.getFurThickness() > sibling.catEmployee.getFurThickness()) ? root : sibling;
		}
		else {

			// Recursively (postorder) traverse the junior / senior subtrees of the root
			CatNode tempJunior = findHeapBreak(root.junior);
			CatNode tempSenior = findHeapBreak(root.senior);

			// return the root of the first subtree where a break in the heap property is found, or null if no break is found
			CatNode result = null;

			// check if a break was found in the junior subtree
			if (tempJunior != null) {
				result = tempJunior;
			}
			else {
				// check if a break was found in the senior subtree
				if (tempSenior != null) {
					result = tempSenior;
				}
				else {
					// no break was found in either subtree
					result = null;
				}
			}

			return result;
		}
	}

	// Compare the given node to its sibling
	public CatNode compareSibling(CatNode root) {
		// If the root is a junior, compare it to its senior sibling
		if (root.parent.senior != null && (root.parent == null) ? false : (root.parent.junior == root)) {
			return root.parent.senior;
		}
		// Otherwise, compare it to its junior sibling
		else if (root.parent.junior != null) {
			return root.parent.junior;
		}
		// If the root has no sibling, return null
		else {
			return null;
		}
	}

	// Other helper functions
	public Cat getOldest(CatNode node) {
		// Base case
		if (node != null && root != null) {
			if (node.senior == null) {
				return node.catEmployee;
			}
			else {
				// Recursively go to next oldest
				return getOldest(node.senior);
			}
		}
		else {
			return null;
		}

	}

	public Cat getYoungest(CatNode node) {
		// Base case
		if (node != null && root != null) {
			if (node.junior == null) {
				return node.catEmployee;
			}
			else {
				// Recursively go to next youngest
				return getYoungest(node.junior);
			}
		}
		else {
			return null;
		}

	}

	public void copy(CatNode root) {

		if (root != null) {
			this.hire(root.catEmployee);
		}

		if (root.junior != null) {
			copy(root.junior);
		}
		if (root.senior != null) {
			copy(root.senior);
		}

	}
}


