package model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Category
{
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @OneToMany(mappedBy = "category", orphanRemoval = true, cascade = CascadeType.ALL)
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
