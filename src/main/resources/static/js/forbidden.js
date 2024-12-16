document.addEventListener('DOMContentLoaded', () => {
    const urlParams = new URLSearchParams(window.location.search);
    const errorMessage = urlParams.get('message');

    if (errorMessage) {
        document.querySelector('.error-message').textContent = errorMessage;
    }
});
