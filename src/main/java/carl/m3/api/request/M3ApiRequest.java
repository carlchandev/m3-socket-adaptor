package carl.m3.api.request;


import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public @Builder @Data
class M3ApiRequest {

    private String programId;
    private String method;
    @Singular private Map<String, String> requestParameters;
    @Singular private List<String> responseFieldIds;

}
