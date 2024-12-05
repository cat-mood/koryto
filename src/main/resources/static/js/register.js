document.addEventListener("DOMContentLoaded", () => {
    const brandDropdown = document.getElementById("carBrand");

    fetch('/api/cars/brands')
        .then(response => {
            if (!response.ok) throw new Error("Ошибка загрузки брендов");
            return response.json();
        })
        .then(brands => {
            // Заполняем выпадающий список брендов
            brandDropdown.innerHTML = '<option value="">Выберите бренд</option>';
            brands.forEach(brand => {
                const option = document.createElement("option");
                option.value = brand;
                option.textContent = brand;
                brandDropdown.appendChild(option);
            });
        })
        .catch(error => console.error('Ошибка:', error));
});

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
