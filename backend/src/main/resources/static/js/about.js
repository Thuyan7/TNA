const carouselMember = document.querySelector(".member-carousel");
const firstCard = carouselMember.querySelector(".card");
const firstCardWidth = firstCard.offsetWidth;
const carouselMemberChildrens = [...carouselMember.children];

let timeoutId;

let cardPerView = Math.round(carouselMember.offsetWidth / firstCardWidth);

carouselMemberChildrens.slice(-cardPerView).reverse().forEach(card => {
    carouselMember.insertAdjacentHTML("afterbegin", card.outerHTML);
});

carouselMemberChildrens.slice(0, cardPerView).forEach(card => {
    carouselMember.insertAdjacentHTML("beforeend", card.outerHTML);
});

carouselMember.classList.add("no-transition");
carouselMember.scrollLeft = carouselMember.offsetWidth;
carouselMember.classList.remove("no-transition");

const infiniteScroll = () => {
    if (carouselMember.scrollLeft === 0) {
        carouselMember.classList.add("no-transition");
        carouselMember.scrollLeft = carouselMember.scrollWidth - (2 * carouselMember.offsetWidth);
        carouselMember.classList.remove("no-transition");
    } else if (Math.ceil(carouselMember.scrollLeft) >= carouselMember.scrollWidth - carouselMember.offsetWidth) {
        carouselMember.classList.add("no-transition");
        carouselMember.scrollLeft = carouselMember.offsetWidth;
        carouselMember.classList.remove("no-transition");
    }
    autoPlay();
}
const autoPlay = () => {
    clearTimeout(timeoutId);
    timeoutId = setTimeout(() => carouselMember.scrollLeft += firstCardWidth, 5000);
}

autoPlay();
carouselMember.addEventListener("scroll", infiniteScroll);