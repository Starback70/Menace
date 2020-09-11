package ru.izotov;

import java.util.List;

public class Matchbox {

    private final List<Integer> beads;
    private final int[][] position;

    Matchbox(List<Integer> beads, int[][] position) {
        this.beads = beads;
        this.position = position;
    }

    public void setBeads(int index, int bead) {
        beads.set(index, beads.get(index) + bead);
    }

    public List<Integer> getBeads() {
        return beads;
    }

    public int[][] getPosition() {
        return position;
    }
}
