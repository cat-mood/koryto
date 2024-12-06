let cartCount = 0;

document.querySelectorAll('.add-to-cart').forEach(button => {
    button.addEventListener('click', () => {
        cartCount++;
        document.querySelector('.cart-count').textContent = cartCount;

        const product = button.parentElement;
        const productName = product.querySelector('h3').textContent;
        const productPrice = product.querySelector('.product-price').textContent;

        alert(`Added ${productName} (${productPrice}) to cart!`);
    });
});

// Mock user data - in real application, this would come from backend
const userData = {
    name: "John Doe",
    cartItems: 0
};

// Update account button if user is logged in
if (userData.name) {
    document.querySelector('.account-btn').innerHTML = `
        <svg width="20" height="20" viewBox="0 0 24 24" fill="white">
            <path d="M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z"/>
        </svg>
        ${userData.name}
    `;
}
