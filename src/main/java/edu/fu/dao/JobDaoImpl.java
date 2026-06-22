package edu.fu.dao;

import edu.fu.entities.Job;
import edu.fu.utils.DbContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JobDaoImpl implements JobDao {

    @Override
    public Job findById(Long id) {
        EntityManager entityManager = DbContext.getEntityManager();

        try {
            return entityManager.find(Job.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Has an error occurred: " + e.getMessage());
        }
    }

    @Override
    public Job createJob(Job job) {
        EntityManager entityManager = DbContext.getEntityManager();
        EntityTransaction tx = null;

        try {
            tx = entityManager.getTransaction();
            tx.begin();

            entityManager.persist(job);

            tx.commit();
            return job;

        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }

            throw new RuntimeException("Has an error occurred: " + e.getMessage());
        }
    }

    @Override
    public List<Job> findAllJobs() {
        EntityManager entityManager = DbContext.getEntityManager();

        TypedQuery<Job> query =
                entityManager.createQuery("SELECT j FROM Job j", Job.class);

        return query.getResultList();
    }

    @Override
    public boolean isExisted(String title) {
        EntityManager entityManager = DbContext.getEntityManager();

        Long count = entityManager.createQuery(
                        "SELECT COUNT(j) FROM Job j WHERE j.title = :title",
                        Long.class)
                .setParameter("title", title)
                .getSingleResult();

        return count > 0;
    }
}
