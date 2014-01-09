package ${package};

import eu.stratosphere.api.common.Plan;
import eu.stratosphere.api.common.Program;
import eu.stratosphere.api.common.operators.FileDataSink;
import eu.stratosphere.api.common.operators.GenericDataSink;
import eu.stratosphere.api.java.record.io.DelimitedOutputFormat;
import eu.stratosphere.client.LocalExecutor;
import eu.stratosphere.nephele.client.JobExecutionResult;


/**
 * This is a outline for a Stratosphere job.
 * 
 * See the comments in getPlan() below on how to start with your job!
 * 
 * You can run it out of your IDE using the main() method.
 * This will use the LocalExecutor to start a little Stratosphere instance
 * out of your IDE.
 * 
 * You can also generate a .jar file that you can submit on your Stratosphere
 * cluster.
 * Just type 
 * 		mvn clean package
 * in the projects root directory.
 * You will find the jar in 
 * 		target/stratosphere-quickstart-0.1-SNAPSHOT-Sample.jar
 *
 */
public class Job implements Program {

    public Plan getPlan(String... args) {
    	/**
    	 * Here, you can start creating your execution plan for stratosphere.
    	 * 
    	 * The Wordcount example in "stratosphere-java-examples" shows you how a very basic job is implemented.
    	 * 
    	 * You could also start with something different.
    	 * Create a FileDataSource first.
    	 * Give it a Input Format.
    	 * Create a  Operator (Join, Match, Reduce, Cross, ..) and connect its input
    	 * with the FileDataSource.
    	 * Connect the output of the Operators with the FileDataSink.
    	 * Connect the FileDataSink with the Plan.
    	 * 
    	 * Run it!
    	 * 
    	 */
        GenericDataSink sink = new FileDataSink(DelimitedOutputFormat.class, "file:///result/path");
		return new Plan(sink, "Stratosphere Quickstart SDK Sample Job");
    }
    
    public String getDescription() {
        return "Usage: ... "; // TODO
    }

    // You can run this using:
    // mvn exec:exec -Dexec.executable="java" -Dexec.args="-cp %classpath ${package}.RunJob <args>"
    public static void main(String[] args) throws Exception {
        Job tut = new Job();
        Plan toExecute = tut.getPlan(args);

        JobExecutionResult result = LocalExecutor.execute(toExecute);
        System.out.println("runtime:  " + result.getNetRuntime());
        System.exit(0);
    }
}