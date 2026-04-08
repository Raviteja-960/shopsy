// main.js — ShopEasy Spring Boot client helpers

// Auto-hide flash alerts after 4s
document.addEventListener('DOMContentLoaded', () => {
  const alerts = document.querySelectorAll('.form-alert.show, .admin-alert.show');
  alerts.forEach(el => {
    setTimeout(() => {
      el.style.transition = 'opacity 0.5s';
      el.style.opacity = '0';
      setTimeout(() => el.remove(), 500);
    }, 4000);
  });

  // Auto-hide toast
  const toast = document.getElementById('toast');
  if (toast && toast.classList.contains('show')) {
    setTimeout(() => toast.classList.remove('show'), 2500);
  }
});
