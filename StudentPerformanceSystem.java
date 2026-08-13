import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Predicate;

public class StudentPerformanceSystem {

    // Functional Interface
    @FunctionalInterface
    interface StudentOperation {
        void execute(Student student);
    }

    // Student Class
    static class Student {

        private int id;
        private String name;
        private String department;
        private double mark;
        private String email;

        public Student(int id, String name, String department,
                       double mark, String email) {

            this.id = id;
            this.name = name;
            this.department = department;
            this.mark = mark;
            this.email = email;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDepartment() {
            return department;
        }

        public double getMark() {
            return mark;
        }

        public String getEmail() {
            return email;
        }

        // String API
        public String getFormattedName() {
            return name.trim().toUpperCase();
        }

        // Grade
        public String getGrade() {

            if (mark >= 90) {
                return "A+";
            } else if (mark >= 80) {
                return "A";
            } else if (mark >= 70) {
                return "B";
            } else if (mark >= 60) {
                return "C";
            } else if (mark >= 50) {
                return "D";
            } else {
                return "F";
            }
        }

        public String getResult() {
            return mark >= 50 ? "PASS" : "FAIL";
        }

        @Override
        public String toString() {

            return String.format(
                    "%-5d %-20s %-15s %-8.2f %-6s %-8s",
                    id,
                    getFormattedName(),
                    department,
                    mark,
                    getGrade(),
                    getResult()
            );
        }
    }

    // Student Service
    static class StudentService {

        private List<Student> students = new ArrayList<>();

        public void addStudent(Student student) {
            students.add(student);
        }

        public List<Student> getAllStudents() {
            return students;
        }

        // Optional + Lambda
        public Optional<Student> findById(int id) {

            return students.stream()
                    .filter(student -> student.getId() == id)
                    .findFirst();
        }

        // Optional + String API + Lambda
        public Optional<Student> findByName(String name) {

            String searchName = name.trim().toLowerCase();

            return students.stream()
                    .filter(student ->
                            student.getName()
                                    .toLowerCase()
                                    .contains(searchName))
                    .findFirst();
        }

        // Lambda + Stream
        public List<Student> getPassedStudents() {

            return students.stream()
                    .filter(student -> student.getMark() >= 50)
                    .toList();
        }

        // Lambda + Stream
        public List<Student> getByDepartment(String department) {

            return students.stream()
                    .filter(student ->
                            student.getDepartment()
                                    .equalsIgnoreCase(department))
                    .toList();
        }

        // Optional + Lambda
        public Optional<Student> getTopStudent() {

            return students.stream()
                    .max(Comparator.comparingDouble(Student::getMark));
        }

        // Stream API
        public double getAverageMark() {

            return students.stream()
                    .mapToDouble(Student::getMark)
                    .average()
                    .orElse(0.0);
        }
    }

    // Display Header
    static void printHeader() {

        System.out.println();
        System.out.println(
                "=========================================================================="
        );

        System.out.printf(
                "%-5s %-20s %-15s %-8s %-6s %-8s%n",
                "ID",
                "NAME",
                "DEPARTMENT",
                "MARK",
                "GRADE",
                "RESULT"
        );

        System.out.println(
                "=========================================================================="
        );
    }

    // Main Method
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        StudentService service = new StudentService();

        // Sample Students
        service.addStudent(
                new Student(
                        101,
                        "Praveen Kumar",
                        "AI & ML",
                        87,
                        "praveen@gmail.com"
                )
        );

        service.addStudent(
                new Student(
                        102,
                        "Arun Kumar",
                        "CSE",
                        76,
                        "arun@gmail.com"
                )
        );

        service.addStudent(
                new Student(
                        103,
                        "Karthik Raj",
                        "IT",
                        92,
                        "karthik@gmail.com"
                )
        );

        service.addStudent(
                new Student(
                        104,
                        "Dinesh",
                        "ECE",
                        48,
                        "dinesh@gmail.com"
                )
        );

        service.addStudent(
                new Student(
                        105,
                        "Rahul",
                        "AI & ML",
                        81,
                        "rahul@gmail.com"
                )
        );

        System.out.println();
        System.out.println(
                "===================================================="
        );

        System.out.println(
                "       STUDENT PERFORMANCE MANAGEMENT"
        );

        System.out.println(
                "===================================================="
        );

        while (true) {

            System.out.println();
            System.out.println("--------------- MENU ---------------");
            System.out.println("1. View All Students");
            System.out.println("2. Search Student by ID");
            System.out.println("3. Search Student by Name");
            System.out.println("4. Add New Student");
            System.out.println("5. View Passed Students");
            System.out.println("6. View Department Students");
            System.out.println("7. Find Top Performer");
            System.out.println("8. View Average Mark");
            System.out.println("9. Student Statistics");
            System.out.println("10. Lambda + Functional Interface");
            System.out.println("11. Predicate Example");
            System.out.println("0. Exit");

            System.out.print("\nEnter your choice: ");

            int choice;

            try {

                choice = Integer.parseInt(scanner.nextLine());

            } catch (NumberFormatException e) {

                System.out.println("Please enter a valid number.");
                continue;
            }

            switch (choice) {

                // View All
                case 1:

                    System.out.println("\nALL STUDENTS");

                    printHeader();

                    service.getAllStudents()
                            .forEach(student ->
                                    System.out.println(student));

                    break;

                // Search ID
                case 2:

                    System.out.print("Enter Student ID: ");

                    try {

                        int id = Integer.parseInt(scanner.nextLine());

                        Optional<Student> result =
                                service.findById(id);

                        if (result.isPresent()) {

                            System.out.println("\nSTUDENT FOUND");

                            printHeader();
                            System.out.println(result.get());

                        } else {

                            System.out.println(
                                    "Student not found."
                            );
                        }

                    } catch (NumberFormatException e) {

                        System.out.println(
                                "Invalid ID."
                        );
                    }

                    break;

                // Search Name
                case 3:

                    System.out.print(
                            "Enter Student Name: "
                    );

                    String name = scanner.nextLine();

                    Optional<Student> result =
                            service.findByName(name);

                    result.ifPresentOrElse(

                            student -> {

                                System.out.println(
                                        "\nSTUDENT FOUND"
                                );

                                printHeader();
                                System.out.println(student);
                            },

                            () -> System.out.println(
                                    "Student not found."
                            )
                    );

                    break;

                // Add Student
                case 4:

                    try {

                        System.out.print("Enter ID: ");
                        int id = Integer.parseInt(
                                scanner.nextLine()
                        );

                        System.out.print("Enter Name: ");
                        String newName =
                                scanner.nextLine();

                        System.out.print(
                                "Enter Department: "
                        );

                        String department =
                                scanner.nextLine();

                        System.out.print("Enter Mark: ");

                        double mark = Double.parseDouble(
                                scanner.nextLine()
                        );

                        System.out.print("Enter Email: ");

                        String email =
                                scanner.nextLine();

                        Student newStudent =
                                new Student(
                                        id,
                                        newName,
                                        department,
                                        mark,
                                        email
                                );

                        service.addStudent(newStudent);

                        System.out.println(
                                "Student added successfully!"
                        );

                    } catch (NumberFormatException e) {

                        System.out.println(
                                "Invalid input."
                        );
                    }

                    break;

                // Passed Students
                case 5:

                    System.out.println(
                            "\nPASSED STUDENTS"
                    );

                    printHeader();

                    service.getPassedStudents()
                            .forEach(student ->
                                    System.out.println(student));

                    break;

                // Department
                case 6:

                    System.out.print(
                            "Enter Department: "
                    );

                    String dept =
                            scanner.nextLine();

                    List<Student> deptStudents =
                            service.getByDepartment(dept);

                    if (deptStudents.isEmpty()) {

                        System.out.println(
                                "No students found."
                        );

                    } else {

                        printHeader();

                        deptStudents.forEach(student ->
                                System.out.println(student));
                    }

                    break;

                // Top Student
                case 7:

                    System.out.println(
                            "\nTOP PERFORMER"
                    );

                    Optional<Student> top =
                            service.getTopStudent();

                    top.ifPresent(student -> {

                        printHeader();
                        System.out.println(student);

                    });

                    break;

                // Average
                case 8:

                    System.out.printf(
                            "\nAverage Mark: %.2f%n",
                            service.getAverageMark()
                    );

                    break;

                // Statistics
                case 9:

                    System.out.println(
                            "\nSTUDENT STATISTICS"
                    );

                    System.out.println(
                            "Total Students : " +
                            service.getAllStudents().size()
                    );

                    System.out.printf(
                            "Average Mark   : %.2f%n",
                            service.getAverageMark()
                    );

                    System.out.println(
                            "Passed Students: " +
                            service.getPassedStudents().size()
                    );

                    service.getTopStudent()
                            .ifPresent(student ->
                                    System.out.println(
                                            "Top Student    : " +
                                            student.getFormattedName()
                                    ));

                    break;

                // Functional Interface + Lambda
                case 10:

                    System.out.println(
                            "\nFUNCTIONAL INTERFACE + LAMBDA"
                    );

                    StudentOperation operation =
                            student -> System.out.println(
                                    student.getFormattedName() +
                                    " -> " +
                                    student.getGrade()
                            );

                    service.getAllStudents()
                            .forEach(operation::execute);

                    break;

                // Predicate + Lambda
                case 11:

                    System.out.println(
                            "\nPREDICATE + LAMBDA"
                    );

                    Predicate<Student> distinction =
                            student -> student.getMark() >= 75;

                    service.getAllStudents()
                            .stream()
                            .filter(distinction)
                            .forEach(student ->
                                    System.out.println(
                                            student.getFormattedName() +
                                            " -> DISTINCTION"
                                    ));

                    break;

                // Exit
                case 0:

                    System.out.println(
                            "\nThank you for using the system!"
                    );

                    scanner.close();

                    return;

                default:

                    System.out.println(
                            "Invalid choice."
                    );
            }
        }
    }
}