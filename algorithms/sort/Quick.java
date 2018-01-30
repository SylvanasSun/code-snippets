/**
 * Quick Sort
 *
 * @author SylvanasSun
 *
 */
public class Quick {

	// This class should not be instantiated.
	private Quick() {
	}

	/**
	 * Rearranges the array in ascending order, using the natural order.
	 *
	 * @param a
	 *            a the array to be sorted
	 */
	public static void sort(Comparable[] a) {
		shuffle(a);
		sort(a, 0, a.length - 1);
	}

	/**
	 * Rearranges the array in ascending order, using a comparator.
	 *
	 * @param comparator
	 *            compare the comparator specifying the order
	 * @param a
	 *            a the array to be sorted
	 */
	public static void sort(Comparator comparator, Object[] a) {
		shuffle(a);
		sort(a, 0, a.length - 1, comparator);
	}

	/**
	 * Print array elements to console
	 *
	 * @param a
	 *            a the array element print to console
	 */
	public static void print(Object[] a) {
		for (int i = 0; i < a.length; i++) {
			System.out.print(a[i] + " ");
		}
	}

	// partition the subarray a[lo..hi] so that a[lo..j-1] <= a[j] <= a[j+1..hi]
	// and return the index j.
	private static int partition(Comparable[] a, int lo, int hi) {
		int i = lo; // left point
		int j = hi + 1; // right point
		Comparable v = a[lo]; // partition element

		while (true) {
			// scan left point
			while (less(a[++i], v)) {
				if (i == hi)
					break;
			}

			// scan right point
			while (less(v, a[--j])) {
				if (j == lo)
					break;
			}

			// check if point cross
			if (i >= j)
				break;

			exch(a, i, j);
		}

		// put partition element v to a[j]
		exch(a, lo, j);
		// now a[lo..j-1] <= a[j] <= a[j+1..hi]
		return j;
	}

	private static int partition(Object[] a, int lo, int hi, Comparator comparator) {
		int i = lo; // left point
		int j = hi + 1; // right point
		Object v = a[lo]; // partition element

		while (true) {
			// scan left point
			while (less(comparator, a[++i], v)) {
				if (i == hi)
					break;
			}

			// scan right point
			while (less(comparator, v, a[--j])) {
				if (j == lo)
					break;
			}

			// check if point cross
			if (i >= j)
				break;

			exch(a, i, j);
		}

		exch(a, lo, j);
		return j;
	}

	private static void sort(Comparable[] a, int lo, int hi) {
		if (hi <= lo)
			return;

		int j = partition(a, lo, hi);
		sort(a, lo, j - 1);
		sort(a, j + 1, hi);
	}

	private static void sort(Object[] a, int lo, int hi, Comparator comparator) {
		if (hi <= lo)
			return;

		int j = partition(a, lo, hi, comparator);
		sort(a, lo, j - 1, comparator);
		sort(a, j + 1, hi, comparator);
	}

	// a < b ?
	private static boolean less(Comparable a, Comparable b) {
		return a.compareTo(b) < 0;
	}

	// a < b ?
	private static boolean less(Comparator comparator, Object a, Object b) {
		return comparator.compare(a, b) < 0;
	}

	// exchange a[i] and a[j]
	private static void exch(Object[] a, int i, int j) {
		Object temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}

	// random sort an array
	private static void shuffle(Object[] a) {
		if (a == null)
			throw new IllegalArgumentException("array is null.");
		Random random = new Random();
		int N = a.length;
		for (int i = 0; i < N; i++) {
			int j = i + random.nextInt(N - i);
			Object temp = a[i];
			a[i] = a[j];
			a[j] = temp;
		}
	}

	// test
	public static void main(String[] args) {
		String[] a = new Scanner(System.in).nextLine().split("\\s+");
		Quick.sort(a);
		Quick.print(a);
	}

}
