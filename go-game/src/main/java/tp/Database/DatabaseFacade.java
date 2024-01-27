package tp.Database;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import tp.Database.dto.GameHistory;
import tp.Database.dto.MoveType;

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
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("javax.persistence.jdbc.user", System.getenv("GoGameHistoryDataBaseUser"));
        System.out.println(System.getenv("GoGameHistoryDataBaseUser"));
        properties.put("javax.persistence.jdbc.password", System.getenv("GoGameHistoryDataBasePasswor"));

        emf = Persistence.createEntityManagerFactory("default", properties);
    }

    public void close() {
        emf.close();
    }
}
