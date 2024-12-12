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

// Modal handling
function showAddProductModal() {
    document.getElementById('addProductModal').style.display = 'block';

    const brandDropdown = document.getElementById("carBrand");

    fetch('/api/cars/brands')
        .then(response => {
            if (!response.ok) throw new Error("Ошибка загрузки брендов");
            return response.json();
        })
        .then(brands => {
            // Заполняем выпадающий список брендов
            brandDropdown.innerHTML = '<option value="">Выберите бренд</option>';
            console.log(typeof brands, brands);
            brands.forEach(brand => {
                const option = document.createElement("option");
                option.value = brand;
                option.textContent = brand;
                brandDropdown.appendChild(option);
                console.log(brand, typeof brand);
            });
        })
        .catch(error => console.error('Ошибка:', error));

    const categoryDropdown = document.getElementById("category");

    fetch('/api/category')
        .then(response => {
            if (!response.ok) throw new Error("Ошибка загрузки категорий");
            return response.json();
        })
        .then(categories => {
            // Заполняем выпадающий список брендов
            categoryDropdown.innerHTML = '<option value="">Выберите бренд</option>';
            categories.forEach(category => {
                const option = document.createElement("option");
                option.value = category.categoryName;
                option.textContent = category.categoryName;
                categoryDropdown.appendChild(option);
            });
        })
        .catch(error => console.error('Ошибка:', error));

    const manufacturerDropdown = document.getElementById("manufacturer");

    fetch('/api/manufacturer')
        .then(response => {
            if (!response.ok) throw new Error("Ошибка загрузки категорий");
            return response.json();
        })
        .then(manufacturers => {
            // Заполняем выпадающий список брендов
            manufacturerDropdown.innerHTML = '<option value="">Выберите бренд</option>';
            manufacturers.forEach(manufacturer => {
                const option = document.createElement("option");
                option.value = manufacturer.manufacturerName;
                option.textContent = manufacturer.manufacturerName;
                manufacturerDropdown.appendChild(option);
            });
        })
        .catch(error => console.error('Ошибка:', error));
}

function closeAddProductModal() {
    document.getElementById('addProductModal').style.display = 'none';
    document.getElementById('addProductForm').reset();
}

function handleAddProduct(event) {
    event.preventDefault();

    const newProduct = {
        partName: document.getElementById('partName').value,
        categoryName: document.getElementById('category').value,
        manufacturerName: document.getElementById('manufacturer').value,
        carBrandName: document.getElementById('carBrand').value,
        carModelName: document.getElementById('carModel').value,
        partDescription: document.getElementById('description').value,
        price: parseFloat(document.getElementById('price').value)
    };

    console.log(newProduct);

    fetch(
        'http://localhost:8080/admin/add-part', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newProduct)
        }
    ).then(response => {
        if (!response.ok) {
            return response.text().then(errorMessage => {
                alert('Add part error: ' + errorMessage);
                throw new Error('Add part error: ' + errorMessage);
            });
        }

        alert('Part added successfully!');
        closeAddProductModal();
        location.reload();
    })
        .catch(error => {
            console.error('Error adding part: ', error);
            alert('Failed to add part due to a network error.');
        });

    closeAddProductModal();
    location.reload();
}

function fetchModels() {
    const brand = document.getElementById("carBrand").value;
    const modelsDropdown = document.getElementById("carModel");

    // Очистка текущего списка моделей
    modelsDropdown.innerHTML = '<option value="">Выберите модель</option>';

    if (brand) {
        fetch(`/api/cars/models?brand=${brand}`)
            .then(response => response.json())
            .then(models => {
                models.forEach(model => {
                    const option = document.createElement("option");
                    option.value = model;
                    option.textContent = model;
                    modelsDropdown.appendChild(option);
                });
            })
            .catch(error => console.error('Ошибка загрузки моделей:', error));
    }
}
