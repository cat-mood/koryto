// document.getElementById('loginForm').addEventListener('submit', function(e) {
//     e.preventDefault();
//
//     const email = document.getElementById('email').value;
//     const password = document.getElementById('password').value;
//
//     // Here you would typically make an API call to your backend
//     console.log('Login attempt:', { email, password });
//
//     // Simulate API call
//     fetch('https://api.autopartspro.com/auth/login', {
//         method: 'POST',
//         headers: {
//             'Content-Type': 'application/json',
//         },
//         body: JSON.stringify({ email, password })
//     })
//         .then(response => response.json())
//         .then(data => {
//             if (data.success) {
//                 window.location.href = 'https://autopartspro.com/dashboard';
//             } else {
//                 alert('Invalid credentials. Please try again.');
//             }
//         })
//         .catch(error => {
//             console.error('Login error:', error);
//             alert('An error occurred during login. Please try again.');
//         });
// });

// Social login handlers
document.querySelector('.google-btn').addEventListener('click', function() {
    window.location.href = 'https://api.autopartspro.com/auth/google';
});

document.querySelector('.facebook-btn').addEventListener('click', function() {
    window.location.href = 'https://api.autopartspro.com/auth/facebook';
});