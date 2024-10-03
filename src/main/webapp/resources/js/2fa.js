document.addEventListener("DOMContentLoaded", function(event) {

    function otpDivInput() {
        const inputs = document.querySelectorAll('#otpDiv > *[id]');
        
        for (let i = 0; i < inputs.length; i++) {
            inputs[i].addEventListener('keydown', function(event) {
                if (event.key === "Backspace") {
                    inputs[i].value = '';
                    if (i !== 0) inputs[i - 1].focus();
                } else if (event.ctrlKey && event.key === 'v') {
                    // Allow Ctrl + V
                    return true;
                } else {
                    if (i === inputs.length - 1 && inputs[i].value !== '') {
                        return true;
                    } else if (event.keyCode > 47 && event.keyCode < 58) {
                        inputs[i].value = event.key;
                        if (i !== inputs.length - 1) inputs[i + 1].focus();
                        event.preventDefault();
                    } else if (event.keyCode > 64 && event.keyCode < 91) {
                        inputs[i].value = String.fromCharCode(event.keyCode);
                        if (i !== inputs.length - 1) inputs[i + 1].focus();
                        event.preventDefault();
                    }
                }
            });

            inputs[i].addEventListener('paste', function(event) {
                event.preventDefault();
                const pasteData = event.clipboardData.getData('text');
                const pasteArray = pasteData.split('');
                for (let j = 0; j < pasteArray.length; j++) {
                    if (i + j < inputs.length) {
                        inputs[i + j].value = pasteArray[j];
                    }
                }
            });
        }
    }

    otpDivInput();
});