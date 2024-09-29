# Edunity

Edunity is an educational application designed to connect students and tutors, manage applications, and streamline attendance tracking.

## Table of Contents

- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [In-Development](#in-development)

## Features

- **User Registration**: Students and tutors can register and manage their profiles.
- **Application Management**: Students can apply for tutoring sessions; tutors can accept or reject applications.
- **Attendance Tracking**: Manage attendance and scores for each session.
- **Role Management**: Different roles for students and tutors with respective access controls.

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/rizzaputri/client-test-0929.git
2. Ensure you have Java 17+ and Maven installed.
3. Run the application:
    ```bash
    mvn spring-boot:run

## Usage
1. Accept Application
   Endpoint: `POST /applications/accept`
   Authorization: `Required (ROLE_TUTOR)`
   Request JSON:
   ```json
   {
        "applicationId": "string"
   }
   ```
   Response JSON:
   ```json
   {
        "statusCode": 200,
        "message": "Application accepted",
        "data": {
                "applicationId": "string",
                "student": {
                           "studentId":  "string",
                           "name":  "string",
                           "location":  "object"
                           },
                "subject": {
                            "subjectIdd": "string",
                            "subjectName": "string"
                           },
                "tutorId": "string",
                "day": "enum",
                "time": "local time",
                "status": true
                },
        "paging": null
   }
   ```
2. Submit Attendance
   Endpoint: `POST /attendance`
   Authorization: `Required (ROLE_TUTOR)`
   Request JSON:
   ```json
   {
        "applicationId": "string",
        "score": [
                 {
                 "score": 85
                 }
                 ]
   }
   ```
   Response JSON:
   ```json
   {
        "id": "attendanceId",
        "date": "2023-09-29",
        "student": {
                    "studentId": "studentId",
                    "name": "John Doe",
                    "location": {
                                "city": "string",
                                "province": "string"
                                }
                   },
        "tutor": {
                  "tutorId": "tutorId",
                  "name": "Jane Smith",
                  "location": {
                              "city": "string",
                              "province": "string"
                              }
                 },
        "score": [
                 {
                  "score": 85
                 }
                 ]
   }
   ```
3. Create Application
   Endpoint: `POST /applications`
   Authorization: `Required (ROLE_STUDENT)`
   Request JSON:
   ```json
   {
        "subjectId": "subjectId",
        "day": "Monday",
        "time": "10:00"
   }
   ```
   Response JSON:
   ```json
   {
        "applicationId": "applicationId",
        "student": {
                    "studentId": "studentId",
                    "name": "John Doe",
                    "location": {
                                "city": "string",
                                "province": "string"
                                }
                   },
        "subject": {
                    "subjectId": "subjectId",
                    "subjectName": "Mathematics"
                   },
        "tutorId": "tutorId",
        "day": "Monday",
        "time": "10:00",
        "status": false
   }
   ```
4. Create Subject
   Endpoint: `POST /subjects`
   Authorization: `Required (ROLE_TUTOR)`
   Request JSON:
   ```json
   {
        "subjectName": "Mathematics"
   }
   ```
   Response JSON:
   ```json
   {
        "subjectId": "subjectId",
        "subjectName": "Mathematics"
   }
   ```
5. User Login
   Endpoint: `POST /login`
   Authorization: None
   Request JSON:
   ```json
   {
        "username": "username",
        "password": "password"
   }
   ```
   Response JSON:
   ```json
   {
        "username": "username",
        "token": "jwtToken",
        "roles": ["ROLE_STUDENT", "ROLE_TUTOR"]
   }
   ```
6. Register Student
   Endpoint: `POST /students/register`
   Authorization: None
   Request JSON:
   ```json
   {
        "username": "john_doe",
        "password": "securePassword",
        "name": "John Doe",
        "email": "john@example.com",
        "phoneNumber": "08123456789",
        "location": {
                    "city": "CityName",
                    "province": "ProvinceName"
                    }
   }
   ```
   Response JSON:
   ```json
   {
        "studentId": "studentId",
        "name": "John Doe",
        "email": "john@example.com",
        "phoneNumber": "08123456789",
        "location": {
                    "city": "CityName",
                    "province": "ProvinceName"
                    },
        "roles": ["ROLE_STUDENT"]
   }
   ```
7. Update Application
   Endpoint: `PUT /applications/{id}`
   Authorization: `Required (ROLE_STUDENT)`
   Request JSON:
   ```json
   {
        "id": "applicationId",
        "subjectId": "newSubjectId",
        "day": "Tuesday",
        "time": "11:00"
   }
   ```
   Response JSON:
   ```json
   {
        "applicationId": "applicationId",
        "student": {
                    "studentId": "studentId",
                    "name": "John Doe"
                   },
        "subject": {
                    "subjectId": "newSubjectId",
                    "subjectName": "Physics"
                   },
        "day": "Tuesday",
        "time": "11:00"
   }
   ```
8. Register Tutor
   Endpoint: `POST /tutors/register`
   Authorization: None
   Request JSON:
   ```json
   {
        "username": "jane_smith",
        "password": "securePassword",
        "name": "Jane Smith",
        "email": "jane@example.com",
        "phoneNumber": "08123456789",
        "location": {
                     "city": "CityName",
                     "province": "ProvinceName"
                    }
   }
   ```
   Response JSON:
   ```json
   {
        "tutorId": "tutorId",
        "name": "Jane Smith",
        "email": "jane@example.com",
        "phoneNumber": "08123456789",
        "location": {
                     "city": "CityName",
                     "province": "ProvinceName"
                    },
        "roles": ["ROLE_TUTOR"]
   }
   ```

## Upcoming Features

- **E-Commerce Functionality**:
    - Merchants will be able to sell digital products related to education, such as e-books, online courses, and study materials.
    - This feature will allow for easy browsing, purchasing, and downloading of digital products, enhancing the learning experience for students.