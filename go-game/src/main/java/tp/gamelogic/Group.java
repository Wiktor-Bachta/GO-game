package tp.gamelogic;

import java.util.HashSet;
import java.util.Set;

public class Group {
    private Set<Stone> stones = new HashSet<>();
    private StoneState state;

    public Group(StoneState state) {
        this.state = state;
    }

    public Group(StoneState state, Stone stone) {
        this.state = state;
        stones.add(stone);
    }

    public Set<Stone> getStones() {
        return stones;
    }

    public void addStone(Stone stone) {
        stones.add(stone);
    }

    public StoneState getState() {
        return state;
    }

    public void setState(StoneState state) {
        this.state = state;
    }

    public void mergeWith(Group group) {
        if (group != null) {
            for (Stone stone : group.getStones()) {
                stones.add(stone);
                stone.setGroup(this);
            }
        }
    }
}
