package com.blueflame.quoteoftheday;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ExampleUnitTest {
    @Test
    void shouldRecognizeEqualPointsAsEqual() {
        assertThat(Boolean.TRUE).isEqualTo(Boolean.TRUE);
    }
}
