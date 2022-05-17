package com.stav.library_managment_system.DataAccessObject;

import com.stav.library_managment_system.DAO.AuthorDAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;



class AuthorDAOIMLPTest {

     @Autowired
     private AuthorDAO authorDAO;
     @MockBean
    private AuthorDAOIMLP authorDAOIMLP;

    @Test
    void getAllAuthors() {

    }

    @Test
    void getAuthorById() {


    }

    @Test
    void searchAuthorByName() {
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void testGetAllAuthors() {
    }

    @Test
    void testGetAuthorById() {
    }

    @Test
    void testSearchAuthorByName() {
    }

    @Test
    void testSave() {
    }

    @Test
    void testUpdate() {
    }

    @Test
    void testDelete() {
    }
}