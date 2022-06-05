package com.stav.library_managment_system.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class Loan {
    private int book_id;
    private  int customer_id;
    private String loan_date;
    private  String return_date;


}
