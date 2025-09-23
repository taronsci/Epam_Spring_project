document.addEventListener("DOMContentLoaded", () => {
    const username = localStorage.getItem("username") || "";
    const email = localStorage.getItem("email") || "";

    document.getElementById("username").value = username;
    document.getElementById("email").value = email;
});


function goToProfile() {
  window.location.href = "user-profile.html";
}

function logout() {
    localStorage.removeItem("isLoggedIn");
    localStorage.removeItem("username");
    localStorage.removeItem("email");

    alert("You have been logged out.");
}

// TODO: Add JS for form submission, edit/delete buttons, and accept/decline requests

document.getElementById("logoutBtn").addEventListener("click", function() {
  
  // Clear login state
  logout();
  // Redirect to dashboard
  goToDashboard();
});

function goToDashboard() {
  window.location.href = "index.html";
}
