package engine.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CompletedQuizInfo {

    @Id
    @JsonIgnore
    long qid;

    int id;
    String completedAt;
    @JsonIgnore

    String username;

    public CompletedQuizInfo()
    {

    }

    public CompletedQuizInfo(long qid,int Id, String completedAt, String username) {
        this.qid = qid;
        this.id = Id;
        this.completedAt = completedAt;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public String getCompletedAt() {
        return completedAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCompletedAt(String completedAt) {
        completedAt = completedAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public long getQid() {
        return qid;
    }

    public void setQid(long id) {
        this.qid = id;
    }

}
