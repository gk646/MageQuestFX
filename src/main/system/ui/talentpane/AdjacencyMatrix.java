package main.system.ui.talentpane;

import java.util.ArrayList;

class AdjacencyMatrix {

    public int length;
    private final int[][] adjacency_matrix;
    public static final ArrayList<int[]> edge_list = new ArrayList<>();


    public AdjacencyMatrix() {
        length = 250;
        adjacency_matrix = new int[length][length];

        makeEdges();
    }

    private void makeEdge(int to, int from) {
        adjacency_matrix[to][from] = 1;
        int[] edge = new int[2];
        edge[0] = to;
        edge[1] = from;
        edge_list.add(edge);
    }

    public int getEdge(int to, int from) {
        return adjacency_matrix[to][from];
    }

    private void makeEdges() {
        makeEdge(2, 8);
        makeEdge(2, 9);
        makeEdge(9, 14);
        makeEdge(14, 10);
        makeEdge(10, 11);
        makeEdge(12, 11);
        makeEdge(8, 13);
        makeEdge(12, 13);
        makeEdge(3, 16);
        makeEdge(16, 17);
        makeEdge(17, 18);
        makeEdge(18, 19);
        makeEdge(11, 15);
        makeEdge(13, 20);
        makeEdge(20, 21);
        makeEdge(21, 22);
        makeEdge(19, 20);
        makeEdge(5, 6);
        makeEdge(6, 23);
        makeEdge(23, 24);
        makeEdge(24, 25);
        makeEdge(25, 26);
        makeEdge(26, 27);
        makeEdge(3, 27);
        makeEdge(25, 28);
        makeEdge(28, 41);
        makeEdge(41, 42);
        makeEdge(42, 43);
        makeEdge(43, 44);
        makeEdge(44, 45);
        makeEdge(42, 49);
        makeEdge(43, 49);
        makeEdge(49, 50);
        makeEdge(50, 51);
        makeEdge(51, 52);
        makeEdge(49, 54);
        makeEdge(54, 53);
        makeEdge(53, 52);
        makeEdge(52, 38);
        makeEdge(52, 37);
        makeEdge(37, 36);
        makeEdge(36, 35);
        makeEdge(35, 34);
        makeEdge(35, 18);
        makeEdge(57, 56);
        makeEdge(57, 59);
        makeEdge(57, 58);
        makeEdge(59, 56);
        makeEdge(59, 58);
        makeEdge(58, 55);
        makeEdge(39, 55);
        makeEdge(55, 40);
        makeEdge(37, 38);
        makeEdge(38, 39);
        makeEdge(39, 40);
        makeEdge(56, 55);
        makeEdge(59, 55);
        makeEdge(44, 46);
        makeEdge(46, 47);
        makeEdge(47, 48);
        makeEdge(28, 29);
        makeEdge(29, 30);
        makeEdge(30, 34);
        makeEdge(34, 33);
        makeEdge(33, 32);
        makeEdge(32, 29);
        makeEdge(30, 31);
        makeEdge(0, 5);
        makeEdge(0, 4);
        makeEdge(4, 7);
        makeEdge(1, 60);
        makeEdge(60, 61);
        makeEdge(61, 62);
        makeEdge(62, 63);
        makeEdge(62, 64);
        makeEdge(64, 65);
        makeEdge(65, 66);
        makeEdge(66, 68);
        makeEdge(68, 69);
        makeEdge(69, 70);
        makeEdge(70, 71);
        makeEdge(71, 77);
        makeEdge(77, 76);
        makeEdge(65, 67);
        makeEdge(67, 77);
        makeEdge(72, 73);
        makeEdge(73, 78);
        makeEdge(78, 79);
        makeEdge(79, 7);
        makeEdge(73, 74);
        makeEdge(74, 75);
        makeEdge(1, 75);
        makeEdge(70, 72);
        makeEdge(63, 81);
        makeEdge(81, 82);
        makeEdge(82, 89);
        makeEdge(89, 88);
        makeEdge(88, 87);
        makeEdge(87, 86);
        makeEdge(86, 85);
        makeEdge(85, 84);
        makeEdge(84, 83);
        makeEdge(83, 62);
        makeEdge(82, 90);
        makeEdge(90, 91);
        makeEdge(91, 97);
        makeEdge(97, 92);
        makeEdge(92, 93);
        makeEdge(92, 96);
        makeEdge(96, 93);
        makeEdge(93, 94);
        makeEdge(94, 95);
        makeEdge(95, 21);
        makeEdge(94, 98);
        makeEdge(98, 97);
        makeEdge(98, 99);
        makeEdge(97, 100);
        makeEdge(101, 102);
        makeEdge(100, 101);
        makeEdge(101, 103);
        makeEdge(103, 104);
        makeEdge(104, 106);
        makeEdge(106, 107);
        makeEdge(107, 108);
        makeEdge(108, 109);
        makeEdge(109, 110);
        makeEdge(110, 121);
        makeEdge(121, 122);
        makeEdge(110, 120);
        makeEdge(120, 119);
        makeEdge(119, 122);
        makeEdge(110, 123);
        makeEdge(123, 124);
        makeEdge(124, 125);
        makeEdge(125, 126);
        makeEdge(126, 127);
        makeEdge(98, 118);
        makeEdge(97, 118);
        makeEdge(118, 117);
        makeEdge(117, 116);
        makeEdge(116, 108);
        makeEdge(115, 114);
        makeEdge(114, 108);
        makeEdge(118, 115);
        makeEdge(96, 98);
        makeEdge(98, 93);
        makeEdge(82, 105);
        makeEdge(105, 82);
        makeEdge(105, 14);
        makeEdge(108, 111);
        makeEdge(111, 112);
        makeEdge(112, 113);
        makeEdge(113, 111);
        makeEdge(102, 104);
        makeEdge(99, 119);
        makeEdge(96, 97);
        makeEdge(112, 108);
        makeEdge(35, 128);
        makeEdge(36, 128);
        makeEdge(128, 129);
        makeEdge(128, 130);
        makeEdge(128, 131);
    }
}

