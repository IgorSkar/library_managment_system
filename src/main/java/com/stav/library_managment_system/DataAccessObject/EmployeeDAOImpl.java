package com.stav.library_managment_system.DataAccessObject;

import com.stav.library_managment_system.DAO.EmployeeDAO;
import com.stav.library_managment_system.Models.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //private SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("scanUserNamePassword");

    @Override
    public List<Employee> getAllEmployees() {
        return jdbcTemplate.query("SELECT * FROM  employees",new BeanPropertyRowMapper<Employee>(Employee.class));
    }

    @Override
    public Employee getEmployeeId(int employeeId) throws DataAccessException {
      Employee employee =  jdbcTemplate.queryForObject("SELECT * FROM employees WHERE employee_id=?",new BeanPropertyRowMapper<Employee>(Employee.class),employeeId);
       return employee;
    }

    @Override
    public int save(Employee employee) {
        return jdbcTemplate.update("INSERT INTO employees (first_name,last_name,user_name,password) VALUES (?,?,?,?)",new Object[]{employee.getFirst_name(),employee.getLast_name(),employee.getUser_name(),employee.getPassword()});
    }


    @Override
    public int update(Employee employee, int employeeId) {
        return jdbcTemplate.update("UPDATE employees SET user_name=?, password=? WHERE employee_id=?",new Object[] {employee.getUser_name(),employee.getPassword(),employeeId});
    }


    @Override
    public void delete(int employeeId) {
        jdbcTemplate.update("DELETE  from employees WHERE employee_id=?",employeeId);
    }


    @Override
    public Employee getByFirstName(String firstName) {
        Employee employee = jdbcTemplate.queryForObject("SELECT * FROM employees WHERE first_name=?",new BeanPropertyRowMapper<Employee>(Employee.class),firstName);
        return employee;
    }

   /* @Override
    public Employee isValidUserAndPassword(String userName, String password) {
        Map<String, String> inParameters = new HashMap<>();


        inParameters.put("username2", userName);
        inParameters.put("password2", password);

        SqlParameterSource in = new MapSqlParameterSource(inParameters);

        Map<String, Object> outParameters = jdbcCall.execute(in);

        jdbcCall.execute(in);

        return (Employee) outParameters;
    }

    */
}


