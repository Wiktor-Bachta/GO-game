package tp.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import tp.Database.dto.GameHistory;
import tp.Database.dto.MoveType;
import java.util.HashMap;
import java.util.Map;

public class DatabaseFacade {

    private EntityManagerFactory emf;

    public void addMoveToDatabase(String gameID, int moveNumber, int x, int y) {

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        GameHistory newMove = new GameHistory(gameID, moveNumber, MoveType.Move, x, y);
        newMove.setGameID(gameID);
        em.persist(newMove);
        em.getTransaction().commit();

        em.close();
    }

    public void addMoveToDatabase(String gameID, int moveNumber) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        GameHistory newMove = new GameHistory(gameID, moveNumber, MoveType.Pass);
        em.persist(newMove);
        em.getTransaction().commit();

        em.close();
    }

    public void open() {
        Map<String, String> env = new HashMap<>();
        env.put("javax.persistence.jdbc.user", System.getenv("GoGameHistoryDataBaseUser"));
        env.put("javax.persistence.jdbc.password", System.getenv("GoGameHistoryDataBasePasswor"));
        emf = Persistence.createEntityManagerFactory("default", env);
    }

    public void close() {
        emf.close();
    }

    public List<GameHistory> getGameHistory(String sessionID) {
        List<GameHistory> gameHistoryList = new ArrayList<>();
        EntityManager em = emf.createEntityManager();
        TypedQuery<GameHistory> query = em.createQuery("SELECT gh FROM GameHistory gh WHERE gh.gameID = :sessionID",
                GameHistory.class);
        query.setParameter("sessionID", sessionID);
        gameHistoryList = query.getResultList();
        em.close();
        close();
        return gameHistoryList;
    }

    public void addRemoveToDatabase(String gameID, int moveNumber, int x, int y) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        GameHistory newMove = new GameHistory(gameID, moveNumber, MoveType.Remove, x, y);
        em.persist(newMove);
        em.getTransaction().commit();

        em.close();
    }
}
