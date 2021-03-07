'use strict';
let quizID = window.location.hash.substring(1);

let questionIDArray = new Array();

// Get all questions from selected quiz
fetch(`http://localhost:8901/quiz/getById/${quizID}`)
.then( (response) => {
    if (response.status !== 200){
        console.log(`status ${response.status}`);
        return;
    }
    response.json()
    .then( (data) => {
        console.log(data)
        populateForm(data)
    })
    .catch( (error) => {console.log(error)})
});


let populateForm = (data) => {
    //Set Quiz name and description on page
    let quizNameBox = document.getElementById('quizName');
    quizNameBox.value = data.quizName;
    
    let quizDescBox = document.getElementById('quizDesc');
    quizDescBox.value = data.quizDescription


    let numQuestions = data.questions.length;

    for(let i=0; i !== numQuestions; i++){
        let content = data.questions[i];
        let quizQuestion = content.question;
        let answers = content.answers.split(';');
        let correct = content.correct;

        //Collect all question ID's for updating later
        questionIDArray.push(content.question_id);
        
        addQuestions(quizQuestion, answers, correct)
    }
}


let addQuestions = (quizQuestion, answers, correct) => {
    let questionContainer = document.createElement('div');
    let textRadioParent = document.createElement('div');
    textRadioParent.className = 'input-group mb-3';

    let questionInput = document.createElement('input');
    questionInput.type = 'text';
    questionInput.id = 'textBox';
    questionInput.className = 'form-control';
    questionInput.value = `${quizQuestion}`;

    for(let i = 0; i !== 4; i++){
        
        

        let ansInput = document.createElement('input');
        ansInput.setAttribute('type', 'text');
        ansInput.setAttribute('id',  `radio${((numberOfQuestions+1)*4)+i}`)
        ansInput.className = 'form-control';
        ansInput.placeholder = 'Answer';
        ansInput.value = answers[i];


        let radInput = document.createElement('input');
        radInput.setAttribute('type', 'radio');
        radInput.className = 'btn-check';
        radInput.setAttribute('name', `btnradio${numberOfQuestions+1}`);
        radInput.setAttribute('id',  `btnradio${((numberOfQuestions+1)*4)+i}`)
        if(answers[i] === correct){
            radInput.checked = true;
        }
        

        let radLabel = document.createElement('label');
        radLabel.className = 'btn btn-outline-primary'
        radLabel.setAttribute('for', `btnradio${((numberOfQuestions+1)*4)+i}`)
        radLabel.textContent = '✔'

        textRadioParent.appendChild(ansInput);
        textRadioParent.appendChild(radInput);
        textRadioParent.appendChild(radLabel);
    }
    questionContainer.appendChild(questionInput);
    questionContainer.appendChild(textRadioParent);

    questionParent.appendChild(questionContainer);


    numberOfQuestions++;
}

//Updates name and description of the selected quiz
let updateQuiz = () => {

    //Get inputted values
    let quizName = document.getElementById('quizName').value;
    let quizDesc = document.getElementById('quizDesc').value;

    //Make sure there is at least 1 question
    let numAns = (4 * (numberOfQuestions-1));
    if (numAns === 0){
        return;
    }

    //Create object to post
    let objectToPost = JSON.stringify({
        "quizDescription": quizDesc,
        "quizName": quizName,
        "quiz_id": quizID
    });

    fetch(`http://localhost:8901/quiz/update/${quizID}`,{
        method: `PUT`,
        headers: {"Content-Type": "application/json"},
        body: objectToPost
    })
    .then( (response) => {
        if (response.status !== 202){
            console.log(`Status ${response.status}`);
            return;
        } else {
            console.log(`All good ${response.status}`)
        }
        response.json()
        .then( (data) => {
            console.log(`Request Successful with JSON response ${data}`)
    })
    .catch( (error) => console.log(error))
    });
    
    updateQuestions();
}

//Updates questions in selected quiz
let updateQuestions = () => {
    // Array of question objects
    let allQuestions = [];

    //Number of answers to loop over
    let numAns = (4 * (numberOfQuestions-1));

    //Make sure there is at least 1 question
    if (numAns === 0){
        return;
    }
    
    //Loop over each inputted question
    for(let i=8; i!==(8+numAns); i=i+4){
        let answers = [];
        let correct;
        let questions;
        let questionId = questionIDArray[0];

        questionIDArray.shift();

        //Loop over each answer in a question
        for(let j=0; j!==4;j++){
            let textId = `radio${i+j}`
            let radioId = `btnradio${i+j}`
            
            //Add all answers to array
            answers.push(document.getElementById(textId).value);
            
            //Place 'correct' answer in seperate variable
            if(document.getElementById(radioId).checked === true){
                correct = document.getElementById(textId).value;
            }

            //Get inputted question
            questions = document.getElementById(textId).parentElement.parentElement.firstChild.value;
            }

        //Format inputted answers for database
        answers = answers.join(";");

        //Create object to post
        let objectToPost =  JSON.stringify({
            "question": questions,
            "answers": answers,
            "correct": correct,
            "quizDescription":{
                "quiz_id": quizID
            }
        });

        if(!questionId){
            console.log("New Question Detected")
            saveNewQuestion(questions, answers, correct)
            return;
        }
        
        fetch(`http://localhost:8901/question/update/${questionId}`,{
            method: `PUT`,
            headers: {"Content-Type": "application/json"},
            body: objectToPost
        })
        .then( (response) => {
            if (response.status !== 202){
                console.log(`Status ${response.status}`);
                return;
            } else {
                console.log(`All good ${response.status}`)
            }
            response.json()
            .then( (data) => {
                console.log(`Request Successful with JSON response ${data}`)
        })
        .catch( (error) => console.log(error))
        });
        }


        //After saving anything left in questionIDArray needs to be deleted#
        if(questionIDArray){
            console.log("I need to delete: " + questionIDArray);

            deleteQuestions(questionIDArray);

        }
    }


    let saveNewQuestion = (questions, answers, correct) => {

        let objectToPost =  JSON.stringify({
            "question": questions,
            "answers": answers,
            "correct": correct,
            "quizDescription":{
                "quiz_id": quizID
            }
        });


        //Post JSON to sql server
        fetch(`http://localhost:8901/question/create`,{
            method: `POST`,
            headers: {"Content-Type": "application/json"},
            body: objectToPost
        })
        .then( (response) => {
            if (response.status !== 201){
                console.log(`Status ${response.status}`);
                return;
            } else {
                console.log(`All good ${response.status}`)
            }
            response.json()
            .then( (data) => {
                console.log(`Request Successful with JSON response ${data}`)
        })
        .catch( (error) => console.log(error))
        });
    }


    let deleteQuestions = (questionIDArray) => {
        for(let i=0; i !== questionIDArray.length; i++){
            //HTTP request to delete quiz at given id
                    fetch(`http://localhost:8901/question/delete/${questionIDArray[i]}`, {
                        method: `DELETE`
                    })
                    .then( (data) => console.log(`Request all good with JSON response ${data}`))
                    .catch( (error) => console.log(error));
        }
        
    
    }