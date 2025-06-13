# Chonky Boyz Financial Bank (Android App)

Welcome to the official repository for the **Chonky Boyz Financial Bank** Android app—a fun, secure, and user-friendly way to view account details and transaction history.

### Built-in Multithreaded Clock & Accessibility-First UI

This app features a **multithreaded user interface**, where a **real-time clock** is displayed on-screen via a dedicated background thread. This ensures responsive UI updates without blocking the main thread, improving both performance and user experience.

The interface also includes **large, high-contrast text and images** to improve accessibility for users with visual impairments.

The **transaction history** is organized using a **custom implementation of the Merge Sort algorithm** in Java. Merge Sort is a **divide-and-conquer** algorithm with a predictable time complexity of **O(n log n)** in all cases (worst, average, and best). This makes it ideal for ensuring fast and stable performance when sorting large datasets, like detailed transaction logs.

#### Why Merge

- **Stable sort**: Maintains the relative order of equal elements (important for transaction ties)
- **Consistent performance**: Unlike quicksort, Merge Sort does not degrade to O(n²) in the worst case
- **Predictable scaling**: Efficient even as the number of transactions grows

---

## Screenshots

![Login Screen](https://github.com/user-attachments/assets/ed80e4c0-f543-4f43-827f-63e556bde15f)
![Home Screen](https://github.com/user-attachments/assets/62f5ce9f-c4ca-4e8a-a9f1-31d79e8875b8)
![Navigation Menu](https://github.com/user-attachments/assets/3250b695-7fe5-4ea2-8bf1-1740c90ce280)
![Account Info](https://github.com/user-attachments/assets/8dc13860-d0c4-40a6-9a71-3e7585b2784c)

---

## Features

- **Live Clock UI**: Real-time clock runs in a separate thread to ensure smooth and accurate time display without blocking the main UI thread
- **Account Overview**: Displays the user’s account name and total balance clearly
- **Transaction History**: Sorted using a custom **Merge Sort** implementation in Java for optimal performance; handles large datasets efficiently with a time complexity of **O(n log n)**
- **Smooth Navigation**: Easily return to the home screen or move between views without delay
- **Secure Logout**: Cleanly ends the session and returns to the login screen
- **Vision-Friendly UI**: Large, high-contrast text and images designed for accessibility, especially for users with visual impairments

---

## Tech Stack

- **Language**: Java
- **Framework**: Android SDK
- **UI Design**: XML-based layouts with accessibility in mind
- **Multithreading**: Java threads for non-blocking UI components
- **IDE**: Android Studio

---

## Screen Overview

- `LoginActivity`: Handles user authentication
- `RegisterActivity`: Supports new account registration
- `HomeActivity`: Main dashboard with navigation
- `Act_Activity`: Displays account and transaction details
- `ClockThread.java`: Background thread managing the real-time clock display

---

## Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/Chaz-Ortiz/rhDraft1/
   cd rhDraft1


---

## Author

**Chaz Ortiz**
[GitHub](https://github.com/Chaz-Ortiz) · [LinkedIn](https://www.linkedin.com/in/chaz-ortiz-615863270/) 
