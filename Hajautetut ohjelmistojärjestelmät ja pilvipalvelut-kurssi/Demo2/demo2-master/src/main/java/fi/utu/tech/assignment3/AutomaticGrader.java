package fi.utu.tech.assignment3;

import java.util.List;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Automatic Grader thread - grades a list of
 * Submissions and adds them to gradedSubmissions list
 */
public class AutomaticGrader extends Thread {

    private List<Submission> ungradedSubmissions;
    private LinkedBlockingQueue<Submission> gradedSubmissions;
    private Random rnd = new Random();

    /**
     * Initialize AutomaticGrader
     *
     * @param ungradedSubmissions List of ungraded submissions to be graded by this grader
     * @param gradedSubmissions   Reference to a shared list where all the graded submissions will be added
     */
    public AutomaticGrader(List<Submission> ungradedSubmissions, LinkedBlockingQueue<Submission> gradedSubmissions) {
        this.ungradedSubmissions = ungradedSubmissions;
        this.gradedSubmissions = gradedSubmissions;
    }

    /**
     * Start grading
     */
    @Override
    public void run() {
        for (var s : ungradedSubmissions) {
            try {
                gradedSubmissions.put(grade(s));
            } catch (InterruptedException e) {
                System.out.println(e.toString());
            }
        }

    }

    /**
     * Grade a submission
     *
     * @param s Submission to be graded
     * @return The graded submission
     */
    public Submission grade(Submission s) {
        try {
            Thread.sleep(s.getDifficulty());
        } catch (InterruptedException e) {
            System.err.println("Who dared to interrupt my sleep?!");
        }
        return s.grade(rnd.nextInt(6));
    }


}


