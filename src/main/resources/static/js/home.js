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
