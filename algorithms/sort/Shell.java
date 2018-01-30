/**
 * Shell Sort
 *
 * @author SylvanasSun
 *
 */
public class Shell {

	// This class should not be instantiated.
	private Shell() {
	}

	/**
	 * Rearranges the array in ascending order, using the natural order.
	 *
	 * @param a
	 *            the array to be sorted
	 */
	public static void sort(Comparable[] a) {
		int h = 1;
		while (h < a.length / 3) {
			// h sequence 1,4,13,40,121,364,1093,...
			h = h * 3 + 1;
		}
		while (h >= 1) {
			for (int i = h; i < a.length; i++) {
				// a[i] insert to a[i-h],a[i-2*h],a[i-3*h]...
				for (int j = i; j >= h && less(a[j], a[j - h]); j -= h) {
					exch(a, j, j - h);
				}
			}
			h = h / 3;
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
		int h = 1;
		while (h < a.length / 3) {
			h = h * 3 + 1;
		}
		while (h >= 1) {
			for (int i = h; i < a.length; i++) {
				for (int j = i; j >= h && less(comparator, a[j], a[j - h]); j -= h) {
					exch(a, j, j - h);
				}
			}
			h = h / 3;
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
		Shell.sort(a);
		Shell.print(a);
	}

}
