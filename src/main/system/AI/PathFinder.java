package main.system.AI;

import main.MainGame;

import java.util.ArrayList;

public class PathFinder {
    MainGame mg;
    Node[][] nodes;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode, goalNode, currentNode;
    boolean goalReached = false;
    int step = 0;

    public PathFinder(MainGame mg) {
        this.mg = mg;

    }
}
