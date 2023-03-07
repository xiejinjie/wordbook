package top.kcoder.wordbook.util;

/**
 * @author yeachiu
 * @date 2023/3/7
 */
public class SystemUtil {
    public static boolean isWindows() {
        String osName = getOsName();
        return osName != null && osName.startsWith("Windows");
    }

    public static boolean isMacOs() {
        String osName = getOsName();
        return osName != null && osName.startsWith("Mac");
    }

    public static String getOsName() {
        return System.getProperty("os.name");
    }
}
