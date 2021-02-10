package com.team5.game.sprites.pathfinding;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.team5.game.tools.Atlas;
import com.team5.game.tools.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class NodeGraphTest {

    private NodeGraph nodeGraph;

    @Mock
    private TextureAtlas mockTextureAtlas;

    @BeforeEach
    public void setup() {
        GameState.initialise();
        Atlas.overrideTextureAtlasWith(mockTextureAtlas);
        nodeGraph = new NodeGraph();
    }

    @Test
    public void nodeGraphHas25Rooms() {
        assertEquals(25, nodeGraph.rooms.size);
    }
    @Test
    public void nodeGraphHas160Links() {
        assertEquals(160, nodeGraph.links.size);
    }
}