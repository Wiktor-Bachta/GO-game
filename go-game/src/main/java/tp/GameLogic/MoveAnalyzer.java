package tp.GameLogic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tp.Game.Move;
import tp.Game.Square;
import tp.Game.SquareState;
import tp.Message.Message;
import tp.Server.Session;

/**
 * This class is responsible for analyzing moves and checking if they are valid.
 * It will return board state after move.
 */
public class MoveAnalyzer {

    private Session session;
    private Stone[][] board;
    private SquareState currentSquareState;
    private int killedLastMove = 0;
    private Stone stoneKilledLastMove;

    public MoveAnalyzer(Session session) {
        board = new Stone[19][19];
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 19; j++) {
                board[i][j] = new Stone(i, j);
            }
        }
        currentSquareState = SquareState.BLACK;
        this.session = session;
    }

    public boolean analyzeMove(Move move) {

        String thisMove = move.getMove();

        String[] splitMove = thisMove.split(";");

        int x = Integer.parseInt(splitMove[0]);
        int y = Integer.parseInt(splitMove[1]);
        Stone stoneToPlace = board[x][y];

        if (stoneToPlace.getState() == SquareState.EMPTY) {
            // TODO: send move to database
            stoneToPlace.setState(currentSquareState);
            ArrayList<Stone> playerNeigbours = getPlayerStoneNeighbours(stoneToPlace, currentSquareState);
            stoneToPlace.setGroup(new Group(currentSquareState, stoneToPlace));
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

            currentSquareState = getOppositeSquareState(currentSquareState);
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

    private SquareState getOppositeSquareState(SquareState state) {
        if (state == SquareState.BLACK) {
            return SquareState.WHITE;
        }
        return SquareState.BLACK;
    }

    private ArrayList<Stone> getStoneNeighbours(Stone stone) {
        ArrayList<Stone> neigbours = new ArrayList<Stone>();
        int x = stone.getX();
        int y = stone.getY();
        if (x != 0) {
            neigbours.add(board[x - 1][y]);
        }
        if (x != 18) {
            neigbours.add(board[x + 1][y]);
        }
        if (y != 0) {
            neigbours.add(board[x][y - 1]);
        }
        if (y != 18) {
            neigbours.add(board[x][y + 1]);
        }
        return neigbours;
    }

    private ArrayList<Stone> getPlayerStoneNeighbours(Stone stone, SquareState state) {
        ArrayList<Stone> neigbours = getStoneNeighbours(stone);
        ArrayList<Stone> matchingNeigbours = new ArrayList<Stone>();
        for (Stone neighbour : neigbours) {
            if (neighbour.getState() == state) {
                matchingNeigbours.add(neighbour);
            }
        }
        return matchingNeigbours;
    }

    private ArrayList<Stone> getOpponentStoneNeighbours(Stone stone, SquareState state) {
        ArrayList<Stone> neigbours = getStoneNeighbours(stone);
        ArrayList<Stone> opponentNeigbours = new ArrayList<Stone>();
        for (Stone neighbour : neigbours) {
            if (neighbour.getState() == getOppositeSquareState(state)) {
                opponentNeigbours.add(neighbour);
            }
        }
        return opponentNeigbours;
    }

    private ArrayList<Stone> getEmptyStoneNeighbours(Stone stone) {
        ArrayList<Stone> neigbours = getStoneNeighbours(stone);
        ArrayList<Stone> emptyNeigbours = new ArrayList<Stone>();
        for (Stone neighbour : neigbours) {
            if (neighbour.getState() == SquareState.EMPTY) {
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
        SquareState groupSquareState = group.getState();
        for (Stone stone : group.getStones()) {
            for (Stone opponentStone : getOpponentStoneNeighbours(stone, groupSquareState)) {
                neighbours.add(opponentStone.getGroup());
            }
        }
        return neighbours;
    }

    public void killGroup(Group group) {
        for (Stone stone : group.getStones()) {
            stone.reset();
            stoneKilledLastMove = stone;
            try {
                session.getPlayer1().getClientConnection()
                        .sendMessage(new Message("Move;Remove;" + stone.getX() + ";" + stone.getY()));
                session.getPlayer2().getClientConnection()
                        .sendMessage(new Message("Move;Remove;" + stone.getX() + ";" + stone.getY()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
