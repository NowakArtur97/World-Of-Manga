document.addEventListener('DOMContentLoaded', () => {
  const mangaCards = document.querySelectorAll('.manga_card__face');
  const mangaCardsLikes = document.querySelectorAll('.manga_card__likes');
  const heartSrc = '/images/icons/heart.png';
  const whiteHeartSrc = '/images/icons/white-heart.png';
  let heart;
  mangaCards.forEach(mangaCard =>
    mangaCard.addEventListener('mouseover', addToFavourites)
  );
  mangaCardsLikes.forEach(mangaCardLike =>
    mangaCardLike.addEventListener('mouseleave', candelAdding)
  );

  function addToFavourites() {
    const mangaCard = this;
    heart = mangaCard.querySelector('.manga_card__icon--heart');

    heart?.addEventListener('mouseover', makeRed);
  }

  function makeRed() {
    heart.src = heartSrc;
  }

  function candelAdding() {
    const mangaCard = this;
    heart = mangaCard.querySelector('.manga_card__icon--heart');

    heart.src = whiteHeartSrc;
  }
});
