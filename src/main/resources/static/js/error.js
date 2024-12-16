document.addEventListener('DOMContentLoaded', () => {
    // Get error details from URL parameters if available
    const urlParams = new URLSearchParams(window.location.search);
    const errorCode = urlParams.get('code');
    const errorMessage = urlParams.get('message');

    if (errorCode) {
        document.querySelector('.error-title').textContent = `Error ${errorCode}`;
    }

    if (errorMessage) {
        document.querySelector('.error-message').textContent = errorMessage;
    }
});
