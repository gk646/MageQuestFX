/*
 * MIT License
 *
 * Copyright (c) 2023 gk646
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package main.system.pathfinding;

import main.MainGame;
import main.system.rendering.WorldRender;

import java.util.ArrayList;
import java.util.List;

public class PathFinder {
    public final ArrayList<Node> pathList = new ArrayList<>();
    private final MainGame mg;
    private final ArrayList<Node> openList = new ArrayList<>();
    private Node[][] nodes;
    private Node startNode;
    private Node goalNode;
    private Node currentNode;
    private boolean goalReached = false;

    public PathFinder(MainGame mg) {
        this.mg = mg;
    }

    public void instantiateNodes() {
        nodes = new Node[500][500];
        for (int i = 0; i < 500; i++) {
            for (int b = 0; b < 500; b++) {
                nodes[i][b] = new Node(i, b);
            }
        }
    }

    public void setNodes(int startCol, int startRow, int goalCol, int goalRow, int maxDistance) {
        int wsize = mg.wRender.worldSize.x;
        int startx = Math.max(0, startCol - maxDistance);
        int starty = Math.max(0, startRow - maxDistance);
        int endx = Math.min(wsize, startCol + maxDistance);
        int endy = Math.min(wsize, startRow + maxDistance);
        for (int i = startx; i < endx; i++) {
            for (int b = starty; b < endy; b++) {
                nodes[i][b].open = false;
                nodes[i][b].checked = false;
                nodes[i][b].solid = false;
            }
        }
        openList.clear();
        pathList.clear();
        goalReached = false;
        startNode = nodes[startCol][startRow];
        currentNode = startNode;
        goalNode = nodes[goalCol][goalRow];
        int tileNum, tileNum2;
        for (int i = startx; i < endx; i++) {
            for (int b = starty; b < endy; b++) {
                tileNum = WorldRender.worldData[i][b];
                tileNum2 = WorldRender.worldData1[i][b];
                if (mg.wRender.tileStorage[tileNum].collision) {
                    nodes[i][b].solid = true;
                } else if (tileNum2 != -1 && mg.wRender.tileStorage[tileNum2].collision) {
                    nodes[i][b].solid = true;
                } else {
                    getCost(nodes[i][b]);
                }
            }
        }
    }


    private void getCost(Node node) {
        //G cost
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;
        //H cost
        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;
        //F cost
        node.fCost = node.gCost + node.hCost;
    }

    public boolean search() {
        while (!goalReached) {
            if (playerTooFar()) {
                return false;
            }
            int col = currentNode.col;
            int row = currentNode.row;
            currentNode.checked = true;
            openList.remove(currentNode);
            if (row - 1 >= 0) {
                openNode(nodes[col][row - 1]);
            }
            if (col - 1 >= 0) {
                openNode(nodes[col - 1][row]);
            }
            if (row + 1 < mg.wRender.worldSize.y) {
                openNode(nodes[col][row + 1]);
            }
            if (col + 1 < mg.wRender.worldSize.x) {
                openNode(nodes[col + 1][row]);
            }
            int bestNodesIndex = 0;
            int bestNodesFCost = 999;

            for (int i = 0; i < openList.size(); i++) {
                if (openList.get(i).fCost < bestNodesFCost) {
                    bestNodesIndex = i;
                    bestNodesFCost = openList.get(i).fCost;
                } else if (openList.get(i).fCost == bestNodesFCost) {
                    if (openList.get(i).gCost < openList.get(bestNodesIndex).gCost) bestNodesIndex = i;
                }
            }
            if (openList.size() == 0) {
                return false;
            }
            currentNode = openList.get(bestNodesIndex);
            if (currentNode == goalNode) {
                goalReached = true;
                trackPath();
                return true;
            }
        }
        return false;
    }

   /* public boolean findPath() {
        PriorityQueue<Node> openList = new PriorityQueue<>();
        Map<Node, Boolean> closedList = new HashMap<>();
        Node startNode = nodes[][startRow];
        Node goalNode = nodes[goalCol][goalRow];
        startNode.gCost = 0;
        startNode.fCost = startNode.hCost(goalNode);
        openList.add(startNode);

        while (!openList.isEmpty()) {
            Node currentNode = openList.poll();
            if (currentNode == goalNode) {
                trackPath();
                return true;
            }
            closedList.put(currentNode, true);

            for (Node neighbor : getNeighbors(currentNode)) {
                if (closedList.containsKey(neighbor)) {
                    continue;
                }

                int tentativeGCost = currentNode.gCost + neighbor.distance(currentNode);
                if (tentativeGCost < neighbor.gCost) {
                    neighbor.parent = currentNode;
                    neighbor.gCost = tentativeGCost;
                    neighbor.fCost = neighbor.gCost + neighbor.hCost(goalNode);
                }
                if (!openList.contains(neighbor)) {
                    openList.add(neighbor);
                }
            }
        }

        return false;
    }

    */

    private List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        int col = node.col;
        int row = node.row;

        if (row - 1 >= 0) {
            neighbors.add(nodes[col][row - 1]);
        }
        if (col - 1 >= 0) {
            neighbors.add(nodes[col - 1][row]);
        }
        if (row + 1 < mg.wRender.worldSize.y) {
            neighbors.add(nodes[col][row + 1]);
        }
        if (col + 1 < mg.wRender.worldSize.x) {
            neighbors.add(nodes[col + 1][row]);
        }

        return neighbors;
    }

    public boolean searchUncapped() {
        while (!goalReached) {
            int col = currentNode.col;
            int row = currentNode.row;

            currentNode.checked = true;
            openList.remove(currentNode);

            if (row - 1 >= 0) {
                openNode(nodes[col][row - 1]);
            }
            if (col - 1 >= 0) {
                openNode(nodes[col - 1][row]);
            }
            if (row + 1 < mg.wRender.worldSize.y) {
                openNode(nodes[col][row + 1]);
            }
            if (col + 1 < mg.wRender.worldSize.x) {
                openNode(nodes[col + 1][row]);
            }

            int bestNodesIndex = 0;
            int bestNodesFCost = 999;

            for (int i = 0; i < openList.size(); i++) {
                if (openList.get(i).fCost < bestNodesFCost) {
                    bestNodesIndex = i;
                    bestNodesFCost = openList.get(i).fCost;
                } else if (openList.get(i).fCost == bestNodesFCost) {
                    if (openList.get(i).gCost < openList.get(bestNodesIndex).gCost) bestNodesIndex = i;
                }
            }
            if (openList.size() == 0) {
                return false;
            }
            currentNode = openList.get(bestNodesIndex);
            if (currentNode == goalNode) {
                goalReached = true;
                trackPath();
                return true;
            }
        }
        return false;
    }

    private void trackPath() {
        Node current = goalNode;
        while (current != startNode) {
            pathList.add(0, current);
            current = current.parent;
        }
    }

    private void openNode(Node node) {
        if (!node.open && !node.checked && !node.solid) {
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }

    private boolean playerTooFar() {
        return Math.abs(currentNode.col - goalNode.col) >= 15 || Math.abs(currentNode.row - goalNode.row) >= 15;
    }
}
