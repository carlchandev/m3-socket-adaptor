package carl.m3.api;

import carl.m3.api.request.M3ApiRequest;

import java.util.Map;

public class M3SocketApiTestMain {

    private static final M3Api m3Api = new M3SocketApi();

    public static void main(String[] args) {
        if (args.length < 2) {
            M3SocketApiTestMain main = new M3SocketApiTestMain();
            main.testCall();
        }
        // TODO another way to read parameter, e.g. console
    }

    private void testCall() {
        M3ApiRequest request = M3ApiRequest.builder()
                .programId("MMS200MI")
                .method("GetItmBasic")
                .requestParameter("CONO", "100")
                .requestParameter("ITNO", "THR0000037-0053")
                .responseFieldId("ITDS")
                .build();
        printRequest(request);
        Map<String, String> resultSet = m3Api.callMethod(request);
        System.out.println("=============================================");
        printResponse(resultSet);
    }

    private void printResponse(Map<String, String> resultSet) {
        StringBuilder response = new StringBuilder("[Response]\n");
        for (Map.Entry<String, String> entry : resultSet.entrySet()) {
            response.append(entry.getKey()).append(" -> ").append(entry.getValue()).append("\n");
        }
        System.out.println(response.toString());
    }

    private void printRequest(M3ApiRequest request) {
        System.out.println("[Request]");
        for (Map.Entry<String, String> entry : request.getRequestParameters().entrySet()) {
            String fieldId = entry.getKey();
            String value = entry.getValue();
            System.out.println(String.format("%s -> %s", fieldId, value));
        }
    }

}
