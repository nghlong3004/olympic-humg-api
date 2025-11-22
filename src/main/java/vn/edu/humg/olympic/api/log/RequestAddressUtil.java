package vn.edu.humg.olympic.api.log;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

public final class RequestAddressUtil {
    public static final String X_FORWARDED_FOR = "X-Forwarded-For";
    public static final String IP_DELIMITER = ",";

    private RequestAddressUtil() {
    }

    public static String getRelatedIP(HttpServletRequest request) {
        var ips = "";
        var headerXForwardedFor = request.getHeader(X_FORWARDED_FOR);
        var requestRemoteAddress = request.getRemoteAddr();

        String[] checkArray = new String[]{headerXForwardedFor, requestRemoteAddress};

        for (String s : checkArray) {
            if (checkText(s)) {
                ips = s;
                break;
            }
        }
        return ips;
    }

    public static String getClientIP(HttpServletRequest request) {
        var clientIP = "";
        var ips = getRelatedIP(request);

        if (StringUtils.hasLength(ips)) {
            if (ips.contains(IP_DELIMITER)) {
                String[] clientIPs = ips.split(IP_DELIMITER);
                clientIP = clientIPs[0].trim();
            }
            else {
                clientIP = ips;
            }
        }
        return clientIP;
    }

    private static boolean checkText(String text) {
        if (StringUtils.hasLength(text)) {
            var lowerStr = text.toLowerCase();
            return !"unknown".equals(lowerStr);
        }
        return false;
    }
}
