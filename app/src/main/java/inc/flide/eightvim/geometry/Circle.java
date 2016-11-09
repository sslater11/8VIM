package inc.flide.eightvim.geometry;

import android.graphics.PointF;
import android.graphics.PointF;

import inc.flide.eightvim.keyboardHelpers.FingerPosition;

public class Circle{
    private PointF centre;
    private float radius;

    public Circle(PointF centre, float radius) {
        this.centre = centre;
        this.radius = radius;
    }

    public PointF getCentre() {
        return centre;
    }

    public void setCentre(PointF centre) {
        this.centre = centre;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    private double getPowerOfPoint(PointF point) {
        /*
        If O is the centre of circle
        Consider startingPoint point P not necessarily on the circumference of the circle.
        If d = OP is the distance between P and the circle's center O, then the power of the point P relative to the circle is
        p=d^2-r^2.
        */

        double dSquare = GeometricUtilities.getSquaredDistanceBetweenPoints(point, centre);
        double rSquare = Math.pow(radius, 2);
        double power = dSquare - rSquare;
        return power;
    }

    public boolean isPointInsideCircle(PointF point){
        if(getPowerOfPoint(point) < 0){
            return true;
        }
        return false;
    }

    public PointF getPointOnCircumferenceAtDegreeAngle(int angleInDegree){
        double angleInRadians = Math.toRadians(angleInDegree);
        return getPointOnCircumferenceAtRadianAngle(angleInRadians);
    }

    public PointF getPointOnCircumferenceAtRadianAngle(double angleInRadians){
        float x = (float) (centre.x + (radius * Math.cos(angleInRadians)));
        float y = (float) (centre.y + (radius * Math.sin(angleInRadians)));
        return new PointF(x,y);
    }

    /** Gets the angle of point p relative to the center */
    public double getAngleInRadiansOfPointWithRespectToCentreOfCircle(PointF point)
    {
        // Get difference of coordinates
        double x = point.x - centre.x;
        double y = centre.y - point.y;

        // Calculate angle with special atan (calculates the correct angle in all quadrants)
        double angle = Math.atan2(y, x);
        // Make all angles positive
        if(angle < 0) {
            angle = Math.PI * 2 + angle;
        }
        return angle;
    }

    /** Get the number of the sector that point p is in
     *  @return 0: right, 1: top, 2: left, 3: bottom */
    public FingerPosition getSectorOfPoint(PointF p) {
        double angleDouble = getAngleInRadiansOfPointWithRespectToCentreOfCircle(p);
        double angleToSectorValue = angleDouble/ (Math.PI / 2);
        int quadrantCyclic = (int)Math.round(angleToSectorValue);
        int baseQuadrant = GeometricUtilities.getBaseQuadrant(quadrantCyclic);

        switch (baseQuadrant){
            case 0:
                return FingerPosition.RIGHT;

            case 1:
                return FingerPosition.TOP;

            case 2 :
                return FingerPosition.LEFT;

            case 3 :
                return FingerPosition.BOTTOM;

        }
        return null;
    }
}
