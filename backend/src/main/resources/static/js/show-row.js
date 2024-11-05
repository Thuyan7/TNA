var slideImages = document.querySelectorAll('.slide-img');
var mainImage = document.getElementById('main-image');
var slideContainer = document.querySelector('.slide');
slideImages.forEach(img => {
    img.addEventListener('click', function () {
        mainImage.th:src = this.th:src;
    });
});

function updateIcons() {
    const btnLeft = document.querySelector('.btn-left i');
    const btnRight = document.querySelector('.btn-right i');

    if (window.innerWidth <= 567) {
        btnLeft.classList.remove('fa-arrow-up');
        btnLeft.classList.add('fa-arrow-left');
        btnRight.classList.remove('fa-arrow-down');
        btnRight.classList.add('fa-arrow-right');
    } else {
        btnLeft.classList.remove('fa-arrow-left');
        btnLeft.classList.add('fa-arrow-up');
        btnRight.classList.remove('fa-arrow-right');
        btnRight.classList.add('fa-arrow-down');
    }
}
window.addEventListener('load', updateIcons);
window.addEventListener('resize', updateIcons);

function scrollSlide(direction) {
    const slide = document.querySelector('.slide');
    const scrollAmount = 135;

    if (direction === 'left') {
        slide.scrollBy({ left: -scrollAmount, behavior: 'smooth' });
    } else if (direction === 'right') {
        slide.scrollBy({ left: scrollAmount, behavior: 'smooth' });
    }
}

document.querySelector('.btn-left').addEventListener('click', () => scrollSlide('left'));
document.querySelector('.btn-right').addEventListener('click', () => scrollSlide('right'));