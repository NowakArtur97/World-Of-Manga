document.addEventListener('DOMContentLoaded', () => {
  const stars = document.querySelectorAll('.manga_card__icon--star');
  stars.forEach(star => star.addEventListener('mouseover', rateManga));
  stars.forEach(star => star.addEventListener('mouseleave', cancelRating));

  function rateManga() {
    const star = this;
    const starId = star.dataset.star;
    stars.forEach(star => {
      if (star.dataset.star <= starId) {
        star.src = '/images/icons/star.png';
      } else {
        star.src = '/images/icons/white-star.png';
      }
    });
  }

  function cancelRating() {
    stars.forEach(star => (star.src = '/images/icons/white-star.png'));
  }
});
