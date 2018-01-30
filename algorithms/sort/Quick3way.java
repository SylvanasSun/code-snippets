/**
 * Quick Sort using three-way split
 *
 * @author SylvanasSun
 *
 */
public class Quick3way {

	// This class should not be instantiated.
	private Quick3way() {
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

	// quicksort the subarray a[lo .. hi] using 3-way partitioning
	private static void sort(Comparable[] a, int lo, int hi) {
		if (hi <= lo)
			return;

		int lt = lo, i = lo + 1, gt = hi;
		Comparable v = a[lo]; // partition element

		// a[lo..lt-1] < a[lt..gt] < a[gt+1..hi]
		while (i <= gt) {
			int cmp = a[i].compareTo(v);
			if (cmp < 0) {
				exch(a, i++, lt++);
			} else if (cmp > 0) {
				exch(a, i, gt--);
			} else {
				i++;
			}
		}
		sort(a, lo, lt - 1);
		sort(a, gt + 1, hi);
	}

	private static void sort(Object[] a, int lo, int hi, Comparator comparator) {
		if (hi <= lo)
			return;

		int lt = lo, i = lo + 1, gt = hi;
		Object v = a[lo]; // partition element

		// a[lo..lt-1] < a[lt..gt] < a[gt+1..hi]
		while (i <= gt) {
			int cmp = comparator.compare(a[i], v);
			if (cmp < 0) {
				exch(a, i++, lt++);
			} else if (cmp > 0) {
				exch(a, i, gt--);
			} else {
				i++;
			}
		}
		sort(a, lo, lt - 1, comparator);
		sort(a, gt + 1, hi, comparator);
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

	public static void main(String[] args) {
		String[] a = new Scanner(System.in).nextLine().split("\\s+");
		Quick3way.sort(a);
		Quick3way.print(a);
	}

}
