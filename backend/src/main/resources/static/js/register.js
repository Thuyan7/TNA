<script>
    document.querySelector('.form').addEventListener('submit', function(event) {
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;

    if (password !== confirmPassword) {
    alert("Mật khẩu và nhập lại mật khẩu không khớp!");
    event.preventDefault();  // Prevent form submission
}
});
</script>
