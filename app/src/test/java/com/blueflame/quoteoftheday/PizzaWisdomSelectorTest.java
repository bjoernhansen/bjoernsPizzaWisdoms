package com.blueflame.quoteoftheday;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PizzaWisdomSelectorTest {
    @Test
    void cyclesThroughAllWisdomsWithoutLosingAny() {
        String first = PizzaWisdomSelector.getNextWisdom();
        Set<String> wisdoms = new HashSet<>();
        wisdoms.add(first);
        for (int i = 1; i < 25; i++) {
            wisdoms.add(PizzaWisdomSelector.getNextWisdom());
        }
        assertThat(wisdoms).hasSize(25).allSatisfy(wisdom -> assertThat(wisdom).isNotBlank());
        assertThat(PizzaWisdomSelector.getNextWisdom()).isEqualTo(first);
    }
}
