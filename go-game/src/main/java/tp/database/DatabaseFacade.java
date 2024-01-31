package tp.database;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import tp.database.dto.GameHistory;
import tp.database.dto.MoveType;

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

    public void open() throws IOException {
        Map<String, String> env = new HashMap<>();
        // Zmiana wprowadzona na potrzebę oddania pracy
        BufferedReader reader = new BufferedReader(new FileReader("src/main/java/tp/database/login.txt"));

        String user = reader.readLine();
        String password = reader.readLine();

        reader.close();

        System.out.println(user);
        System.out.println(password);

        env.put("javax.persistence.jdbc.user", user);
        env.put("javax.persistence.jdbc.password", password);
        //env.put("javax.persistence.jdbc.user", System.getenv("GoGameHistoryDataBaseUser"));
        //env.put("javax.persistence.jdbc.password", System.getenv("GoGameHistoryDataBasePasswor"));
        emf = Persistence.createEntityManagerFactory("default", env);
    }

    public void close() {
        emf.close();
    }

    public List<GameHistory> getGameHistory(String sessionID) throws IOException {
        open();
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
