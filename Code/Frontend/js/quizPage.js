'use strict';

let quizID = window.location.hash.substring(1);

// Get all questions from quiz 1
fetch(`http://localhost:8901/quiz/getById/${quizID}`)
.then( (response) => {
    if (response.status !== 200){
        console.log(`status ${response.status}`);
        return;
    }
    response.json()
    .then( (data) => {
        // WHEN QUIZ IS SELECTED SWITCH PAGE PASS ID TO quizPage.html
        console.log(data.questions)
    })
    .catch( (error) => {console.log(error)})
});