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

// document.addEventListener("DOMContentLoaded", async function () {
//     if (userId) {
//         cartCountElement.textContent = await getCartCount(userId);
//     }
// });

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
