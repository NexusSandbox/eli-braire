//package eli.braire.math.space;
//
//import eli.veritas.Verifier;
//
///**
// * TODO Functional Description
// *
// * @author The Architect
// */
//public enum RotationDirection
//{
// COUNTER_CLOCKWISE,
// COLLINEAR,
// CLOCKWISE;
//
//    public static RotationDirection evaluate(final Point p1, final Point p2, final Point p3)
//    {
//        final Matrix matrix = Matrix.create(p1, p2, p3);
//
//        final double determinant = matrix.determinant();
//        if (Verifier.Inequality.isLessThan(determinant, 0, AbstractLinearConstruct.EPSILON))
//        {
//            return CLOCKWISE;
//        }
//        else if (Verifier.Inequality.isGreaterThan(determinant, 0, AbstractLinearConstruct.EPSILON))
//        {
//            return COUNTER_CLOCKWISE;
//        }
//        return COLLINEAR;
//    }
//}
