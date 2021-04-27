package service;

import model.Employee;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class EmployeeService {
    public static SessionFactory sessionFactory;
    public static EntityManager entityManager;

    static {
        try {
            sessionFactory = new Configuration().configure("hibernate.conf.xml").buildSessionFactory();
            entityManager = sessionFactory.createEntityManager();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    public List<Employee> findAll() {
        String queryStr = "select e from Employee as e";
        TypedQuery<Employee> query = entityManager.createQuery(queryStr, Employee.class);
        return query.getResultList();
    }

    public void save(Employee employee) {
        Session session =null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

           session.save(employee);
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public Employee update(Employee employee) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Employee emp = findById(employee.getId());
            emp.setName(employee.getName());
            emp.setDate(employee.getDate());
            emp.setAvatar(employee.getAvatar());
            emp.setGender(employee.isGender());

            session.saveOrUpdate(emp);

            transaction.commit();
            return emp;

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }

    public Employee findById(Long id) {
        String queryStr = "select e from Employee as e where e.id = :id";
        Employee employee = entityManager.createQuery(queryStr, Employee.class).setParameter("id", id).getSingleResult();
        return employee;
    }

 @Transactional
    public void remove(Long id) {
     Session session = null;
     Transaction transaction = null;
     try {
         session = sessionFactory.openSession();
         transaction = session.beginTransaction();
         Employee employee = findById(id);
         session.remove(employee);
         transaction.commit();
     } catch (Exception e) {
         if (transaction != null) {
             transaction.rollback();
         }
     } finally {
         if (session != null) {
             session.close();
         }
     }
    }
}
