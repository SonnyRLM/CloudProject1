'use strict';
let quizID = window.location.hash.substring(1);

let carouselElement;

// Get all questions from quiz 1
fetch(`http://localhost:8901/quiz/getById/${quizID}`)
.then( (response) => {
    if (response.status !== 200){
        console.log(`status ${response.status}`);
        return;
    }
    response.json()
    .then( (data) => {
        console.log(data.questions)

        carouselElement = document.getElementById('carouselCustom');
        
        generateCarousel(data)

    })
    .catch( (error) => {console.log(error)})
});



let generateCarousel = (data) => {
    // Container for all slides
    let carouselInner = document.createElement('div');
    carouselInner.className = 'carousel-inner height100';

    let numQuestions = data.questions.length;
    // Slide generation
    for(let i=0;i !== numQuestions;i++) {
    
        let carouselItem = document.createElement('div');


        if (i === 0) {carouselItem.className = 'carousel-item active height100 questionSlide'}
        else{carouselItem.className = 'carousel-item height100 questionSlide'}
        
        let svg = document.createElement('div');
        svg.className = 'bd-placeholder-img bd-placeholder-img-lg d-block w-100 height100';

        svg.setAttribute('width', '800px');
        svg.setAttribute('height', '400px');


        let questionText = document.createElement('text')
        questionText.textContent = data.questions[i].question;
        questionText.className = 'height100 questionText';
        questionText.setAttribute('x', '50%');
        questionText.setAttribute('y', '50%');
        

        let answerButtons = document.createElement('div')
        answerButtons.className = 'carousel-caption d-none d-md-block';


        //Radio button definitons for answerButtons
        let radioButtonGroup = document.createElement('div');
        radioButtonGroup.className = 'btn-group';
        radioButtonGroup.setAttribute('role', 'group');
        
        radioButtonGroup = createRadioButtons(radioButtonGroup, data.questions[i].answers);
        



        // Append all to DOM
        svg.appendChild(questionText);
        carouselItem.appendChild(svg);
        answerButtons.appendChild(radioButtonGroup)
        carouselItem.appendChild(answerButtons);
        carouselInner.appendChild(carouselItem);
        carouselElement.appendChild(carouselInner);
    };
}

let createRadioButtons = (tempRadioGroup, ans) => {
    //let tempRadioGroup = document.createElement('div')

    let ansArray = ans.split(';');
    let numAns = ansArray.length;

    for(let i=0; i !== numAns; i++){
        //Make button for each ansArray[i]

        let radioInput = document.createElement('input');
        radioInput.setAttribute('type', 'radio');
        radioInput.className = 'btn-check';
        radioInput.setAttribute('name', 'btnradio');
        radioInput.setAttribute('autocomplete', 'off')

        //Id need to be set dynamically for each answer
        radioInput.setAttribute('name', `btnradio${i+1}`);


        let radioLabel = document.createElement('label');
        radioLabel.className = 'btn btn-outline-primary';
        radioLabel.setAttribute('for', `btnradio${i+1}`);
        radioLabel.textContent = ansArray[i];

        tempRadioGroup.appendChild(radioInput);
        tempRadioGroup.appendChild(radioLabel);
    }
    return tempRadioGroup;
};