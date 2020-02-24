document.addEventListener('DOMContentLoaded', () => {
  const mangaCards = document.querySelectorAll('.manga_card__face');
  const mangaCardsRating = document.querySelectorAll('.manga_card_rating');
  const starSrc = '/images/icons/star.png';
  const whiteStarSrc = '/images/icons/white-star.png';
  let stars = [];
  mangaCards.forEach(mangaCard =>
    mangaCard.addEventListener('mouseover', getStars)
  );
  mangaCardsRating.forEach(mangaCardRating =>
    mangaCardRating.addEventListener('mouseleave', cancelRating)
  );

  function getStars() {
    const mangaCard = this;
    stars = mangaCard.querySelectorAll('.manga_card__icon--star');
    stars.forEach(star => star.addEventListener('mouseover', rateManga));
  }

  function rateManga() {
    const star = this;
    const starId = star.dataset.star;
    stars.forEach(star => {
      if (star.dataset.star <= starId) {
        star.src = starSrc;
      } else {
        star.src = whiteStarSrc;
      }
    });
  }

  function cancelRating() {
    for (star of stars) {
      if ((star.src = starSrc)) {
        star.src = whiteStarSrc;
      } else {
        return;
      }
    }
  }
});
