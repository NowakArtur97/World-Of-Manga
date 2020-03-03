document.addEventListener('DOMContentLoaded', () => {
  const hiddenContent = document.querySelectorAll('.form__label--show');

  hiddenContent.forEach(content => content.addEventListener('click', hideShow));

  function hideShow() {
    const group = this.parentNode.querySelector('.form__group--flex');
    console.log(group.style.display);

    if (group.style.display === 'none' || group.style.display === '') {
      group.style.display = 'flex';
      group.style.flexDirection = 'row';
      group.style.flexWrap = 'wrap';
      group.style.justifyContent = 'center';
    } else {
      group.style.display = 'none';
    }
  }
});
