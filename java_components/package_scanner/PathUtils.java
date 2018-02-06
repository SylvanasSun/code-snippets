public class PathUtils {

    private static final String FILE_SEPARATOR = System.getProperty("file.separator");

    private static final String CLASS_FILE_SUFFIX = ".class";

    private static final String JAR_PROTOCOL = "jar";

    private static final String FILE_PROTOCOL = "file";

    private PathUtils() {
    }

    public static String trimSuffix(String filename) {
        if (filename == null || "".equals(filename))
            return filename;

        int dotIndex = filename.lastIndexOf(".");
        if (-1 == dotIndex)
            return filename;
        return filename.substring(0, dotIndex);
    }

    public static String pathToPackage(String path) {
        if (path == null || "".equals(path))
            return path;

        if (path.startsWith(FILE_SEPARATOR))
            path = path.substring(1);
        return path.replace(FILE_SEPARATOR, ".");
    }

    public static String packageToPath(String packageName) {
        if (packageName == null || "".equals(packageName))
            return packageName;
        return packageName.replace(".", FILE_SEPARATOR);
    }

    /**
     * By protocol of the url get resource type.
     */
    public static ResourceType getResourceType(URL url) {
        String protocol = url.getProtocol();
        switch (protocol) {
            case JAR_PROTOCOL:
                return ResourceType.JAR;
            case FILE_PROTOCOL:
                return ResourceType.FILE;
            default:
                return ResourceType.INVALID;
        }
    }

    public static boolean isClassFile(String path) {
        if (path == null || "".equals(path))
            return false;
        return path.endsWith(CLASS_FILE_SUFFIX);
    }

    /**
     * Return main path of the url.
     * Example:
     * "file:/com/example/hello" to "/com/example/hello"
     * "jar:file:/com/example/hello.jar!/" to "/com/example/hello.jar"
     */
    public static String getUrlMainPath(URL url) throws UnsupportedEncodingException {
        if (url == null)
            return "";

        String filePath = URLDecoder.decode(url.getFile(), "utf-8");
        // if file is not the jar
        int pos = filePath.indexOf("!");
        if (-1 == pos)
            return filePath;

        return filePath.substring(5, pos);
    }

    public static String concat(Object... args) {
        if (args == null || args.length == 0)
            return "";

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < args.length; i++)
            stringBuilder.append(args[i]);

        return stringBuilder.toString();
    }

}
