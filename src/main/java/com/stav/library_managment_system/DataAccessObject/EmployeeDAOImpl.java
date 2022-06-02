package com.stav.library_managment_system.DataAccessObject;

import com.stav.library_managment_system.DAO.EmployeeDAO;
import com.stav.library_managment_system.Models.Customer;
import com.stav.library_managment_system.Models.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.Types;
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

    public boolean createEmployee(String first_name, String last_name, String email, String password, String role){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("create_employee");
        Map<String, String> inParams = new HashMap<>();
        inParams.put("first_name", first_name);
        inParams.put("last_name", last_name);
        inParams.put("username", email);
        inParams.put("password", password);
        inParams.put("role", role);

        SqlParameterSource in = new MapSqlParameterSource(inParams);
        Map map = jdbcCall.execute(in);
        return (int) map.get("succeed") >= 1;
    }

    @Override
    public int save(Employee employee) {
        return jdbcTemplate.update("INSERT INTO employees (first_name,last_name,password) VALUES (?,?,?)",new Object[]{employee.getFirst_name(),employee.getLast_name(),employee.getPassword()});
    }


    @Override
    public int update(Employee employee, int employeeId) {
        return jdbcTemplate.update("UPDATE employees SET password=? WHERE employee_id=?",new Object[] {employee.getPassword(),employeeId});
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

   @Override
    public Employee isValidEmployee(String email, String password) {
       String query = "SELECT * FROM employees WHERE email =? AND password =?";
       try {
           Employee employee = jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(Employee.class), email, password);
           System.out.println("This is what we get in backend from Database: " + employee.toString());
           return employee;
       }catch(EmptyResultDataAccessException e){
           return null;
       }
   }
}



