package com.plusnet.facade;

import com.plusnet.entity.Course;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class CourseFacade extends AbstractFacade<Course> {

    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CourseFacade() {
        super(Course.class);
    }

    public int getByLanguage(int languageCode) {
        String language;
        if (languageCode == 1) {
            language = "Svenska";
        } else {
            language = "Engelska";
        }
        Query query = em.createNamedQuery("Course.findByLanguage", Course.class);
        return query.setParameter("courseLanguage", language).getResultList().size();
    }

    // This method is not exposed to users
    public String getMostPopulatedCourseName(String popularity) {
        Query query = em.createNativeQuery("SELECT c.COURSE_NAME "
                                            + "FROM student_has_course shc "
                                            + "JOIN course c ON (c.id = shc.course_id) "
                                            + "GROUP BY shc.course_id "
                                            + "ORDER BY COUNT(*) " + popularity
                                            + " LIMIT 1;");
        String result = (String) query.getSingleResult();
        return result;
    }

    // This method is not exposed to users
    public long getMostPopulatedCourseCount(String popularity) {
        Query query = em.createNativeQuery("SELECT count(*) AS counted "
                                            + "FROM student_has_course shc "
                                            + "JOIN course c ON (c.id = shc.course_id) "
                                            + "GROUP BY shc.course_id "
                                            + "ORDER BY counted " + popularity
                                            + " LIMIT 1;");
        long result = (long) query.getSingleResult();
        return result;
    }
    
    // This method is not exposed to users
    public long getCourseCountByLevel(String level) {
        Query query = em.createNativeQuery("SELECT count(*) " +
                                            "FROM course AS c " +
                                            "WHERE c.COURSE_LEVEL = '" + level + "';");
        long result = (long) query.getSingleResult();
        return result;
    }

    public Course findByName(String name) {
        Query query = em.createNamedQuery("Course.findByCourseName", Course.class);
        query.setParameter("courseName", name);
        Course course = (Course) query.getSingleResult();
        return course;
    }
}
