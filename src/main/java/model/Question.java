package model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
public class Question implements Serializable
{
    @Id
    @GeneratedValue
    private Long id;

    private String message;

    @ManyToOne
    @JsonBackReference
    private Category category;

    @ManyToOne
    @JsonBackReference
    private Question prevQuestion;

    @OneToMany(mappedBy = "prevQuestion")
    @JsonManagedReference
    private List<Question> nextQuestionsOnPositive;

    public Question()
    {
    }

    public Question(String message, Question prevQuestion, List<Question> nextQuestionsOnPositive)
    {
        this.message = message;
        this.prevQuestion = prevQuestion;
        this.nextQuestionsOnPositive = nextQuestionsOnPositive;
    }

    public Question(String message)
    {
        this(message, null, null);
    }

    public boolean isPrevAvailable()
    {
        return prevQuestion != null;
    }

    public boolean isNextOnPositiveAvailable()
    {
        return nextQuestionsOnPositive != null && nextQuestionsOnPositive.size() != 0;
    }

    @JsonIgnore
    public List<Question> getSiblingPositiveQuestions()
    {
        return prevQuestion.nextQuestionsOnPositive;
    }

    @Override
    public String toString()
    {
        return message;
    }
}
