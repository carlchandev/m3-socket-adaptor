package carl.m3.api;

import carl.m3.api.request.M3ApiRequest;

import java.util.Map;

public interface M3Api {
    Map<String, String> callMethod(M3ApiRequest request);
}
