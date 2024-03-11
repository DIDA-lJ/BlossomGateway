package blossom.gateway.common.utils;

/**
 * @author linqi
 * @version 1.0.0
 * @description 系统
 */

public class SystemUtil {
    public static boolean isLinux() {
        String osName = System.getProperty("os.name").toLowerCase();
        return osName.contains("linux");
    }

}