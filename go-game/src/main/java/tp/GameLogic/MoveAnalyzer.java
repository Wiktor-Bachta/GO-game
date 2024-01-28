package tp.GameLogic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.bytebuddy.description.annotation.AnnotationList.Empty;
import tp.Game.StoneState;
import tp.Message.Message;
import tp.Server.Session;

/**
 * This class is responsible for analyzing moves and checking if they are valid.
 * It will return board state after move.
 */
public class MoveAnalyzer {

    private Session session;
    private Stone[][] board;
    private int size;
    private StoneState currentStoneState;
    private int killedLastMove = 0;
    private Stone stoneKilledLastMove;
    private boolean passEndsGame = false;
    private boolean onePlayerAgreedToEnd = false;

    public MoveAnalyzer(Session session) {
        size = session.getBoardSize();
        board = new Stone[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = new Stone(i, j);
            }
        }
        currentStoneState = StoneState.BLACK;
        this.session = session;
    }

    public boolean analyzeMove(int x, int y) {

        Stone stoneToPlace = board[x][y];

        if (stoneToPlace.getState() == StoneState.EMPTY) {
            // TODO: send move to database
            stoneToPlace.setState(currentStoneState);
            ArrayList<Stone> playerNeigbours = getPlayerStoneNeighbours(stoneToPlace, currentStoneState);
            stoneToPlace.setGroup(new Group(currentStoneState, stoneToPlace));
            for (Stone neighbour : playerNeigbours) {
                neighbour.getGroup().mergeWith(stoneToPlace.getGroup());
            }

            if (koRuleViolated(stoneToPlace)) {
                return false;
            }

            boolean allowed = false;
            killedLastMove = 0;
            for (Group opponentGroup : getNeighbourGroups(stoneToPlace.getGroup())) {
                if (!hasLiberties(opponentGroup)) {
                    killedLastMove += opponentGroup.getStones().size();
                    killGroup(opponentGroup);
                    allowed = true;
                }
            }

            if (!hasLiberties(stoneToPlace.getGroup()) && !allowed) {
                stoneToPlace.reset();
                return false;
            }

            currentStoneState = getOppositeStoneState(currentStoneState);
            passEndsGame = false;
            return true;
        }

        return false;
    }

    private boolean koRuleViolated(Stone stoneToPlace) {
        if (killedLastMove == 1 && stoneToPlace.getX() == stoneKilledLastMove.getX()
                && stoneToPlace.getY() == stoneKilledLastMove.getY()) {
            stoneToPlace.reset();
            return true;
        }
        return false;
    }

    private StoneState getOppositeStoneState(StoneState state) {
        if (state == StoneState.BLACK) {
            return StoneState.WHITE;
        }
        return StoneState.BLACK;
    }

    private ArrayList<Stone> getStoneNeighbours(Stone stone) {
        ArrayList<Stone> neigbours = new ArrayList<Stone>();
        int x = stone.getX();
        int y = stone.getY();
        if (x != 0) {
            neigbours.add(board[x - 1][y]);
        }
        if (x != size - 1) {
            neigbours.add(board[x + 1][y]);
        }
        if (y != 0) {
            neigbours.add(board[x][y - 1]);
        }
        if (y != size - 1) {
            neigbours.add(board[x][y + 1]);
        }
        return neigbours;
    }

    private ArrayList<Stone> getPlayerStoneNeighbours(Stone stone, StoneState state) {
        ArrayList<Stone> neigbours = getStoneNeighbours(stone);
        ArrayList<Stone> matchingNeigbours = new ArrayList<Stone>();
        for (Stone neighbour : neigbours) {
            if (neighbour.getState() == state) {
                matchingNeigbours.add(neighbour);
            }
        }
        return matchingNeigbours;
    }

    private ArrayList<Stone> getOpponentStoneNeighbours(Stone stone, StoneState state) {
        ArrayList<Stone> neigbours = getStoneNeighbours(stone);
        ArrayList<Stone> opponentNeigbours = new ArrayList<Stone>();
        for (Stone neighbour : neigbours) {
            if (neighbour.getState() == getOppositeStoneState(state)) {
                opponentNeigbours.add(neighbour);
            }
        }
        return opponentNeigbours;
    }

    private ArrayList<Stone> getEmptyStoneNeighbours(Stone stone) {
        ArrayList<Stone> neigbours = getStoneNeighbours(stone);
        ArrayList<Stone> emptyNeigbours = new ArrayList<Stone>();
        for (Stone neighbour : neigbours) {
            if (neighbour.getState() == StoneState.EMPTY) {
                emptyNeigbours.add(neighbour);
            }
        }
        return emptyNeigbours;
    }

    private boolean hasLiberties(Group group) {
        for (Stone stone : group.getStones()) {
            if (!getEmptyStoneNeighbours(stone).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public Set<Group> getNeighbourGroups(Group group) {
        Set<Group> neighbours = new HashSet<>();
        StoneState groupStoneState = group.getState();
        for (Stone stone : group.getStones()) {
            for (Stone opponentStone : getOpponentStoneNeighbours(stone, groupStoneState)) {
                neighbours.add(opponentStone.getGroup());
            }
        }
        return neighbours;
    }

    public void killGroup(Group group) {
        List<Stone> stonesCopy = new ArrayList<>(group.getStones());
        for (Stone stone : stonesCopy) {
            stone.reset();
            stoneKilledLastMove = stone;
            try {
                session.getDatabaseFacade().addRemoveToDatabase(session.getID(), session.getMoveCount() + 1,
                        stone.getX(), stone.getY());
                session.getPlayer1().getClientConnection()
                        .sendMessage(new Message("Move;Remove;" + stone.getX() + ";" + stone.getY()));
                session.getPlayer2().getClientConnection()
                        .sendMessage(new Message("Move;Remove;" + stone.getX() + ";" + stone.getY()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setPassEndsGame(boolean val) {
        this.passEndsGame = val;
    }

    public boolean getPassEndsGame() {
        return passEndsGame;
    }

    public boolean getOnePlayerAgreedToEnd() {
        return onePlayerAgreedToEnd;
    }

    public void setOnePlayerAgreedToEnd(boolean val) {
        onePlayerAgreedToEnd = val;
    }

    public int calculatePoints(StoneState state) {
        int result = getPoints(getEmptyGroups(), state);
        for (Stone stone : getEmptyStones()) {
            stone.reset();
        }
        return result;
    }

    public void skipTurn() {
        currentStoneState = getOppositeStoneState(currentStoneState);
    }

    private List<Group> getEmptyGroups() {
        HashSet<Group> emptyGroups = new HashSet<>();
        for (Stone stone : getEmptyStones()) {
            stone.setGroup(new Group(StoneState.EMPTY, stone));
            for (Stone neighbour : getEmptyStoneNeighbours(stone)) {
                stone.getGroup().mergeWith(neighbour.getGroup());
            }
        }
        for (Stone stone : getEmptyStones()) {
            emptyGroups.add(stone.getGroup());
        }
        return new ArrayList<Group>(emptyGroups);
    }

    private int getPoints(List<Group> emptyGroups, StoneState state) {
        int result = 0;
        for (Group emptyGroup : emptyGroups) {
            result += emptyGroup.getStones().size();
            outerloop: for (Stone stone : emptyGroup.getStones()) {
                for (Stone neighbour : getStoneNeighbours(stone)) {
                    if (neighbour.getState() == getOppositeStoneState(state)) {
                        result -= emptyGroup.getStones().size();
                        break outerloop;
                    }
                }
            }
        }
        return result;
    }

    private List<Stone> getEmptyStones() {
        ArrayList<Stone> result = new ArrayList<Stone>();
        for (Stone[] stoneRow : board) {
            for (Stone stone : stoneRow) {
                if (stone.getState() == StoneState.EMPTY) {
                    result.add(stone);
                }
            }
        }
        return result;
    }

    public void setCurrentStoneState(StoneState state) {
        currentStoneState = state;
    }
}
