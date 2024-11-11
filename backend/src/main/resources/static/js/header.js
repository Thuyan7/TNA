//Scroll menu
const header = document.querySelector(".header");
const headerOriginalTop = header.getBoundingClientRect().top;

window.addEventListener("scroll", function () {
    if (window.pageYOffset === 0) {
        header.classList.remove("scrolled");
        header.style.transform = "translateY(-100%)";

        setTimeout(() => {
            header.style.transition = "transform .7s ease-in-out";
            header.style.transform = "translateY(0)";
        }, 10);
    } else if (window.pageYOffset > headerOriginalTop + 30) {
        header.classList.add("scrolled");
        header.style.transition = "none";
        header.style.transform = "translateY(0)";
    }
});
//Mobile menu
var menuMbIcon = document.querySelector('.menu-mb-icon');
var menuMb = document.querySelector('.menu-mb');

menuMbIcon.onclick = function () {
    if (menuMb.style.display === 'block') {
        menuMb.style.display = 'none';
    } else {
        menuMb.style.display = 'block';
    }
};

