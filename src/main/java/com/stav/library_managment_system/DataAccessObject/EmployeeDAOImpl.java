package com.stav.library_managment_system.DataAccessObject;

import com.stav.library_managment_system.DAO.EmployeeDAO;
import com.stav.library_managment_system.Models.Customer;
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


    public Employee isValidEmployee (String email, String password){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("employee_authenticator");
        Map<String, String> inParams = new HashMap<>();
        inParams.put("username", email);
        inParams.put("userpassword", password);

        SqlParameterSource in = new MapSqlParameterSource(inParams);
        Map m = jdbcCall.execute(in);
        if((int) m.get("succeed") == 0){
            return null;
        }

        String query = "SELECT * FROM employees WHERE email = ?";
        return jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(Employee.class), email);
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


}



