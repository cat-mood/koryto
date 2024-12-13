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

async function fetchUser(id) {
    return await fetch(`http://localhost:8080/api/user?id=${id}`)
        .then(response => {
            if (!response.ok) throw new Error("Ошибка загрузки пользователя");
            return response.json();
        });
}

async function updateUser(user) {
    console.log(JSON.stringify(user));
    await fetch(
        'http://localhost:8080/admin/update-role', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(user)
        }
    ).then(response => {
        if (!response.ok) {
            return response.text().then(errorMessage => {
                alert('Add part error: ' + errorMessage);
                throw new Error('Add part error: ' + errorMessage);
            });
        }
    });
}

async function toggleUserRole(id) {
    const user = await fetchUser(id);
    console.log(user);
    if (user) {
        user.role = user.role === 'ROLE_ADMIN' ? 'ROLE_USER' : 'ROLE_ADMIN';
        await updateUser(user);
    }

    location.reload();
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

        closeAddProductModal();
        location.reload();
    });

    closeAddProductModal();
    location.reload();
}

async function fetchModels() {
    const brand = document.getElementById("carBrand").value;
    const modelsDropdown = document.getElementById("carModel");

    // Очистка текущего списка моделей
    modelsDropdown.innerHTML = '<option value="">Выберите модель</option>';

    if (brand) {
        await fetch(`/api/cars/models?brand=${brand}`)
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

async function fetchEditModels() {
    const brand = document.getElementById("editCarBrand").value;
    const modelsDropdown = document.getElementById("editCarModel");

    // Очистка текущего списка моделей
    modelsDropdown.innerHTML = '<option value="">Выберите модель</option>';

    if (brand) {
        await fetch(`/api/cars/models?brand=${brand}`)
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

function closeEditProductModal() {
    document.getElementById('editProductModal').style.display = 'none';
    document.getElementById('editProductForm').reset();
}

function handleEditProduct(event) {
    event.preventDefault();

    const id = parseInt(document.getElementById('editProductId').value);

    const updateProduct = {
        partId: id,
        partName: document.getElementById('editPartName').value,
        categoryName: document.getElementById('editCategory').value,
        manufacturerName: document.getElementById('editManufacturer').value,
        carBrandName: document.getElementById('editCarBrand').value,
        carModelName: document.getElementById('editCarModel').value,
        partDescription: document.getElementById('editDescription').value,
        price: parseFloat(document.getElementById('editPrice').value)
    };

    console.log(updateProduct);

    fetch(
        'http://localhost:8080/admin/update-part', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(updateProduct)
        }
    ).then(response => {
        if (!response.ok) {
            return response.text().then(errorMessage => {
                alert('Add part error: ' + errorMessage);
                throw new Error('Add part error: ' + errorMessage);
            });
        }

        closeEditProductModal();
        location.reload();
    })
        .catch(error => {
            console.error('Error adding part: ', error);
            alert('Failed to add part due to a network error.');
        });
}

async function showEditProductModal(id) {
    const brandDropdown = document.getElementById("editCarBrand");

    await fetch('/api/cars/brands')
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

    const categoryDropdown = document.getElementById("editCategory");

    await fetch('/api/category')
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
        });

    const manufacturerDropdown = document.getElementById("editManufacturer");

    await fetch('/api/manufacturer')
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

    // Отправляем запрос на сервер для получения продукта по id
    await fetch(`http://localhost:8080/api/part?id=${id}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch product details');
            }
            return response.json();
        })
        .then(async product => {
            // Проверяем, получили ли мы данные о продукте
            if (!product) return;

            // Заполняем поля формы значениями продукта
            document.getElementById('editProductId').value = id;
            document.getElementById('editPartName').value = product.partName;
            document.getElementById('editCategory').value = product.categoryName;
            document.getElementById('editManufacturer').value = product.manufacturerName;
            document.getElementById('editCarBrand').value = product.carBrandName;

            await fetchEditModels(); // Обновляем выпадающий список моделей автомобилей

            console.log(product.carModelName);
            document.getElementById('editCarModel').value = product.carModelName;
            document.getElementById('editDescription').value = product.partDescription;
            document.getElementById('editPrice').value = product.price;

            // Показываем модальное окно
            document.getElementById('editProductModal').style.display = 'block';
        })
        .catch(error => {
            console.error('Error fetching product details:', error);
            alert('Failed to load product details. Please try again.');
        });
}

