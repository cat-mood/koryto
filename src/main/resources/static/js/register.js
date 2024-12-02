document.getElementById('registerForm').addEventListener('submit', function(e) {
    e.preventDefault();

    // Validate passwords match
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;

    if (password !== confirmPassword) {
        alert('Passwords do not match!');
        return;
    }

    // Collect form data
    const formData = {
        firstName: document.getElementById('firstName').value,
        middleName: document.getElementById('middleName').value,
        lastName: document.getElementById('lastName').value,
        email: document.getElementById('email').value,
        birthDate: document.getElementById('birthDate').value,
        city: document.getElementById('city').value,
        address: document.getElementById('address').value,
        postIndex: document.getElementById('postIndex').value,
        carBrand: document.getElementById('carBrand').value,
        carModel: document.getElementById('carModel').value,
        password: password
    };

    // Send registration request
    fetch('https://api.autopartspro.com/auth/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData)
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert('Registration successful! Please check your email to verify your account.');
                window.location.href = 'https://autopartspro.com/login';
            } else {
                alert(data.message || 'Registration failed. Please try again.');
            }
        })
        .catch(error => {
            console.error('Registration error:', error);
            alert('An error occurred during registration. Please try again.');
        });
});

// Dynamic car models based on brand selection (can be expanded)
const carModels = {
    toyota: ['Camry', 'Corolla', 'RAV4', 'Highlander'],
    honda: ['Civic', 'Accord', 'CR-V', 'Pilot'],
    ford: ['F-150', 'Mustang', 'Explorer', 'Escape'],
    bmw: ['3 Series', '5 Series', 'X3', 'X5'],
    mercedes: ['C-Class', 'E-Class', 'GLC', 'GLE'],
    volkswagen: ['Golf', 'Passat', 'Tiguan', 'Atlas'],
    audi: ['A3', 'A4', 'Q5', 'Q7']
};

document.getElementById('carBrand').addEventListener('change', function() {
    const modelInput = document.getElementById('carModel');
    const selectedBrand = this.value;

    if (selectedBrand && carModels[selectedBrand]) {
        const datalist = document.createElement('datalist');
        datalist.id = 'modelsList';
        carModels[selectedBrand].forEach(model => {
            const option = document.createElement('option');
            option.value = model;
            datalist.appendChild(option);
        });

        modelInput.setAttribute('list', 'modelsList');
        document.querySelector('#modelsList')?.remove();
        document.body.appendChild(datalist);
    }
});