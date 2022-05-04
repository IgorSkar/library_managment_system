package Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Book {
    @JsonProperty("id")
    private int id;
    private  String title;
    private String description;
    private String language;
    private String ISBN;
    private  String publicationDate;
    private String genre;

}
