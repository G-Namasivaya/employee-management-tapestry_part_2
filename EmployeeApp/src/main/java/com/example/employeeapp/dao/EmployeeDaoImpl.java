package com.example.employeeapp.dao;

import com.example.employeeapp.entities.Employee;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class EmployeeDaoImpl implements EmployeeDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Employee employee) {
        sessionFactory.getCurrentSession().saveOrUpdate(employee);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Employee> getEmployeeData() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Employee.class);
        return criteria.list();
    }

    @Override
  public Employee getById(Long id) {
    Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Employee.class);
    criteria.add(Restrictions.idEq(id));
    return (Employee) criteria.uniqueResult();
  }   

  @Override
  public Employee findByCredentials(String username, String password) {
      Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Employee.class);
      criteria.add(Restrictions.eq("username", username));
      criteria.add(Restrictions.eq("password", password));
      criteria.setFetchMode("role", org.hibernate.FetchMode.JOIN);
      criteria.setFetchMode("permissions", org.hibernate.FetchMode.JOIN);
      return (Employee) criteria.uniqueResult();
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<Employee> search(String query) {
      if (query == null || query.trim().isEmpty()) return java.util.Collections.emptyList();
      
      // Prefix match for autocomplete
      String sql = "SELECT * FROM employee WHERE name ILIKE :query OR cast(id as text) ILIKE :query";
      return sessionFactory.getCurrentSession().createNativeQuery(sql, Employee.class)
          .setParameter("query", query + "%")
          .list();
  }

  @Override
  public com.example.employeeapp.entities.Role getRoleByName(String name) {
      if (name == null || name.trim().isEmpty()) return null;
      Criteria criteria = sessionFactory.getCurrentSession().createCriteria(com.example.employeeapp.entities.Role.class);
      criteria.add(Restrictions.eq("name", name));
      com.example.employeeapp.entities.Role role = (com.example.employeeapp.entities.Role) criteria.uniqueResult();
      if (role == null) {
          role = new com.example.employeeapp.entities.Role(name);
          sessionFactory.getCurrentSession().save(role);
      }
      return role;
  }

  @Override
  public com.example.employeeapp.entities.Permission getPermissionByName(String name) {
      if (name == null || name.trim().isEmpty()) return null;
      Criteria criteria = sessionFactory.getCurrentSession().createCriteria(com.example.employeeapp.entities.Permission.class);
      criteria.add(Restrictions.eq("name", name));
      com.example.employeeapp.entities.Permission perm = (com.example.employeeapp.entities.Permission) criteria.uniqueResult();
      if (perm == null) {
          perm = new com.example.employeeapp.entities.Permission(name);
          sessionFactory.getCurrentSession().save(perm);
      }
      return perm;
  }
}
