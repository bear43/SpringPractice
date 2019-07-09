package model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Category implements Serializable
{
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String title;

    @OneToMany(mappedBy = "category", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Question> startQuestions;

    public Category()
    {

    }

    public Category(String title, List<Question> startQuestions)
    {
        this.title = title;
        this.startQuestions = startQuestions;
    }

    public Category(String title)
    {
        this(title, new ArrayList<>());
    }

    @Override
    public String toString()
    {
        return title;
    }
}
