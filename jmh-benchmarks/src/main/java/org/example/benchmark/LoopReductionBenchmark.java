/*
 * JVM Performance Benchmarks
 *
 * Copyright (C) 2019 - 2023 Ionut Balosin
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.example.benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.CompilerControl;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(value = 1)
@State(Scope.Benchmark)
public class LoopReductionBenchmark {

    // $ java -jar */*/benchmarks.jar ".*LoopReductionBenchmark.*"

    @Param({"1048576"})
    private int iterations;

    @Param({"128"})
    private int offset;

    @Benchmark
    public void initial_loop() {
        auto_reduction(iterations, offset);
    }

    @Benchmark
    public void manual_loop_reduction() {
        manual_reduction(iterations, offset);
    }

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    private int auto_reduction(final int iterations, int accumulator) {
        for (int i = 0; i < iterations; ++i) {
            accumulator++;
        }
        return accumulator;
    }

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    private int manual_reduction(final int iterations, int accumulator) {
        if (iterations > 0) {
            return accumulator + iterations;
        } else {
            return accumulator;
        }
    }
}
