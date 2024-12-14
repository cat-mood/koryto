const cartCountElement = document.querySelector(".cart-count");
const userId = cartCountElement.getAttribute("data-user-id");

async function getCartCount(user_id) {
    return await fetch(`http://localhost:8080/api/cart/get-size?id=${user_id}`)
        .then(async response => {
            if (!response.ok) throw new Error("Ошибка загрузки корзины");
            const json = await response.json();
            console.log(json);
            return json;
        }).catch(error => {
            console.error(error);
            return 0;
        })
}

document.addEventListener("DOMContentLoaded", async function () {
    if (userId) {
        cartCountElement.textContent = await getCartCount(userId);
    }
});

async function updateAmount(userId, partId, amount) {
    await fetch(
        'http://localhost:8080/api/cart/update-part', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                'userId': userId,
                'partId': partId,
                'amount': amount
            })
        }
    ).then(response => {
        if (!response.ok) throw new Error('Ошибка инкриминирования товара');
    }).catch(error => {
        console.error(error);
    });

    location.reload();
}

document.querySelectorAll('.quantity-btn').forEach(button => {
    button.addEventListener('click', () => {
        const quantitySpan = button.parentElement.querySelector('span');
        const minusButton = button.parentElement.querySelector('.quantity-btn:first-child');
        let quantity = parseInt(quantitySpan.textContent);

        if (button.textContent === '+') {
            minusButton.disabled = false;
        } else if (quantity > 1) {
            if (quantity === 1) {
                minusButton.disabled = true;
            }
        }
    });
});

document.querySelectorAll('.quantity-controls').forEach(controls => {
    const quantity = parseInt(controls.querySelector('span').textContent);
    const minusButton = controls.querySelector('.quantity-btn:first-child');
    if (quantity === 1) {
        minusButton.disabled = true;
    }
});

async function deletePart(userId, partId) {
    await fetch(
        'http://localhost:8080/api/cart/delete-part', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                'userId': userId,
                'partId': partId
            })
        }
    ).then(response => {
        if (!response.ok) throw new Error('Ошибка удаления продукта');
    }).catch(error => {
        console.error(error);
    });

    location.reload();
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

async function createOrder(cost) {
    await fetch(
        "http://localhost:8080/api/orders/create-order", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                "cost": cost
            })
        }
    ).then(response => {
        if (!response.ok) throw new Error("Ошибка создания заказа");
        console.log(response);
    }).catch(error => console.error(error));
}
