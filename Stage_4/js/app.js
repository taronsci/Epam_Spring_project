// Global variable
let isLoggedIn = false;

function updateHeader() {
  const headerActions = document.querySelector(".header-actions");
  const profileCircle = document.querySelector(".profile-circle");

  // Read login state from localStorage
  isLoggedIn = localStorage.getItem("isLoggedIn") === "true";

  if (isLoggedIn) {
    headerActions.style.display = "none";
    profileCircle.style.display = "flex";
  } else {
    headerActions.style.display = "flex";
    profileCircle.style.display = "none";
  }
}

// Run after DOM loads
document.addEventListener("DOMContentLoaded", () => {
  updateHeader();
});


function goToLogin(){
  window.location.href = "login.html";
}
function goToSignup(){
  window.location.href = "signup.html";
}

function performSearch() {
  const query = document.getElementById("searchBar").value;
  const filter = document.getElementById("filterType").value;
  alert(`Searching for "${query}" with filter: ${filter}`);
  // TODO: Connect to backend API for real search
}

function registerBook() {
  if(!isLoggedIn){
    alert("Please log in to register a book.");
    return;
  }
  // Show register modal if logged in
  document.getElementById("registerModal").style.display = "block";
}

function requestBook(bookId) {
  if(!isLoggedIn){
    alert("Please log in to request a book.");
    return;
  }
  alert(`Request sent for book ID: ${bookId}`);
}


function goToProfile() {
  window.location.href = "user-profile.html";
}

const modal = document.getElementById("registerModal");
const registerBtn = document.querySelector(".register-btn");
const closeBtn = document.querySelector(".close-btn");

registerBtn.onclick = () => modal.style.display = "block";
closeBtn.onclick = () => modal.style.display = "none";
window.onclick = (event) => { if(event.target === modal) modal.style.display = "none"; }

const form = document.getElementById("registerBookForm");
const transactionSelect = document.getElementById("transaction");
const priceInput = document.getElementById("price");
const rentalInput = document.getElementById("rentalDuration");

form.addEventListener("submit", function(e) {
  e.preventDefault();

  const transaction = transactionSelect.value;

  // Conditional validation
  if(transaction === "SELL" && !priceInput.value){
    alert("Price is required for Sell transactions!");
    priceInput.focus();
    return;
  }
  if(transaction === "RENT"){
    if(!priceInput.value){
      alert("Price is required for Rent transactions!");
      priceInput.focus();
      return;
    }
    if(!rentalInput.value){
      alert("Rental duration is required for Rent transactions!");
      rentalInput.focus();
      return;
    }
  }

  // Grab data
  const book = {
    title: document.getElementById("title").value,
    author: document.getElementById("author").value,
    year: document.getElementById("year").value,
    genre: document.getElementById("genre").value,
    condition: document.getElementById("condition").value,
    transaction: transaction,
    price: priceInput.value,
    rentalDuration: rentalInput.value
  };

  console.log("Book registered:", book);
  alert(`Book "${book.title}" registered successfully!`);

  modal.style.display = "none";
  form.reset();
});


