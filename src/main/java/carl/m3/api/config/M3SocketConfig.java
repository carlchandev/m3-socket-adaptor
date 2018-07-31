package carl.m3.api.config;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class M3SocketConfig {
    private String host;
    private int port;
    private String username;
    private String password;
}
