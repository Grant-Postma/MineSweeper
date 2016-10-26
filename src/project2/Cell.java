package project2;


public class Cell {
    private boolean isExposed;
    private boolean isMine;
    private int adjMines;
    private boolean isFlagged;

    public boolean isFlagged() {
		return isFlagged;
	}

	public void setFlagged(boolean isFlagged) {
		this.isFlagged = isFlagged;
	}

	public Cell(boolean exposed, boolean mine) {
        isExposed = exposed;
        isMine = mine;
    }

    public boolean isExposed() {
        return isExposed;
    }

    public void setExposed(boolean exposed) {
        isExposed = exposed;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

	public int getAdjMines() {
		return adjMines;
	}

	public void setAdjMines(int adjMines) {
		this.adjMines = adjMines;
	}
}