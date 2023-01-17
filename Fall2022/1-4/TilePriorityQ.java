package finalproject;

import java.util.ArrayList;

import finalproject.system.Tile;

public class TilePriorityQ {
	// TODO level 3: Add fields that can help you implement this data type
	public ArrayList<Tile> minHeap;

	// TODO level 3: implement the constructor for the priority queue
	public TilePriorityQ (ArrayList<Tile> vertices) {

		this.minHeap = new ArrayList<>();

		for (Tile tile: vertices) {
			this.minHeap.add(tile);
		}

		for (int k = minHeap.size()/2; k >= 0; k--) {
			downHeap(k);
		}

	}

	// TODO level 3: implement remove min as seen in class
	public Tile removeMin() {

		if (minHeap.isEmpty()) {
			return null;
		}

		Tile temp = minHeap.get(0);

		if (minHeap.size() > 1) {
			minHeap.set(0, minHeap.remove(minHeap.size() - 1));
			downHeap(0);
		} else {
			minHeap.remove(0);
		}

		return temp;
	}

	// TODO level 3: implement updateKeys as described in the pdf
	public void updateKeys(Tile t, Tile newPred, double newEstimate) {

		t.predecessor = newPred;
		t.costEstimate = newEstimate;

		for (int k = minHeap.size()/2; k >= 0; k--) {
			downHeap(k);
		}

	}

	// Helper functions
	private void downHeap(int index) {
		while (true) {

			int leftChildIndex = 2 * index + 1;
			int rightChildIndex = 2 * index + 2;

			int minIndex = index;

			if (leftChildIndex < minHeap.size() && minHeap.get(leftChildIndex).costEstimate < minHeap.get(minIndex).costEstimate) {
				minIndex = leftChildIndex;
			}

			if (rightChildIndex < minHeap.size() && minHeap.get(rightChildIndex).costEstimate < minHeap.get(minIndex).costEstimate) {
				minIndex = rightChildIndex;
			}

			if (minIndex != index) {
				Tile temp = minHeap.get(index);
				minHeap.set(index, minHeap.get(minIndex));
				minHeap.set(minIndex, temp);
				index = minIndex;
			} else {
				break;
			}
		}
	}

	public boolean isEmpty() {
		return minHeap.isEmpty();
	}




}
