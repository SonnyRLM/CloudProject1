
//Parent Element for questionInput's
let questionParent = document.getElementById('questionWrapper')

let numberOfQuestions = 1;

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
        ansInput.className = 'form-control';
        ansInput.placeholder = 'Answer';


        let radInput = document.createElement('input');
        radInput.setAttribute('type', 'radio');
        radInput.className = 'btn-check';
        radInput.setAttribute('name', `btnradio${numberOfQuestions+1}`);
        radInput.setAttribute('id',  `btnradio${((numberOfQuestions+1)*4)+i}`)

        let radLabel = document.createElement('label');
        radLabel.className = 'btn btn-outline-primary'
        radLabel.setAttribute('for', `btnradio${((numberOfQuestions+1)*4)+i}`)
        radLabel.textContent = '✔'

        textRadioParent.appendChild(ansInput);
        textRadioParent.appendChild(radInput);
        textRadioParent.appendChild(radLabel);
        console.log(textRadioParent);
    }

    console.log(textRadioParent);
    questionContainer.appendChild(questionInput);
    questionContainer.appendChild(textRadioParent);

    questionParent.appendChild(questionContainer);


    numberOfQuestions++;
}


let saveQuestions = () => {



    
}