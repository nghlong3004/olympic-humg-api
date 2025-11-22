package vn.edu.humg.olympic.api.constant;

public final class APIConstant {
    private static final String COMMON_PATH = "/api";
    private static final String API_VERSION = "/v1";
    public static final String API_BASE_PATH = COMMON_PATH + API_VERSION;

    public static final String API_AUTH_PATH = API_BASE_PATH + "/auth";

    public static final String REGISTER = "/register";

    public static final String LOGIN = "/login";

    public static final String API_REGISTER_PATH = API_AUTH_PATH + REGISTER;

    public static final String API_LOGIN_PATH = API_AUTH_PATH + LOGIN;

    private APIConstant() {
        throw new UnsupportedOperationException("This class should never be instantiated");
    }
}
