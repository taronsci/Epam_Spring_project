document.getElementById("search").addEventListener("click", ()=> {
  fetch('http://localhost:8080/hello')
  .then(response => response.text())
  .then(data => {
    alert("response from backend: "+data);
  })
  .catch(error =>{
    alert("error: "+ error);
    console.error('Error calling backend:', error);
  });
})


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
  // DEV ONLY: clear login state on fresh start
  // if (location.hostname === "127.0.0.1" || location.hostname === "localhost") {
  //   localStorage.removeItem("isLoggedIn");
  // }
  loadBooks();
  updateHeader();
});

let currentPage = 0;
const pageSize = 9; 

//book-cards on front page
async function loadBooks(page = 0) {
    try {
        const response = await fetch(`http://localhost:8080/api/listing?page=${page}&size=${pageSize}`);
        const data = await response.json(); // array of bookListingDTO objects

        const resultsContainer = document.getElementById("resultsContainer");
        resultsContainer.innerHTML = ""; // clear placeholders

        // HATEOAS PagedModel: actual items are in _embedded.bookListingDTOList
        const books = data._embedded?.bookListingDTOList || [];

        books.forEach(bookListingDTO => {
            const card = document.createElement("div");
            card.className = "book-card";

            card.innerHTML = `
                <h3>${bookListingDTO.book.title}</h3>
                <p><strong>Author:</strong> ${bookListingDTO.book.author}</p>
                <p><strong>Condition:</strong> ${bookListingDTO.condition}</p>
                <p><strong>Type:</strong> ${bookListingDTO.transaction_type}${bookListingDTO.price ? ` ($${bookListingDTO.price})` : ''}</p>
            `;
            
            const button = document.createElement("button");
            button.textContent = "Request book";
            button.addEventListener("click", () => requestBook(bookListingDTO));
            card.appendChild(button);

            resultsContainer.appendChild(card);
        });
        
        // Update pagination info
        currentPage = data.page?.number || 0;
        const totalPages = data.page?.totalPages || 1;

        // Enable/disable next/prev buttons
        document.getElementById("prevBtn").disabled = currentPage <= 0;
        document.getElementById("nextBtn").disabled = currentPage >= totalPages - 1;

        document.getElementById("pageInfo").textContent = `${currentPage + 1} of ${totalPages}`;

    } catch (err) {
        console.error("Failed to load books:", err);
    }
}

async function requestBook(bookListingDTO) {
    alert("requesting book")

    if (!isLoggedIn) {
        alert("You must be logged in to request a book.");
        return;
    }
    
    // request data
    const request = {
      requesterId: localStorage.getItem("userID"),
      listingId: bookListingDTO.id,

      requestAt: new Date().toISOString()
    };
    alert("before try");
    try {
        alert("before post");
        const response = await fetch("http://localhost:8080/api/request", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(request)
        });
        alert("after post");

        if (!response.ok) {
            if (response.status === 401) 
              return alert("Unauthorized. Please log in again.");
            throw new Error(await response.text());
        }

        const data = await response.json();
        alert(`Book request sent successfully!\n${data || ""}`);

    } catch (err) {
        console.error(err);
        alert("Failed to request book. Please try again later.");
    }
}
  
function nextPage() {
    loadBooks(currentPage + 1);
}

function prevPage() {
    loadBooks(currentPage - 1);
}

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

// function registerBook() {
//   if(!isLoggedIn){
//     alert("Please log in to register a book.");
//     return;
//   }
//   // Show register modal if logged in
//   document.getElementById("registerModal").style.display = "block";
// }



function goToProfile() {
  window.location.href = "user-profile.html";
}


// Modal stuff
const modal = document.getElementById("registerModal");
const registerBtn = document.querySelector(".register-btn");
const closeBtn = document.querySelector(".close-btn");

registerBtn.onclick = () => {
  if (!isLoggedIn) {
        alert("You must be logged in to register a book!");
        return;
    }
  modal.style.display = "block";
}
closeBtn.onclick = () => modal.style.display = "none";
window.onclick = (event) => { if(event.target === modal) modal.style.display = "none"; }

const form = document.getElementById("registerBookForm");
const transactionSelect = document.getElementById("transaction");
const priceInput = document.getElementById("price");
const rentalInput = document.getElementById("rentalDuration");

form.addEventListener("submit", async function(e) {
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
    year: document.getElementById("year").value || null,
    genre: document.getElementById("genre").value || null
  };

  try{
    const bookResponse = await fetch("http://localhost:8080/api/book", {
      method: "POST",
      headers: {"Content-Type": "application/json"},
      body: JSON.stringify(book)
    });

    if(!bookResponse.ok){
      throw new Error("Book creation failed");
    }

    const bookId = await bookResponse.json();
    alert(`Book id is ${bookId}`);

    const listing = {
      bookId: bookId,
      ownerId: localStorage.getItem("userID"),
      condition: document.getElementById("condition").value,
      transaction_type: transaction,
      price: priceInput.value || null,
      rentalDuration: rentalInput.value || null,
    };

    const listingResponse = await fetch("http://localhost:8080/api/listing", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(listing)
    });

    if(listingResponse.status === 201){
      alert(`Book "${book.title}" registered successfully!`);
      modal.style.display = "none";
      form.reset();
    }
    else if (!listingResponse.ok) {
      throw new Error("Listing creation failed");
    }

    
  } catch (err) {
    console.error(err);
    alert(err);
  }


  console.log("Book registered:", book);
  alert(`Book "${book.title}" registered successfully!`);

  modal.style.display = "none";
  form.reset();
});


