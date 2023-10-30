import java.math.RoundingMode;
import java.util.*;
import java.text.DecimalFormat;

public class ScoreProcessing {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // Create data structures to store student info and course scores
        TreeMap<String, String> studentNames = new TreeMap<>(); // Used for Format: <student id>, <name>
        Map<String, Map<String, Double>> studentScores = new HashMap<>(); // Used for Format: <student id>, <course name>, <marks>
        Set<String> courseNames = new TreeSet<>(); // Used for the headline

        // Read input lines, store in String form
        while (in.hasNextLine()) {
            String line = in.nextLine().trim();
            if (line.equals("END")) {
                break;
            }

            // Split the line using a comma as the separator
            String[] info = line.split(", ");
            if (info.length == 2) {
                // Format: <student id>, <name>
                String studentId = info[0];
                String studentName = info[1];
                studentNames.put(studentId, studentName);
            } else if (info.length == 3) {
                // Format: <student id>, <course name>, <marks>
                String studentId = info[0];
                String courseName = info[1];
                double marks = Double.parseDouble(info[2]);
                courseNames.add(courseName); // Course name is add to the headline which has been created as a Treeset.
                studentScores.computeIfAbsent(studentId, k -> new HashMap<>()).put(courseName,marks);
            }
        }

        // Print the CSV headline
        System.out.print("student id, name");
        for (String course : courseNames) {
            System.out.print(", " + course);
        }
        System.out.println(", average");

        // Print student data and calculate averages
        DecimalFormat df = new DecimalFormat("0.0");
        // Make sure that the averages can be round up to one decimal place
        df.setRoundingMode(RoundingMode.HALF_UP);

        for (String studentId : studentNames.keySet()) { //Operate the key set which stores studentIDs
            String studentName = studentNames.get(studentId);
            Map<String, Double> scores = studentScores.getOrDefault(studentId, new HashMap<>());
            double average = 0.0;
            int count = 0;
            System.out.print(studentId + ", " + studentName);
            for (String course : courseNames) {
                Double score = scores.get(course);
                if (score != null) {
                    System.out.print(", " + df.format(score));
                    average += score;
                    count++;
                } else {
                    System.out.print(", ");
                }
            }
            if (count > 0) {
                average /= count;
                System.out.print(", " + df.format(average));
            } else {
                System.out.print(", ");
            }
            System.out.println();
        }
    }
}
