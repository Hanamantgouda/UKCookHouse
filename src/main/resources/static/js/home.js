const profileBtn = document.getElementById("profileBtn");
const sidebar = document.getElementById("sidebar");
const overlay = document.getElementById("overlay");
const logoutBtn = document.getElementById("logoutBtn");

function toggleSidebar() {
  const isActive = sidebar.classList.toggle("active");
  overlay.classList.toggle("active");
  profileBtn.classList.toggle("active", isActive);
}

profileBtn.addEventListener("click", toggleSidebar);

overlay.addEventListener("click", () => {
  sidebar.classList.remove("active");
  overlay.classList.remove("active");
  profileBtn.classList.remove("active");
});

logoutBtn.addEventListener("click", () => {
  document.cookie = "userEmail=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
  document.cookie = "userToken=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
  document.cookie = "username=; expires=Thu, 01 Jan  1970 00:00:00 UTC; path=/;";
  document.cookie = "userID=; expires=Thu, 01 Jan  1970 00:00:00 UTC; path=/;";

  localStorage.removeItem("selectedIngredients");
  localStorage.removeItem("recipeState");
  localStorage.removeItem("difficultyRecipeState");
  
  window.location.href = "../index.html";
});


function showSortOptions() {
    document.getElementById("sortOptions").style.display = "flex";
  }

  function closePopup() {
    document.getElementById("sortOptions").style.display = "none";
  }

  function redirectTo(type) {
    if (type === "time") {
      window.location.href = "SortByTime.html";
    } else if (type === "difficulty") {
      window.location.href = "SortByDifficulty.html";
    }
  }
