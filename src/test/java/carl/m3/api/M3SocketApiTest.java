package carl.m3.api;

import MvxAPI.MvxSockJ;
import carl.m3.api.adaptor.M3SocketAdaptor;
import carl.m3.api.request.M3ApiRequest;
import name.falgout.jeffrey.testing.junit.mockito.MockitoExtension;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Map;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class M3SocketApiTest {

    @Test
    @DisplayName("Should complete socket call and return response fields")
    void callMethod() {
        M3ApiRequest request = M3ApiRequest.builder()
                .programId("MyProgram")
                .method("myMethod")
                .requestParameter("k1", "v1")
                .requestParameter("k2", "v2")
                .responseFieldId("f1")
                .responseFieldId("f2")
                .build();

        M3SocketAdaptor m3SocketAdaptor = mock(M3SocketAdaptor.class);
        M3SocketApi api = new M3SocketApi(m3SocketAdaptor);

        MvxSockJ socket = new MvxSockJ("host", 36800, "username", 0, "");
        MvxSockJ spySocket = spy(socket);

        when(m3SocketAdaptor.newInstance(anyString(), anyInt(), anyString())).thenReturn(spySocket);
        doReturn(0).when(spySocket).mvxInit(anyString(), anyString(), anyString(), anyString());
        doReturn(0).when(spySocket).mvxSetMaxWait(anyInt());
        doReturn("v1").when(spySocket).mvxGetField("f1");
        doReturn("v2").when(spySocket).mvxGetField("f2");

        Map<String, String> actual = api.callMethod(request);

        verify(m3SocketAdaptor, times(1)).newInstance(anyString(), anyInt(), anyString());
        verify(spySocket, times(1)).mvxInit(anyString(), anyString(), anyString(), anyString());
        verify(spySocket, times(1)).mvxSetMaxWait(anyInt());
        verify(spySocket, times(1)).mvxSetField("k1", "v1");
        verify(spySocket, times(1)).mvxSetField("k2", "v2");
        verify(spySocket, times(1)).mvxGetField("f1");
        verify(spySocket, times(1)).mvxGetField("f2");

        Assertions.assertThat(actual)
                .containsEntry("f1", "v1")
                .containsEntry("f2", "v2");

    }
}