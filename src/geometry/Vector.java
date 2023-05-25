package geometry;

public class Vector {
    private double x;
    private double y;
    private double radius;
    private double theta;

    /**
     * construtor vazio do vetor, que cria o vetor nulo
     */
    public Vector() {
        this.x = 0.0;
        this.y = 0.0;
        this.radius = 0.0;
        this.theta = 0.0;
    }

    /**
     * construtor padrão do vetor, com especificação de coordenadas
     * @param x coordenada/componente x do vetor
     * @param y coordenada/componente y do vetor
     */
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
        computePolarCoordinates();
    }

    /**
     * método to string
     */
    public String toString() {
        return "(x,y) = (" + this.x + ", " + this.y + ")" + 
        "\n" + "(radius,theta) = (" + this.radius + ", " + this.theta + ")\n";
    }

    /**
     * redefine as duas coordenadas cartesianas (x,y)
     * @param x nova coordenada/componente x
     * @param y nova coordenada/componente y
     */
    public void setCartesianCoordinates(double x, double y) {
        this.x = x;
        this.y = y;
        computePolarCoordinates();
    }

    /**
     * redefine as coordenadas polares (radius,theta)
     * @param radius novo valor de raio
     * @param theta novo valor de ângulo
     */
    public void setPolarCoordinates(double radius, double theta) {
        this.radius = radius;
        this.theta = theta;
        computeCartesianCoordinates();
    }

    /**
     * componente x do vetor
     * @return valor de x
     */
    public double getX() {
        return this.x;
    }

    /**
     * componente y do vetor
     * @return valor de y
     */
    public double getY() {
        return this.y;
    }

    /**
     * componente radial do vetor
     * @return valor de radius
     */
    public double getRadius() {
        return this.radius;
    }

    /**
     * componente angular do vetor
     * @return valor de theta
     */
    public double getTheta() {
        return this.theta;
    }

    /**
     * redefine componente x do vetor
     * @param newX novo valor para x
     */
    public void setX(double newX) {
        this.x = newX;
        computePolarCoordinates();
    }

    /**
     * redefine componente y do vetor
     * @param newY novo valor para y
     */
    public void setY(double newY) {
        this.y = newY;
        computePolarCoordinates();
    }

    /**
     * redefine raio do vetor
     * @param newRadius novo valor para radius
     */
    public void setRadius(double newRadius) {
        this.radius = newRadius;
        computeCartesianCoordinates();
    }

    /**
     * redefine ângulo do vetor
     * @param newTheta novo valor para theta
     */
    public void setTheta(double newTheta) {
        this.theta = newTheta;
        computeCartesianCoordinates();
    }

    /**
     * computa as coordeanas cartesianas a partir das polares
     */
    private void computeCartesianCoordinates() {
        this.x = this.radius*Math.cos(theta);
        this.y = this.radius*Math.sin(theta);
    } 

    /**
     * computa as coordenadas polares a partir das cartesianas
     */
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