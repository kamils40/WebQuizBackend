Hello, this is my Spring boot project that implements some kind of Quiz Back-end. Project idea was from JetBrainsAcademy. How to use it:
server port is 8889, user info and questions are stored in databases. To register send POST request to /api/quizzes/register with body
that contains email and password, where password need to have at least 5 characters and email must be valid otherwise server returns
status 400. Every other request require authentication.
PostRequest to /api/quizzes/ with JSON body and valid arguments (Title,Text, Options and answers, where program demands at least 2 options)
creates new question and  assign it to the logged user
GetRequest to /api/quizzes/ with o param integer returns JSON page with available quizzes, there's 10 quizzes per page max and integer
given in param determine page number, by default when no integer is given program returns first page.
DeleteQuest to /api/quizzes/id deletes quest with given id, but only when authenticated user is the one that created this Question.
GetMapping to /api/quizzes/id returns JSON with Question information of given id.
GetMapping to/api/quizzes/completed with optional param integer returns page of all quizzes completed by authenticated user,
maximum 10 quizzes per page, given integer determines page number, by default first page, quizzes are sorted by date of complete descending.
PostMapping to /api/quizzes/id/solve with JSON body and answer argument, where answer is an array of integers and given integers are 
counterparts of place of chosen options starting from 0.

Made by Kamil Stawicki as part of my portoflio 

