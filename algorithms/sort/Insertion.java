/**
 * Insertion Sort
 *
 * @author SylvanasSun
 *
 */
public class Insertion {

	// This class should not be instantiated
	private Insertion() {
	}

	/**
	 * Rearranges the array in ascending order, using the natural order.
	 *
	 * @param a
	 *            a the array to be sorted
	 */
	public static void sort(Comparable[] a) {
		for (int i = 0; i < a.length; i++) {
			// a[i] insert to a[i-1]、a[i-2]、a[i-3]...
			for (int j = i; j > 0 && less(a[j], a[j - 1]); j--) {
				exch(a, j, j - 1);
			}
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
		for (int i = 0; i < a.length; i++) {
			for (int j = i; j > 0 && less(comparator, a[j], a[j - 1]); j--) {
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
		String[] s = new Scanner(System.in).nextLine().split("\\s+");
		Insertion.sort(s);
		Insertion.print(s);
	}

}
