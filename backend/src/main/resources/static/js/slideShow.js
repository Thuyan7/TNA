//Slide Banner
let section1Dom = document.querySelector('.banner');
let SliderDom = section1Dom.querySelector('.list');

let runNextAuto = setInterval(showSlider, 5000);

function showSlider() {
    SliderDom.appendChild(SliderDom.querySelector('.item'));
}
//Room slide
const carouselRoom1 = document.querySelector(".carousel-room-1");
const carouselRoom2 = document.querySelector(".carousel-room-2");
const firstCardWidthRoom = carouselRoom1.querySelector(".card").offsetWidth;
const arrowBtnsRoom = document.querySelectorAll(".btn-arrow");
const carouselRoom1Childrens = [...carouselRoom1.children];
const carouselRoom2Childrens = [...carouselRoom2.children];

let timeoutIdRoom, timeoutIdRoom2;

let cardPerViewRoom = Math.round(carouselRoom1.offsetWidth / firstCardWidthRoom);

carouselRoom1Childrens.slice(-cardPerViewRoom).reverse().forEach(card => {
    carouselRoom1.insertAdjacentHTML("afterbegin", card.outerHTML);
});

carouselRoom1Childrens.slice(0, cardPerViewRoom).forEach(card => {
    carouselRoom1.insertAdjacentHTML("beforeend", card.outerHTML);
});
carouselRoom2Childrens.slice(-cardPerViewRoom).reverse().forEach(card => {
    carouselRoom2.insertAdjacentHTML("afterbegin", card.outerHTML);
});

carouselRoom2Childrens.slice(0, cardPerViewRoom).forEach(card => {
    carouselRoom2.insertAdjacentHTML("beforeend", card.outerHTML);
});

carouselRoom1.classList.add("no-transition");
carouselRoom1.scrollLeft = carouselRoom1.offsetWidth;
carouselRoom1.classList.remove("no-transition");
carouselRoom2.classList.add("no-transition");
carouselRoom2.scrollLeft = carouselRoom2.offsetWidth;
carouselRoom2.classList.remove("no-transition");

arrowBtnsRoom.forEach(btn => {
    btn.addEventListener("click", () => {
        if (btn.classList.contains("btn-l-room-1")) {
            carouselRoom1.scrollLeft -= firstCardWidthRoom;
        } else if (btn.classList.contains("btn-r-room-1")) {
            carouselRoom1.scrollLeft += firstCardWidthRoom;
        } else if (btn.classList.contains("btn-l-room-2")) {
            carouselRoom2.scrollLeft -= firstCardWidthRoom;
        } else if (btn.classList.contains("btn-r-room-2")) {
            carouselRoom2.scrollLeft += firstCardWidthRoom;
        }
    });
});

const infiniteScrollRoom = () => {
    if (carouselRoom1.scrollLeft === 0) {
        carouselRoom1.classList.add("no-transition");
        carouselRoom1.scrollLeft = carouselRoom1.scrollWidth - (2 * carouselRoom1.offsetWidth);
        carouselRoom1.classList.remove("no-transition");
    } else if (Math.ceil(carouselRoom1.scrollLeft) >= carouselRoom1.scrollWidth - carouselRoom1.offsetWidth) {
        carouselRoom1.classList.add("no-transition");
        carouselRoom1.scrollLeft = carouselRoom1.offsetWidth;
        carouselRoom1.classList.remove("no-transition");
    }
    autoPlayRoom();
}
const infiniteScrollRoom2 = () => {
    if (carouselRoom2.scrollLeft === 0) {
        carouselRoom2.classList.add("no-transition");
        carouselRoom2.scrollLeft = carouselRoom2.scrollWidth - (2 * carouselRoom2.offsetWidth);
        carouselRoom2.classList.remove("no-transition");
    } else if (Math.ceil(carouselRoom2.scrollLeft) >= carouselRoom2.scrollWidth - carouselRoom2.offsetWidth) {
        carouselRoom2.classList.add("no-transition");
        carouselRoom2.scrollLeft = carouselRoom2.offsetWidth;
        carouselRoom2.classList.remove("no-transition");
    }
    autoPlayRoom2();
}
const autoPlayRoom2 = () => {
    clearTimeout(timeoutIdRoom2);
    timeoutIdRoom2 = setTimeout(() => carouselRoom2.scrollLeft += firstCardWidthRoom, 6000);
}
autoPlayRoom2();
const autoPlayRoom = () => {
    clearTimeout(timeoutIdRoom);
    timeoutIdRoom = setTimeout(() => carouselRoom1.scrollLeft += firstCardWidthRoom, 6000);
}
autoPlayRoom();
carouselRoom1.addEventListener("scroll", infiniteScrollRoom);
carouselRoom2.addEventListener("scroll", infiniteScrollRoom2);
//Comment slide
const carouselComments = document.querySelector(".carousel-comments");
const firstCardWidth = carouselComments.querySelector(".card").offsetWidth;
const arrowBtns = document.querySelectorAll(".comments .btn-arrow");
const carouselCommentsChildrens = [...carouselComments.children];

let timeoutId;

let cardPerView = Math.round(carouselComments.offsetWidth / firstCardWidth);

carouselCommentsChildrens.slice(-cardPerView).reverse().forEach(card => {
    carouselComments.insertAdjacentHTML("afterbegin", card.outerHTML);
});

carouselCommentsChildrens.slice(0, cardPerView).forEach(card => {
    carouselComments.insertAdjacentHTML("beforeend", card.outerHTML);
});

carouselComments.classList.add("no-transition");
carouselComments.scrollLeft = carouselComments.offsetWidth;
carouselComments.classList.remove("no-transition");

arrowBtns.forEach(btn => {
    btn.addEventListener("click", () => {
        if (btn.classList.contains("btn-l")) {
            carouselComments.scrollLeft -= firstCardWidth;
        } else {
            carouselComments.scrollLeft += firstCardWidth;
        }
    });
});

const infiniteScroll = () => {
    if (carouselComments.scrollLeft === 0) {
        carouselComments.classList.add("no-transition");
        carouselComments.scrollLeft = carouselComments.scrollWidth - (2 * carouselComments.offsetWidth);
        carouselComments.classList.remove("no-transition");
    } else if (Math.ceil(carouselComments.scrollLeft) >= carouselComments.scrollWidth - carouselComments.offsetWidth) {
        carouselComments.classList.add("no-transition");
        carouselComments.scrollLeft = carouselComments.offsetWidth;
        carouselComments.classList.remove("no-transition");
    }
    autoPlay();
}
const autoPlay = () => {
    clearTimeout(timeoutId);
    timeoutId = setTimeout(() => carouselComments.scrollLeft += firstCardWidth, 6000);
}

autoPlay();
carouselComments.addEventListener("scroll", infiniteScroll);

//Reload web
