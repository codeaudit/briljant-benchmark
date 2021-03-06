/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Isak Karlsson
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.briljantframework;

import java.util.concurrent.TimeUnit;

import org.briljantframework.array.Arrays;
import org.briljantframework.array.DoubleArray;
import org.briljantframework.array.api.ArrayRoutines;
import org.briljantframework.array.base.BaseArrayBackend;
import org.briljantframework.array.netlib.NetlibArrayBackend;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;


public class ArrayRoutinesBenchmark {

  @BenchmarkMode(Mode.AverageTime)
  @OutputTimeUnit(TimeUnit.MILLISECONDS)
  @Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
  @Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
  @Fork(1)
  @State(Scope.Benchmark)
  public static abstract class Abstract1dArrayRoutinesBenchmark {

    ArrayRoutines routines;
    DoubleArray x;

    @Param({"10", "100000"})
    private int vectorSize;

    @Setup
    public void setup() {
      routines = getRoutines();
      x = Arrays.randn(vectorSize);
    }

    @Benchmark
    public double test_norm2() {
      return routines.norm2(x);
    }

    @Benchmark
    public double test_asum() {
      return routines.asum(x);
    }

    @Benchmark
    public double test_iamax() {
      return routines.iamax(x);
    }

    @Benchmark
    public void test_scal() {
      routines.scal(30.2, x);
    }

    @Benchmark
    public double test_inner() {
      return routines.inner(x, x);
    }

    public abstract ArrayRoutines getRoutines();
  }

  public static class Netlib1dArrayRoutinesBenchmark extends Abstract1dArrayRoutinesBenchmark {

    @Override
    public ArrayRoutines getRoutines() {
      return new NetlibArrayBackend().getArrayRoutines();
    }
  }

  public static class Base1dArrayRoutinesBenchmark extends Abstract1dArrayRoutinesBenchmark {

    @Override
    public ArrayRoutines getRoutines() {
      return new BaseArrayBackend().getArrayRoutines();
    }
  }


}
