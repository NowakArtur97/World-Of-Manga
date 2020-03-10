document.addEventListener('DOMContentLoaded', () => {
  const slider = document.querySelector('.manga_slider');
  let isDown = false;
  let startX;
  let scrollLeft;

  slider.addEventListener('mousedown', e => {
    isDown = true;
    slider.classList.add('manga_slider--active');
    startX = e.pageX - slider.offsetLeft;
    scrollLeft = slider.scrollLeft;
  });

  slider.addEventListener('mouseup', () => {
    isDown = false;
    slider.classList.remove('manga_slider--active');
  });

  slider.addEventListener('mouseleave', () => {
    isDown = false;
    slider.classList.remove('manga_slider--active');
  });

  slider.addEventListener('mousemove', e => {
    if (isDown) {
      e.preventDefault();

      const x = e.pageX - slider.offsetLeft;
      const walk = (x - startX) * 3;

      slider.scrollLeft = scrollLeft - walk;
    }
  });
});
