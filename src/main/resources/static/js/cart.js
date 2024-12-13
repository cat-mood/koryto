document.querySelectorAll('.quantity-btn').forEach(button => {
    button.addEventListener('click', () => {
        const quantitySpan = button.parentElement.querySelector('span');
        const minusButton = button.parentElement.querySelector('.quantity-btn:first-child');
        let quantity = parseInt(quantitySpan.textContent);

        if (button.textContent === '+') {
            quantity++;
            minusButton.disabled = false;
        } else if (quantity > 1) {
            quantity--;
            if (quantity === 1) {
                minusButton.disabled = true;
            }
        }

        quantitySpan.textContent = quantity;
        updateTotals();
    });
});

document.querySelectorAll('.quantity-controls').forEach(controls => {
    const quantity = parseInt(controls.querySelector('span').textContent);
    const minusButton = controls.querySelector('.quantity-btn:first-child');
    if (quantity === 1) {
        minusButton.disabled = true;
    }
});

document.querySelectorAll('.delete-btn').forEach(button => {
    button.addEventListener('click', () => {
        button.closest('.cart-item').remove();
        updateCartCount();
        updateTotals();

        if (document.querySelectorAll('.cart-item').length === 0) {
            showEmptyCart();
        }
    });
});

function updateCartCount() {
    const itemCount = document.querySelectorAll('.cart-item').length;
    document.querySelector('.cart-count').textContent = itemCount;
    document.querySelector('.cart-title span').textContent = `${itemCount} items`;
}

function updateTotals() {
    // In a real application, this would recalculate based on actual quantities and prices
    // This is just a mock implementation
    const subtotal = calculateSubtotal();
    const shipping = 5.99;
    const tax = subtotal * 0.08;
    const total = subtotal + shipping + tax;

    document.querySelector('.cart-summary').innerHTML = `
        <div class="summary-row">
            <span>Subtotal:</span>
            <strong>$${subtotal.toFixed(2)}</strong>
        </div>
        <div class="summary-row">
            <span>Shipping:</span>
            <strong>$${shipping.toFixed(2)}</strong>
        </div>
        <div class="summary-row">
            <span>Tax:</span>
            <strong>$${tax.toFixed(2)}</strong>
        </div>
        <div class="summary-row">
            <h3>Total:</h3>
            <h3>$${total.toFixed(2)}</h3>
        </div>
        <button class="checkout-btn">Proceed to Checkout</button>
    `;

    // After updating totals, check quantities and update minus buttons
    document.querySelectorAll('.quantity-controls').forEach(controls => {
        const quantity = parseInt(controls.querySelector('span').textContent);
        const minusButton = controls.querySelector('.quantity-btn:first-child');
        if (quantity === 1) {
            minusButton.disabled = true;
        } else {
            minusButton.disabled = false;
        }
    });
}

function calculateSubtotal() {
    let subtotal = 0;
    document.querySelectorAll('.cart-item').forEach(item => {
        const price = parseFloat(item.querySelector('.item-price').textContent.replace('$', ''));
        const quantity = parseInt(item.querySelector('.quantity-controls span').textContent);
        subtotal += price * quantity;
    });
    return subtotal;
}

function showEmptyCart() {
    document.querySelector('.cart-container').innerHTML = `
        <div class="empty-cart">
            <svg width="100" height="100" viewBox="0 0 24 24" fill="#1d3557">
                <path d="M19 6h-2c0-2.76-2.24-5-5-5S7 3.24 7 6H5c-1.1 0-2 .9-2 2v12c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V8c0-1.1-.9-2-2-2zm-7-3c1.66 0 3 1.34 3 3H9c0-1.66 1.34-3 3-3zm7 17H5V8h14v12zm-7-8c-1.66 0-3-1.34-3-3H7c0 2.76 2.24 5 5 5s5-2.24 5-5h-2c0 1.66-1.34 3-3 3z"/>
            </svg>
            <h2>Your cart is empty</h2>
            <p>Looks like you haven't added any items to your cart yet.</p>
            <a href="https://autopartspro.com" class="continue-shopping">Continue Shopping</a>
        </div>
    `;
}

document.querySelector('.checkout-btn').addEventListener('click', () => {
    alert('Proceeding to checkout...');
    // In a real application, this would redirect to the checkout page
});
