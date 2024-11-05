document.getElementById('images').addEventListener('change', function(event) {
    const imgWrap = document.getElementById('imgWrap');
    imgWrap.innerHTML = ""; // Xóa ảnh cũ trước khi thêm ảnh mới
    const files = event.target.files; // Lấy danh sách file đã chọn

    for (let i = 0; i < files.length; i++) {
        const file = files[i];

        // Kiểm tra nếu file là hình ảnh
        if (file.type.startsWith('image/')) {
            const reader = new FileReader();

            reader.onload = function(e) {
                // Tạo thẻ img và thêm vào imgWrap
                const img = document.createElement('img');
                img.src = e.target.result;
                img.classList.add('post-img'); // Thêm class 'img'
                imgWrap.appendChild(img);
            }

            reader.readAsDataURL(file); // Đọc file dưới dạng URL
        } else {
            const errorMsg = document.createElement('p');
            errorMsg.innerText = 'Vui lòng chọn tệp hình ảnh hợp lệ.';
            imgWrap.appendChild(errorMsg);
        }
    }

    document.getElementById("price").addEventListener("input", function(event) {
        const cleanedValue = event.target.value.replace(/[.,]/g, "");
        event.target.value = cleanedValue;
    });
});
