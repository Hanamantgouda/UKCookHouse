// Function to get a cookie value by name
function getCookie(name) {
  const value = `; ${document.cookie}`;
  const parts = value.split(`; ${name}=`);
  if (parts.length === 2) return parts.pop().split(';').shift();
  return "";
}

// Get username and email from cookies
const username = getCookie("username");
const email = getCookie("userEmail");

// Update the DOM
document.getElementById("username").textContent = username || "Guest";
document.getElementById("userEmail").textContent = email || "Not available";
