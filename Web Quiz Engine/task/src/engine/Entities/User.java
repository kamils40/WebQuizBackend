package engine.Entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
public class User {
    @NotNull
    @Email(regexp = ".+@.+\\..+|")
    //@Pattern(regexp="([a-zA-Z0-9\\-\\.\\_]+)'+'(\\@)([a-zA-Z0-9\\-\\.]+)'+'(\\.)([a-zA-Z])$")
    private String email;

    @NotNull
    @Size(min = 5, message = "Password must be at least 5 characters long")
    private String password;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER,orphanRemoval = true, cascade = CascadeType.DETACH)
    private Set<Question> questions;

    public User()
    {

    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    public boolean isValid()
    {
        if(this.email.contains(".") && this.email.contains("@") && this.password.length() >4)
        {
            return true;
        }
        return false;
    }
}
