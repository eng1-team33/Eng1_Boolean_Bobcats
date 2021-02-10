package com.team5.game.environment;

import com.team5.game.tools.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BrigTest {

    private Brig brig;

    @BeforeEach
    void setup() {
        GameState.initialise();
        brig = new Brig();
    }

    @Test
    void imprisonIncrementsPrisonerNumber() {
        brig.imprison();
        assertEquals(brig.getPrisoners(), 1);
        brig.imprison();
        assertEquals(brig.getPrisoners(), 2);
    }

    @Test
    void allCaught() {
        for (int i = 0; i < 5; i++) {
            brig.imprison();
        }
        assertFalse(brig.allCaught());
        brig.imprison();
        assertTrue(brig.allCaught());
    }
}