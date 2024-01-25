package tp.Database;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import tp.Database.dto.GameHistory;
import tp.Database.dto.MoveType;

public class DatabaseFacade {

    public static void addMoveToDatabase(String gameID, int moveNumber, int x, int y) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        GameHistory newMove = new GameHistory(gameID, moveNumber, MoveType.Move, x, y);
        newMove.setGameID(gameID);
        em.persist(newMove);
        em.getTransaction().commit();

        em.close();
        emf.close();

    }

    public static void addMoveToDatabase(String gameID, int moveNumber) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        GameHistory newMove = new GameHistory(gameID, moveNumber, MoveType.Pass);
        em.persist(newMove);
        em.getTransaction().commit();

        em.close();
        emf.close();
    }
}
