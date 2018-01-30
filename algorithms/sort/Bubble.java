/**
 * Bubble Sort
 *
 * @author SylvanasSun
 *
 */
public class Bubble {

	// This class should not be instantiated.
	private Bubble() {
	}

	/**
	 * Rearranges the array in ascending order, using the natural order.
	 *
	 * @param a
	 *            a the array to be sorted
	 */
	public static void sort(Comparable[] a) {
		for (int i = 0; i < a.length - 1; i++) {
			for (int j = 0; j < a.length - 1 - i; j++) {
				if (less(a[j + 1], a[j])) {
					exch(a, j, j + 1);
				}
			}
		}
	}

	/**
	 * Rearranges the array in ascending order, using a comparator.
	 *
	 * @param a
	 *            a the arry to be sorted
	 * @param comparator
	 *            comparator the comparator specifying the order
	 */
	public static void sort(Object[] a, Comparator comparator) {
		for (int i = 0; i < a.length - 1; i++) {
			for (int j = 0; j < a.length - 1 - i; j++) {
				if (less(comparator, a[j + 1], a[j])) {
					exch(a, j, j + 1);
				}
			}
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

	// print array elements to console
	public static void print(Comparable[] a) {
		for (int i = 0; i < a.length; i++) {
			System.out.print(a[i] + " ");
		}
	}

	// test
	public static void main(String[] args) {
		String[] s = new Scanner(System.in).nextLine().split("\\s+");
		Bubble.sort(s);
		Bubble.print(s);
	}
}
