package carl.m3.api.adaptor;

import MvxAPI.MvxSockJ;

public class M3SocketAdaptor {

    public MvxSockJ newInstance(String host, int port, String username) {
        return new MvxSockJ(host, port, username, 0, "");
    }
}
