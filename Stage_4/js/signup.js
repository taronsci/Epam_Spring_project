document.getElementById("signupForm").addEventListener("submit", function(event) {
  event.preventDefault();

  const username = document.getElementById("username").value.trim();
  const email = document.getElementById("email").value.trim();
  const password = document.getElementById("password").value;
  const confirmPassword = document.getElementById("confirmPassword").value;

  if (!username || !email || !password || !confirmPassword) {
    alert("All fields are required!");
    return;
  }

  if (password !== confirmPassword) {
    alert("Passwords do not match!");
    return;
  }

  alert(`Signup attempt:\nUsername: ${username}\nEmail: ${email}`);
  // TODO: Replace with API call to backend Spring signup
});

function goToDashboard() {
  window.location.href = "index.html";
}
