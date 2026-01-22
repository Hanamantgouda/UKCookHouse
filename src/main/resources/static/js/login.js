function toggleForm() {
      const loginForm = document.getElementById("loginForm");
      const signupForm = document.getElementById("signupForm");
      if (loginForm.style.display === "none") {
        loginForm.style.display = "flex";
        signupForm.style.display = "none";
      } else {
        loginForm.style.display = "none";
        signupForm.style.display = "flex";
      }
    }

    //REGISTER USER
    document.getElementById("signupForm").addEventListener("submit", async function (e) {
      e.preventDefault();

      const user = {
        username: document.getElementById("signupUsername").value.trim(),
        email: document.getElementById("signupEmail").value.trim(),
        password: document.getElementById("signupPassword").value.trim()
      };

      if (!user.username || !user.email || !user.password) {
        Swal.fire({
          icon: "warning",
          title: "Missing Fields",
          text: "Please fill all fields before registering.",
          confirmButtonColor: "#ff4d4d"
        });
        return;
      }

      Swal.fire({
        title: "Registering...",
        text: "Please wait while we create your account.",
        allowOutsideClick: false,
        didOpen: () => Swal.showLoading()
      });

      try {
        const response = await fetch("/api/register", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(user)
        });

        const data = await response.json();
        Swal.close();

        if (data.status === "success") {
          Swal.fire({
            icon: "success",
            title: "Registration Complete 🎉",
            text: "Your account has been created successfully!",
            showConfirmButton: false,
            timer: 1800
          });

          setTimeout(() => {
            document.getElementById("signupForm").reset();
            toggleForm();
          }, 1800);
        } else {
          Swal.fire({
            icon: "error",
            title: "Registration Failed",
            text: data.message || "Something went wrong. Try again!",
            confirmButtonColor: "#ff4d4d"
          });
        }
      } catch (error) {
        Swal.close();
        Swal.fire({
          icon: "error",
          title: "Server Error",
          text: "Could not connect to the server. Please try again later.",
          confirmButtonColor: "#ff4d4d"
        });
      }
    });



    // LOGIN USER
document.getElementById("loginForm").addEventListener("submit", async function (e) {
  e.preventDefault();

  const credentials = {
    email: document.getElementById("loginEmail").value.trim(),
    password: document.getElementById("loginPassword").value.trim()
  };

  if (!credentials.email || !credentials.password) {
    Swal.fire({
      icon: "warning",
      title: "Missing Fields",
      text: "Please enter both email and password.",
      confirmButtonColor: "#ff4d4d"
    });
    return;
  }

  Swal.fire({
    title: "Logging in...",
    text: "Please wait while we verify your credentials.",
    allowOutsideClick: false,
    didOpen: () => Swal.showLoading()
  });

  try {
    const response = await fetch("/api/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(credentials)
    });

    const data = await response.json();
    Swal.close();

    if (data.status === "success") {
      // Create 7-day cookie
      const expiryDate = new Date();
      expiryDate.setDate(expiryDate.getDate() + 7);

      // You can store user info or token returned from backend
      document.cookie = `userEmail=${credentials.email}; expires=${expiryDate.toUTCString()}; path=/; SameSite=Lax`;
      document.cookie = `userToken=${data.token || "sampleToken"}; expires=${expiryDate.toUTCString()}; path=/; SameSite=Lax`;
      document.cookie = `userID=${data.user_id}; expires=${expiryDate.toUTCString()}; path=/; SameSite=Lax`;
      document.cookie = `username=${data.username}; expires=${expiryDate.toUTCString()}; path=/; SameSite=Lax`;
      
      Swal.fire({
        icon: "success",
        title: "Welcome Back 👋",
        text: `Hello ${data.username || ""}, login successful!`,
        showConfirmButton: false,
        timer: 1800
      });

      // Redirect after login
      setTimeout(() => {
        window.location.href = "home.html";
      }, 1800);

    } else {
      Swal.fire({
        icon: "error",
        title: "Login Failed",
        text: data.message,
        confirmButtonColor: "#ff4d4d"
      });
    }
  } catch (error) {
    Swal.close();
    Swal.fire({
      icon: "error",
      title: "Server Error",
      text: "Could not connect to the server. Please try again later.",
      confirmButtonColor: "#ff4d4d"
    });
  }
});
