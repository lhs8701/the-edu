package joeuncamp.dabombackend.util;

import jakarta.servlet.http.HttpServletRequest;

public class DeviceUtil {
    /**
     * 현재 접속 중인 장치가 IOS인지 확인합니다.
     *
     * @param request request
     * @return true/false
     */
    public static boolean isIosAppSupported(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        return userAgent != null && userAgent.contains("iPhone") && userAgent.contains("Mobile");
    }
}
