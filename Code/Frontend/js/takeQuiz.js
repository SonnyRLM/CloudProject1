'use strict'

let cardContainer;

// Get all quiz names/descriptions
fetch('http://localhost:8901/quiz/getAll')
.then( (response) => {
    if (response.status !== 200){
        console.log(`status ${response.status}`);
        return;
    }
    response.json()
    .then( (data) => {
        // CREATES CARD FOR EACH QUIZ WITH NAME AND DESCRIPTION
        console.log(data)

        cardContainer = document.getElementById('cards');
        console.log(cardContainer);
        data.forEach( (data) => {
            generateCards(data);
        });
    })
    .catch( (error) => {console.log(error)})
});



let generateCards = (data) => {
    let card = document.createElement('div');

    card.className = 'card';
    //card.style["width", "margin", "border-color", "border-width"] = ['18rem', '20px', 'grey', '2px']
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

    cardBody.appendChild(img);
    cardBody.appendChild(title);
    cardBody.appendChild(description);
    cardBody.appendChild(btn);
    card.appendChild(cardBody);
    cardContainer.appendChild(card); 
}

