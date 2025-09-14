document.getElementById("loginForm").addEventListener("submit", function(event) {
  event.preventDefault();

  const username = document.getElementById("username").value;
  const password = document.getElementById("password").value;

  if (username && password) {
    alert(`Login attempt with:\nUsername: ${username}\nPassword: ${password}`);
    // TODO: Replace with API call to backend Spring login

    localStorage.setItem("isLoggedIn", "true");
    localStorage.setItem("username", username);
    
    window.location.href = "index.html";
  } else {
    alert("Please fill in all fields!");
  }
});

function goToDashboard() {
  window.location.href = "index.html";
}


