'use strict';
let quizID = window.location.hash.substring(1);

let carouselElement;

// Store answers for evaluation
let answers = [];

let numQuestions;

// Get all questions from selected quiz
fetch(`http://localhost:8901/quiz/getById/${quizID}`)
.then( (response) => {
    if (response.status !== 200){
        console.log(`status ${response.status}`);
        return;
    }
    response.json()
    .then( (data) => {
        carouselElement = document.getElementById('carouselCustom');
        generateCarousel(data)
    })
    .catch( (error) => {console.log(error)})
});


//Generates carousel for questions to be displayed
let generateCarousel = (data) => {
    // Container for all slides
    let carouselInner = document.createElement('div');
    carouselInner.className = 'carousel-inner height100';

    numQuestions = data.questions.length;
    // Slide generation
    for(let i=0;i !== numQuestions;i++) {
        let carouselItem = document.createElement('div');

        //Store answers for later
        answers.push(data.questions[i].correct);

        if (i === 0) {carouselItem.className = 'carousel-item active height100 questionSlide'}
        else{carouselItem.className = 'carousel-item height100 questionSlide'}
        
        let svg = document.createElement('div');
        svg.className = 'bd-placeholder-img bd-placeholder-img-lg d-block w-100 height100';

        svg.setAttribute('width', '800px');
        svg.setAttribute('height', '400px');


        let questionText = document.createElement('p')
        questionText.textContent = `Question ${i+1}: ${data.questions[i].question}`;
        questionText.className = 'height100 questionText';
        questionText.setAttribute('x', '50%');
        questionText.setAttribute('y', '50%');
        

        let answerButtons = document.createElement('div')
        answerButtons.className = 'carousel-caption d-none d-md-block';


        //Radio button definitons for answerButtons
        let radioButtonGroup = document.createElement('div');
        radioButtonGroup.className = 'btn-group';
        radioButtonGroup.setAttribute('role', 'group');
        
        radioButtonGroup = createRadioButtons(radioButtonGroup, data.questions[i].answers, i);
        
        // Append all to DOM
        svg.appendChild(questionText);
        carouselItem.appendChild(svg);
        answerButtons.appendChild(radioButtonGroup)
        carouselItem.appendChild(answerButtons);
        carouselInner.appendChild(carouselItem);
        carouselElement.appendChild(carouselInner);
    };
}

let createRadioButtons = (tempRadioGroup, ans, qNum) => {
    let ansArray = ans.split(';');
    let numAns = ansArray.length;

    for(let i=0; i !== numAns; i++){
        //Make radio button for each ansArray[i]
        let radioInput = document.createElement('input');
        radioInput.setAttribute('type', 'radio');
        radioInput.className = 'btn-check bringFront';
        radioInput.setAttribute('name', `btnradio${qNum+1}`);
        radioInput.setAttribute('autocomplete', 'off');
        radioInput.setAttribute('value', `${ansArray[i]}`)

        //Id need to be set dynamically for each answer
        radioInput.setAttribute('id', `btnradio${((qNum+1)*4)+i}`);


        let radioLabel = document.createElement('label');
        radioLabel.className = 'btn btn-outline-primary';
        radioLabel.setAttribute('for', `btnradio${((qNum+1)*4)+i}`);
        radioLabel.textContent = ansArray[i];

        tempRadioGroup.appendChild(radioInput);
        tempRadioGroup.appendChild(radioLabel);
    }
    return tempRadioGroup;
};

function evalAnswers(){
    let allInputs = document.getElementsByTagName('input');
    let scoreDisplay = document.getElementById('scoreContainer');
    let selectedAns = [];
    let score = 0;
    

    //Get entered answers from form
    for(let i=0; i !== allInputs.length; i++){
        if(allInputs[i].checked === true){
            selectedAns.push(allInputs[i].value);
        }
    }

    //Calculate how many correct answers
    for(let i=0; i !== answers.length; i++){
        if(selectedAns[i] === answers[i]){
            score += 1;
    }}

    //Display score on modal element
    scoreDisplay.textContent = `Congratulations! You scored: ${score}/${numQuestions}!`
}
