package org.briljantframework;

import org.briljantframework.data.resolver.Resolve;
import org.briljantframework.data.vector.Convert;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

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
public class ConvertToBenchmark {

  @Param({"1000000"})
  int invocations;

  @Benchmark
  public void convertToDoublePerformance_IfChain(Blackhole blackhole) {
    for (int i = 0; i < invocations; i++) {
      blackhole.consume(Convert.to(Double.class, i));
    }
  }

  @Benchmark
  public void convertToDoublePerformance_Resolver(Blackhole blackhole) {
    for (int i = 0; i < invocations; i++) {
      blackhole.consume(Resolve.to(Double.class, i));
    }
  }
}
