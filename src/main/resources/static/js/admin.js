const users = [
    { id: 1, username: 'john_doe', email: 'john@example.com', role: 'user' },
    { id: 2, username: 'jane_smith', email: 'jane@example.com', role: 'admin' }
];


// Navigation
document.querySelectorAll('.nav-item').forEach(item => {
    item.addEventListener('click', function() {
        // Update active states
        document.querySelectorAll('.nav-item').forEach(nav => nav.classList.remove('active'));
        document.querySelectorAll('.panel').forEach(panel => panel.classList.remove('active'));

        this.classList.add('active');
        document.getElementById(this.dataset.panel).classList.add('active');
    });
});

function editProduct(id) {
    const row = document.querySelector(`tr:has([onclick="editProduct(${id})"])`);
    row.querySelector('.product-name').style.display = 'none';
    row.querySelector('.edit-name').style.display = 'block';
    row.querySelector('.product-price').style.display = 'none';
    row.querySelector('.edit-price').style.display = 'block';
    row.querySelector('.save-btn').style.display = 'inline-block';
}

function saveProduct(id) {
    const row = document.querySelector(`tr:has([onclick="editProduct(${id})"])`);
    const newName = row.querySelector('.edit-name').value;
    const newPrice = parseFloat(row.querySelector('.edit-price').value);

    // Update product in array (replace with API call)
    const product = products.find(p => p.id === id);
    if (product) {
        product.name = newName;
        product.price = newPrice;
    }

    renderProducts();
}

function deleteProduct(id) {
    if (confirm('Are you sure you want to delete this product?')) {
        // Remove product from array (replace with API call)
        const index = products.findIndex(p => p.id === id);
        if (index > -1) {
            products.splice(index, 1);
        }
        renderProducts();
    }
}

// Users Management
function renderUsers() {
    const tbody = document.getElementById('usersTable');
    tbody.innerHTML = users.map(user => `
        <tr>
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>${user.email}</td>
            <td>${user.role}</td>
            <td>
                <button class="btn btn-primary" onclick="toggleUserRole(${user.id})">${user.role === 'admin' ? 'Remove Admin' : 'Make Admin'}</button>
                <button class="btn btn-danger" onclick="deleteUser(${user.id})">Delete</button>
            </td>
        </tr>
    `).join('');
}

function toggleUserRole(id) {
    const user = users.find(u => u.id === id);
    if (user) {
        user.role = user.role === 'admin' ? 'user' : 'admin';
        renderUsers();
    }
}

function deleteUser(id) {
    if (confirm('Are you sure you want to delete this user?')) {
        const index = users.findIndex(u => u.id === id);
        if (index > -1) {
            users.splice(index, 1);
        }
        renderUsers();
    }
}

// Initial render
renderProducts();
renderUsers();
