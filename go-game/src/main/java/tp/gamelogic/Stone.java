package tp.gamelogic;

public class Stone {
    private int x;
    private int y;
    private StoneState stoneState;
    private Group group;

    public Stone(int x, int y) {
        this.x = x;
        this.y = y;
        this.group = null; // Initially not part of any group
        this.stoneState = StoneState.EMPTY;
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

    public StoneState getState() {
        return stoneState;
    }

    public void setState(StoneState state) {
        this.stoneState = state;
    }

    public void reset() {
        group.getStones().remove(this);
        group = null;
        stoneState = StoneState.EMPTY;
    }
}
