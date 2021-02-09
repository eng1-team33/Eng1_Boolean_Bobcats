package com.team5.game.sprites.pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.team5.game.tools.GameState;

public class NodeGraph implements IndexedGraph<Node> {

    /*
    NodeGraph creates a graph of Nodes and Links and allows
    an NPC to find a path using A* search from one point
    in the map to another.
     */

    //A* Search Elements
    NodeHeuristic nodeHeuristic;
    Array<Node> nodes;
    Array<Room> rooms;
    Array<System> systems;
    Array<Link> links;

    ObjectMap<Node, Array<Connection<Node>>> nodeMap = new ObjectMap<>();

    private int nodeIndex = 0;

    public NodeGraph() {
        nodeHeuristic = new NodeHeuristic();
        nodes = new Array<>();
        rooms = new Array<>();
        systems = new Array<>();
        links = new Array<>();

        buildNodeMap();
        setSystemsBroken();
    }

    private void setSystemsBroken() {
        for(int i = 0; i < systems.size; i++) {
            systems.get(i).setBroken(GameState.getInstance().getSystemsBroken()[i]);
        }
    }

    //Adding a new system to the graph.
    public void addSystem(System system) {
        systems.add(system);
        this.addNode(system);
    }

    //Adding a new room to the graph.
    public void addRoom(Room room) {
        rooms.add(room);
        this.addNode(room);
    }

    //Adding a new node to the graph.
    public void addNode(Node node) {
        node.setIndex(nodeIndex);
        nodeIndex++;

        nodes.add(node);
    }

    //Links a node to one other node.
    public void linkNodes(Node fromNode, Node toNode) {
        Link link = new Link(fromNode, toNode);

        if (!nodeMap.containsKey(fromNode)) {
            nodeMap.put(fromNode, new Array<Connection<Node>>());
        }
        nodeMap.get(fromNode).add(link);
        links.add(link);
    }

    //Links a node to an array of other nodes.
    public void linkNodes(Node fromNode, Node... args) {
        for (Node node : args) {
            this.linkNodes(fromNode, node);
        }
    }

    //Returns a path from a startNode to a goalNode.
    public GraphPath<Node> findPath(Node startNode, Node goalNode) {
        GraphPath<Node> path = new DefaultGraphPath<>();
        new IndexedAStarPathFinder<>(this).searchNodePath(startNode, goalNode, nodeHeuristic, path);
        return path;
    }

    public Array<System> getSystems() {
        return systems;
    }

    //Returns a random room given an exception.
    public Node getRandomRoom(Node exception) {
        Node node = exception;
        while (node.equals(exception)) {
            node = rooms.random();
        }
        return node;
    }

    public Node getRandomRoom() {
        return rooms.random();
    }

    public System getRandomSystem() {
        return systems.random();
    }

    //Called every frame, draws all of the systems on the map.
    public void drawSystems(SpriteBatch batch) {
        for (System sys : systems) {
            sys.draw(batch);
        }
    }

    @Override
    public int getIndex(Node node) {
        return node.index;
    }

    @Override
    public int getNodeCount() {
        return nodes.size;
    }

    @Override
    public Array<Connection<Node>> getConnections(Node fromNode) {
        if (nodeMap.containsKey(fromNode)) {
            return nodeMap.get(fromNode);
        }

        return new Array<>();
    }

    //Builds the graph full of nodes used for our map.
    void buildNodeMap() {
        //Declaring the Nodes
        Room bridge = new Room("Bridge", 49.5f, 92.5f, "Bridge", 12, 6);
        Node bridgeExit = new Node(49.5f, 87.5f, "Bridge Exit", 2, 0);
        Node centreNorthWing = new Node(49.5f, 79.5f, "Centre North Wing", 1, 1);

        Room brig = new Room("Brig", 19.5f, 90, "Brig", 12, 2);
        Node brigExit = new Node(19.5f, 87.5f, "Brig Exit", 2, 0);
        Node leftNorthWing = new Node(19.5f, 79.5f, "Left North Wing", 1, 1);

        Room rightInfirmary = new Room("Infirmary", 81.5f, 88.5f, "Right Infirmary", 6, 2f);
        Room leftInfirmary = new Room("Infirmary", 74f, 88.5f, "Left Infirmary", 1, 1.5f);
        Node infirmaryExit = new Node(81.5f, 84.5f, "Infirmary Exit", 2, 0);
        Node rightNorthWing = new Node(81.5f, 79.5f, "Right North Wing", 1, 1);
        System leftHealthPad = new System("Infirmary", 74f, 91.5f, "Left Health Pad", 73, 92);
        System rightHealthPad = new System("Infirmary", 83f, 91.5f, "Right Health Pad", 82, 92);

        Room topLeftCafeteria = new Room("Cafeteria", 46.5f, 68.5f, "Top Left Cafeteria");
        Room topRightCafeteria = new Room("Cafeteria", 51.5f, 68.5f, "Top Right Cafeteria");
        Room midLeftCafeteria = new Room("Cafeteria", 46.5f, 62.5f, "Mid Left Cafeteria", 1, 1);
        Room midRightCafeteria = new Room("Cafeteria", 51.5f, 62.5f, "Mid Right Cafeteria", 1, 1);
        Room bottomLeftCafeteria = new Room("Cafeteria", 46.5f, 56.5f, "Bottom Left Cafeteria");
        Room bottomRightCafeteria = new Room("Cafeteria", 51.5f, 56.5f, "Bottom Right Cafeteria");
        Node westCafeteriaExit = new Node(40.5f, 62, "West Cafeteria Exit", 0, 1.5f);
        Node eastCafeteriaExit = new Node(58.5f, 62, "East Cafeteria Exit", 0, 1.5f);
        Node northCafeteriaExit = new Node(49.5f, 70.5f, "North Cafeteria Exit", 2, 0);
        Node southCafeteriaExit = new Node(49.5f, 54.5f, "South Cafeteria Exit", 2, 0);
        System leftVendingMachine = new System("Cafeteria", 45, 68.5f, "Vending Machine", 44, 69);
        System rightVendingMachine = new System("Cafeteria", 54, 68.5f, "Vending Machine", 53, 69);

        //PQ stands for personal quarters, that's just a bit long for a name
        Room midRightPQ = new Room("PQ", 23.5f, 61.5f, "Mid Right PQ");
        Room midCentrePQ = new Room("PQ", 14.5f, 61.5f, "Mid Centre PQ");
        Room midLeftPQ = new Room("PQ", 6.5f, 61.5f, "Mid Left PQ");
        Room topRightPQ = new Room("PQ", 23.5f, 66.5f, "Top Right PQ");
        Room topCentrePQ = new Room("PQ", 14.5f, 66.5f, "Top Centre PQ");
        Room topLeftPQ = new Room("PQ", 6.5f, 66.5f, "Top Left PQ");
        Room bottomRightPQ = new Room("PQ", 23.5f, 56.5f, "Bottom Right PQ");
        Room bottomCentrePQ = new Room("PQ", 14.5f, 56.5f, "Bottom Centre PQ");
        Room bottomLeftPQ = new Room("PQ", 6.5f, 56.5f, "Bottom Left PQ");
        Node PQExit = new Node(26.5f, 62, "PQ Exit", 0, 1.5f);
        System topBeds = new System("PQ", 22.5f, 69f, "Top Beds", 9, 68);
        System midBeds = new System("PQ", 22.5f, 64f, "Mid Beds", 9, 63);
        System bottomBeds = new System("PQ", 22.5f, 59f, "Bottom Beds", 9, 58);

        Room cargo = new Room("Cargo", 81.5f, 62.5f, "Cargo Bay", 2, 3);
        Node northCargoExit = new Node(81.5f, 74.5f, "North Cargo Exit", 2, 0);
        Node westCargoExit = new Node(73.5f, 62f, "West Cargo Exit", 0, 1.5f);
        Node southCargoExit = new Node(81.5f, 53.5f, "South Cargo Exit", 1, 0);
        System topLeftBoxes = new System("Cargo", 77.5f, 66.5f, "Top Left Boxes", 74, 67);
        System topRightBoxes = new System("Cargo", 85.5f, 66.5f, "Top Right Boxes", 82, 67);
        System bottomLeftBoxes = new System("Cargo", 77.5f, 60.5f, "Bottom Left Boxes", 74, 55);
        System bottomRightBoxes = new System("Cargo", 85.5f, 60.5f, "Bottom Right Boxes", 82, 55);

        Room reactor = new Room("Reactor", 49.5f, 37.5f, "Reactor", 1, 1);
        Node northReactorExit = new Node(49.5f, 45.5f, "North Reactor Exit", 2, 0);
        Node eastReactorExit = new Node(55.5f, 37, "East Reactor Exit", 0, 1.5f);
        System topLeftReactor = new System("Reactor", 46.5f, 39.5f, "Top Left Reactor", 45, 40);
        System topRightReactor = new System("Reactor", 52.5f, 39.5f, "Top Right Reactor", 51, 40);
        System bottomLeftReactor = new System("Reactor", 46.5f, 36.5f, "Bottom Left Reactor", 45, 33);
        System bottomRightReactor = new System("Reactor", 52.5f, 36.5f, "Bottom Right Reactor", 51, 33);

        Room midLeftEngine = new Room("Engine", 78.5f, 36.5f, "Mid Left Engine", 2, 10);
        Room midCentreEngine = new Room("Engine", 83.5f, 36.5f, "Mid Centre Engine", 2, 10);
        Room midRightEngine = new Room("Engine", 91.5f, 36.5f, "Mid Right Engine", 5, 5);
        Room leftEngine = new Room("Engine", 78.5f, 45, "Top Left Engine");
        Room centreEngine = new Room("Engine", 83.5f, 45, "Top Centre Engine");
        Node northEngineExit = new Node(81.5f, 47.5f, "North Engine Exit", 1, 0);
        Node westEngineExit = new Node(65.5f, 37, "West Engine Exit", 0, 1.5f);
        System topLeftEngine = new System("Engine", 77.5f, 42.5f, "Top Left Engine", 72, 42);
        System topRightEngine = new System("Engine", 93.5f, 42.5f, "Top Right Engine", 88, 42);
        System bottomLeftEngine = new System("Engine", 77.5f, 31.5f, "Bottom Left Engine", 72, 31);
        System bottomRightEngine = new System("Engine", 93.5f, 31.5f, "Bottom Right Engine", 88, 31);

        //Adding the Nodes to the NodeGraph
        addNode(bridge);
        addNode(bridgeExit);
        addNode(centreNorthWing);

        addRoom(brig);
        addNode(brigExit);
        addNode(leftNorthWing);

        addRoom(rightInfirmary);
        addRoom(leftInfirmary);
        addNode(infirmaryExit);
        addNode(rightNorthWing);
        addSystem(leftHealthPad);
        addSystem(rightHealthPad);

        addRoom(topLeftCafeteria);
        addRoom(topRightCafeteria);
        addRoom(midLeftCafeteria);
        addRoom(midRightCafeteria);
        addRoom(bottomLeftCafeteria);
        addRoom(bottomRightCafeteria);
        addNode(westCafeteriaExit);
        addNode(eastCafeteriaExit);
        addNode(northCafeteriaExit);
        addNode(southCafeteriaExit);
        addSystem(leftVendingMachine);
        addSystem(rightVendingMachine);

        addRoom(midRightPQ);
        addRoom(midCentrePQ);
        addRoom(midLeftPQ);
        addRoom(topRightPQ);
        addRoom(topCentrePQ);
        addRoom(topLeftPQ);
        addRoom(bottomRightPQ);
        addRoom(bottomCentrePQ);
        addRoom(bottomLeftPQ);
        addNode(PQExit);
        addSystem(topBeds);
        addSystem(midBeds);
        addSystem(bottomBeds);

        addRoom(cargo);
        addNode(northCargoExit);
        addNode(westCargoExit);
        addNode(southCargoExit);
        addSystem(topLeftBoxes);
        addSystem(topRightBoxes);
        addSystem(bottomLeftBoxes);
        addSystem(bottomRightBoxes);

        addRoom(reactor);
        addNode(northReactorExit);
        addNode(eastReactorExit);
        addSystem(topLeftReactor);
        addSystem(topRightReactor);
        addSystem(bottomLeftReactor);
        addSystem(bottomRightReactor);

        addRoom(midLeftEngine);
        addRoom(midCentreEngine);
        addRoom(midRightEngine);
        addRoom(leftEngine);
        addRoom(centreEngine);
        addNode(northEngineExit);
        addNode(westEngineExit);
        addSystem(topLeftEngine);
        addSystem(topRightEngine);
        addSystem(bottomLeftEngine);
        addSystem(bottomRightEngine);

        //Linking the Nodes on the NodeGraph
        linkNodes(bridge, bridgeExit);
        linkNodes(bridgeExit, bridge, centreNorthWing);
        linkNodes(centreNorthWing, bridgeExit, leftNorthWing, rightNorthWing, northCafeteriaExit);

        linkNodes(brig, brigExit);
        linkNodes(brigExit, brig, leftNorthWing);
        linkNodes(leftNorthWing, centreNorthWing, brigExit);

        linkNodes(rightInfirmary, infirmaryExit, rightHealthPad, leftInfirmary);
        linkNodes(leftInfirmary, infirmaryExit, leftHealthPad, rightInfirmary);
        linkNodes(infirmaryExit, rightInfirmary, leftInfirmary, rightNorthWing);
        linkNodes(rightNorthWing, infirmaryExit, centreNorthWing, northCargoExit);
        linkNodes(leftHealthPad, leftInfirmary);
        linkNodes(rightHealthPad, rightInfirmary);

        linkNodes(topLeftCafeteria, northCafeteriaExit, topRightCafeteria, midLeftCafeteria, leftVendingMachine);
        linkNodes(topRightCafeteria, northCafeteriaExit, topLeftCafeteria, midRightCafeteria, rightVendingMachine);
        linkNodes(midLeftCafeteria, westCafeteriaExit, topLeftCafeteria, midRightCafeteria, bottomLeftCafeteria);
        linkNodes(midRightCafeteria, eastCafeteriaExit, topRightCafeteria, midLeftCafeteria, bottomRightCafeteria);
        linkNodes(bottomLeftCafeteria, southCafeteriaExit, midLeftCafeteria, bottomRightCafeteria);
        linkNodes(bottomRightCafeteria, southCafeteriaExit, midRightCafeteria, bottomLeftCafeteria);
        linkNodes(westCafeteriaExit, midLeftCafeteria, PQExit);
        linkNodes(eastCafeteriaExit, midRightCafeteria, westCargoExit);
        linkNodes(northCafeteriaExit, topLeftCafeteria, topRightCafeteria, centreNorthWing);
        linkNodes(southCafeteriaExit, bottomLeftCafeteria, bottomRightCafeteria, northReactorExit);
        linkNodes(leftVendingMachine, topLeftCafeteria);
        linkNodes(rightVendingMachine, topRightCafeteria);

        linkNodes(midRightPQ, PQExit, midCentrePQ, topRightPQ, bottomRightPQ, midBeds, bottomBeds);
        linkNodes(midCentrePQ, midRightPQ, midLeftPQ);
        linkNodes(midLeftPQ, midCentrePQ, topLeftPQ, bottomLeftPQ);
        linkNodes(topRightPQ, midRightPQ, topCentrePQ, midBeds, topBeds);
        linkNodes(topCentrePQ, topRightPQ, topLeftPQ);
        linkNodes(topLeftPQ, midLeftPQ, topCentrePQ);
        linkNodes(bottomRightPQ, midRightPQ, bottomCentrePQ, bottomBeds);
        linkNodes(bottomCentrePQ, bottomRightPQ, bottomLeftPQ);
        linkNodes(bottomLeftPQ, midLeftPQ, bottomCentrePQ);
        linkNodes(PQExit, midRightPQ, westCafeteriaExit);
        linkNodes(topBeds, topRightPQ);
        linkNodes(midBeds, midRightPQ, topRightPQ);
        linkNodes(bottomBeds, bottomRightPQ, midRightPQ);

        linkNodes(cargo, northCargoExit, southCargoExit, westCargoExit,
                topLeftBoxes, topRightBoxes, bottomLeftBoxes, bottomRightBoxes);
        linkNodes(northCargoExit, cargo, rightNorthWing);
        linkNodes(westCargoExit, cargo, eastCafeteriaExit, topLeftBoxes, bottomLeftBoxes);
        linkNodes(southCargoExit, cargo, northEngineExit);
        linkNodes(topLeftBoxes, cargo, westCargoExit);
        linkNodes(topRightBoxes, cargo);
        linkNodes(bottomLeftBoxes, cargo, westCargoExit);
        linkNodes(bottomRightBoxes, cargo);

        linkNodes(reactor, northReactorExit, eastReactorExit,
                topLeftReactor, topRightReactor, bottomLeftReactor, bottomRightReactor);
        linkNodes(northReactorExit, southCafeteriaExit, reactor);
        linkNodes(eastReactorExit, westEngineExit, reactor, topRightReactor, bottomRightReactor);
        linkNodes(topLeftReactor, reactor);
        linkNodes(topRightReactor, reactor, eastReactorExit);
        linkNodes(bottomLeftReactor, reactor);
        linkNodes(bottomRightReactor, reactor, eastReactorExit);

        linkNodes(midLeftEngine, westEngineExit, leftEngine, midCentreEngine, topLeftEngine, bottomLeftEngine);
        linkNodes(midCentreEngine, centreEngine, midLeftEngine, midRightEngine);
        linkNodes(midRightEngine, midCentreEngine, topRightEngine, bottomRightEngine);
        linkNodes(leftEngine, northEngineExit, midLeftEngine, centreEngine, topLeftEngine);
        linkNodes(centreEngine, northEngineExit, midCentreEngine, leftEngine);
        linkNodes(northEngineExit, southCargoExit, leftEngine, centreEngine);
        linkNodes(westEngineExit, eastReactorExit, midLeftEngine);
        linkNodes(topLeftEngine, midLeftEngine, leftEngine);
        linkNodes(topRightEngine, midRightEngine);
        linkNodes(bottomLeftEngine, midLeftEngine);
        linkNodes(bottomRightEngine, midRightEngine);
    }

    Array<Link> getLinks() {
        return links;
    }

}
