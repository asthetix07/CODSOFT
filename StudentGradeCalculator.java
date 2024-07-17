import java.util.Scanner;

public class StudentGradeCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ask for the total number of subjects
        System.out.print("Enter the total number of subjects: ");
        int numSubjects = scanner.nextInt();

        // Array to hold marks for each subject
        int[] marks = new int[numSubjects];
        int totalMarks = 0;

        // Take marks for each subject
        for (int i = 0; i < numSubjects; i++) {
            System.out.print("Enter marks obtained in subject " + (i + 1) + " (out of 100): ");
            marks[i] = scanner.nextInt();
            totalMarks += marks[i];
        }

        // Calculate average percentage
        double averagePercentage = (double) totalMarks / numSubjects;

        // Determine the grade
        String grade;
        if (averagePercentage >= 95) {
            grade = "A+";
        } else if (averagePercentage >= 85) {
            grade = "A";
        } else if (averagePercentage >= 75) {
            grade = "B+";
        } else if (averagePercentage >= 65) {
            grade = "B";
        } else if (averagePercentage >= 55) {
            grade = "C+";
        } else if (averagePercentage >= 45) {
            grade = "C";
        } else if (averagePercentage >= 35) {
            grade = "D";
        } else {
            grade = "F";
        }

        // Display results
        System.out.println("\nResults:");
        System.out.println("Total Marks: " + totalMarks);
        System.out.println("Average Percentage: " + averagePercentage + "%");
        System.out.println("Grade: " + grade);

        // Close the scanner
        scanner.close();
    }
}
