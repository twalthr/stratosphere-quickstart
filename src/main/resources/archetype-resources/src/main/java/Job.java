import eu.stratosphere.pact.client.LocalExecutor;
import eu.stratosphere.pact.common.plan.Plan;
import eu.stratosphere.pact.common.plan.PlanAssembler;
import eu.stratosphere.pact.common.plan.PlanAssemblerDescription;



public class Job implements PlanAssembler, PlanAssemblerDescription {

    public static void execute(Plan toExecute) throws Exception {
        LocalExecutor executor = new LocalExecutor();
        executor.start();
        long runtime = executor.executePlan(toExecute);
        System.out.println("runtime:  " + runtime);
        executor.stop();
    }

    @Override
    public Plan getPlan(String... args) {
        // your Plan goes here
    }

    @Override
    public String getDescription() {
        return "Usage: ... "; // TODO
    }

    public static void main(String[] args) throws Exception {
        Job tut = new Job();
        Plan toExecute = tut.getPlan( /* Arguments */);
        execute(toExecute);
    }
}