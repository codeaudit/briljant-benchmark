package org.briljantframework;

import org.apache.commons.math3.random.UniformRandomGenerator;
import org.apache.commons.math3.random.Well1024a;
import org.briljantframework.data.SortOrder;
import org.briljantframework.data.dataframe.DataFrame;
import org.briljantframework.data.dataframe.MixedDataFrame;
import org.briljantframework.data.vector.Vector;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.concurrent.TimeUnit;

/**
 * @author Isak Karlsson <isak-kar@dsv.su.se>
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class DataFrameSortBenchmark {

  private DataFrame dataFrame;

  @Setup(Level.Iteration)
  public void setupDataFrame() {
    DataFrame.Builder builder = new MixedDataFrame.Builder();
    UniformRandomGenerator gen = new UniformRandomGenerator(new Well1024a());
    for (int i = 0; i < 1000_000; i++) {
      builder.setRecord(String.valueOf(i), Vector.of(gen.nextNormalizedDouble(), i, 20.0, 30.0));
    }
    builder.setColumnIndex("First", "Second", "Third", "Fourth");
    dataFrame = builder.build();
  }

  @Benchmark
  public Object sort() {
    return dataFrame.sortBy(SortOrder.DESC, "First");
  }
}
