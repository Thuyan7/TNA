document.addEventListener('DOMContentLoaded', function() {
    const closeButton = document.querySelector('.btn-close');
    const cancelButton = document.querySelector('.btn-secondary');

    // Close modal
    closeButton.addEventListener('click', function() {
        closeModal();
    });

    cancelButton.addEventListener('click', function() {
        closeModal();
    });

    function closeModal() {
        document.querySelector('.modal').style.display = 'none'; // Hoặc xóa modal khỏi DOM
    }

    // Mã thêm cho xử lý upload ảnh có thể được thêm vào đây
});
