package engine;

import engine.Entities.*;
import engine.Services.QuizService;
import engine.Services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

@RestController
public class Quiz {

   private final QuizService quizService;

    private final UserService userService;

    public Quiz(QuizService quizService, UserService userService) {
        this.quizService = quizService;
        this.userService = userService;
    }

    @GetMapping(path = "/api/quizzes/{id}")
    public Object getQuestion(@PathVariable Integer id, HttpServletResponse res) {

        if (id > quizService.getQuestionRepositorySize()
                || quizService.getQuestionRepositorySize() == 0) {
            res.setStatus(404);
            return null;
        }


        return quizService.read(id,res);


    }

    @PostMapping(path = "/api/quizzes", consumes = "application/json")
    public Question addQuestion(@Valid @RequestBody Question question, Authentication auth) {

        quizService.create(question,auth);
        return question;
    }

    @GetMapping(path = "/api/quizzes")
    public Page<Question> allQuestions(@RequestParam Optional<Integer> page) {
        return quizService.printAll(page.orElse(0));
    }




    @PostMapping(path = "/api/quizzes/{id}/solve", consumes = "application/json")
    public Feedback solveQuestion(@RequestBody Answer answer, @PathVariable Integer id,
                                  HttpServletResponse res, Authentication auth )
    {
         Question question = quizService.read(id,res);

        if(question!=null)
        {
            if(quizService.solvingQuestion(question,answer.getAnswer(),auth))
            {
               quizService.addSolvedQuestion(question,auth);
            }
            return new Feedback(quizService.solvingQuestion(question,answer.getAnswer(),auth));
        }
        else
        {
            res.setStatus(404);
            return null;
        }
    }
    @PostMapping(path ="/api/register", consumes = "application/json")
    public Object registerUser(@RequestBody User user, HttpServletResponse res, BindingResult result)
    {
       if(user.isValid())
       {
          return userService.addUser(user,res);
       }
       else
       {
           res.setStatus(400);
           return new String("Your password is too short or you passed wrong email");
       }
   }



    @DeleteMapping(path="api/quizzes/{id}")
    public Object deleteQuestion(@PathVariable int id, HttpServletResponse res, Authentication auth)
    {
        return quizService.deleteQuiz(id,auth);

    }

    @GetMapping(path="api/quizzes/completed")
    public Page<CompletedQuizInfo> getCompletedQuizzes(Authentication auth,
                                                       @RequestParam Optional<Integer> page)
    {
        return quizService.getCompletedQuizzes(auth.getName(),page.orElse(0));
    }





}
