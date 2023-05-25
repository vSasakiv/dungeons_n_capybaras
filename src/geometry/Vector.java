package geometry;

public class Vector {
    private double x;
    private double y;
    private double radius;
    private double theta;

    public Vector() {
        this.x = 0.0;
        this.y = 0.0;
        this.radius = 0.0;
        this.theta = 0.0;
    }

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
        computePolarCoordinates();
    }

    public String toString() {
        return "(x,y) = (" + this.x + ", " + this.y + ")" + 
        "\n" + "(radius,theta) = (" + this.radius + ", " + this.theta + ")\n";
    }

    public void setCartesianCoordinates(double x, double y) {
        this.x = x;
        this.y = y;
        computePolarCoordinates();
    }

    public void setPolarCoordinates(double radius, double theta) {
        this.radius = radius;
        this.theta = theta;
        computeCartesianCoordinates();
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getRadius() {
        return this.radius;
    }

    public double getTheta() {
        return this.theta;
    }

    public void setX(double newX) {
        this.x = newX;
        computePolarCoordinates();
    }

    public void setY(double newY) {
        this.y = newY;
        computePolarCoordinates();
    }

    public void setRadius(double newRadius) {
        this.radius = newRadius;
        computeCartesianCoordinates();
    }

    public void setTheta(double newTheta) {
        this.theta = newTheta;
        computeCartesianCoordinates();
    }

    private void computeCartesianCoordinates() {
        this.x = this.radius*Math.cos(theta);
        this.y = this.radius*Math.sin(theta);
    } 

    private void computePolarCoordinates() {
        this.radius = Math.sqrt((x*x) + (y*y));
        
        if(this.x > 0) {
            this.theta = Math.atan(this.y/this.x);
        } else if(this.x < 0) {
            this.theta = (2*Math.PI) - Math.atan(this.y/this.x);
        } else {
            this.theta = 0;
        }
    }
}