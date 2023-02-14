package tracker;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.Comparator;

public class Main {

    static final String[] MESSAGES = {
            "Learning Progress Tracker",
            "No input",
            "Error: unknown command!",
            "Bye!",
            "Enter student credentials or 'back' to return:",
            "Incorrect credentials.",
            "Enter 'exit' to exit the program.",
            "The student has been added.",
            "No students found",
            "Enter an id and points or 'back' to return",
            "Enter an id or 'back' to return",
            "Type the name of a course to see details or 'back' to quit",
            "Unknown course."
    };

    private static final String[] COMMANDS = {
            "exit",
            "back",
            "add students",
            "list"
    };

    private static final String[] COURSE_STAT_INFO_HEADERS = {
        "Most popular: ",
        "Least popular: ",
        "Highest activity: ",
        "Lowest activity: ",
        "Easiest course: ",
        "Hardest course: "
    };

    private static final byte START_LEVEL = 0;
    private static final byte ADD_LEVEL = 1;
    private static final byte ADD_POINTS_LEVEL = 2;
    private static final byte FIND_LEVEL = 3;
    private static final byte STAT_LEVEL = 4;

    static ArrayList<Course> courses = new ArrayList<>();
    static Course java = new Course("Java");
    static Course DSA = new Course("DSA");
    static Course databases = new Course("Databases");
    static Course spring = new Course("Spring");


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        StringBuilder userInput = new StringBuilder();
        byte menuLevel;

        System.out.println(MESSAGES[0]);

        setMenuLevel(START_LEVEL);
        courses.add(java);
        courses.add(DSA);
        courses.add(databases);
        courses.add(spring);


        do {
            userInput.setLength(0);
            userInput.append(scanner.nextLine());
            if (userInput.toString().isBlank()) {userInput.setLength(0);}

            switch (userInput.toString()) {
                case "" -> System.out.println(MESSAGES[1]);
                case "add points" -> {
                    addPointsCommand(scanner);
                    userInput.setLength(0);
                }
                case "add students" -> {
                    addCommand(scanner);
                    userInput.setLength(0);
                }
                case "back" -> backCommand(START_LEVEL);
                case "exit" -> System.out.println(MESSAGES[3]);
                case "find" -> {
                    findCommand(scanner);
                    userInput.setLength(0);
                }
                case "list" -> listCommand();
                case "notify" -> notifyCommand();
                case "statistics" -> {
                    statCommand(scanner);
                    userInput.setLength(0);
                }
                default -> System.out.println(MESSAGES[2]);
            }


        } while (!userInput.toString().equals(COMMANDS[0]));

    }

    private static void notifyCommand() {
        Student.checkCoursesCompletions();
    }

    private static void statCommand(Scanner scanner) {
        System.out.println(MESSAGES[11]);
        setMenuLevel(STAT_LEVEL);
        for (Course course : courses) {
            course.resetEnrollmentCounter();
        }
        Student.countCoursesEnrollments();
        System.out.println(COURSE_STAT_INFO_HEADERS[0] + Student.findMostPopularCourse(courses));
        System.out.println(COURSE_STAT_INFO_HEADERS[1] + Student.findLeastPopularCourse(courses));
        System.out.println(COURSE_STAT_INFO_HEADERS[2] + Student.findHighestActivityCourse(courses));
        System.out.println(COURSE_STAT_INFO_HEADERS[3] + Student.findLowestActivityCourse(courses));
        System.out.println(COURSE_STAT_INFO_HEADERS[4] + Student.findEasiestCourse(courses));
        System.out.println(COURSE_STAT_INFO_HEADERS[5] + Student.findHardestCourse(courses));
        StringBuilder userAddInput = new StringBuilder();
        do {
            userAddInput.setLength(0);
            userAddInput.append(scanner.nextLine());
            if (userAddInput.toString().isBlank()) {userAddInput.setLength(0);}

            switch (userAddInput.toString().toLowerCase()) {
                case "back" -> backCommand(FIND_LEVEL);
                case "java" -> Student.listStudentRatingForCourse(java);
                case "dsa" -> Student.listStudentRatingForCourse(DSA);
                case "databases" -> Student.listStudentRatingForCourse(databases);
                case "spring" -> Student.listStudentRatingForCourse(spring);
                default -> System.out.println(MESSAGES[12]);
            }

        } while (!userAddInput.toString().equals(COMMANDS[1]));
    }

    private static byte setMenuLevel(byte level) {
        return level;
    }

    private static void findCommand(Scanner scanner) {
        System.out.println(MESSAGES[10]);
        setMenuLevel(FIND_LEVEL);
        StringBuilder userAddInput = new StringBuilder();
        do {
            userAddInput.setLength(0);
            userAddInput.append(scanner.nextLine());
            if (userAddInput.toString().isBlank()) {userAddInput.setLength(0);}

            switch (userAddInput.toString()) {
                case "back" -> backCommand(FIND_LEVEL);
                default -> Student.findStudentAndPrintPoints(userAddInput.toString());
            }

        } while (!userAddInput.toString().equals(COMMANDS[1]));
    }

    private static void addPointsCommand(Scanner scanner) {
        System.out.println(MESSAGES[9]);
        setMenuLevel(ADD_POINTS_LEVEL);
        StringBuilder userAddInput = new StringBuilder();
        do {
            userAddInput.setLength(0);
            userAddInput.append(scanner.nextLine());
            if (userAddInput.toString().isBlank()) {userAddInput.setLength(0);}

            switch (userAddInput.toString()) {
                case "back" -> backCommand(ADD_POINTS_LEVEL);
                default -> Student.addPoints(userAddInput.toString());
            }

        } while (!userAddInput.toString().equals(COMMANDS[1]));
    }

    private static void listCommand() {
        if (Student.getAddedStudentsCounter() == 0) {
            System.out.println(MESSAGES[8]);
        } else {
            System.out.println("Students:");
            for (int i = 1; i < Student.getAddedStudentsCounter() + 1; i++ ) {
                System.out.println(i);
            }
        }
    }

    private static void backCommand(byte menuLevel) {
        if (menuLevel == ADD_LEVEL) {
            System.out.println("Total " + Student.getAddedStudentsCounter() + " students have been added.");
            setMenuLevel(START_LEVEL);
        } else if (menuLevel == ADD_POINTS_LEVEL) {
            setMenuLevel(START_LEVEL);
        } else if (menuLevel == FIND_LEVEL) {
            setMenuLevel(START_LEVEL);
        } else if (menuLevel == STAT_LEVEL) {
            setMenuLevel(START_LEVEL);
        } else {
            //Start level
            System.out.println(MESSAGES[6]);
        }
    }

    private static void addCommand(Scanner scanner) {
        System.out.println(MESSAGES[4]);
        setMenuLevel(ADD_LEVEL);
        StringBuilder userAddInput = new StringBuilder();
        do {
            userAddInput.setLength(0);
            userAddInput.append(scanner.nextLine());
            if (userAddInput.toString().isBlank()) {userAddInput.setLength(0);}

            switch (userAddInput.toString()) {
                case "back" -> backCommand(ADD_LEVEL);
                default -> Student.createStudent(userAddInput.toString());
            }

        } while (!userAddInput.toString().equals(COMMANDS[1]));

    }
}

class Student {

    private static final HashSet<String> EMAILS = new HashSet<>();
    private static final HashMap<Integer, Student> ID_STUDENT_MAP = new HashMap<>();
    private static final ArrayList<Student> STUDENTS_LIST = new ArrayList<>();

    private static final String[] STUDENT_MESSAGES = {
            "Incorrect first name",
            "Incorrect last name",
            "Incorrect email",
            "This email is already taken.",
            "Incorrect points format.",
            "Points updated",
            "id    points    completed"
    };

    private static int addedStudentsCounter = 0;
    private final String firstName;
    private final String lastName;
    private final String email;
    private int pointsJava = 0;
    private boolean isJavaCompleted = false;
    private int pointsDSA = 0;
    private boolean isDSACompleted = false;
    private int pointsDataBases = 0;
    private boolean isDataBasesCompleted = false;
    private int pointsSpring = 0;
    private boolean isSpringCompleted = false;
    private final int id;

    private static final String patternName = "[A-Za-z]+((([-'])[A-Za-z][a-z]*)+|[A-Za-z]+)";
    private static final String patternEmail = "\\w+[\\.\\-_\\w]*@\\w+\\.\\w+";

    Student(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        addedStudentsCounter++;
        this.id = addedStudentsCounter;
        System.out.println(Main.MESSAGES[7]);
    }

    static void createStudent(String inputString) {
        String[] words = inputString.split(" ");
        int wordsNumber = words.length;
        if (wordsNumber < 3) {
            System.out.println(Main.MESSAGES[5]);
        } else {
            String firstName = words[0];
            String email = words[wordsNumber - 1];
            StringBuilder lastName = new StringBuilder(words[1]);
            for (int i = 2; i < wordsNumber - 1; i++) {
                lastName.append(' ').append(words[i]);
            }
            if (checkFirstName(firstName) && checkLastName(lastName, wordsNumber - 2) && checkEmail(email)) {
                Student newStudent = new Student(firstName, lastName.toString(), email);
                STUDENTS_LIST.add(newStudent);
                EMAILS.add(email);
                ID_STUDENT_MAP.put(newStudent.getId(), newStudent);
            }
        }
    }

    private static boolean checkEmail(String email) {
        boolean result = email.matches(patternEmail);
        if (!result) {
            System.out.println(STUDENT_MESSAGES[2]);
        } else if (Student.EMAILS.contains(email)) {
            result = false;
            System.out.println(STUDENT_MESSAGES[3]);
        }
        return result;
    }

    private static boolean checkLastName(StringBuilder lastName, int wordsNumber) {
        //System.out.println(lastName);
        boolean result = true;
        if (wordsNumber == 1) {
            result = lastName.toString().matches(patternName);
        } else {
            String[] lastNameWords = lastName.toString().split(" ");
            if (lastNameWords[0].matches(patternName)) {
                for (int i = 1; i < wordsNumber; i++) {
                    if (!lastNameWords[i].matches(patternName)) {
                        result = false;
                        break;
                    }
                }
            } else {
                result = false;
            }
        }
        if (!result) {
            System.out.println(STUDENT_MESSAGES[1]);
        }
        return result;
    }

    protected static boolean checkFirstName(String firstName) {
        boolean result = firstName.matches(patternName);
        if (!result) {
            System.out.println(STUDENT_MESSAGES[0]);
        }
        return result;
    }

    public static int getAddedStudentsCounter() {
        return addedStudentsCounter;
    }

    public static void addPoints(String toString) {
        String[] input = toString.split(" ");
        if (input[0].matches("[1-9][0-9]*") && Student.ID_STUDENT_MAP.containsKey(Integer.parseInt(input[0]))) {
            if (checkPointsFormat(input)) {
                Student studentToUpdate = ID_STUDENT_MAP.get(Integer.parseInt(input[0]));
                if (Integer.parseInt(input[1]) > 0) {
                    studentToUpdate.setPointsJava(Integer.parseInt(input[1]));
                    Main.java.addGradeSum(Integer.parseInt(input[1]));
                    Main.java.increaseSubmissions();
                }
                if (Integer.parseInt(input[2]) > 0) {
                    studentToUpdate.setPointsDSA(Integer.parseInt(input[2]));
                    Main.DSA.addGradeSum(Integer.parseInt(input[2]));
                    Main.DSA.increaseSubmissions();
                }
                if (Integer.parseInt(input[3]) > 0) {
                    studentToUpdate.setPointsDataBases(Integer.parseInt(input[3]));
                    Main.databases.addGradeSum(Integer.parseInt(input[3]));
                    Main.databases.increaseSubmissions();
                }
                if (Integer.parseInt(input[4]) > 0) {
                    studentToUpdate.setPointsSpring(Integer.parseInt(input[4]));
                    Main.spring.addGradeSum(Integer.parseInt(input[4]));
                    Main.spring.increaseSubmissions();
                }
                System.out.println(STUDENT_MESSAGES[5]);
            }
        } else {
            System.out.printf("No student is found for id=%s\n", input[0]);
        }
    }

    private static boolean checkPointsFormat(String[] input) {
        boolean result;
        if (input.length != 5) {
            System.out.println(Student.STUDENT_MESSAGES[4]);
            result = false;
        } else {
            result = true;
            for (int i = 1; i < 5; i++) {
                if (!input[i].matches("[0-9]+")) {
                    System.out.println(Student.STUDENT_MESSAGES[4]);
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    public static void findStudentAndPrintPoints(String toString) {
        int idToFind = Integer.parseInt(toString);
        if (Student.ID_STUDENT_MAP.containsKey(idToFind)) {
            Student s = ID_STUDENT_MAP.get(idToFind);
            System.out.printf("%d points: Java=%d; DSA=%d; Databases=%d; Spring=%d\n", idToFind, s.getPointsJava(), s.getPointsDSA(), s.getPointsDataBases(), s.getPointsSpring());
        } else {
            System.out.printf("No student is found for id=%s\n", toString);
        }
    }

    public static String findMostPopularCourse(ArrayList<Course> courses) {
        StringBuilder toReturn = new StringBuilder();
        courses.sort(Comparator.comparingInt(Course::getEnrollments).reversed());
        //System.out.println(courses);
        if (courses.get(0).getEnrollments() > 0) {
            toReturn.append(courses.get(0).toString());
            for (int i = 1; i < 4; i++) {
                if (courses.get(i).getEnrollments() == courses.get(0).getEnrollments()) {
                    toReturn.append(", ").append(courses.get(i).toString());
                } else {
                    break;
                }
            }
        } else {
            toReturn.append("n/a");
        }
        return toReturn.toString();
    }

    public static String findLeastPopularCourse(ArrayList<Course> courses) {
        StringBuilder toReturn = new StringBuilder();
        //System.out.println(courses);
        if ("n/a".equals(findMostPopularCourse(courses))) {
            toReturn.append("n/a");
        } else if (courses.get(0).getEnrollments() == courses.get(3).getEnrollments()) {
            toReturn.append("n/a");
        } else {
            courses.sort(Comparator.comparingInt(Course::getEnrollments));
            toReturn.append(courses.get(0).toString());
            for (int i = 1; i < 4; i++) {
                if (courses.get(i).getEnrollments() == courses.get(0).getEnrollments()) {
                    toReturn.append(", ").append(courses.get(i).toString());
                } else {break;}
            }
        }
        return toReturn.toString();
    }

    public static String findHighestActivityCourse(ArrayList<Course> courses) {
        StringBuilder toReturn = new StringBuilder();
        courses.sort(Comparator.comparingInt(Course::getSubmissions).reversed());
        if (courses.get(0).getSubmissions() > 0) {
            toReturn.append(courses.get(0).toString());
            for (int i = 1; i < 4; i++) {
                if (courses.get(i).getSubmissions() == courses.get(0).getSubmissions()) {
                    toReturn.append(", ").append(courses.get(i).toString());
                } else {
                    break;
                }
            }
        } else {
            toReturn.append("n/a");
        }
        return toReturn.toString();
    }

    public static String findLowestActivityCourse(ArrayList<Course> courses) {
        StringBuilder toReturn = new StringBuilder();
        if ("n/a".equals(findHighestActivityCourse(courses))) {
            toReturn.append("n/a");
        } else if (courses.get(0).getSubmissions() == courses.get(3).getSubmissions()) {
            toReturn.append("n/a");
        } else {
            courses.sort(Comparator.comparingInt(Course::getSubmissions));
            toReturn.append(courses.get(0).toString());
            for (int i = 1; i < 4; i++) {
                if (courses.get(i).getSubmissions() == courses.get(0).getSubmissions()) {
                    toReturn.append(", ").append(courses.get(i).toString());
                } else {break;}
            }
        }
        return toReturn.toString();
    }

    public static String findHardestCourse(ArrayList<Course> courses) {
        StringBuilder toReturn = new StringBuilder();
        courses.sort(Comparator.comparingDouble(Course::getAverageGradePerAssignment));
        if (courses.get(0).getAverageGradePerAssignment() > 0) {
            toReturn.append(courses.get(0).toString());
            for (int i = 1; i < 4; i++) {
                if (courses.get(i).getAverageGradePerAssignment() == courses.get(0).getAverageGradePerAssignment()) {
                    toReturn.append(", ").append(courses.get(i).toString());
                } else {
                    break;
                }
            }
        } else {
            toReturn.append("n/a");
        }
        return toReturn.toString();
    }

    public static String findEasiestCourse(ArrayList<Course> courses) {
        StringBuilder toReturn = new StringBuilder();
        if ("n/a".equals(findHardestCourse(courses))) {
            toReturn.append("n/a");
        } else if (courses.get(0).getAverageGradePerAssignment() == courses.get(3).getAverageGradePerAssignment()) {
            toReturn.append("n/a");
        } else {
            courses.sort(Comparator.comparingDouble(Course::getAverageGradePerAssignment).reversed());
            toReturn.append(courses.get(0).toString());
            for (int i = 1; i < 4; i++) {
                if (courses.get(i).getAverageGradePerAssignment() == courses.get(0).getAverageGradePerAssignment()) {
                    toReturn.append(", ").append(courses.get(i).toString());
                } else {break;}
            }
        }
        return toReturn.toString();
    }

    public static void countCoursesEnrollments() {
        for (Student student : STUDENTS_LIST) {
            if (student.getPointsJava() > 0) {
                Main.java.increaseEnrollments();
            }
            if (student.getPointsDSA() > 0) {
                Main.DSA.increaseEnrollments();
            }
            if (student.getPointsDataBases() > 0) {
                Main.databases.increaseEnrollments();
            }
            if (student.getPointsSpring() > 0) {
                Main.spring.increaseEnrollments();
            }
        }
    }

    public static void listStudentRatingForCourse(Course course) {
        System.out.println(course.toString());
        System.out.println(STUDENT_MESSAGES[6]);
        switch (course.toString().toLowerCase()) {
            case "java" -> {
                STUDENTS_LIST.sort(Comparator.comparingInt(Student::getPointsJava).reversed().thenComparing(Student::getId));
                for (Student student : STUDENTS_LIST) {
                    student.printRating(course);
                }
            }
            case "dsa" -> {
                STUDENTS_LIST.sort(Comparator.comparingInt(Student::getPointsDSA).reversed().thenComparing(Student::getId));
                for (Student student : STUDENTS_LIST) {
                    student.printRating(course);
                }
            }
            case "databases" -> {
                STUDENTS_LIST.sort(Comparator.comparingInt(Student::getPointsDataBases).reversed().thenComparing(Student::getId));
                for (Student student : STUDENTS_LIST) {
                    student.printRating(course);
                }
            }
            case "spring" -> {
                STUDENTS_LIST.sort(Comparator.comparingInt(Student::getPointsSpring).reversed().thenComparing(Student::getId));
                for (Student student : STUDENTS_LIST) {
                    student.printRating(course);
                }
            }
        }
    }

    public static void checkCoursesCompletions() {
        int studentsNotifiedCounter = 0;
        boolean thisStudentIsNotified;
        for (Student student : STUDENTS_LIST) {
            thisStudentIsNotified = false;
            if (!student.isJavaCompleted() && student.getPointsJava() >= Course.POINTS_TO_COMPLETE_JAVA) {
                student.setJavaCompleted(true);
                student.printNotification(Main.java);
                thisStudentIsNotified = true;
            }
            if (!student.isDSACompleted() && student.getPointsDSA() >= Course.POINTS_TO_COMPLETE_DSA) {
                student.setDSACompleted(true);
                student.printNotification(Main.DSA);
                thisStudentIsNotified = true;
            }
            if (!student.isDataBasesCompleted() && student.getPointsDataBases() >= Course.POINTS_TO_COMPLETE_DATABASE) {
                student.setDataBasesCompleted(true);
                student.printNotification(Main.databases);
                thisStudentIsNotified = true;
            }
            if (!student.isSpringCompleted() && student.getPointsSpring() >= Course.POINTS_TO_COMPLETE_SPRING) {
                student.setSpringCompleted(true);
                student.printNotification(Main.spring);
                thisStudentIsNotified = true;
            }
            if (thisStudentIsNotified) studentsNotifiedCounter++;
        }
        System.out.printf("Total %d students have been notified.\n", studentsNotifiedCounter);
    }

    private void printNotification(Course course) {
        System.out.println("To: " + getEmail());
        System.out.println("Re: Your Learning Progress");
        System.out.printf("Hello, %s %s! You have accomplished our %s course!\n", getFirstName(), getLastName(), course.toString());
    }

    private void printRating(Course course) {

        switch (course.toString().toLowerCase()) {
            case "java" -> {
                int point = this.getPointsJava();
                if (point== 0) break;
                System.out.print(this.getId() + " ");
                System.out.print(point + " ");
                BigDecimal percentsOfCourse = new BigDecimal((double) 100 * point / Course.POINTS_TO_COMPLETE_JAVA);
                percentsOfCourse = percentsOfCourse.setScale(1, RoundingMode.HALF_UP);
                System.out.print(percentsOfCourse + "%\n");
            }
            case "dsa" -> {
                int point = this.getPointsDSA();
                if (point== 0) break;
                System.out.print(this.getId() + " ");
                System.out.print(point + " ");
                BigDecimal percentsOfCourse = new BigDecimal((double) 100* point / Course.POINTS_TO_COMPLETE_DSA);
                percentsOfCourse = percentsOfCourse.setScale(1, RoundingMode.HALF_UP);
                System.out.print(percentsOfCourse + "%\n");
            }
            case "databases" -> {
                int point = this.getPointsDataBases();
                if (point== 0) break;
                System.out.print(this.getId() + " ");
                System.out.print(point + " ");
                BigDecimal percentsOfCourse = new BigDecimal((double) 100* point / Course.POINTS_TO_COMPLETE_DATABASE);
                percentsOfCourse = percentsOfCourse.setScale(1, RoundingMode.HALF_UP);
                System.out.print(percentsOfCourse + "%\n");
            }
            case "spring" -> {
                int point = this.getPointsSpring();
                if (point== 0) break;
                System.out.print(this.getId() + " ");
                System.out.print(point + " ");
                BigDecimal percentsOfCourse = new BigDecimal((double) 100* point / Course.POINTS_TO_COMPLETE_SPRING);
                percentsOfCourse = percentsOfCourse.setScale(1, RoundingMode.HALF_UP);
                System.out.print(percentsOfCourse + "%\n");
            }
        }
    }

    public int getId() {
        return this.id;
    }

    public void setPointsJava(int pointsJava) {
        this.pointsJava += pointsJava;
    }

    public void setPointsDSA(int pointsDSA) {
        this.pointsDSA += pointsDSA;
    }

    public void setPointsDataBases(int pointsDataBases) {
        this.pointsDataBases += pointsDataBases;
    }

    public void setPointsSpring(int pointsSpring) {
        this.pointsSpring += pointsSpring;
    }

    public int getPointsJava() {
        return pointsJava;
    }

    public int getPointsDSA() {
        return pointsDSA;
    }

    public int getPointsDataBases() {
        return pointsDataBases;
    }

    public int getPointsSpring() {
        return pointsSpring;
    }

    public boolean isJavaCompleted() {
        return isJavaCompleted;
    }

    public void setJavaCompleted(boolean javaCompleted) {
        isJavaCompleted = javaCompleted;
    }

    public boolean isDSACompleted() {
        return isDSACompleted;
    }

    public void setDSACompleted(boolean DSACompleted) {
        isDSACompleted = DSACompleted;
    }

    public boolean isDataBasesCompleted() {
        return isDataBasesCompleted;
    }

    public void setDataBasesCompleted(boolean dataBasesCompleted) {
        isDataBasesCompleted = dataBasesCompleted;
    }

    public boolean isSpringCompleted() {
        return isSpringCompleted;
    }

    public void setSpringCompleted(boolean springCompleted) {
        isSpringCompleted = springCompleted;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}

class Course {
    private int enrollments;
    private int submissions;
    private long totalGradeSum;
    private double averageGradePerAssignment;
    private final String name;
    public static final int POINTS_TO_COMPLETE_JAVA = 600;
    public static final int POINTS_TO_COMPLETE_DSA = 400;
    public static final int POINTS_TO_COMPLETE_DATABASE = 480;
    public static final int POINTS_TO_COMPLETE_SPRING = 550;



    Course(String name) {
        this.enrollments = 0;
        this.submissions = 0;
        this.averageGradePerAssignment = 0;
        this.totalGradeSum = 0;
        this.name = name;
    }

    public void increaseEnrollments() {
        this.enrollments++;
    }

    public void increaseSubmissions() {
        this.submissions++;
    }

    public void addGradeSum(int grade) {
        this.totalGradeSum += grade;
    }

    public void resetEnrollmentCounter() {
        this.enrollments = 0;
    }

    public int getEnrollments() {
        return this.enrollments;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public int getSubmissions() {
        return this.submissions;
    }

    public double getAverageGradePerAssignment() {
        if (this.submissions != 0) {
            this.averageGradePerAssignment = (double) this.totalGradeSum / (double) this.submissions;
        } else {
            this.averageGradePerAssignment = 0;
        }
        return this.averageGradePerAssignment;
    }

}
