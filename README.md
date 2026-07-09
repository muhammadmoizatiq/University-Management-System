# 🎓 University Management System

A desktop-based **University Management System** developed using **Java** and **JavaFX**. The application provides dedicated portals for **Administrators, Faculty Members, and Students**, enabling efficient management of academic operations such as course administration, student enrollment, classroom assignments, and grading.

The system uses **Java Object Serialization** for local data persistence, storing application data in binary `.dat` files without requiring an external database.

---

## ✨ Features

### 🔐 Authentication & User Management
- Role-based login system
- Student and Faculty registration
- Automatic creation of the default administrator account
- Username validation and duplicate account prevention

### 👨‍💼 Administrator Portal
- View all registered students
- View all faculty members
- Create and manage courses
- Assign faculty members to courses
- Assign classrooms to courses
- Monitor university records through a centralized dashboard

### 🎓 Student Portal
- View personal profile
- Browse available courses
- Enroll in courses
- View enrolled courses
- Access academic marks and transcript

### 👨‍🏫 Faculty Portal
- View assigned teaching schedule
- View enrolled students for each course
- Enter and update student marks
- Manage grading for assigned courses

---

## 👥 User Roles

| Role | Capabilities |
|------|--------------|
| **Admin** | Manage students, faculty, courses, classrooms, and assignments |
| **Faculty** | View assigned courses, manage grades, view enrolled students |
| **Student** | Enroll in courses, view profile, view academic results |

---

## 🛠 Technologies Used

- Java 21
- JavaFX
- Java Object Serialization
- JavaFX CSS
- IntelliJ IDEA

---

## 💾 Data Storage

The application stores data locally using serialized binary files.

- `users.dat`
- `students.dat`
- `faculty.dat`
- `courses.dat`
- `classrooms.dat`

No external database is required.

---

## 📂 Project Structure

```
src/
│
├── university/
│   ├── model/
│   └── ui/
│
├── resources/
│   └── styles/
│
└── images/
```

---

## 🚀 Getting Started

### Prerequisites

- Java JDK 21
- JavaFX SDK
- IntelliJ IDEA (recommended)

### Clone the Repository

```bash
git clone https://github.com/muhammadmoizatiq/University-Management-System.git
```

### Run the Project

1. Open the project in IntelliJ IDEA.
2. Configure the JavaFX SDK.
3. Add the required JavaFX VM options.
4. Run `MainApp.java`.

---

## 🔑 Default Administrator Login

The system automatically creates an administrator account if no user database exists.

**Username**

```
admin
```

**Password**

```
admin123
```

---

## 🧠 Object-Oriented Programming Concepts

This project demonstrates the practical use of:

- Encapsulation
- Inheritance
- Polymorphism
- Composition
- Layered Architecture
- Object Serialization
- Role-Based Access Control (RBAC)

---

## 🔮 Future Improvements

- Database integration (SQLite/PostgreSQL)
- Password hashing (BCrypt)
- CRUD operations for all entities
- Classroom scheduling conflict detection
- Capacity validation
- Enhanced reporting and analytics
- Search and filtering improvements

---

## 📸 Screenshots

Screenshots will be added soon.

---

## 👨‍💻 Author

**Muhammad Moiz Attique**

BS Computer Science  
COMSATS University Islamabad, Wah Campus

GitHub:
https://github.com/muhammadmoizatiq

---

## ⭐ Support

If you found this project useful, consider giving the repository a ⭐.
