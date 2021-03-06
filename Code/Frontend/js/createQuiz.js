
//Parent Element for questionInput's
let questionParent = document.getElementById('questionWrapper')

let numberOfQuestions = 1;

//Create element to input each question
let addQuestElement = () => {
    let questionContainer = document.createElement('div');
    let textRadioParent = document.createElement('div');
    textRadioParent.className = 'input-group mb-3';

    let questionInput = document.createElement('input');
    questionInput.type = 'text';
    questionInput.id = 'textBox';
    questionInput.className = 'form-control';
    questionInput.placeholder = `Question ${numberOfQuestions}`;

    for(let i = 0; i !== 4; i++){
        
        

        let ansInput = document.createElement('input');
        ansInput.setAttribute('type', 'text');
        ansInput.setAttribute('id',  `radio${((numberOfQuestions+1)*4)+i}`)
        ansInput.className = 'form-control';
        ansInput.placeholder = 'Answer';


        let radInput = document.createElement('input');
        radInput.setAttribute('type', 'radio');
        radInput.className = 'btn-check';
        radInput.setAttribute('name', `btnradio${numberOfQuestions+1}`);
        radInput.setAttribute('id',  `btnradio${((numberOfQuestions+1)*4)+i}`)
        radInput.checked = true;

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

//Remove bottom question
let delQuestElement = () => {
    let parentElement = document.getElementById('questionWrapper');
    parentElement.removeChild(parentElement.lastChild);
    numberOfQuestions--;
}


let saveQuestions = (quiz_id) => {

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
        objectToPost = JSON.stringify({
            "answers": answers,
            "correct": correct,
            "question": questions,
            "quizDescription": {
                "quiz_id": quiz_id
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
};
}
    

let saveQuiz = () => {

    //Get inputted values
    let quizName = document.getElementById('quizName').value;
    let quizDesc = document.getElementById('quizDesc').value;

    //Make sure there is at least 1 question
    let numAns = (4 * (numberOfQuestions-1));
    if (numAns === 0){
        return;
    }

    //Create object to post
    objectToPost = JSON.stringify({
        "quizDescription": quizDesc,
        "quizName": quizName
    })

    //Post JSON to sql server
    fetch(`http://localhost:8901/quiz/create`,{
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
        
        //Save questions to new quiz id
        saveQuestions(data.quiz_id)

    })
    .catch( (error) => console.log(error))
    });

    //Return to quiz page after adding quiz - THIS IS BROKEN FIX
    //leavePage();
}

let leavePage = () => {
    window.location.href = 'takeQuiz.html'
}


