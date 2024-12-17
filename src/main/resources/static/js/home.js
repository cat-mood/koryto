async function addToCart(partId) {
    await fetch(
        'http://localhost:8080/api/cart/add-part-to-cart', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                'partId': partId,
                'amount': 1
            })
        })
        .then(response => {
            if (!response.ok) throw new Error("Ошибка добавления продукта в корзину");
        })
        .catch(error => console.log(error));

    location.reload();
}

function normalize(value, defaultValue = '') {
    // Проверяем на null, undefined, пустую строку, NaN
    return (value === null || value === undefined || value === '' || isNaN(value))
        ? defaultValue
        : value;
}

// Search and Filter Functionality
const searchInput = document.querySelector('.search-input');
const searchButton = document.querySelector('.search-button');
const filters = document.querySelectorAll('.filter-select');
const priceInputs = document.querySelectorAll('.price-input');

function performSearch() {
    const searchTerm = searchInput.value;
    const categoryId = normalize(document.getElementById('category')?.value);
    const carBrandId = normalize(document.getElementById('carBrand')?.value);
    const manufacturerId = normalize(document.getElementById('manufacturer')?.value);
    const minPrice = normalize(parseFloat(document.getElementById('minPrice')?.value));
    const maxPrice = normalize(parseFloat(document.getElementById('maxPrice')?.value), 'Infinity');

    console.log(searchTerm, categoryId, carBrandId, manufacturerId, minPrice, maxPrice);

    location.href = `http://localhost:8080/?search=${searchTerm}&categoryId=${categoryId}`
    + `&carModelId=${carBrandId}&manufacturerId=${manufacturerId}`
    + `&minPrice=${minPrice}&maxPrice=${maxPrice}`;
}

// Add debounce to prevent too frequent search updates
function debounce(func, wait) {
    let timeout;
    return function executedFunction(...args) {
        const later = () => {
            clearTimeout(timeout);
            func(...args);
        };
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
    };
}

// Add event listeners with debounced search
const debouncedSearch = debounce(performSearch, 300);

searchButton.addEventListener('click', performSearch);
// searchInput.addEventListener('input', debouncedSearch);
searchInput.addEventListener('keypress', (e) => {
    if (e.key === 'Enter') performSearch();
});

// filters.forEach(filter => {
//     filter.addEventListener('change', performSearch);
// });

// priceInputs.forEach(input => {
//     input.addEventListener('input', debouncedSearch);
// });
