/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import JPA.Amregistry;
import JPA.Amusers;
import JPA.exceptions.IllegalOrphanException;
import JPA.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Antonio
 */
public class AmusersJpaController implements Serializable {

    public AmusersJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Amusers amusers) {
        if (amusers.getAmregistryList() == null) {
            amusers.setAmregistryList(new ArrayList<Amregistry>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Amregistry> attachedAmregistryList = new ArrayList<Amregistry>();
            for (Amregistry amregistryListAmregistryToAttach : amusers.getAmregistryList()) {
                amregistryListAmregistryToAttach = em.getReference(amregistryListAmregistryToAttach.getClass(), amregistryListAmregistryToAttach.getIdPlay());
                attachedAmregistryList.add(amregistryListAmregistryToAttach);
            }
            amusers.setAmregistryList(attachedAmregistryList);
            em.persist(amusers);
            for (Amregistry amregistryListAmregistry : amusers.getAmregistryList()) {
                Amusers oldIdUserOfAmregistryListAmregistry = amregistryListAmregistry.getIdUser();
                amregistryListAmregistry.setIdUser(amusers);
                amregistryListAmregistry = em.merge(amregistryListAmregistry);
                if (oldIdUserOfAmregistryListAmregistry != null) {
                    oldIdUserOfAmregistryListAmregistry.getAmregistryList().remove(amregistryListAmregistry);
                    oldIdUserOfAmregistryListAmregistry = em.merge(oldIdUserOfAmregistryListAmregistry);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Amusers> getUsers() {
        Query query = getEntityManager().createQuery("SELECT u FROM Amusers u", Amusers.class);
        return (List<Amusers>) query.getResultList();
    }


    public void edit(Amusers amusers) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Amusers persistentAmusers = em.find(Amusers.class, amusers.getIdUser());
            List<Amregistry> amregistryListOld = persistentAmusers.getAmregistryList();
            List<Amregistry> amregistryListNew = amusers.getAmregistryList();
            List<String> illegalOrphanMessages = null;
            for (Amregistry amregistryListOldAmregistry : amregistryListOld) {
                if (!amregistryListNew.contains(amregistryListOldAmregistry)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Amregistry " + amregistryListOldAmregistry + " since its idUser field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Amregistry> attachedAmregistryListNew = new ArrayList<Amregistry>();
            for (Amregistry amregistryListNewAmregistryToAttach : amregistryListNew) {
                amregistryListNewAmregistryToAttach = em.getReference(amregistryListNewAmregistryToAttach.getClass(), amregistryListNewAmregistryToAttach.getIdPlay());
                attachedAmregistryListNew.add(amregistryListNewAmregistryToAttach);
            }
            amregistryListNew = attachedAmregistryListNew;
            amusers.setAmregistryList(amregistryListNew);
            amusers = em.merge(amusers);
            for (Amregistry amregistryListNewAmregistry : amregistryListNew) {
                if (!amregistryListOld.contains(amregistryListNewAmregistry)) {
                    Amusers oldIdUserOfAmregistryListNewAmregistry = amregistryListNewAmregistry.getIdUser();
                    amregistryListNewAmregistry.setIdUser(amusers);
                    amregistryListNewAmregistry = em.merge(amregistryListNewAmregistry);
                    if (oldIdUserOfAmregistryListNewAmregistry != null && !oldIdUserOfAmregistryListNewAmregistry.equals(amusers)) {
                        oldIdUserOfAmregistryListNewAmregistry.getAmregistryList().remove(amregistryListNewAmregistry);
                        oldIdUserOfAmregistryListNewAmregistry = em.merge(oldIdUserOfAmregistryListNewAmregistry);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = amusers.getIdUser();
                if (findAmusers(id) == null) {
                    throw new NonexistentEntityException("The amusers with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Amusers amusers;
            try {
                amusers = em.getReference(Amusers.class, id);
                amusers.getIdUser();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The amusers with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Amregistry> amregistryListOrphanCheck = amusers.getAmregistryList();
            for (Amregistry amregistryListOrphanCheckAmregistry : amregistryListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Amusers (" + amusers + ") cannot be destroyed since the Amregistry " + amregistryListOrphanCheckAmregistry + " in its amregistryList field has a non-nullable idUser field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(amusers);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Amusers> findAmusersEntities() {
        return findAmusersEntities(true, -1, -1);
    }

    public List<Amusers> findAmusersEntities(int maxResults, int firstResult) {
        return findAmusersEntities(false, maxResults, firstResult);
    }

    private List<Amusers> findAmusersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Amusers.class));
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

    public Amusers findAmusers(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Amusers.class, id);
        } finally {
            em.close();
        }
    }

    public int getAmusersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Amusers> rt = cq.from(Amusers.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
