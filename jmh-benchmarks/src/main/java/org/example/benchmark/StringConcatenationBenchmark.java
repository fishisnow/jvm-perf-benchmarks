package org.example.benchmark;

import java.util.Random;
import java.util.concurrent.TimeUnit;

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

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(value = 1)
@State(Scope.Benchmark)
public class StringConcatenationBenchmark {

    // java -jar benchmarks/target/benchmarks.jar ".*StringConcatenationBenchmark.*" -prof gc

    private final Random random = new Random(16384);

    private String aString;
    private int anInt;
    private float aFloat;
    private long aLong;
    private double aDouble;
    private boolean aBool;
    private Object anObject;

    @Param({"128"})
    private int capacity;

    @Param
    private CODER coder;

    @Setup
    public void setup() {
        final char aChar;
        switch (coder) {
            case LATIN1:
                aChar = 'a';
                break;
            case UTF16:
                aChar = 'ʬ';
                break;
            default:
                throw new UnsupportedOperationException("Unsupported coder type " + coder);
        }

        final StringBuilder sb = new StringBuilder(capacity);
        for (int i = 0; i < capacity; i++) {
            sb.append((char) (aChar + random.nextInt(26)));
        }
        aString = sb.toString();
        anInt = random.nextInt();
        aFloat = random.nextFloat();
        aLong = random.nextLong();
        aDouble = random.nextDouble();
        aBool = random.nextBoolean();
        anObject = random.nextLong();
    }

    @Benchmark
    public String string_builder() {
        // explicitly do not set a capacity (i.e., the cost is impacted by the byte array resizing)
        return new StringBuilder()
                .append(aString)
                .append(anInt)
                .append(aFloat)
                .append(aLong)
                .append(aDouble)
                .append(aBool)
                .append(anObject)
                .toString();
    }

    @Benchmark
    public String string_buffer() {
        // explicitly do not set a capacity (i.e., the cost is impacted by the byte array resizing)
        return new StringBuffer()
                .append(aString)
                .append(anInt)
                .append(aFloat)
                .append(aLong)
                .append(aDouble)
                .append(aBool)
                .append(anObject)
                .toString();
    }

    @Benchmark
    public String plus_operator() {
        return aString + anInt + aFloat + aLong + aDouble + aBool + anObject;
    }

    public enum CODER {
        LATIN1,
        UTF16;
    }
}