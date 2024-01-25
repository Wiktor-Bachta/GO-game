package tp.Database;

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
        emf = Persistence.createEntityManagerFactory("default");
    }

    public void close() {
        emf.close();
    }
}
