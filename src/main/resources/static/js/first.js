// STEP 1: AUTO-REDIRECT if cookies exist (add this at the very top)
document.addEventListener("DOMContentLoaded", function () {
  const cookies = document.cookie.split("; ").reduce((acc, current) => {
    const [key, value] = current.split("=");
    acc[key] = value;
    return acc;
  }, {});

  // If both cookies are present → redirect to home.html
  if (cookies.userEmail && cookies.userToken) {
    window.location.href = "html/home.html";
    return;
  }

  // STEP 2: NAVBAR TOGGLE LOGIC (your existing code below)
  const hamburger = document.getElementById("hamburger");
  const navMenu = document.querySelector("nav");

  hamburger.addEventListener("click", function () {
    if (window.innerWidth <= 768) {
      if (navMenu.classList.contains("active")) {
        navMenu.classList.remove("active");
        navMenu.classList.add("closing");

        navMenu.addEventListener(
          "animationend",
          function () {
            navMenu.classList.remove("closing");
            navMenu.style.display = "none";
            navMenu.style.transform = "translateX(100%) scale(0.5) rotate(15deg)";
            navMenu.style.opacity = "0";
          },
          { once: true }
        );
      } else {
        navMenu.style.display = "block";
        navMenu.classList.add("active");
      }
    }
  });
});
