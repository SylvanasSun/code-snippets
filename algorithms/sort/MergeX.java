/**
 * Merge Sort optimized using insertion handle small array.
 *
 * @author SylvanasSun
 *
 */
public class MergeX {
	private static final int CUTOFF = 7; // cutoff to insertion sort

	// This class should not be instantiated.
	private MergeX() {
	}

	/**
	 * Rearranges the array in ascending order, using the natural order.
	 *
	 * @param a
	 *            the array to be sorted
	 */
	public static void sort(Comparable[] a) {
		Comparable[] aux = a.clone();
		sort(aux, a, 0, a.length - 1);
	}

	/**
	 * Rearranges the array in ascending order, using the provided order.
	 *
	 * @param a
	 *            the array to be sorted
	 * @param comparator
	 *            the comparator that defines the total order
	 */
	public static void sort(Object[] a, Comparator comparator) {
		Object[] aux = a.clone();
		sort(aux, a, 0, a.length - 1, comparator);
	}

	private static void merge(Comparable[] src, Comparable[] dst, int lo, int mid, int hi) {
		int i = lo, j = mid + 1;
		for (int k = lo; k <= hi; k++) {
			if (i > mid) {
				dst[k] = src[j++];
			} else if (j > hi) {
				dst[k] = src[i++];
			} else if (less(src[j], src[i])) {
				dst[k] = src[j++];
			} else {
				dst[k] = src[i++];
			}
		}
	}

	private static void merge(Object[] src, Object[] dst, Comparator comparator, int lo, int mid, int hi) {
		int i = lo, j = mid + 1;
		for (int k = lo; k <= hi; k++) {
			if (i > mid) {
				dst[k] = src[j++];
			} else if (j > hi) {
				dst[k] = src[i++];
			} else if (less(comparator, src[j], src[i])) {
				dst[k] = src[j++];
			} else {
				dst[k] = src[i++];
			}
		}
	}

	private static void sort(Comparable[] src, Comparable[] dst, int lo, int hi) {
		// if (hi <= lo) return;
		if (hi <= lo + CUTOFF) {
			insertionSort(dst, lo, hi);
			return;
		}
		int mid = lo + (hi - lo) / 2;
		sort(dst, src, lo, mid);
		sort(dst, src, mid + 1, hi);

		// using System.arraycopy() is a bit faster than the above loop
		if (!less(src[mid + 1], src[mid])) {
			System.arraycopy(src, lo, dst, lo, hi - lo + 1);
			return;
		}

		merge(src, dst, lo, mid, hi);
	}

	private static void sort(Object[] src, Object[] dst, int lo, int hi, Comparator comparator) {
		if (hi <= lo + CUTOFF) {
			insertionSort(dst, lo, hi, comparator);
			return;
		}
		int mid = lo + (hi - lo) / 2;
		sort(dst, src, lo, mid, comparator);
		sort(dst, src, mid + 1, hi, comparator);

		// using System.arraycopy() is a bit faster than the above loop
		if (!less(comparator, src[mid + 1], src[mid])) {
			System.arraycopy(src, lo, dst, lo, hi - lo + 1);
			return;
		}

		merge(src, dst, comparator, lo, mid, hi);
	}

	// using insertion sort handle small array
	private static void insertionSort(Comparable[] a, int lo, int hi) {
		for (int i = lo; i <= hi; i++) {
			for (int j = i; j > lo && less(a[j], a[j - 1]); j--) {
				exch(a, j, j - 1);
			}
		}
	}

	private static void insertionSort(Object[] a, int lo, int hi, Comparator comparator) {
		for (int i = lo; i <= hi; i++) {
			for (int j = i; j > lo && less(comparator, a[j], a[j - 1]); j--) {
				exch(a, j, j - 1);
			}
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
		MergeX.sort(a);
		MergeX.print(a);
	}

}
