'use strict'
let cardContainer;
let quizTarget;

// Get all quiz names/descriptions
fetch(`http://localhost:8901/quiz/getAll`)
.then( (response) => {
    if (response.status !== 200){
        console.log(`status ${response.status}`);
        return;
    }
    response.json()
    .then( (data) => {
        //Find container DOM object
        cardContainer = document.getElementById('cards');
        //Generate card for each quiz
        data.forEach( (data) => {
            generateCards(data);
        });
    })
    .catch( (error) => {console.log(error)})
});


//Generates cards dynamically for each quiz in database
let generateCards = (data) => {
    let card = document.createElement('div');

    card.className = 'card';
    card.setAttribute('style', 'width:18rem; margin: 20px; border-color: grey; border-width: 2px;')
    
    let cardBody = document.createElement('div');
    cardBody.className = 'card-body';

    let title = document.createElement('h5');
    title.innerText = data.quizName;
    title.className = 'card-title';

    let description = document.createElement('p');
    description.innerText = data.quizDescription;
    description.className = 'card-text';

    let img = document.createElement('img');
    img.className = 'card-img-top';
    img.src =  'images/noImage.png';

    let btn = document.createElement('a')
    btn.className = 'btn btn-primary';
    btn.innerText = 'Take this quiz';
    btn.href = 'quizPage.html' + '#' + data.quiz_id;

    let del = document.createElement('button')
    del.className = 'btn btn-danger';
    del.textContent = 'Edit/Delete'
    del.setAttribute('style', 'float:right; width:100px; height:38px; font-size:15px')
    del.setAttribute('data-bs-toggle', 'modal')
    del.setAttribute('data-bs-target', '#exampleModal')
    del.setAttribute('onclick', `setQuizTarget(${data.quiz_id})`)

    cardBody.appendChild(img);
    cardBody.appendChild(title);
    cardBody.appendChild(description);
    cardBody.appendChild(btn);
    cardBody.appendChild(del)
    card.appendChild(cardBody);
    cardContainer.appendChild(card); 
}

//Set quizTarget when 'edit/delete' button is pressed
let setQuizTarget = (id) => {
    quizTarget = id;
}

let deleteQuiz = () => {

    //HTTP request to delete quiz at given id
    fetch(`http://localhost:8901/quiz/delete/${quizTarget}`, {
        method: `DELETE`
    })
    .then( (data) => console.log(`Request all good with JSON response ${data}`))
    .catch( (error) => console.log(error));

}

let editQuiz = () => {
    console.log('go edit this quiz: ' + quizTarget)
    window.location.href = 'editQuiz.html'+ '#' + quizTarget;
}