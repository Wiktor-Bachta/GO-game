package tp.database.dto;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnTransformer;

import tp.database.dto.MoveType;

@Entity
@Table(name = "GameHistory")
public class
GameHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int moveID;

    private String gameID;

    private int moveNumber;

    @Enumerated(EnumType.STRING)
    @ColumnTransformer(write="?::movetype")
    private MoveType moveType;

    private int x;
    private int y;
    

    public GameHistory() {
        // Default constructor required by JPA
    }

    public GameHistory(String gameID, int moveNumber, MoveType moveType, int x, int y) {
        this.gameID = gameID;
        this.moveNumber = moveNumber;
        this.moveType = moveType;
        this.x = x;
        this.y = y;
    }

    public GameHistory(String gameID, int moveNumber, MoveType moveType) {
        this.gameID = gameID;
        this.moveNumber = moveNumber;
        this.moveType = moveType;
    }

    public GameHistory(int i, MoveType moveType, int i1, int i2) {
        this.moveNumber = i;
        this.moveType = moveType;
        this.x = i1;
        this.y = i2;
    }

    // Getters and setters for other fields

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    public void setMoveNumber(int moveNumber) {
        this.moveNumber = moveNumber;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    public void setMoveType(MoveType moveType) {
        this.moveType = moveType;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
