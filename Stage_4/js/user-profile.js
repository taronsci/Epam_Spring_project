document.addEventListener("DOMContentLoaded", () => {
    const username = localStorage.getItem("username") || "";
    const email = localStorage.getItem("email") || "";

    document.getElementById("username").value = username;
    document.getElementById("email").value = email;

  loadMyListings();
  loadMyRequests(); //requests I have made
  loadRequestsReceived(); //requests for my listings 

});

let currentPageListings = 0;
let currentPageRequestsR = 0;
const pageSize = 3; 

async function loadMyListings(page = 0) {
    try {
        const userId = localStorage.getItem("userID");
        const response = await fetch(`http://localhost:8080/api/listing/${userId}?page=${page}&size=${pageSize}`);
        const data = await response.json();

        const resultsContainer = document.getElementById("myListings");
        resultsContainer.innerHTML = ""; 

        const books = data._embedded?.bookListingDTOList || [];

        books.forEach(bookListingDTO => {
            const card = document.createElement("div");
            card.className = "book-card";

            card.innerHTML = `
                <h3>${bookListingDTO.book.title}</h3>
                <p><strong>Author:</strong> ${bookListingDTO.book.author}</p>
                <p><strong>Condition:</strong> ${bookListingDTO.condition}</p>
                <p><strong>Type:</strong> ${bookListingDTO.transaction_type}${bookListingDTO.price ? ` ($${bookListingDTO.price})` : ''}</p>
                <p><strong>ListingStatus:</strong> ${bookListingDTO.status}</p>
            `;
            
            resultsContainer.appendChild(card);
        });
        
        currentPageListings = data.page?.number || 0;
        const totalPages = data.page?.totalPages || 1;

        document.getElementById("prevBtn").disabled = currentPageListings <= 0;
        document.getElementById("nextBtn").disabled = currentPageListings >= totalPages - 1;

        document.getElementById("page1Info").textContent = `${currentPageListings + 1} of ${totalPages}`;

    } catch (err) {
        console.error("Failed to load books:", err);
    }
}
function nextPageL() {
    loadMyListings(currentPageListings + 1);
}
function prevPageL() {
    loadMyListings(currentPageListings - 1);
}

async function loadRequestsReceived(page = 0) {
    try {
        const userId = localStorage.getItem("userID");
        const response = await fetch(`http://localhost:8080/api/request/${userId}?page=${page}&size=${pageSize}`);
        const data = await response.json();

        const resultsContainer = document.getElementById("requests");
        resultsContainer.innerHTML = ""; 

        const books = data._embedded?.bookRequestDTOList || [];

        books.forEach(bookRequestDTO => {
            const card = document.createElement("div");
            card.className = "book-card";

            const date = new Date(bookRequestDTO.createdAt);
            const formattedDate = date.toLocaleString();

            card.innerHTML = `
                <h3>${bookRequestDTO.book.title}</h3>
                <p><strong>Author:</strong> ${bookRequestDTO.book.author}</p>
                <p><strong>Requester:</strong> ${bookRequestDTO.requesterUsername}</p>
                <p><strong>ListingStatus:</strong> ${bookRequestDTO.status}</p>
                <p><strong>RequestedAt:</strong> ${formattedDate}</p>
            `;

            const acceptbutton = document.createElement("button");
            acceptbutton.textContent = "Accept Request";
            acceptbutton.addEventListener("click", () => respondToRequest(bookListingDTO,true));
            card.appendChild(acceptbutton);

            const rejectbutton = document.createElement("button");
            rejectbutton.textContent = "Reject Request";
            rejectbutton.addEventListener("click", () => respondToRequest(bookListingDTO,false));
            card.appendChild(rejectbutton);
        

            resultsContainer.appendChild(card);
        });
        
        currentPageRequestsR = data.page?.number || 0;
        const totalPages = data.page?.totalPages || 1;

        document.getElementById("prevBtnR").disabled = currentPageRequestsR <= 0;
        document.getElementById("nextBtnR").disabled = currentPageRequestsR >= totalPages - 1;

        document.getElementById("pageInfoR").textContent = `${currentPageRequestsR + 1} of ${totalPages}`;

    } catch (err) {
        console.error("Failed to load books:", err);
    }
}

function nextPageR() {
    loadRequestsReceived(currentPageRequestsR + 1);
}

function prevPageR() {
    loadRequestsReceived(currentPageRequestsR - 1);
}

// To be implemented
async function loadMyRequests(page = 0) {
    try {
        const userId = localStorage.getItem("userID");
        const response = await fetch(`http://localhost:8080/api/request/${userId}?page=${page}&size=${pageSize}`);
        const data = await response.json();

        const resultsContainer = document.getElementById("requests");
        resultsContainer.innerHTML = ""; 

        const books = data._embedded?.bookRequestDTOList || [];

        books.forEach(bookRequestDTO => {
            const card = document.createElement("div");
            card.className = "book-card";

            card.innerHTML = `
                <h3>${bookRequestDTO.book.title}</h3>
                <p><strong>Author:</strong> ${bookRequestDTO.book.author}</p>
                <p><strong>Requester:</strong> ${bookRequestDTO.requesterUsername}</p>
                <p><strong>ListingStatus:</strong> ${bookRequestDTO.status}</p>
                <p><strong>RequestedAt:</strong> ${bookRequestDTO.createdAt}</p>
            `;

            const button = document.createElement("button");
            button.textContent = "Accept Request";
            button.addEventListener("click", () => acceptRequest(bookListingDTO));
            card.appendChild(button);

            resultsContainer.appendChild(card);
        });
        
        currentPage = data.page?.number || 0;
        const totalPages = data.page?.totalPages || 1;

        document.getElementById("prevBtnR").disabled = currentPage <= 0;
        document.getElementById("nextBtnR").disabled = currentPage >= totalPages - 1;

        document.getElementById("page2Info").textContent = `${currentPage + 1} of ${totalPages}`;

    } catch (err) {
        console.error("Failed to load books:", err);
    }
}

// to be implemented
async function respondToRequest(bookListingDTO, accept) {
    try {
        const userId = localStorage.getItem("userID");
        const response = await fetch(`http://localhost:8080/api/request/${userId}?page=${page}&size=${pageSize}`);
        const data = await response.json();

        const resultsContainer = document.getElementById("requests");
        resultsContainer.innerHTML = ""; 

        const books = data._embedded?.bookRequestDTOList || [];

        books.forEach(bookRequestDTO => {
            const card = document.createElement("div");
            card.className = "book-card";

            card.innerHTML = `
                <h3>${bookRequestDTO.book.title}</h3>
                <p><strong>Author:</strong> ${bookRequestDTO.book.author}</p>
                <p><strong>Requester:</strong> ${bookRequestDTO.requesterUsername}</p>
                <p><strong>ListingStatus:</strong> ${bookRequestDTO.status}</p>
                <p><strong>RequestedAt:</strong> ${bookRequestDTO.createdAt}</p>
            `;

            const button = document.createElement("button");
            button.textContent = "Accept Request";
            button.addEventListener("click", () => acceptRequest(bookListingDTO));
            card.appendChild(button);

            resultsContainer.appendChild(card);
        });
        
        currentPage = data.page?.number || 0;
        const totalPages = data.page?.totalPages || 1;

        document.getElementById("prevBtn").disabled = currentPage <= 0;
        document.getElementById("nextBtn").disabled = currentPage >= totalPages - 1;

        document.getElementById("page2Info").textContent = `${currentPage + 1} of ${totalPages}`;

    } catch (err) {
        console.error("Failed to load books:", err);
    }
}

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
