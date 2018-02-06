/**
 * Interface PackageScanner is the basic interface for package scanning.
 *
 * Created by SylvanasSun on 10/13/2017.
 */
public interface PackageScanner {

    /**
     * Scanning specified package then return a class list of the after the scan.
     */
    List<Class<?>> scan(String packageName);

    /**
     * Scanning specified package then invoke callback and
     * return a class list of the after the scan.
     */
    List<Class<?>> scan(String packageName, ScannedClassHandler handler);

}
