document.addEventListener('DOMContentLoaded', function() {
    const btn = document.getElementById('favoriteBtn');
    if (!btn) return;

    const csrfToken = document.querySelector('meta[name="_csrf"]').content;
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

    btn.addEventListener('click', async () => {
        const houseId = btn.getAttribute('data-house-id');

        try {
            const response = await fetch(`/favorites/toggle/${houseId}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    [csrfHeader]: csrfToken
                }
            });

            if (!response.ok) throw new Error('通信エラー');

            const data = await response.json();

            btn.textContent = data.isFavorite ? '★ お気に入り済み' : '☆ お気に入り';
        } catch (err) {
            console.error(err);
            alert('お気に入り登録に失敗しました');
        }
    });
});