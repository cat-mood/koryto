async function updateAmount(partId, amount) {
    await fetch(
        'http://localhost:8080/api/cart/update-part', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
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

async function deletePart(partId) {
    await fetch(
        'http://localhost:8080/api/cart/delete-part', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
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

function proceedToCheckout(button) {
    const cost = parseFloat(button.dataset.total);

    // Преобразуем HTML-escaped JSON обратно в нормальный вид
    const rawItems = button.dataset.items;
    const jsonString = rawItems.replace(/&quot;/g, '"');
    const items = JSON.parse(jsonString);

    console.log("Total cost:", cost);
    console.log("Items:", items);

    // Далее вызываешь свою логику
    createOrder(cost, items);
}

async function createOrder(cost, parts) {
    console.log(parts);
    await fetch(
        'http://localhost:8080/api/orders/create-order', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                "cost": cost
            })
        }
    ).then(async response => {
        if (!response.ok) throw new Error("Ошибка создания заказа");
        const orderId = await response.json();
        console.log(`orderId: ${orderId}`);
        await createOrderBody(orderId, parts);
        await clearCart();
    }).catch(error => console.error(error));

    location.reload();
}

async function clearCart() {
    await fetch(
        'http://localhost:8080/api/cart/clear-cart', {
            method: 'POST'
        }
    ).then(response => {
        if (!response.ok) throw new Error("Ошибка очистки корзины");
    }).catch(error => console.error(error));
}

async function createOrderBody(orderId, parts) {
    const orderBody = [];
    // console.log(parts);
    Object.entries(parts).forEach(([key, part]) => {
        console.log(key, part);
        orderBody.push({
            orderId: orderId,
            partId: part.partId,
            amount: part.amount
        });
    });

    await fetch(
        'http://localhost:8080/api/orders/create-order-body', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(orderBody)
        }
    ).then(response => {
        if (!response.ok) return new Error('Ошибка создания тела заказа');
    }).catch(error => console.error(error));
}
