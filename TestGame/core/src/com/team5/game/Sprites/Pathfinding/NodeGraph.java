package com.team5.game.Sprites.Pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class NodeGraph implements IndexedGraph<Node> {

    NodeHeuristic nodeHeuristic;
    Array<Node> nodes;
    Array<Link> links;

    ObjectMap<Node, Array<Connection<Node>>> nodeMap = new ObjectMap<>();

    private int nodeIndex = 0;

    public NodeGraph(){
        nodeHeuristic = new NodeHeuristic();
        nodes = new Array<>();
        links = new Array<>();

        buildNodeMap();
    }

    public void addNode(Node node){
        node.index = nodeIndex;
        nodeIndex++;

        nodes.add(node);
    }

    public void linkNodes(Node fromNode, Node toNode){
        Link link = new Link(fromNode, toNode);

        if (!nodeMap.containsKey(fromNode)){
            nodeMap.put(fromNode, new Array<Connection<Node>>());
        }
        nodeMap.get(fromNode).add(link);
        links.add(link);
    }

    public GraphPath<Node> findPath(Node startNode, Node goalNode){
        GraphPath<Node> path = new DefaultGraphPath<>();
        new IndexedAStarPathFinder<>(this).searchNodePath(startNode, goalNode, nodeHeuristic, path);
        return path;
    }

    public Node getNode(int index){
        return nodes.get(index);
    }

    @Override
    public int getIndex(Node node) {
        return node.index;
    }

    @Override
    public int getNodeCount() {
        return nodeIndex;
    }

    @Override
    public Array<Connection<Node>> getConnections(Node fromNode) {
        if (nodeMap.containsKey(fromNode)){
            return nodeMap.get(fromNode);
        }

        return new Array<>();
    }

    void buildNodeMap(){
        Node lowerEngine = new Node(128, 304, "Lower Engine");
        Node westWing = new Node(128, 624, "West Wing");
        Node upperEngine = new Node(128, 784, "Upper Engine");
        Node reactor = new Node(64, 624, "Reactor");
        Node security = new Node(208, 624, "Security");
        Node northWing = new Node(288, 784, "North Wing");
        Node medical = new Node(288, 704, "Lower Engine");
        Node upperCafe = new Node(464, 784, "Upper Cafeteria");
        Node lowerCafe = new Node(464, 688, "Lower Cafeteria");
        Node weapons = new Node(736, 688, "Weapons");
        Node upperEastWing = new Node(736, 608, "Upper East Wing");
        Node oxygen = new Node(640, 608, "Oxygen");
        Node navigation = new Node(832, 512, "Navigation");
        Node lowerEastWing = new Node(736, 512, "Lower East Wing");
        Node shields = new Node(736, 336, "Lower Engine");
        Node southEastWing = new Node(608, 336, "South East Wing");
        Node communication = new Node(608, 256, "Communication");
        Node upperStorage = new Node(464, 352, "Upper Storage");
        Node lowerStorage = new Node(464, 304, "Lower Storage");
        Node southWestWing = new Node(272, 304, "South West Wing");
        Node electrical = new Node(272, 400, "Electrical");
        Node centralWing = new Node(464, 512, "Central Wing");
        Node admin = new Node(624, 512, "Admin");

        addNode(lowerEngine);
        addNode(westWing);
        addNode(upperEngine);
        addNode(reactor);
        addNode(security);
        addNode(northWing);
        addNode(medical);
        addNode(upperCafe);
        addNode(lowerCafe);
        addNode(weapons);
        addNode(upperEastWing);
        addNode(oxygen);
        addNode(navigation);
        addNode(lowerEastWing);
        addNode(shields);
        addNode(southEastWing);
        addNode(communication);
        addNode(upperStorage);
        addNode(lowerStorage);
        addNode(southWestWing);
        addNode(electrical);
        addNode(centralWing);
        addNode(admin);

        linkNodes(lowerEngine, westWing);
        linkNodes(lowerEngine, southWestWing);
        linkNodes(westWing, reactor);
        linkNodes(westWing, security);
        linkNodes(westWing, lowerEngine);
        linkNodes(westWing, upperEngine);
        linkNodes(upperEngine, westWing);
        linkNodes(upperEngine, northWing);
        linkNodes(reactor, westWing);
        linkNodes(security, westWing);
        linkNodes(northWing, upperEngine);
        linkNodes(northWing, medical);
        linkNodes(northWing, upperCafe);
        linkNodes(medical, northWing);
        linkNodes(upperCafe, northWing);
        linkNodes(upperCafe, lowerCafe);
        linkNodes(lowerCafe, upperCafe);
        linkNodes(lowerCafe, weapons);
        linkNodes(lowerCafe, centralWing);
        linkNodes(weapons, lowerCafe);
        linkNodes(weapons, upperEastWing);
        linkNodes(upperEastWing, weapons);
        linkNodes(upperEastWing, oxygen);
        linkNodes(upperEastWing, lowerEastWing);
        linkNodes(oxygen, upperEastWing);
        linkNodes(navigation, lowerEastWing);
        linkNodes(lowerEastWing, upperEastWing);
        linkNodes(lowerEastWing, navigation);
        linkNodes(lowerEastWing, shields);
        linkNodes(shields, lowerEastWing);
        linkNodes(shields, southEastWing);
        linkNodes(southEastWing, shields);
        linkNodes(southEastWing, communication);
        linkNodes(southEastWing, upperStorage);
        linkNodes(communication, southEastWing);
        linkNodes(upperStorage, centralWing);
        linkNodes(upperStorage, southEastWing);
        linkNodes(upperStorage, lowerStorage);
        linkNodes(lowerStorage, upperStorage);
        linkNodes(lowerStorage, southWestWing);
        linkNodes(southWestWing, lowerStorage);
        linkNodes(southWestWing, electrical);
        linkNodes(southWestWing, lowerEngine);
        linkNodes(electrical, southWestWing);
        linkNodes(centralWing, upperStorage);
        linkNodes(centralWing, lowerCafe);
        linkNodes(admin, centralWing);
    }
}
