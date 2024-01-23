package tp.GameLogic;

public class Stone {
    private int x;
    private int y;
    private Group group;

    public Stone(int x, int y) {
        this.x = x;
        this.y = y;
        this.group = null; // Initially not part of any group
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

    // Other methods as needed
}
