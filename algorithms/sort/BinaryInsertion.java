/**
 * Insertion Sort class provides a static method for sorting an array using an
 * optimized binary insertion sort with half exchanges.
 *
 * @author SylvanasSun
 *
 */
public class BinaryInsertion {

	// This class should not be instantiated.
	private BinaryInsertion() {
	}

	/**
	 * Rearranges the array in ascending order, using the natural order.
	 *
	 * @param a
	 *            the array to be sorted
	 */
	public static void sort(Comparable[] a) {
		int length = a.length;
		for (int i = 1; i < length; i++) {
			// binary search to determine index j at which to insert a[i]
			Comparable v = a[i];
			int lo = 0, hi = i;
			while (lo < hi) {
				int mid = lo + (hi - lo) / 2;
				if (less(v, a[mid]))
					hi = mid;
				else
					lo = mid + 1;
			}

			// insertion sort with "half exchanges"
			// (insert a[i] at index j and shift a[j], ..., a[i-1] to right)
			for (int j = i; j > lo; --j)
				a[j] = a[j - 1];
			a[lo] = v;
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
		for (int i = 1; i < length; i++) {
			// binary search to determine index j at which to insert a[i]
			Object v = a[i];
			int lo = 0, hi = 1;
			while (lo < hi) {
				int mid = lo + (hi - lo) / 2;
				if (less(comparator, v, a[mid])) {
					hi = mid;
				} else {
					lo = mid + 1;
				}
			}

			// insertion sort with "half exchanges"
			// (insert a[i] at index j and shift a[j],...,a[i-1] to right)
			for (int j = i; j > lo; --j) {
				a[j] = a[j - 1];
			}
			a[lo] = v;
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
		BinaryInsertion.sort(a);
		BinaryInsertion.print(a);
	}

}
