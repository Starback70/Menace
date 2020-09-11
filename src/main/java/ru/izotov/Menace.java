package ru.izotov;

import ru.izotov.random.RNG;

import java.util.*;

public class Menace {

    Map<Integer, Matchbox> setBox1 = new HashMap<>();
    Map<Integer, Matchbox> setBox3 = new HashMap<>();
    Map<Integer, Matchbox> setBox5 = new HashMap<>();
    Map<Integer, Matchbox> setBox7 = new HashMap<>();
    List<Integer> boxes = new ArrayList<>();
    List<Integer> beads = new ArrayList<>();
    int box;
    int bead;

    Menace() {
        setBoxes();
    }

    public int[][] doMove(int[][] field) {
        List<Integer> listBeads = new ArrayList<>();
        switch (countMoves(field)) {
            case 1:
                listBeads = getBead(setBox1, field);
                box = listBeads.get(listBeads.size() - 1);
                break;
            case 3:
                listBeads = getBead(setBox3, field);
                box = listBeads.get(listBeads.size() - 1);
                break;
            case 5:
                listBeads = getBead(setBox5, field);
                box = listBeads.get(listBeads.size() - 1);
                break;
            case 7:
                listBeads = getBead(setBox7, field);
                box = listBeads.get(listBeads.size() - 1);
                break;
            default:
                System.out.println("Неправильный ход");
                break;
        }
        boxes.add(box);
        listBeads.remove(listBeads.size() - 1);
        bead = RNG.getRandom(listBeads); // номер позиции в листе весо
        beads.add(bead);
        field[bead / 3][bead % 3] = 1;
        return field;
    }

    private int countMoves(int[][] field) {
        int count = 0;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                if (field[i][j] == 0) {
                    count++;
                }
            }
        }
        return 10 - count;
    }

    List<Integer> getBead(Map<Integer, Matchbox> boxes, int[][] field) {
        ArrayList<Integer> result = new ArrayList<>();
        for (Map.Entry<Integer, Matchbox> entry : boxes.entrySet()) {
            int count = 0;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (Math.abs(field[i][j]) == Math.abs(entry.getValue().getPosition()[i][j])) {
                        count++;
                        if (count == 9) {
                            result.addAll(entry.getValue().getBeads());
                            result.add(entry.getKey());
                        }
                    }
                }
            }
        }
        return result;
    }

    void setBoxes() {
        setBox1.put(0, new Matchbox(Arrays.asList(4, 4, 4, 4, 4, 4, 4, 4, 4), new int[3][3]));
        List<int[][]> position = new ArrayList<>();
        for (int k = 0; k < 9; k++) {
            int[][] pos = new int[3][3];
            pos[k / 3][k % 3] = 1;
            for (int l = 0; l < 9; l++) {
                if (pos[l / 3][l % 3] == 1 || pos[l / 3][l % 3] == -1) {
                    continue;
                }
                pos[l / 3][l % 3] = -1;
                if (!containsMatrix(position, pos)) {
                    position.add(cloneMatrix(pos));
                }
                pos[l / 3][l % 3] = 0;
            }
        }
        for (int i = 0; i < position.size(); i++) {
            List<Integer> list = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                if (position.get(i)[j / 3][j % 3] == 0) {
                    list.add(3);
                } else {
                    list.add(0);
                }
            }
            setBox3.put(i, new Matchbox(list, position.get(i)));
        }

        position = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            int[][] pos = new int[3][3];
            pos[i / 3][i % 3] = 1;
            for (int j = 0; j < 9; j++) {
                if (pos[j / 3][j % 3] == 1) {
                    continue;
                }
                pos[j / 3][j % 3] = 1;
                for (int k = 0; k < 9; k++) {
                    if (pos[k / 3][k % 3] == 1) {
                        continue;
                    }
                    pos[k / 3][k % 3] = -1;
                    for (int l = 0; l < 9; l++) {
                        if (pos[l / 3][l % 3] == 1 || pos[l / 3][l % 3] == -1) {
                            continue;
                        }
                        pos[l / 3][l % 3] = -1;
                        if (!containsMatrix(position, pos)) {
                            position.add(cloneMatrix(pos));
                        }
                        pos[l / 3][l % 3] = 0;
                    }
                    pos[k / 3][k % 3] = 0;
                }
                pos[j / 3][j % 3] = 0;
            }
        }
        for (int i = 0; i < position.size(); i++) {
            List<Integer> list = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                if (position.get(i)[j / 3][j % 3] == 0) {
                    list.add(2);
                } else {
                    list.add(0);
                }
            }
            setBox5.put(i, new Matchbox(list, position.get(i)));
        }

        position = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            int[][] pos = new int[3][3];
            pos[i / 3][i % 3] = 1;
            for (int j = 0; j < 9; j++) {
                if (pos[j / 3][j % 3] == 1) {
                    continue;
                }
                pos[j / 3][j % 3] = 1;
                for (int k = 0; k < 9; k++) {
                    if (pos[k / 3][k % 3] == 1) {
                        continue;
                    }
                    pos[k / 3][k % 3] = 1;
                    for (int l = 0; l < 9; l++) {
                        if (pos[l / 3][l % 3] == 1) {
                            continue;
                        }
                        pos[l / 3][l % 3] = -1;
                        for (int m = 0; m < 9; m++) {
                            if (pos[m / 3][m % 3] == 1 || pos[m / 3][m % 3] == -1) {
                                continue;
                            }
                            pos[m / 3][m % 3] = -1;
                            for (int n = 0; n < 9; n++) {
                                if (pos[n / 3][n % 3] == 1 || pos[n / 3][n % 3] == -1) {
                                    continue;
                                }
                                pos[n / 3][n % 3] = -1;
                                if (!containsMatrix(position, pos)) {
                                    position.add(cloneMatrix(pos));
                                }
                                pos[n / 3][n % 3] = 0;
                            }
                            pos[m / 3][m % 3] = 0;
                        }
                        pos[l / 3][l % 3] = 0;
                    }
                    pos[k / 3][k % 3] = 0;
                }
                pos[j / 3][j % 3] = 0;
            }
        }
        for (int i = 0; i < position.size(); i++) {
            List<Integer> list = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                if (position.get(i)[j / 3][j % 3] == 0) {
                    list.add(1);
                } else {
                    list.add(0);
                }
            }
            setBox7.put(i, new Matchbox(list, position.get(i)));
        }
    }

    static boolean containsMatrix(List<int[][]> positions, int[][] position) {
        boolean result = false;
        for (int[][] ints : positions) {
            int count = 0;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (Math.abs(position[i][j]) == Math.abs(ints[i][j])) {
                        count++;
                        if (count == 9) {
                            result = true;
                        }
                    }
                }
            }
        }
        return result;
    }

    static int[][] cloneMatrix(int[][] matrix) {
        int[][] newMatrix = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            System.arraycopy(matrix[i], 0, newMatrix[i], 0, matrix[0].length);
        }
        return newMatrix;
    }

    public void reset() {
        boxes = new ArrayList<>();
        beads = new ArrayList<>();
    }

    void outBoxes(Map<Integer, Matchbox> positions) {
        System.out.println("========================");
        for (Map.Entry<Integer, Matchbox> entry : positions.entrySet()) {
            System.out.println("--- " + entry.getKey() + " ---");
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    System.out.print(entry.getValue().getPosition()[i][j] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    public static void outMatrix(int[][] matrix) {
        for (int[] ints : matrix) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(ints[j] + " ");
            }
            System.out.println();
        }
    }
}
