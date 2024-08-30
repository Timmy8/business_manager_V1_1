# Business Manager V1.1

Business Manager is a comprehensive solution for managing business processes, including an API service, a user interface, and a Telegram bot for integration.
 > **Note:** Telegram bot is not available in the open version

## Project Structure

- **core-api-service**: Backend service implemented in Java to handle business logic and data.
- **manager-ui-app**: Web application for managing users and data.
- **telegram-bot-app**: Telegram bot for interacting with users and performing tasks.

## Installation and Setup

1. **Clone the repository to your device**:

   ```bash
   git clone https://github.com/Timmy8/business_manager_V1_1.git
   cd business_manager_V1_1
   
2. **Install dependencies**:
   > **Note:** The entire installation process takes place using Docker. If you need to run the project yourself, see point 4

   Install Docker ([link](https://www.docker.com/))
   
   Follow the instructions in each module's README or Dockerfile to install specific dependencies

3. **Run the project**:
   > **You need to change ALL the fields in the .env file!!!**

   Start Docker Compose:
   ```bash
   docker-compose up
   
4 **Run the project without docker**:
   1. **Change** the **application.yml** in each of the modules or configure the environment variables.
   2. **Delete** docker-compose dependency.
   3. **Install**: Java version 21+, any SQL database system, any java IDE or command line for run.
   4. **Run** applications

## Technologies Used
- **Java**: For backend development.
- **Spring Boot**: Spring boot, Spring security, Spring DATA JPA, SPring MVC,
- **HTML, CSS, JS, Thumeleaf**: For the frontend application.
- **Docker**: For containerizing applications.
- **PostgreSQL**: For database management.
- **Hibername**: For database connection and usage.
- **Flyway**: For database version controle.
- **Spring boot validation**: As validation sysyem.
- **log4j2**: As logging system.
- **lombok**: For development.

## How to use (1 - UI pages, 2 - API endpoints)
## 1. UI Pages:
### Pages for customers: 
### Authorization method: 
  - **No authentication**

### 1. Make appointment page
  ```bash
  http://localhost:8000/appointment
  ```
![image](https://github.com/user-attachments/assets/d9d00bc9-52f3-4674-9b56-5c09580629d4)

### 2. Registration page
  ```bash
  http://localhost:8000/registration
  ```
![image](https://github.com/user-attachments/assets/daa931c1-6caf-4b02-b96e-9ba183fa9a92)

### Pages for managers: 
### Authorization method: 
  - **Basic authentication (ADMIN_USERNAME/ADMIN_PASSWORD)** from **.env**

### 1. Get all clients page
  ```bash
  http://localhost:8080/manager/clients/list
  ```
  - **Return**: Page where you can see:
    - All registered clients
    - Add new client
    - Block client
    - Edit client information
    - Delete client

### 2. Add new client page
  ```bash
  http://localhost:8080/manager/clients/create
  ```
  - **Return**: Page where you can see:
    - Client registration page
   
### 3. Edit client page
  ```bash
  http://localhost:8080/manager/clients/{clientId}/edit
  ```
  - **Request params**:
    - clientID (integer) - Client ID.
  - **Return**: Page where you can see
    - Client edit page

### 4. Get all proposals page
  ```bash
  http://localhost:8080/manager/proposals/list
  ```
  - **Return**: Page where you can see:
    - All created proposals
    - Add new proposal
    - Edit proposal information
    - Delete proposal

### 5. Add new proposal page
  ```bash
  http://localhost:8080/manager/proposals/create
  ```
  - **Return**: Page where you can see:
    - Proposal creating page
   
### 6. Edit proposal page
  ```bash
  http://localhost:8080/manager/proposals/{proposalId}/edit
  ```
  - **Request params**:
    - proposalId (integer) - Proposal ID.
  - **Return**: Page where you can see
    - Proposal edit page

### 7. Get all appointments page
  ```bash
  http://localhost:8080/manager/appointments/list
  ```
  - **Return**: Page where you can see:
    - All created appointments
    - Add new appointment
    - Edit appointment information
    - Delete appointment

### 8. Add new appointment page
  ```bash
  http://localhost:8080/manager/appointments/create
  ```
  - **Return**: Page where you can see:
    - Appointment creating page
   
### 9. Edit appointment page
  ```bash
  http://localhost:8080/manager/appointments/{appointmentId}/edit
  ```
  - **Request params**:
    - appointmentId (integer) - Appointment ID.
  - **Return**: Page where you can see
    - Appointment edit page

## 2. API endpoints:
### Basic api URL: 
  ```bash
  http://localhost:8000/manager-api/
  ```
## Authorization method: 
  - **Basic authentication (ADMIN_USERNAME/ADMIN_PASSWORD)** from **.env**
   
### 1. Receive all clients list

- **Method**: `GET`
- **URL**: `/clients`
- **Descirption**: Get all clients list.
- **Authorization**: Basic authentication (Username/Password).
- **Response**:
  - **200 OK**:
    ```json
    [
      {
        "name": "Client name",
        "surname": "Client surname",
        "phoneNumber": "CLient phone number (+375XXYYYYYYY)",
        "description": "Descirption (can be empty)",
        "blocked": "Client account status (true/false)",
        "id": 1
      }
    ]
    ```
  - **401 Unauthorized**: Need Authorization.
  - **500 Internal Server Error**: Server error.

### 2. Create new client

- **Method**: `POST`
- **URL**: `/clients`
- **Descirption**: Create new client.
- **Authorization**: No.
- **Request body**:
  ```json
  {
    "name": "New client name",
    "surname": "New client surname",
    "phoneNumber": "New client phone number (+375XXYYYYYYY)",
    "description": "Description (can be empty)"
}
- **Response**:
  - **201 Created**:
    ```json
    {
      "name": "New client name",
      "surname": "New client surname",
      "phoneNumber": "New client phone number (+375XXYYYYYYY)",
      "description": "Description (can be empty)",
      "blocked": false,
      "id": 1
    } 
    ```
  - **400 Bad Request**: List of errors with incorrectly entered data.
  - **404 Not Found**: User with this phone number already exists!.
 
### 3. Get client {ID} info

- **Method**: `GET`
- **URL**: `/clients/{clientID}`
- **Descirption**: Get information about client by ID.
- **Authorization**: Basic authentication (Username/Password).
- **Request params**:
  - clientID (integer) - Client ID.
- **Response**:
  - **200 OK**:
    ```json
    {
      "name": "Client {clientID} name",
      "surname": "Client {clientID} surname",
      "phoneNumber": "Client {clientID} phone number (+375XXYYYYYYY)",
      "description": "Client {clientID} description (can be empty)",
      "blocked": "Client {clientID} blocked status (true/false)",
      "id": 1
    } 
    ```
  - **401 Unauthorized**: Need Authorization.
  - **404 Not Found**: Client not found!.
 
### 4. Update client {ID} info

- **Method**: `PATCH`
- **URL**: `/clients/{clientID}`
- **Descirption**: Update information about client by ID.
- **Authorization**: Basic authentication (Username/Password).
- **Request params**:
  - clientID (integer) - Client ID.
- **Request body**:
  ```json
  {
    "name": "New client name(or no changes)",
    "surname": "New client surname(or no changes",
    "phoneNumber": "New client phone number (or no changes)",
    "description": "New description (or no changes)"
}
- **Response**:
  - **204 No Content**: Successfully changed.
  - **400 Bad Request**: List of errors with incorrectly entered data.
  - **401 Unauthorized**: Need Authorization.
  - **404 Not Found**: Client not found!.
  - **404 Not Found**: User with this phone number already exists!.

 ### 5. Delete client by {ID}

- **Method**: `DELETE`
- **URL**: `/clients/{clientID}`
- **Descirption**: Delete client by ID.
- **Authorization**: Basic authentication (Username/Password).
- **Request params**:
  - clientID (integer) - Client ID.
- **Response**:
  - **204 No Content**: Successfully deleted.
  - **401 Unauthorized**: Need Authorization.
  - **404 Not Found**: Client not found!.

### 6. Get client {phoneNumber} info by phone number

- **Method**: `GET`
- **URL**: `/clients/{phoneNumber}`
- **Descirption**: Get information about client by ID.
- **Authorization**: Basic authentication (Username/Password).
- **Request params**:
  - phoneNumber (String) - Client phone number (+375XXYYYYYYY).
- **Response**:
  - **200 OK**:
    ```json
    {
      "name": "Client {phoneNumber} name",
      "surname": "Client {phoneNumber} surname",
      "phoneNumber": "Client {phoneNumber} phone number (+375XXYYYYYYY)",
      "description": "Client {phoneNumber} description (can be empty)",
      "blocked": "Client {phoneNumber} blocked status (true/false)",
      "id": 1
    } 
    ```
  - **401 Unauthorized**: Need Authorization.
  - **404 Not Found**: Client not found!.

### 7. Receive all proposals list

- **Method**: `GET`
- **URL**: `/proposals`
- **Descirption**: Get all proposals list.
- **Authorization**: Basic authentication (Username/Password).
- **Response**:
  - **200 OK**:
    ```json
    [
      {
        "name": "Proposal name",
        "description": "Proposal descirption",
        "price": "Proposal price(decimal)",
        "id": 1
      }
    ]
    ```
  - **401 Unauthorized**: Need Authorization.
  - **500 Internal Server Error**: Server error.

### 8. Create new proposal

- **Method**: `POST`
- **URL**: `/proposals`
- **Descirption**: Create new proposal.
- **Authorization**: Basic authentication (Username/Password).
- **Request body**:
  ```json
  {
    "name": "New proposal name",
    "description": "New proposal descirption",
    "price": "new Proposal price(decimal)",
  }
  ```
- **Response**:
  - **201 Created**:
  ```json
  {
    "name": "Proposal name",
    "description": "Proposal descirption",
    "price": "Proposal price(decimal)",
    "id": 1
  }
  ```
  - **400 Bad Request**: List of errors with incorrectly entered data.
  - **401 Unauthorized**: Need Authorization.
 
### 9. Get proposal {ID} info

- **Method**: `GET`
- **URL**: `/proposals/{proposalId}`
- **Descirption**: Get information about proposal by ID.
- **Authorization**: Basic authentication (Username/Password).
- **Request params**:
  - proposalId (integer) - Proposal ID.
- **Response**:
  - **200 OK**:
    ```json
    {
      "name": "Proposal name",
      "description": "Proposal descirption",
      "price": "Proposal price(decimal)",
      "id": 1
    }
    ```
  - **401 Unauthorized**: Need Authorization.
  - **404 Not Found**: Proposal not found!.
 
### 10. Update proposal {ID} info

- **Method**: `PATCH`
- **URL**: `/proposals/{proposalId}`
- **Descirption**: Update information about proposal by ID.
- **Authorization**: Basic authentication (Username/Password).
- **Request params**:
  - proposalId (integer) - Proposal ID.
- **Request body**:
  ```json
  {
    "name": "New proposal name",
    "description": "New proposal descirption",
    "price": "new Proposal price(decimal)",
  }
  ```
- **Response**:
  - **204 No Content**: Successfully changed.
  - **400 Bad Request**: List of errors with incorrectly entered data.
  - **401 Unauthorized**: Need Authorization.
  - **404 Not Found**: Proposal not found!.

 ### 11. Delete Proposal by {ID}

- **Method**: `DELETE`
- **URL**: `/proposals/{proposalId}`
- **Descirption**: Delete proposal by ID.
- **Authorization**: Basic authentication (Username/Password).
- **Request params**:
  - proposalId (integer) - Proposal ID.
- **Response**:
  - **204 No Content**: Successfully deleted.
  - **401 Unauthorized**: Need Authorization.
  - **404 Not Found**: Proposal not found!.

 ### 12. Receive all appointments list

- **Method**: `GET`
- **URL**: `/appointments`
- **Descirption**: Get all appointments list.
- **Authorization**: Basic authentication (Username/Password).
- **Response**:
  - **200 OK**:
    ```json
    [
      {
        "id": 1,
        "visitDate": "Visit date(format: dd-MM-yy HH:mm)",
        "clientId": 1,
        "proposals": [
            {
                "name": "Selected proposal name",
                "description": "Selected proposal descirption",
                "price": "Selected proposal price(decimal)",
                "id": 2
            }
        ]
      }
    ]
    ```
  - **401 Unauthorized**: Need Authorization.
  - **500 Internal Server Error**: Server error.

### 13. Create new appointment

- **Method**: `POST`
- **URL**: `/appointments`
- **Descirption**: Create new appointment.
- **Authorization**: Basic authentication (Username/Password).
- **Request body**:
  ```json
  {
    "visitDate": "Visit date (format: dd-MM-yy HH:mm)",
    "clientId": 1,
    "proposalId": 1
  }
  ```

- **Response**:
  - **201 Created**:
  ```json
  {
    "id": 1,
    "visitDate": "Visit date (format: dd-MM-yy HH:mm)",
    "clientId": 1,
    "proposals": [
        {
            "name": "Selected proposal name",
            "description": "Selected proposal descirption",
            "price": "Selected proposal price(decimal)",
            "id": 1
        }
    ]
  }
  ```
  - **400 Bad Request**: List of errors with incorrectly entered data.
  - **401 Unauthorized**: Need Authorization.
 
### 14. Get appointment {ID} info

- **Method**: `GET`
- **URL**: `/appointments/{appointmentId}`
- **Descirption**: Get information about appointment by ID.
- **Authorization**: Basic authentication (Username/Password).
- **Request params**:
  - appointmentId (integer) - Appointment ID.
- **Response**:
  - **200 OK**:
    ```json
    {
      "id": 1,
      "visitDate": "Visit date (format: dd-MM-yy HH:mm)",
      "clientId": 1,
      "proposals":
      [
        {
          "name": "Selected proposal name",
          "description": "Selected proposal descirption",
          "price": "Selected proposal price(decimal)",
          "id": 1
        }
      ]
    }
    ```
  - **401 Unauthorized**: Need Authorization.
  - **404 Not Found**: Proposal not found!.
 
### 15. Update appointment {ID} info

- **Method**: `PATCH`
- **URL**: `/appointments/{appointmentId}`
- **Descirption**: Update information about appointment by ID.
- **Authorization**: Basic authentication (Username/Password).
- **Request params**:
  - appointmentId (integer) - Appointment ID.
- **Request body**:
  ```json
  {
    "visitDate": "Visit date (format: dd-MM-yy HH:mm)",
    "clientId": 1,
    "proposalId": 1
  }
  ```
- **Response**:
  - **204 No Content**: Successfully changed.
  - **400 Bad Request**: List of errors with incorrectly entered data.
  - **401 Unauthorized**: Need Authorization.
  - **404 Not Found**: Appointment not found!.

 ### 16. Delete Appointment by {ID}

- **Method**: `DELETE`
- **URL**: `/appointments/{appointmentId}`
- **Descirption**: Delete appointment by ID.
- **Authorization**: Basic authentication (Username/Password).
- **Request params**:
  - appointmentId (integer) - Appointment ID.
- **Response**:
  - **204 No Content**: Successfully deleted.
  - **401 Unauthorized**: Need Authorization.
  - **404 Not Found**: Appointment not found!.
 
## How to Contribute
1. Fork the repository.
2. Create a branch for your changes (git checkout -b feature/my-feature).
3. Commit your changes (git commit -m 'Add new feature...').
4. Push to the branch (git push origin feature/my-feature).
5. Create a Pull Request.
