/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import JPA.Amregistry;
import JPA.Amusers;
import JPA.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Antonio
 */
public class AmregistryJpaController implements Serializable {

    public AmregistryJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Amregistry amregistry) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Amusers idUser = amregistry.getIdUser();
            if (idUser != null) {
                idUser = em.getReference(idUser.getClass(), idUser.getIdUser());
                amregistry.setIdUser(idUser);
            }
            em.persist(amregistry);
            if (idUser != null) {
                idUser.getAmregistryList().add(amregistry);
                idUser = em.merge(idUser);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Amregistry> getMaxScores() {
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT r FROM Amregistry r JOIN FETCH r.idUser ORDER BY r.speed DESC").setMaxResults(3);
        return (List<Amregistry>) query.getResultList();
    }

    public List<Amregistry> getMaxScores10() {
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT r FROM Amregistry r JOIN FETCH r.idUser ORDER BY r.speed DESC").setMaxResults(10);
        return (List<Amregistry>) query.getResultList();
    }

    public List<Amregistry> getScores(String nickname) {
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT r FROM Amregistry r JOIN FETCH r.idUser WHERE r.idUser.nameUser = :nickname");
        query.setParameter("nickname", nickname);
        return (List<Amregistry>) query.getResultList();
    }

    public List<Amregistry> getOnline() {
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT r FROM Amregistry r JOIN FETCH r.idUser ORDER BY r.endGame DESC").setMaxResults(5);
        return (List<Amregistry>) query.getResultList();
    }
    public void edit(Amregistry amregistry) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Amregistry persistentAmregistry = em.find(Amregistry.class, amregistry.getIdPlay());
            Amusers idUserOld = persistentAmregistry.getIdUser();
            Amusers idUserNew = amregistry.getIdUser();
            if (idUserNew != null) {
                idUserNew = em.getReference(idUserNew.getClass(), idUserNew.getIdUser());
                amregistry.setIdUser(idUserNew);
            }
            amregistry = em.merge(amregistry);
            if (idUserOld != null && !idUserOld.equals(idUserNew)) {
                idUserOld.getAmregistryList().remove(amregistry);
                idUserOld = em.merge(idUserOld);
            }
            if (idUserNew != null && !idUserNew.equals(idUserOld)) {
                idUserNew.getAmregistryList().add(amregistry);
                idUserNew = em.merge(idUserNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = amregistry.getIdPlay();
                if (findAmregistry(id) == null) {
                    throw new NonexistentEntityException("The amregistry with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Amregistry amregistry;
            try {
                amregistry = em.getReference(Amregistry.class, id);
                amregistry.getIdPlay();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The amregistry with id " + id + " no longer exists.", enfe);
            }
            Amusers idUser = amregistry.getIdUser();
            if (idUser != null) {
                idUser.getAmregistryList().remove(amregistry);
                idUser = em.merge(idUser);
            }
            em.remove(amregistry);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Amregistry> findAmregistryEntities() {
        return findAmregistryEntities(true, -1, -1);
    }

    public List<Amregistry> findAmregistryEntities(int maxResults, int firstResult) {
        return findAmregistryEntities(false, maxResults, firstResult);
    }

    private List<Amregistry> findAmregistryEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Amregistry.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Amregistry findAmregistry(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Amregistry.class, id);
        } finally {
            em.close();
        }
    }

    public int getAmregistryCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Amregistry> rt = cq.from(Amregistry.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
