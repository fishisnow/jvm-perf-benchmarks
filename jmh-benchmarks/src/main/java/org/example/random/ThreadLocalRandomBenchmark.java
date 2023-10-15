package org.example.random;

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

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(value = 5)
@State(Scope.Benchmark)
public class ThreadLocalRandomBenchmark {

    // $ java -jar */*/benchmarks.jar ".*ThreadLocalRandomBenchmark.*"

    @Param("1024")
    private int bytesSize;

    private final ThreadLocalRandom randomGenerator = ThreadLocalRandom.current();
    private byte[] bytes;

    @Setup
    public void setup() {
        bytes = new byte[bytesSize];
    }

    @Benchmark
    public boolean next_boolean() {
        return randomGenerator.nextBoolean();
    }

    @Benchmark
    public byte[] next_bytes() {
        randomGenerator.nextBytes(bytes);
        return bytes;
    }

    @Benchmark
    public float next_float() {
        return randomGenerator.nextFloat();
    }

    @Benchmark
    public double next_double() {
        return randomGenerator.nextDouble();
    }

    @Benchmark
    public int next_int() {
        return randomGenerator.nextInt();
    }

    @Benchmark
    public long next_long() {
        return randomGenerator.nextLong();
    }

    @Benchmark
    public double next_gaussian() {
        return randomGenerator.nextGaussian();
    }

    @Benchmark
    public double next_exponential() {
        return randomGenerator.nextExponential();
    }

    @Benchmark
    public DoubleStream doubles() {
        return randomGenerator.doubles(bytesSize);
    }

    @Benchmark
    public IntStream ints() {
        return randomGenerator.ints(bytesSize);
    }

    @Benchmark
    public LongStream longs() {
        return randomGenerator.longs(bytesSize);
    }
}
