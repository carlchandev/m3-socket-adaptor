package carl.m3.api.request;

import name.falgout.jeffrey.testing.junit.mockito.MockitoExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
class M3ApiRequestTest {

    @Test
    @DisplayName("Should build the m3 socket request with supplied field/value pairs")
    void builder() {
        M3ApiRequest actual = M3ApiRequest.builder()
                .programId("MyProgram")
                .method("myMethod")
                .requestParameter("k1", "v1")
                .requestParameter("k2", "v2")
                .responseFieldId("r1")
                .responseFieldId("r2")
                .build();
        assertAll(() -> {
                    assertThat(actual)
                            .hasFieldOrPropertyWithValue("programId", "MyProgram")
                            .hasFieldOrPropertyWithValue("method", "myMethod");
                },
                () -> {
                    assertThat(actual.getRequestParameters())
                            .containsEntry("k1", "v1")
                            .containsEntry("k2", "v2");
                },
                () -> {
                    assertThat(actual.getResponseFieldIds())
                            .contains("r1", "r2");
                }
        );

    }
}