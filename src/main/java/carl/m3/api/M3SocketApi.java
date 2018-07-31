package carl.m3.api;

import MvxAPI.MvxSockJ;
import carl.m3.api.adaptor.M3SocketAdaptor;
import carl.m3.api.config.M3SocketConfig;
import carl.m3.api.request.M3ApiRequest;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class M3SocketApi implements M3Api {

    private M3SocketConfig config;
    private M3SocketAdaptor m3SocketAdaptor;

    public M3SocketApi() {
        loadM3Configuration();
        this.m3SocketAdaptor = new M3SocketAdaptor();
    }

    /**
     * This constructor is to enable unit test because JUnit 5 does not support PowerMockito yet
     *
     * @param m3SocketAdaptor
     */
    public M3SocketApi(M3SocketAdaptor m3SocketAdaptor) {
        loadM3Configuration();
        this.m3SocketAdaptor = m3SocketAdaptor;
    }

    private void loadM3Configuration() {
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("m3-socket-api.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            config = M3SocketConfig.builder()
                    .host(properties.getProperty("m3.host"))
                    .port(Integer.valueOf(properties.getProperty("m3.port")))
                    .username(properties.getProperty("m3.username"))
                    .password(properties.getProperty("m3.password"))
                    .build();
        } catch (Exception ex) {
            throw new IllegalStateException("Invalid config! Please verify your m3-socket-api.properties.", ex);
        }
        System.out.println(String.format("Connecting to [%s:%s] with user [%s]", config.getHost(), config.getPort(), config.getUsername()));
    }

    @Override
    public Map<String, String> callMethod(M3ApiRequest request) {
        System.out.println("[Details]");
        System.out.println("Program ID: " + request.getProgramId());
        System.out.println("Method: " + request.getMethod());
        Map<String, String> resultSet = new HashMap<>();
        MvxSockJ socket = null;
        try {
            socket = m3SocketAdaptor.newInstance(config.getHost(), config.getPort(), config.getUsername());
            int responseCode = socket.mvxInit("", config.getUsername(), config.getPassword(),
                    request.getProgramId());
            validateSocket(socket, responseCode);
            setSocketSettings(socket);
            setRequestFieldParamtersToSocket(request, socket);
            socket.mvxAccess(request.getMethod());
            for (String fieldId : request.getResponseFieldIds()) {
                resultSet.put(fieldId, socket.mvxGetField(fieldId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                socket.mvxClose();
            }
        }
        return resultSet;
    }

    private void setRequestFieldParamtersToSocket(M3ApiRequest request, MvxSockJ socket) {
        for (Map.Entry<String, String> entry : request.getRequestParameters().entrySet()) {
            String fieldId = entry.getKey();
            String value = entry.getValue();
            socket.mvxSetField(fieldId, value);
        }
    }

    /**
     * Set Movex socket max wait time (in ms)
     *
     * @param socket
     */
    private void setSocketSettings(MvxSockJ socket) {
        // todo Extract to config file?
        socket.mvxSetMaxWait(60000);
        socket.mvxTrans("SetLstMaxRec   " + 200);
    }

    private void validateSocket(MvxSockJ socket, int responseCode) {
        if (responseCode != 0) {
            throw new IllegalStateException("Fail to initialize M3 Socket in MvxSockJ.mvxInit(). Error: " + socket.mvxGetLastError());
        }
    }

}
