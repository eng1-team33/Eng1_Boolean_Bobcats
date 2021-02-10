package com.team5.game.environment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application;
import com.team5.game.tools.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mock.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class SystemCheckerTest {

    private SystemChecker systemChecker;

    @BeforeEach
    void setup() {
        GameState.initialise();
        Gdx.app = mock(Application.class);
        systemChecker = new SystemChecker();
    }

    @Test
    void breakSystemIncrementsSystemBroken() {
        systemChecker.breakSystem();
        assertEquals(systemChecker.getSystemsBroken(), 1);
        systemChecker.breakSystem();
        assertEquals(systemChecker.getSystemsBroken(), 2);
    }

    @Test
    void testAllSystemsBroken() {
        for (int i = 0; i < 14; i++) {
            systemChecker.breakSystem();
        }
        assertFalse(systemChecker.allSystemsBroken());
        systemChecker.breakSystem();
        assertTrue(systemChecker.allSystemsBroken());
    }


}