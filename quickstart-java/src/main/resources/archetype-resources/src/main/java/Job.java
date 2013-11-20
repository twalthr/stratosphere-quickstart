package ${package};

import eu.stratosphere.pact.client.LocalExecutor;
import eu.stratosphere.pact.common.contract.FileDataSink;
import eu.stratosphere.pact.common.contract.GenericDataSink;
import eu.stratosphere.pact.common.io.DelimitedOutputFormat;
import eu.stratosphere.pact.common.plan.Plan;
import eu.stratosphere.pact.common.plan.PlanAssembler;
import eu.stratosphere.pact.common.plan.PlanAssemblerDescription;


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
public class Job implements PlanAssembler, PlanAssemblerDescription {

    public Plan getPlan(String... args) {
    	/**
    	 * Here, you can start creating your execution plan for stratosphere.
    	 * 
    	 * The Wordcount example in "pact-examples" shows you how a very basic job is implemented.
    	 * 
    	 * You could also start with something different.
    	 * Create a FileDataSource first.
    	 * Give it a Input Format.
    	 * Create a PACT Contract (Join, Match, Reduce, Cross, ..) and connect its input
    	 * with the FileDataSource.
    	 * Connect the output of your PACT Contract with the FileDataSink.
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

        long runtime = LocalExecutor.execute(toExecute);
        System.out.println("runtime:  " + runtime);
        System.exit(0);
    }
}