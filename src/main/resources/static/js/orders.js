document.querySelectorAll('.order-card').forEach(card => {
    card.addEventListener('click', () => {
        // const items = JSON.parse(card.dataset);
        const modal = document.getElementById('orderModal');
        const modalTitle = modal.querySelector('.modal-title');
        const orderItems = modal.querySelector('.order-items');
        const orderId = card.dataset.orderid;
        console.log(orderId);

        modalTitle.textContent = 'Order #' + orderId;
        orderItems.innerHTML = fullOrders.map(item => {
            if (item.orderId != orderId) {
                return "";
            } else {
                return `
                    <div class="order-item">
                        <div class="item-image">
                            <svg width="40" height="40" viewBox="0 0 24 24" fill="#1d3557">
                                <path d="M20 3H4c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zm0 16H4V5h16v14z"/>
                                <path d="M12 6c-2.76 0-5 2.24-5 5s2.24 5 5 5 5-2.24 5-5-2.24-5-5-5zm0 8c-1.65 0-3-1.35-3-3s1.35-3 3-3 3 1.35 3 3-1.35 3-3 3z"/>
                            </svg>
                        </div>
                        <div class="item-details">
                            <h4>${item.partName}</h4>
                            <p>Amount: ${item.amount}</p>
                        </div>
                        <div class="item-price">$${item.price}</div>
                    </div>
                `;
            }
        }).join('');

        modal.style.display = 'block';
    });
});

document.querySelector('.close-modal').addEventListener('click', () => {
    document.getElementById('orderModal').style.display = 'none';
});

window.addEventListener('click', (event) => {
    const modal = document.getElementById('orderModal');
    if (event.target === modal) {
        modal.style.display = 'none';
    }
});

function showEmptyOrders() {
    document.querySelector('.orders-container').innerHTML = `
        <div class="empty-orders">
            <svg width="100" height="100" viewBox="0 0 24 24" fill="#1d3557">
                <path d="M19 3h-4.18C14.4 1.84 13.3 1 12 1c-1.3 0-2.4.84-2.82 2H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zm-7 0c.55 0 1 .45 1 1s-.45 1-1 1-1-.45-1-1 .45-1 1-1zm2 14H7v-2h7v2zm3-4H7v-2h10v2zm0-4H7V7h10v2z"/>
            </svg>
            <h2>No Orders Yet</h2>
            <p>You haven't placed any orders yet.</p>
            <a href="/" class="shop-now-btn">Start Shopping</a>
        </div>
    `;
}
