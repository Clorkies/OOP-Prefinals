public abstract class Employee extends Person {
    private int months_worked;
    private double salary;
    public Employee(String name, int age, int months_worked, double salary) {
        super(name, age);
        this.salary = salary;
        this.months_worked = months_worked;
    }

    public int getMonthsWorked() {
        return months_worked;
    }

    public double getSalary() {
        return salary;
    }

    public double thirteenthmonth() {
        return (salary * months_worked) / 6;
    }
}
