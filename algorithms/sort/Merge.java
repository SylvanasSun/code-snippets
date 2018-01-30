/**
 * Top-Down Merge Sort
 *
 * @author SylvanasSun
 *
 */
public class Merge {

	// This class should not be instantiated.
	private Merge() {
	}

	// stably merge a[lo .. mid] with a[mid+1 ..hi] using aux[lo .. hi]
	private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
		// copy a[] to aux[]
		for (int k = lo; k <= hi; k++) {
			aux[k] = a[k];
		}

		// merge back to a[]
		int i = lo, j = mid + 1;
		for (int k = lo; k <= hi; k++) {
			if (i > mid) {
				a[k] = aux[j++];
			} else if (j > hi) {
				a[k] = aux[i++];
			} else if (less(aux[j], aux[i])) {
				a[k] = aux[j++];
			} else {
				a[k] = aux[i++];
			}
		}
	}

	private static void merge(Object[] a, Object[] aux, Comparator comparator, int lo, int mid, int hi) {
		// copy a[] to aux[]
		for (int k = lo; k <= hi; k++) {
			aux[k] = a[k];
		}

		// merge back to a[]
		int i = lo, j = mid + 1;
		for (int k = lo; k <= hi; k++) {
			if (i > mid) {
				a[k] = aux[j++];
			} else if (j > hi) {
				a[k] = aux[i++];
			} else if (less(comparator, aux[j], aux[i])) {
				a[k] = aux[j++];
			} else {
				a[k] = aux[i++];
			}
		}
	}

	// mergesort a[lo..hi] using auxiliary array aux[lo..hi]
	private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
		if (hi <= lo)
			return;

		int mid = lo + (hi - lo) / 2;
		sort(a, aux, lo, mid);
		sort(a, aux, mid + 1, hi);
		merge(a, aux, lo, mid, hi);
	}

	private static void sort(Object[] a, Object[] aux, Comparator comparator, int lo, int hi) {
		if (hi <= lo)
			return;

		int mid = lo + (hi - lo) / 2;
		sort(a, aux, comparator, lo, mid);
		sort(a, aux, comparator, mid + 1, hi);
		merge(a, aux, comparator, lo, mid, hi);
	}

	/**
	 * Rearranges the array in ascending order, using the natural order.
	 *
	 * @param a
	 *            the array to be sorted
	 */
	public static void sort(Comparable[] a) {
		Comparable[] aux = new Comparable[a.length];
		sort(a, aux, 0, a.length - 1);
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
		Object[] aux = new Object[a.length];
		sort(a, aux, comparator, 0, a.length - 1);
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

	// test
	public static void main(String[] args) {
		String[] a = new Scanner(System.in).nextLine().split("\\s+");
		Merge.sort(a);
		Merge.print(a);
	}

}
