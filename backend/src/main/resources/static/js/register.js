document.querySelector('.form').addEventListener('submit', function(event) {
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;

    if (password !== confirmPassword) {
        alert("Mật khẩu không khớp.Vui lòng nhậpp lại!");
        event.preventDefault();
    }
});
