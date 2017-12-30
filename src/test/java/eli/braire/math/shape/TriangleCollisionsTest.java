package eli.braire.math.shape;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import eli.braire.math.space.Point;
import eli.veritas.exception.AssertionException;

/**
 * TODO Functional Description
 *
 * @author The Architect
 */
public class TriangleCollisionsTest
{
    private static final double     screenWidth   = 1000;
    private static final double     screenHeight  = 1000;
    private static final int        triangleCount = 1000;
    private static final Random     random        = new Random(0l);
    private static final List<Face> triangles     = new ArrayList<>(TriangleCollisionsTest.triangleCount);

    @BeforeClass
    public static void beforeClass() throws AssertionException
    {
        for (int index = 0; index < TriangleCollisionsTest.triangleCount; index++)
        {
            final Point point1 = Point.create(TriangleCollisionsTest.screenWidth * TriangleCollisionsTest.random.nextDouble(),
                                              TriangleCollisionsTest.screenHeight * TriangleCollisionsTest.random.nextDouble());
            final Point point2 = Point.create(TriangleCollisionsTest.screenWidth * TriangleCollisionsTest.random.nextDouble(),
                                              TriangleCollisionsTest.screenHeight * TriangleCollisionsTest.random.nextDouble());
            final Point point3 = Point.create(TriangleCollisionsTest.screenWidth * TriangleCollisionsTest.random.nextDouble(),
                                              TriangleCollisionsTest.screenHeight * TriangleCollisionsTest.random.nextDouble());
            TriangleCollisionsTest.triangles.add(Face.create(point1, point2, point3));
        }
    }

    @Test
    @Ignore("Needs fixing...")
    public void testCollisions() throws AssertionException
    {
        new TestPlan()
        {
            @Override
            protected int executeTest() throws AssertionException
            {
                int collisions = 0;
                for (int i = 0; i < TriangleCollisionsTest.triangles.size(); i++)
                {
                    for (int j = i + 1; j < TriangleCollisionsTest.triangles.size(); j++)
                    {
                        if (TriangleCollisionsTest.triangles.get(i).intersects(TriangleCollisionsTest.triangles.get(j)))
                        {
                            collisions++;
                        }
                    }
                }

                return collisions;
            }
        }.performTest();
    }

    public abstract class TestPlan
    {
        final int testIterations = 100;

        protected abstract int executeTest() throws AssertionException;

        public void performTest() throws AssertionException
        {
            final long[] timeSpans = new long[testIterations];
            double averageTime = 0;
            int totalCollisions = 0;
            for (int i = 0; i < testIterations; i++)
            {
                final long startTime = System.nanoTime();
                final int collisions = executeTest();
                timeSpans[i] = System.nanoTime() - startTime;
                averageTime += timeSpans[i];
                totalCollisions += collisions;
                System.out.println(MessageFormat.format("Test[{0}]: {1,number,#.000} ms", i, timeSpans[i] / 1000000d));
            }
            averageTime /= testIterations;
            double standardDeviation = 0;
            for (final long timeSpan : timeSpans)
            {
                final double deviation = timeSpan - averageTime;
                standardDeviation += deviation * deviation;
            }
            standardDeviation = Math.sqrt(standardDeviation / testIterations);
            System.out.println(MessageFormat.format("Average Duration for {0} collisions: {1,number,0.000} ms ({2,number,0.000} μs/collision) with σ {3,number,0.000} ms",
                                                    totalCollisions / testIterations,
                                                    averageTime / 1000000D,
                                                    averageTime * testIterations / (1000D * totalCollisions),
                                                    standardDeviation / 1000000D));
        }
    }

}
