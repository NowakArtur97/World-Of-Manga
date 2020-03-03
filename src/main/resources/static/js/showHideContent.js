document.addEventListener('DOMContentLoaded', () => {
  const hiddenContent = document.querySelectorAll('.form__group--show');

  hiddenContent.forEach(content => content.addEventListener('click', hideShow));

  function hideShow() {
    const group = this.querySelector('.form__group--flex');
    console.log(group.style.display);

    if (group.style.display === 'none' || group.style.display === '') {
      group.style.display = 'flex';
      group.style.flexDirection = 'column';
    } else {
      group.style.display = 'none';
    }
  }
});
