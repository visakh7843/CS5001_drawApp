package Tests;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class ModelTestSuiteRunner{
    public static void main(String[] args){
        Result result = JUnitCore.runClasses(Tests.ModelTestSuite.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        if(result.wasSuccessful()) {
            System.out.println("Passed all " + result.getRunCount() +" Model Tests!");
            System.exit(0);
        }
        else {
            System.out.println("Failed " + result.getFailureCount() + " out of " + result.getRunCount() +"Model tests!");
            System.exit(1);
        }

    }
}