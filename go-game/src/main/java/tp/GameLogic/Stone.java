package tp.GameLogic;

import tp.Game.SquareState;

public class Stone {
    private int x;
    private int y;
    private SquareState stoneState;
    private Group group;

    public Stone(int x, int y) {
        this.x = x;
        this.y = y;
        this.group = null; // Initially not part of any group
        this.stoneState = SquareState.EMPTY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public SquareState getState() {
        return stoneState;
    }

    public void setState(SquareState state) {
        this.stoneState = state;
    }

    public void reset() {
        group.getStones().remove(this);
        group = null;
        stoneState = SquareState.EMPTY;
    }
}
