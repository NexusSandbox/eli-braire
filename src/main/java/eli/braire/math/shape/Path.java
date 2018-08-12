//package eli.braire.math.shape;
//
//import java.util.Collection;
//import java.util.Iterator;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.ListIterator;
//
//import com.google.common.collect.Lists;
//
//import eli.braire.math.space.Point;
//import eli.veritas.Verifier;
//import eli.veritas.exception.AssertionException;
//
///**
// * A {@link Collection} corresponding to a sequence of {@link Point waypoints}. Each {@link Point} is guaranteed to be contiguous with the rest of the
// * {@link Path}.
// *
// * @author The Architect
// */
//public class Path implements List<Point>
//{
//    private final LinkedList<Point> waypoints = Lists.newLinkedList();
//
//    private Path(final Point originWaypoint, final Point terminusWaypoint)
//    {
//        waypoints.add(originWaypoint);
//        waypoints.add(terminusWaypoint);
//    }
//
//    private Path(final List<Point> waypoints)
//    {
//        this.waypoints.addAll(waypoints);
//    }
//
//    /**
//     * @param originWaypoint
//     * @param terminusWaypoint
//     * @return
//     */
//    public static Path of(final Point originWaypoint, final Point terminusWaypoint)
//    {
//        Verifier.isNotNull(originWaypoint);
//        Verifier.isNotNull(terminusWaypoint);
//
//        return new Path(originWaypoint, terminusWaypoint);
//    }
//
//    /**
//     * @param waypoints
//     * @return
//     * @throws AssertionException
//     */
//    public static Path of(final List<Point> waypoints) throws AssertionException
//    {
//        Verifier.Collections.assertNotEmpty("Unable to create empty Path.", waypoints);
//        Verifier.Collections.assertContainsNoValue("Unable to create Path with NULL waypoints.", waypoints, null);
//        Point previousWaypoint = waypoints.get(0);
//        for (int index = 1; index < waypoints.size(); index++)
//        {
//            final Point currentWaypoint = waypoints.get(index);
//            Verifier.Equality.assertEqual(String.format("All waypoints must have the same rank: %s", currentWaypoint),
//                                          currentWaypoint.RANK,
//                                          previousWaypoint.RANK);
//            Verifier.Equality.assertNotEqual(String.format("Each waypoint must be distinct from the previous waypoint: %s -> %s",
//                                                           previousWaypoint,
//                                                           currentWaypoint),
//                                             currentWaypoint,
//                                             previousWaypoint);
//            previousWaypoint = currentWaypoint;
//        }
//
//        return new Path(waypoints);
//    }
//
//    @Override
//    public boolean add(final Point waypoint)
//    {
//        try
//        {
//            Verifier.assertNotNull("Unable to insert NULL waypoints into Path.", waypoint);
//        }
//        catch (final AssertionException e)
//        {
//            e.printStackTrace();
//            return false;
//        }
//
//        // TODO Find nearest segment between waypoints, and insert
//        throw new UnsupportedOperationException("Implement logic to find the nearest segment between two waypoints and insert at that index.");
//    }
//
//    @Override
//    public void add(final int index, final Point waypoint)
//    {
//        try
//        {
//            Verifier.assertNotNull("Unable to insert NULL waypoints into Path.", waypoint);
//            Verifier.Equality.assertNotEqual("Unable to replace origin Waypoint of Path.", index, 0);
//            Verifier.Equality.assertNotEqual("Unable to replace terminus Waypoint of Path.", index, waypoints.size());
//            Verifier.Equality.assertNotEqual(String.format("Each waypoint must be distinct from the previous waypoint: %s -> %s",
//                                                           waypoints.get(index - 1),
//                                                           waypoint),
//                                             waypoint,
//                                             waypoints.get(index - 1));
//            Verifier.Equality.assertNotEqual(String.format("Each waypoint must be distinct from the next waypoint: %s -> %s",
//                                                           waypoint,
//                                                           waypoints.get(index)),
//                                             waypoint,
//                                             waypoints.get(index));
//        }
//        catch (final AssertionException e)
//        {
//            e.printStackTrace();
//            return;
//        }
//
//        waypoints.add(index, waypoint);
//    }
//
//    @Override
//    public boolean addAll(final Collection<? extends Point> waypoints)
//    {
//        if (Verifier.Collections.isEmpty(waypoints))
//        {
//            return true;
//        }
//        try
//        {
//            Verifier.Collections.assertContainsNoValue("Unable to insert waypoints Path with NULL waypoints.", waypoints, null);
//            final Iterator<? extends Point> pointIterator = waypoints.iterator();
//            Point previousWaypoint = pointIterator.next();
//            if (pointIterator.hasNext())
//            {
//                for (Point currentWaypoint = pointIterator.next(); pointIterator.hasNext(); previousWaypoint = currentWaypoint, currentWaypoint = pointIterator.next())
//                {
//                    Verifier.Equality.assertEqual(String.format("All waypoints must have the same rank: %s", currentWaypoint),
//                                                  currentWaypoint.RANK,
//                                                  previousWaypoint.RANK);
//                    // TODO For each waypoint, find nearest segment and check corresponding waypoints in existing path.
//                    Verifier.Equality.assertNotEqual(String.format("Each waypoint must be distinct from the previous waypoint: %s -> %s",
//                                                                   previousWaypoint,
//                                                                   currentWaypoint),
//                                                     currentWaypoint,
//                                                     previousWaypoint);
//                }
//            }
//        }
//        catch (final AssertionException e)
//        {
//            e.printStackTrace();
//            return false;
//        }
//
//        // TODO Find nearest segment between waypoints, and insert
//        throw new UnsupportedOperationException("Implement logic to find the nearest segment between two waypoints and insert at that index.");
//    }
//
//    @Override
//    public boolean addAll(final int index, final Collection<? extends Point> waypoints)
//    {
//        if (Verifier.Collections.isEmpty(waypoints))
//        {
//            return true;
//        }
//        try
//        {
//            Verifier.Collections.assertNotEmpty("Unable to insert empty Path.", waypoints);
//            Verifier.Collections.assertContainsNoValue("Unable to insert waypoints Path with NULL waypoints.", waypoints, null);
//            final Iterator<? extends Point> pointIterator = waypoints.iterator();
//            Point previousWaypoint = pointIterator.next();
//            if (pointIterator.hasNext())
//            {
//                for (Point currentWaypoint = pointIterator.next(); pointIterator.hasNext(); previousWaypoint = currentWaypoint, currentWaypoint = pointIterator.next())
//                {
//                    Verifier.Equality.assertEqual(String.format("All waypoints must have the same rank: %s", currentWaypoint),
//                                                  currentWaypoint.RANK,
//                                                  previousWaypoint.RANK);
//                    // TODO For each waypoint, find nearest segment and check corresponding waypoints in existing path.
//                    Verifier.Equality.assertNotEqual(String.format("Each waypoint must be distinct from the previous waypoint: %s -> %s",
//                                                                   previousWaypoint,
//                                                                   currentWaypoint),
//                                                     currentWaypoint,
//                                                     previousWaypoint);
//                }
//            }
//        }
//        catch (final AssertionException e)
//        {
//            e.printStackTrace();
//            return false;
//        }
//
//        // TODO Find nearest segment between waypoints, and insert
//        return this.waypoints.addAll(index, waypoints);
//    }
//
//    @Override
//    public void clear()
//    {
//        throw new UnsupportedOperationException("Unable to remove all waypoints from Path. This would result in an empty Path.");
//    }
//
//    @Override
//    public boolean contains(final Object segment)
//    {
//        return waypoints.contains(segment);
//    }
//
//    @Override
//    public boolean containsAll(final Collection<?> waypoints)
//    {
//        return waypoints.containsAll(waypoints);
//    }
//
//    @Override
//    public Point get(final int index)
//    {
//        return waypoints.get(index);
//    }
//
//    @Override
//    public int indexOf(final Object segment)
//    {
//        return waypoints.indexOf(segment);
//    }
//
//    @Override
//    public boolean isEmpty()
//    {
//        return waypoints.isEmpty();
//    }
//
//    @Override
//    public Iterator<Point> iterator()
//    {
//        return waypoints.iterator();
//    }
//
//    @Override
//    public int lastIndexOf(final Object segment)
//    {
//        return waypoints.lastIndexOf(segment);
//    }
//
//    @Override
//    public ListIterator<Point> listIterator()
//    {
//        return waypoints.listIterator();
//    }
//
//    @Override
//    public ListIterator<Point> listIterator(final int index)
//    {
//        return waypoints.listIterator(index);
//    }
//
//    @Override
//    public boolean remove(final Object arg0)
//    {
//        return false;
//    }
//
//    @Override
//    public Point remove(final int arg0)
//    {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    @Override
//    public boolean removeAll(final Collection<?> arg0)
//    {
//        // TODO Auto-generated method stub
//        return false;
//    }
//
//    @Override
//    public boolean retainAll(final Collection<?> arg0)
//    {
//        // TODO Auto-generated method stub
//        return false;
//    }
//
//    @Override
//    public Point set(final int arg0, final Point arg1)
//    {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    @Override
//    public int size()
//    {
//        // TODO Auto-generated method stub
//        return 0;
//    }
//
//    @Override
//    public List<Point> subList(final int arg0, final int arg1)
//    {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    @Override
//    public Object[] toArray()
//    {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    @Override
//    public <T> T[] toArray(final T[] arg0)
//    {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//}
