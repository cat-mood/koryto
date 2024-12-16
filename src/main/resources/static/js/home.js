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

// Search and Filter Functionality
const searchInput = document.querySelector('.search-input');
const searchButton = document.querySelector('.search-button');
const filters = document.querySelectorAll('.filter-select');
const priceInputs = document.querySelectorAll('.price-input');

function performSearch() {
    const searchTerm = searchInput.value.toLowerCase();
    const category = document.getElementById('category').value.toLowerCase();
    const carModel = document.getElementById('carModel').value.toLowerCase();
    const manufacturer = document.getElementById('manufacturer').value.toLowerCase();
    const minPrice = parseFloat(document.getElementById('minPrice').value) || 0;
    const maxPrice = parseFloat(document.getElementById('maxPrice').value) || Infinity;

    const products = document.querySelectorAll('.product-card');

    products.forEach(product => {
        const productName = product.querySelector('h3').textContent.toLowerCase();
        const productPrice = parseFloat(product.querySelector('.product-price').textContent.replace('$', ''));

        // Check if product matches all selected filters
        let matchesSearch = !searchTerm || productName.includes(searchTerm);
        let matchesCategory = !category || productName.includes(category);
        let matchesCarModel = !carModel || productName.includes(carModel);
        let matchesManufacturer = !manufacturer || productName.includes(manufacturer);
        let matchesPrice = productPrice >= minPrice && productPrice <= maxPrice;

        // Show product only if it matches all active filters
        const shouldShow = matchesSearch && matchesCategory &&
            matchesCarModel && matchesManufacturer &&
            matchesPrice;

        // Add animation for smooth transition
        if (shouldShow) {
            product.style.display = 'block';
            product.style.animation = 'slideIn 0.3s ease-out';
        } else {
            product.style.display = 'none';
        }
    });

    // Show message if no products found
    const visibleProducts = document.querySelectorAll('.product-card[style="display: block"]');
    const noResultsMsg = document.getElementById('no-results-message') || document.createElement('div');
    noResultsMsg.id = 'no-results-message';

    if (visibleProducts.length === 0) {
        noResultsMsg.textContent = 'No products found matching your criteria';
        noResultsMsg.style.textAlign = 'center';
        noResultsMsg.style.padding = '2rem';
        noResultsMsg.style.color = 'var(--dark)';
        document.querySelector('.products-grid').appendChild(noResultsMsg);
    } else if (noResultsMsg.parentElement) {
        noResultsMsg.remove();
    }
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
searchInput.addEventListener('input', debouncedSearch);
searchInput.addEventListener('keypress', (e) => {
    if (e.key === 'Enter') performSearch();
});

filters.forEach(filter => {
    filter.addEventListener('change', performSearch);
});

priceInputs.forEach(input => {
    input.addEventListener('input', debouncedSearch);
});
