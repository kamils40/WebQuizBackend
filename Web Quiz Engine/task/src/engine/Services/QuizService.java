package engine.Services;

import engine.Repositories.CompletedQuizRepository;
import engine.Entities.CompletedQuizInfo;
import engine.Entities.Question;
import engine.Repositories.QuestionRepository;
import engine.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class QuizService {


    private QuestionRepository questionRepository;
    private UserRepository userRepository;
    private CompletedQuizRepository completedQuizRepository;

    @Autowired
    public QuizService(QuestionRepository questionRepository, UserRepository userRepository,
                       CompletedQuizRepository completedQuizRepository)
    {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.completedQuizRepository = completedQuizRepository;
    }

    public Question create(Question question, Authentication auth)
    {

        question.setUser(userRepository.findByEmail(auth.getName()));
        questionRepository.save(question);
        return question;

    }
    public ResponseEntity<String> deleteQuiz(Integer id, Authentication auth)
    {
        Question questionToDelete = null;
        Set<Question> userQuestions = userRepository.findByEmail(auth.getName()).getQuestions();
        for(Question q : userQuestions)
        {
            if(q.getId() == id)
            {
               questionToDelete = q;
            }
        }
        if(questionRepository.findById(id).isEmpty())
        {
            return new ResponseEntity<>("Quiz was not found", HttpStatus.NOT_FOUND);
        }
        if(questionToDelete == null)
        {
            return new ResponseEntity<>("You are not an author of the Quiz", HttpStatus.FORBIDDEN);
        }
        else
        {
            questionRepository.deleteById(questionToDelete.getId());
            return new ResponseEntity<>("Question successfully deleted", HttpStatus.NO_CONTENT);
        }
    }
    public Question read(Integer id, HttpServletResponse res)
    {
       try {
           return questionRepository.findById(id).get();
       }
       catch(NoSuchElementException ex)
       {
           res.setStatus(404);
           return null;
       }

    }
    public void addSolvedQuestion(Question question, Authentication auth)
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Instant instant = timestamp.toInstant();
        completedQuizRepository.save(new CompletedQuizInfo(completedQuizRepository.count(),question.getId(),
                instant.toString(), auth.getName()));
    }
    public boolean solvingQuestion(Question question, int[] answer, Authentication auth)
    {
        if((answer ==null && question.getAnswer() ==null) |
                (answer == null && question.getAnswer().length ==0)|
                (answer.length == 0 && question.getAnswer()==null))
        {


            return true;
        }
        if(answer != null && question.getAnswer() != null) {
            return areAnswersEqual(question, answer);

        }
        return false;
    }
    public Page<CompletedQuizInfo> getCompletedQuizzes(String name, int page)
    {
        return completedQuizRepository.findByUsername(name, PageRequest.of(page,10,
                Sort.by("completedAt").descending()));
    }

    public boolean areAnswersEqual(Question question,int[] array)
    {
        if(array == null | question==null | array.length != question.getAnswer().length)
        {
            return false;
        }

        for( int i : array)
        {

            if(!question.doesAnswerContains(i)) {
                return false;
            }

        }
        return true;
    }
    public void delete(int id)
    {
        questionRepository.deleteById(id);
    }

    public Page<Question> printAll(Integer pageNumber)
    {
        return questionRepository.findAll(PageRequest.of(pageNumber, 10, Sort.by("id")));
    }
    public int getQuestionRepositorySize()
    {
        return (int) this.questionRepository.count();
    }

}
