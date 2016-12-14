class Complex {
    private double real;
    private double image;

    public Complex(double real,double image) {
        this.real = real;
        this.image = image;
    }
    public Complex(double real) {
        this.real = real;
        this.image = 0;
    }
    public double getReal() {
        return real;
    }
    public void setReal(double real) {
        this.real = real;
    }
    public double getImage() {
        return image;
    }
    public void setImage(double image) {
        this.image = image;
    }
    public Complex add(Complex a) {
        return new Complex(this.real + a.real, this.image + a.image);
    }
    public Complex sub(Complex a) {
        return new Complex(this.real - a.real, this.image - a.image);
    }
    public Complex mul(Complex a) {
        return new Complex(this.real*a.real - this.image*a.image, this.image*a.real + this.real*a.image);
    }
    public Complex div(Complex a) {
        this.real = (real*a.real + image*a.image)/(a.real*a.real + a.image*a.image);
        this.image = (image*a.real - real*a.image)/(a.real*a.real + a.image*a.image);
        return new Complex(this.real,this.image);
    }
    public Complex neg() {
        return new Complex(-this.real,-this.image);
    }
    public String toString() {
        if(image != 0) {
            return real + "+(" + image + "i)";
        } else {
            return real + "";
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Complex data1 = new Complex(1);
        Complex data2 = new Complex(3,4);
        Complex result_neg = data1.neg();
        Complex result_add = data1.add(data2);
        Complex result_sub = data1.sub(data2);
        Complex result_mul = data1.mul(data2);
        Complex result_div = data1.div(data2);

        System.out.println("The negative of the complex numbers is " + result_neg);
        System.out.println("The sum of the two complex numbers is " + result_add);
        System.out.println("The difference of the two complex numbers is " + result_sub);
        System.out.println("The product of the two complex numbers is " + result_mul);
        System.out.println("The division of the two complex numbers is " + result_div);
    }
}