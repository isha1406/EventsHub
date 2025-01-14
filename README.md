# Ticket Booking System
This is a full-stack application built with Angular for the frontend and Spring Boot for the backend. The application allows users to book tickets, manage their details, and verify their booking by downloading the ticket. It also includes an admin portal with JWT-based authentication, enabling secure access to view and manage event and ticket data.

## Features
### User Features
   * #### Ticket Booking:
      * Users can book any event ticket by selecting the quantity and providing the required details.
      * The ticket can be download for the event entry.
* #### Manage Details:
   * Add personal information while booking the tickets.
   * Edit details for any upcoming event ticket booking.

### Admin Features
* #### Admin Login:
   * Secure login for admin users.
   * API access requires a valid JWT token for authentication.
* #### Add:
   * Admin can add the details of all events.
* #### Edit:
   * Admin can add the details of all events.

## Technologies Used
### Frontend
* #### Angular
  * Responsive and dynamic UI.
  * API calls.
  * Form validation.
  * CSS for styling the application.
### Backend
* #### Spring Boot
  * REST APIs for handling user and admin operations.
  * JWT-based authentication for secure admin access.
  * Hibernate and JPA for database interactions.
### Database
* #### MySQL
  * Relational database for storing user details, events data, and admin credentials.

## Key Functionalities
* #### User Workflow:
   * Book an appointment by selecting date, time, and filling out traveler details.
   * Receive an OTP on the provided email for verification.
   * Enter the OTP to confirm the booking.
   * Get a confirmation email with the booking details.
* #### Admin Workflow:
   * Admin logs in with valid credentials to access the admin panel.
   * Secure API access with JWT token validation.
   * View the complete list of events and user details.
   * Admin can add new event with its details.

## Setup Instructions
### Prerequisites
  **Node.js** for the frontend.\
  **Java (JDK 17 or above)** for the backend.\
  **MySQL** for the database.
  
### Backend Setup
* #### Clone the backend repository:
       git clone <backend-repo-url>
       cd <backend-folder>
* #### Configure application.properties in the src/main/resources folder:
       spring.datasource.url=jdbc:mysql://localhost:3306/eventsdatabase
       spring.datasource.username=root
       spring.datasource.password=<password>
       spring.jpa.hibernate.ddl-auto=update
       logging.file.name=logs/eventsHub.log
       Logging.file.max-size=20MB    
* #### Run the backend server:
       mvn spring-boot:run

### Frontend Setup
* #### Clone the frontend repository:
       git clone <frontend-repo-url>
       cd <frontend-folder>

* #### Install dependencies:
       npm install

* #### Start the frontend development server:
       npm start
  
Access the application in your browser at (http://localhost:3000).
