document.addEventListener('DOMContentLoaded', () => {
  const languageOptions = document.querySelector('.header__language_select');

  languageOptions.addEventListener('change', changeWebsiteLanguage);

  function changeWebsiteLanguage() {
    const languageSelected = languageOptions.value;
    if (languageSelected.length > 1) {
      window.location.replace('?lang=' + languageSelected);
    }
  }
});
