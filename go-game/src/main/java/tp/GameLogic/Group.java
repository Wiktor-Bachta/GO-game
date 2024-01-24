package tp.GameLogic;

import tp.Game.SquareState;

import java.util.HashSet;
import java.util.Set;

public class Group {
    private Set<Stone> stones = new HashSet<>();
    private SquareState state;

    public Group(SquareState state) {
        this.state = state;
    }

    public Group(SquareState state, Stone stone) {
        this.state = state;
        stones.add(stone);
    }

    public Set<Stone> getStones() {
        return stones;
    }

    public void addStone(Stone stone) {
        stones.add(stone);
    }

    public SquareState getState() {
        return state;
    }

    public void setState(SquareState state) {
        this.state = state;
    }

    public void mergeWith(Group group) {
        for (Stone stone : group.getStones()) {
            stones.add(stone);
            stone.setGroup(this);
        }
    }
}
