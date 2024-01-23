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

    // Other methods as needed
}
