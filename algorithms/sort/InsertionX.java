/**
 * Insertion Sort. class provides static methods for sorting an array using an
 * optimized version of insertion sort (with half exchanges and a sentinel).
 *
 * @author SylvanasSun
 *
 */
public class InsertionX {

	// This class should not be instantiated.
	private InsertionX() {
	}

	/**
	 * Rearranges the array in ascending order, using the natural order.
	 *
	 * @param a
	 *            the array to be sorted
	 */
	public static void sort(Comparable[] a) {
		int length = a.length;

		// put smallest element in position to serve as sentinel
		int exchanges = 0;
		for (int i = length - 1; i > 0; i--) {
			if (less(a[i], a[i - 1])) {
				exch(a, i, i - 1);
				exchanges++;
			}
		}
		if (exchanges == 0)
			return;

		// insertion sort with half-exchanges
		for (int i = 2; i < length; i++) {
			Comparable v = a[i];
			int j = i;
			while (less(v, a[j - 1])) {
				a[j] = a[j - 1];
				j--;
			}
			a[j] = v;
		}
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
		int length = a.length;

		// put smallest element in position to serve as sentinel
		int exchanges = 0;
		for (int i = length - 1; i > 0; i--) {
			if (less(comparator, a[i], a[i - 1])) {
				exch(a, i, i - 1);
				exchanges++;
			}
		}
		if (exchanges == 0)
			return;

		// insertion sort with half-exchanges
		for (int i = 2; i < length; i++) {
			Object v = a[i];
			int j = i;
			while (less(comparator, v, a[j - 1])) {
				a[j] = a[j - 1];
				j--;
			}
			a[j] = v;
		}
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

	// test
	public static void main(String[] args) {
		String[] a = new Scanner(System.in).nextLine().split("\\s+");
		InsertionX.sort(a);
		InsertionX.print(a);
	}

}
