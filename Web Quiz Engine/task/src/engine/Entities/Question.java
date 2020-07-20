package engine.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Question {

    @NotBlank(message = "title is mandatory")
    private String title;
    @NotBlank(message = "text is mandatory")
    private String text;
    @Size(min =2)
    @NotNull(message = "Options are mandatory")
    private String[] options;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int[] answer;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(nullable = false)
    User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Question()
    {

    }



    public Question(String title, String text, String[] options, int[] answer)
    {
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public String[] getOptions() {
        return options;
    }

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }
    public void setAnswer(int[] answer) {
        this.answer = answer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int[] getAnswer() {
        return answer;
    }
public boolean doesAnswerContains(Integer i)
    {
        for(Integer z = 0; z<answer.length; z++)
        {
            if(answer[z] == i)
            {
                return true;
            }
        }
        return false;
    }
}
