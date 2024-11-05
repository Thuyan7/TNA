document.querySelector('.select').addEventListener('click', function () {
    var menu = document.querySelector('.menu-item');
    var items = menu.querySelectorAll('.item');
    if (menu.classList.contains('show')) {
        items.forEach(function (item, index) {
            setTimeout(function () {
                item.style.opacity = '0';
                item.style.transform = 'translateX(-120%)';
            }, index * 300);
        });
        setTimeout(function () {
            menu.classList.remove('show');
        }, items.length * 300);
    } else {
        items.forEach(function (item) {
            item.style.opacity = '0';
            item.style.transform = 'translateX(-100%)';
        });
        menu.classList.add('show');
        setTimeout(function () {
            items.forEach(function (item, index) {
                setTimeout(function () {
                    item.style.opacity = '1';
                    item.style.transform = 'translateX(0)';
                }, index * 300);
            });
        }, 10);
    }
});

var btnBack = document.querySelector('.btn-back');
if (btnBack) {
    btnBack.addEventListener('click', function () {
        history.replaceState(null, null, ' ');
        window.scrollTo(0, 0);
    });
}

document.addEventListener("DOMContentLoaded", function () {
    if (window.location.hash) {
        history.replaceState(null, null, ' ');
        window.scrollTo(0, 0);
    }
});
