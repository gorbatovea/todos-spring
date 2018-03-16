function updateCounter(difference) {
    counter += difference;
    counterField.childNodes[0].data = counter + ' items left';
    if (counter === 0) {
        todoContainer.classList.add('__empty');
        selectAll.style.visibility = 'hidden';
        activeFilter.classList.remove('__active');
        activeFilter = filters[0];
        activeFilter.classList.add('__active');
    } else {
        todoContainer.classList.remove('__empty');
        selectAll.style.visibility = 'visible';
    }
}