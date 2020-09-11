package ru.izotov;

import java.util.Scanner;

public class Game {

    protected static final int CELLS = 3;
    int[][] field = new int[CELLS][CELLS];
    boolean isGameOver = false;
    int step = 0;
    int part = 0;

    public static void main(String[] args) {
        new Game().startGame();
    }

    public void startGame() {
        int position;
        Menace menace = new Menace();
        System.out.println("--- Партия №" + ++part + " ---");
        while (true) {
            step++;
            System.out.println("=== Ход: " + step + " ===");
            field = menace.doMove(field);
            displayField();
            if (checkGameOver(menace)) {
                isGameOver = false;
                continue;
            }
            step++;
            System.out.println("=== Ход: " + step + " ===");
            System.out.print("Ход игрока: ");
            position = choice();
            if (position >= 0 && position <= 8) {
                doMove(position);
                displayField();
                if (checkGameOver(menace)) {
                    isGameOver = false;
                }
            } else if (position == 10) {
                System.out.println("Выход из игры");
                return;
            }
        }
    }

    int choice() {
        while (true) {
            int choice = parseInput(new Scanner(System.in).nextInt());
            if (choice >= 0 && choice <= 8) {
                if (field[choice / 3][choice % 3] == 0) {
                    return choice;
                } else {
                    System.out.println("Клетка занята");
                }
            } else if (choice == 10) {
                return choice;
            } else {
                System.out.println("Некорректный ввод");
            }
        }
    }

    int parseInput(int choice) {
        switch (choice) {
            case 1:
                choice = 6;
                break;
            case 2:
                choice = 7;
                break;
            case 3:
                choice = 8;
                break;
            case 4:
                choice = 3;
                break;
            case 5:
                choice = 4;
                break;
            case 6:
                choice = 5;
                break;
            case 7:
                choice = 0;
                break;
            case 8:
                choice = 1;
                break;
            case 9:
                choice = 2;
                break;
            case 0:
                choice = 10;
                break;
            default:
                choice = -1;
                break;
        }
        return choice;
    }

    boolean checkGameOver(Menace menace) {
        int changeBeads = 0;
        if (step > 7) {
            reset(menace);
            System.out.println("Ничья!");
            System.out.println("--- Партия №" + ++part + " ---");
            return true;
        }
        if (gameOver() == 1) {
            System.out.println("Машина выграла!");
            changeBeads = 1;
            isGameOver = true;
        } else if (gameOver() == -1) {
            System.out.println("Человек победил!");
            changeBeads = -1;
            isGameOver = true;
        }
        if (isGameOver) {
            System.out.println("beads: " + menace.beads);
            if (step >= 1) {
                menace.setBox1.get(menace.boxes.get(0)).setBeads(menace.beads.get(0), changeBeads);
                System.out.println(menace.setBox1.get(menace.boxes.get(0)).getBeads());
            }
            if (step >= 3) {
                menace.setBox3.get(menace.boxes.get(1)).setBeads(menace.beads.get(1), changeBeads);
                System.out.println(menace.setBox3.get(menace.boxes.get(1)).getBeads());
            }
            if (step >= 5) {
                menace.setBox5.get(menace.boxes.get(2)).setBeads(menace.beads.get(2), changeBeads);
                System.out.println(menace.setBox5.get(menace.boxes.get(2)).getBeads());
            }
            if (step >= 7) {
                menace.setBox7.get(menace.boxes.get(3)).setBeads(menace.beads.get(3), changeBeads);
                System.out.println(menace.setBox7.get(menace.boxes.get(3)).getBeads());
            }
            reset(menace);
            System.out.println("--- Партия №" + ++part + " ---");
        }
        return isGameOver;
    }

    int gameOver() {
        int result = 0;
        if (field[0][0] == 1 && field[0][1] == 1 && field[0][2] == 1
                || field[1][0] == 1 && field[1][1] == 1 && field[1][2] == 1
                || field[2][0] == 1 && field[2][1] == 1 && field[2][2] == 1
                || field[0][0] == 1 && field[1][0] == 1 && field[2][0] == 1
                || field[0][1] == 1 && field[1][1] == 1 && field[2][1] == 1
                || field[0][2] == 1 && field[1][2] == 1 && field[2][2] == 1
                || field[0][0] == 1 && field[1][1] == 1 && field[2][2] == 1
                || field[2][0] == 1 && field[1][1] == 1 && field[0][2] == 1) {
            result = 1;
        } else if (field[0][0] == -1 && field[0][1] == -1 && field[0][2] == -1
                || field[1][0] == -1 && field[1][1] == -1 && field[1][2] == -1
                || field[2][0] == -1 && field[2][1] == -1 && field[2][2] == -1
                || field[0][0] == -1 && field[1][0] == -1 && field[2][0] == -1
                || field[0][1] == -1 && field[1][1] == -1 && field[2][1] == -1
                || field[0][2] == -1 && field[1][2] == -1 && field[2][2] == -1
                || field[0][0] == -1 && field[1][1] == -1 && field[2][2] == -1
                || field[2][0] == -1 && field[1][1] == -1 && field[0][2] == -1) {
            result = -1;
        }
        return result;
    }

    void doMove(int pos) {
        field[pos / 3][pos % 3] = -1;
    }

    void reset(Menace menace) {
        menace.reset();
        field = new int[CELLS][CELLS];
        step = 0;
    }

    void displayField() {
        String symbol = "";
        for (int[] ints : field) {
            for (int j = 0; j < field[0].length; j++) {
                switch (ints[j]) {
                    case 0:
                        symbol = "\u22c5";
                        break;
                    case 1:
                        symbol = "\u274c";
                        break;
                    case -1:
                        symbol = "\u25ef";
                        break;
                    default:
                        break;
                }
                System.out.print(symbol + "  ");
            }
            System.out.println();
        }
    }
}
