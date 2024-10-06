document.addEventListener("DOMContentLoaded", function(event) {

    const timeRemainingBtn = document.getElementById('timeRemainingBtn');
    let timeRemaining = timeRemainingBtn.getAttribute('data-time');
    // data-time is in seconds
    // set the timer in button as format MM:SS
    
    function formatTime(time) {
        let minutes = Math.floor(time / 60);
        let seconds = time % 60;
        return `${minutes < 10 ? '0' + minutes : minutes}:${seconds < 10 ? '0' + seconds : seconds}`;
    }

    function decrementTime() {
        timeRemaining--;
        timeRemainingBtn.innerText = formatTime(timeRemaining);
        if (timeRemaining === 0) {
            timeRemainingBtn.disabled = true;
            timeRemainingBtn.innerText = 'Resend';
            clearInterval(interval);
        }
    }

    let interval = setInterval(decrementTime, 1000);

});