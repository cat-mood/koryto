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
            console.log(error);
            return 0;
        })
}

document.addEventListener("DOMContentLoaded", async function () {
    if (userId) {
        cartCountElement.textContent = await getCartCount(userId);
    }
});

async function addToCart(userId, partId) {
    await fetch(
        'http://localhost:8080/api/cart/add-part-to-cart', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                'userId': userId,
                'partId': partId,
                'amount': 1
            })
        })
        .then(response => {
            if (!response.ok) throw new Error("Ошибка добавления продукта в корзину");
        })
        .catch(error => console.log(error));

    document.querySelector('.cart-count').textContent = await getCartCount(userId);
}

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
