package ${package};

import java.util.Iterator;

import eu.stratosphere.api.common.Plan;
import eu.stratosphere.api.common.Program;
import eu.stratosphere.api.common.operators.FileDataSink;
import eu.stratosphere.api.common.operators.FileDataSource;
import eu.stratosphere.api.java.record.functions.MapFunction;
import eu.stratosphere.api.java.record.functions.ReduceFunction;
import eu.stratosphere.api.java.record.io.CsvOutputFormat;
import eu.stratosphere.api.java.record.io.TextInputFormat;
import eu.stratosphere.api.java.record.operators.MapOperator;
import eu.stratosphere.api.java.record.operators.ReduceOperator;
import eu.stratosphere.client.LocalExecutor;
import eu.stratosphere.nephele.client.JobExecutionResult;
import eu.stratosphere.types.IntValue;
import eu.stratosphere.types.Record;
import eu.stratosphere.types.StringValue;
import eu.stratosphere.util.Collector;


/**
 * A sample word count Stratosphere job.
 * <p>
 * You can run this sample from your IDE using the main() method. The LocalExecutor starts a Stratosphere instance out
 * of your IDE.
 * <p>
 * The two inner classes SplitWords and CountWords provide sample user logic to count the words. Please check the
 * respective comments for details.
 */
public class WordCountJob implements Program {

	// -- SAMPLE OPERATORS with word counting logic ---------------------------

	public static class SplitWords extends MapFunction {

		// resusable mutable objects
		private final Record output = new Record();

		private final StringValue word = new StringValue();

		private final IntValue one = new IntValue(1);

		/**
		 * Splits every line by whitespace and emits a (word, 1) record for
		 * each word.
		 */
		@Override
		public void map(Record record, Collector<Record> collector) {
			// read the first field of the record
			// note: Record field indexes start with 0
			StringValue line = record.getField(0, StringValue.class);

			// split every line by no word characters
			for (String currentWord : line.getValue().toLowerCase().split("\\W+")) {
				
				// skip zero-length words
				if(currentWord.length() <= 0) continue;
				
				// output: (word, 1) record
				this.word.setValue(currentWord);

				this.output.setField(0, this.word);
				this.output.setField(1, this.one);

				collector.collect(this.output);
			}
		}
	}

	public static class CountWords extends ReduceFunction {

		private final IntValue count = new IntValue();

		/**
		 * Counts the ones for each word and emits a (word, sum) record for
		 * each word.
		 */
		@Override
		public void reduce(Iterator<Record> records, Collector<Record> out) throws Exception {
			Record current = null;

			int sum = 0;
			while (records.hasNext()) {
				current = records.next();
				sum += current.getField(1, IntValue.class).getValue();
			}

			// output: (word, sum) record
			// note: the current record has the word already as first field (index 0).
			// therefore we only set the second field (index 1) to the sum.
			this.count.setValue(sum);
			current.setField(1, this.count);

			out.collect(current);
		}

	}

	// -- SAMPLE PLAN which connects the operators ----------------------------

	/**
	 * Connects the operators and returns the resulting plan.
	 * 
	 * <pre>
	 * +------------+    +-------------+    +----------------+    +----------+
	 * |   Source   | => | MapContract | => | ReduceContract | => |   Sink   |
	 * +------------+    +-------------+    +----------------+    +----------+
	 *  foo bar foo       (foo, 1)           (foo, 2)              foo, 2
	 *                    (bar, 1)           (bar, 1)              bar, 1
	 *                    (foo, 1)
	 * </pre>
	 * @return Plan object describing the Stratosphere Job.
	 */
	public Plan getPlan(String... args) {
		String inputPath = (args.length >= 1 ? args[0] : "");
		String outputPath = (args.length >= 2 ? args[1] : "");
		int parallelism = (args.length >= 3 ? Integer.parseInt(args[2]) : 1);

		// input: treat input as text with TextInputFormat
		FileDataSource source = new FileDataSource(TextInputFormat.class, inputPath, "input: lines");

		// build map contract with SplitWords class
		MapOperator words = MapOperator.builder(SplitWords.class)
			.input(source) // file source as input to the mapper
			.name("tokenize lines")
			.build();

		// build reduce contract with CountWords class
		ReduceOperator counts = ReduceOperator.builder(CountWords.class)
			.input(words) // map output as input to the reduce
			.keyField(StringValue.class, 0)
			.name("count words")
			.build();
		
		// output: write every record from the reduce as output
		FileDataSink sink = new FileDataSink(CsvOutputFormat.class, outputPath, counts, "output: word counts");
		
		// configure the record output format (at least one field needs to be specified) 
		CsvOutputFormat.configureRecordFormat(sink)
			.recordDelimiter('\n')
			.fieldDelimiter(' ')
			// the following lines configure the fields to write (in given order)
			// e.g. word (field 0), count (field 1)
			.field(StringValue.class, 0)        // <--+ swap lines 
			.field(IntValue.class, 1);      // <--+    if you want (count, word) instead

		Plan plan = new Plan(sink, "WordCount Sample Job");
		plan.setDefaultParallelism(parallelism);

		return plan;
	}

	public String getDescription() {
		return "Usage: [input] [output]";
	}

	// -- RUNNING IN LOCAL MODE -----------------------------------------------

	public static void main(String[] args) throws Exception {
		WordCountJob tut = new WordCountJob();
		String inputPath = "file:///path/to/input";
		String outputPath = "file:///path/to/output";
		String parallelism = "2";

		Plan toExecute = tut.getPlan(inputPath, outputPath, parallelism);
		// alternatively: Plan toExecute = tut.getPlan(args); 
		
		JobExecutionResult result = LocalExecutor.execute(toExecute);
		System.out.println("runtime:  " + result.getNetRuntime());
		System.exit(0);
	}
}