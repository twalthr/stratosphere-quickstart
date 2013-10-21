package ${package};

import scala.Array.canBuildFrom
import eu.stratosphere.pact.client.LocalExecutor
import eu.stratosphere.scala.DataSource
import eu.stratosphere.scala.ScalaPlan
import eu.stratosphere.scala.operators.arrayToIterator
import eu.stratosphere.scala.operators.DelimitedDataSourceFormat
import eu.stratosphere.scala.operators.DelimitedDataSinkFormat
import eu.stratosphere.scala.TextFile
import eu.stratosphere.pact.common.plan.PlanAssembler
import eu.stratosphere.pact.common.plan.PlanAssemblerDescription

// You can run this using:
// mvn exec:exec -Dexec.executable="java" -Dexec.args="-cp %classpath ${package}.RunJob 2 file:///home/aljoscha/nohup.out file:///home/aljoscha/wc-out"
object RunJob {
  def main(args: Array[String]) {
    val job = new Job
    if (args.size < 3) {
      println(job.getDescription)
      return
    }
    val plan = job.getScalaPlan(args(0).toInt, args(1), args(2))
    LocalExecutor.execute(plan)
    System.exit(0)
  }
}


/**
 * This is a outline for a Stratosphere scala job. It is actually the WordCount 
 * example from the stratosphere distribution.
 * 
 * You can run it out of your IDE using the main() method of RunJob.
 * This will use the LocalExecutor to start a little Stratosphere instance
 * out of your IDE.
 * 
 * You can also generate a .jar file that you can submit on your Stratosphere
 * cluster.
 * Just type 
 *      mvn clean package
 * in the projects root directory.
 * You will find the jar in 
 *      target/stratosphere-quickstart-0.1-SNAPSHOT-Sample.jar
 *
 */
class Job extends PlanAssembler with PlanAssemblerDescription with Serializable {
  override def getDescription() = {
    "Parameters: [numSubStasks] [input] [output]"
  }
  override def getPlan(args: String*) = {
    getScalaPlan(args(0).toInt, args(1), args(2))
  }

  def formatOutput = (word: String, count: Int) => "%s %d".format(word, count)

  def getScalaPlan(numSubTasks: Int, textInput: String, wordsOutput: String) = {
    val input = TextFile(textInput)

    val words = input flatMap { _.toLowerCase().split("""\W+""") filter { _ != "" } map { (_, 1) } }
    val counts = words groupBy { case (word, _) => word } reduce { (w1, w2) => (w1._1, w1._2 + w2._2) }

    counts neglects { case (word, _) => word }
    counts preserves({ case (word, _) => word }, { case (word, _) => word })
    val output = counts.write(wordsOutput, DelimitedDataSinkFormat(formatOutput.tupled))
  
    val plan = new ScalaPlan(Seq(output), "Word Count (immutable)")
    plan.setDefaultParallelism(numSubTasks)
    plan
  }
}