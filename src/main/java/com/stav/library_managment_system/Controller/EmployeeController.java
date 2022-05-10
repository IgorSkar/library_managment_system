package com.stav.library_managment_system.Controller;

import com.stav.library_managment_system.DAO.EmployeeDAO;
import com.stav.library_managment_system.Models.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeDAO employeeDAO;


    @GetMapping

    public List<Employee> getAllEmployees(){
        return employeeDAO.getAllEmployees();
    }

    @GetMapping("/{employeeId}")
    public  ResponseEntity<?> getEmployeeById(@PathVariable int employeeId) {
        Employee employee = null;
        try {
            employee = employeeDAO.getEmployeeId(employeeId);
        } catch (DataAccessException e) {
            return new ResponseEntity<String>("id not found", HttpStatus.BAD_REQUEST);
        }
        return  new ResponseEntity<Employee>(employee,HttpStatus.OK);
    }
    @GetMapping("123")
      public ResponseEntity<?> getEmployeeByName(@RequestParam String firstName){
         Employee employee = null;
         try {
             employee= employeeDAO.getByFirstName(firstName);
         } catch (DataAccessException e){
             return  new ResponseEntity<String>(" employee fist_name not found in database" ,HttpStatus.BAD_REQUEST);
         }
         return new ResponseEntity<Employee>(employee,HttpStatus.OK);
      }

    @GetMapping("/employeeLogin")
       public Employee isValidEmployee(@RequestParam() String user_name, @RequestParam() String password) {
        return employeeDAO.isValidEmployee(user_name, password);
    }


    @GetMapping("create")
    public boolean createCustomer(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String username, @RequestParam String password, @RequestParam String role){
        return employeeDAO.createEmployee(firstName, lastName, username, password, role);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<?> updateEmployee(@RequestBody Employee employee,@PathVariable int employeeId){
        int result = employeeDAO.update(employee,employeeId);
        return  new ResponseEntity<String>("employee updated successfully!",HttpStatus.OK);
    }


    @DeleteMapping("/{employeeId}")
    public ResponseEntity<?> deleteEmployById(@PathVariable int employeeId){
         employeeDAO.delete(employeeId);
        return  new ResponseEntity<String>("employee deleted successfully!",HttpStatus.OK);
    }
}
