function goToDashboard() {
    window.location.href = "index.html";
}

function goToProfile() {
  window.location.href = "user-profile.html";
}

// TODO: Add JS for form submission, edit/delete buttons, and accept/decline requests

document.getElementById("logoutBtn").addEventListener("click", function() {
  // Clear login state
  localStorage.removeItem("isLoggedIn");
  localStorage.removeItem("userEmail");

  // Redirect to dashboard
  window.location.href = "index.html";
});
