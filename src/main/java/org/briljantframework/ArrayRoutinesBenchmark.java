/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.briljantframework;

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

import java.util.concurrent.TimeUnit;


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
