import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * max heap priority queue
 *
 * @author SylvanasSun
 *
 */
public class MaxPriorityQueue<T> implements Iterable<T> {

	private T[] pq; // store element array index 1 from the starting
	private int size; // number of element on priority queue
	private Comparator<T> comparator; // optional comparator

	/**
	 * Initializes an empty priority queue with the optional initial capacity
	 *
	 * @param initCapacity
	 *            the initial capacity of this priority queue
	 */
	public MaxPriorityQueue(int initCapacity) {
		pq = (T[]) new Object[initCapacity + 1];
		size = 0;
	}

	/**
	 * Initializes an empty priority queue with default capacity
	 */
	public MaxPriorityQueue() {
		this(1);
	}

	/**
	 * Initializes an empty priority queue with the optional initial capacity
	 * using the optional comparator
	 *
	 * @param initCapacity
	 *            the initial capacity of this priority queue
	 * @param comparator
	 *            comparator the order in which to compare the element
	 */
	public MaxPriorityQueue(int initCapacity, Comparator<T> comparator) {
		this.comparator = comparator;
		pq = (T[]) new Object[initCapacity + 1];
		size = 0;
	}

	/**
	 * Initializes an empty priority queue with default capacity using the
	 * optional comparator
	 *
	 * @param comparator
	 *            comparator the order in which to compare the element
	 */
	public MaxPriorityQueue(Comparator<T> comparator) {
		this(1, comparator);
	}

	/**
	 * Initializes a priority queue from the array of t.
	 *
	 * @param t
	 *            the array of t
	 */
	public MaxPriorityQueue(T[] t) {
		size = t.length;
		pq = (T[]) new Object[t.length + 1];
		for (int i = 0; i < size; i++) {
			pq[i + 1] = t[i];
		}
		// construction max heap
		for (int i = size / 2; i >= 1; i--) {
			sink(i);
		}
		assert isMaxHeap();
	}

	/**
	 * This priority queue is empty?
	 *
	 * @return {@code true} if this priority queue is empty; {@code false}
	 *         otherwise
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Returns the number of elements on this priority queue
	 *
	 * @return the number of elements on this priority queue
	 */
	public int size() {
		return size;
	}

	/**
	 * Returns a largest element on this priority queue
	 *
	 * @return a largest element on this priority queue
	 * @throws NoSuchElementException
	 *             if this priority queue is empty
	 */
	public T max() {
		if (isEmpty())
			throw new NoSuchElementException("Priority queue underflow");
		return pq[1];
	}

	/**
	 * Adds a new element to this priority queue
	 *
	 * @param t
	 *            the new element add to this priority queue
	 */
	public void insert(T t) {
		if (size >= pq.length - 1)
			resize(2 * pq.length);
		pq[++size] = t;
		swim(size);
		assert isMaxHeap();
	}

	/**
	 * Removes and returns a largest element on this priority queue
	 *
	 * @return a largest element on this priority queue
	 * @throws NoSuchElementException
	 *             if this priority queue is empty
	 */
	public T delMax() {
		if (isEmpty())
			throw new NoSuchElementException("Priority queue underflow");
		T max = pq[1];
		exch(1, size--);
		sink(1);
		pq[size + 1] = null;
		if ((size > 0) && (size == (pq.length - 1) / 4))
			resize(pq.length / 2);
		assert isMaxHeap();
		return max;
	}

	// extend priority queue capacity
	private void resize(int capacity) {
		assert capacity > size;
		T[] temp = (T[]) new Object[capacity];
		for (int i = 1; i <= size; i++) {
			temp[i] = pq[i];
		}
		pq = temp;
	}

	private void swim(int k) {
		while (k > 1 && less(k / 2, k)) {
			exch(k, k / 2);
			k = k / 2;
		}
	}

	private void sink(int k) {
		while (2 * k <= size) {
			int j = 2 * k;
			if (j < size && less(j, j + 1))
				j++;
			if (!less(k, j))
				break;
			exch(k, j);
			k = j;
		}
	}

	// i < j ?
	private boolean less(int i, int j) {
		if (comparator == null) {
			return ((Comparable<T>) pq[i]).compareTo(pq[j]) < 0;
		} else {
			return comparator.compare(pq[i], pq[j]) < 0;
		}
	}

	// exchange element pq[i] and pq[j]
	private void exch(int i, int j) {
		T temp = pq[i];
		pq[i] = pq[j];
		pq[j] = temp;
	}

	// check heap order
	// is subtree of pq[1..n] rooted at k a max heap?
	private boolean isMaxHeap(int k) {
		if (k > size)
			return true;
		int left = 2 * k;
		int right = 2 * k + 1;
		if (left <= size && less(k, left))
			return false;
		if (right <= size && less(k, right))
			return false;
		return isMaxHeap(left) && isMaxHeap(right);
	}

	// is pq[1..n] a max heap?
	private boolean isMaxHeap() {
		return isMaxHeap(1);
	}

	@Override
	public Iterator<T> iterator() {
		return new HeapIterator();
	}

	private class HeapIterator implements Iterator<T> {
		// create a new pq
		private MaxPriorityQueue<T> copy;

		public HeapIterator() {
			if (comparator == null) {
				copy = new MaxPriorityQueue<T>(size);
			} else {
				copy = new MaxPriorityQueue<T>(size, comparator);
			}
			for (int i = 1; i <= size; i++) {
				copy.insert(pq[i]);
			}
		}

		@Override
		public boolean hasNext() {
			return !copy.isEmpty();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

		@Override
		public T next() {
			if (!hasNext())
				throw new NoSuchElementException();
			return copy.delMax();
		}

	}

	// test
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		MaxPriorityQueue<String> pq = new MaxPriorityQueue<String>();
		System.out.println("Please input data.");
		while (scanner.hasNextLine()) {
			String s = scanner.nextLine();
			if ("end".equals(s))
				break;
			if (!"-".equals(s)) {
				System.out.println("insert element: " + s);
				pq.insert(s);
				System.out.println("current size: " + pq.size());
			} else {
				System.out.println("delete max element: " + pq.delMax());
				System.out.println("current size: " + pq.size());
			}
		}
		System.out.println("current size: " + pq.size());
		for (String s : pq) {
			System.out.print(s + " ");
		}
	}

}
