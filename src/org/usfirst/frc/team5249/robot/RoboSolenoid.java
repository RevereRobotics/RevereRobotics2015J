package org.usfirst.frc.team5249.robot;

import edu.wpi.first.wpilibj.Solenoid;

/**
 *
 * @author sam
 */
public class RoboSolenoid {
    private Solenoid foo;
    private Solenoid bar;

    private boolean doublek = false;

    /*
     * For a one-solenoid system
     */
    public RoboSolenoid(int port) {
        foo = new Solenoid(8, port);
        doublek = false;
        System.out.println("MySolenoid enabled on port " + port);
    }

    /*
     * For a double solenoid system
     */
    public RoboSolenoid(int port1, int port2) {
        doublek = true;
        foo = new Solenoid(8, port1);
        bar = new Solenoid(8, port2);
        System.out.println("Double MySolenoid enabled on ports " + port1 + " and " + port2);
    }

    /*
     * For a double solenoid system, custom slot
     */
    public RoboSolenoid(int slot, int port1, int port2) {
        doublek = true;
        foo = new Solenoid(slot, port1);
        bar = new Solenoid(slot, port2);
        System.out.println("Double MySolenoid enabled on slot " + slot + " and ports " + port1 + " and " + port2);
    }

    /*
     * Extends the solenoid
     */
    public void extend() {
        foo.set(true);

        if(doublek) {
            bar.set(false);
        }
    }

    /*
     * Retracts the solenoid
     */
    public void retract() {
        foo.set(false);

        if(doublek) {
            bar.set(true);
        }
    }

    /*
     * Gets the state of the first solenoid
     */
    public boolean get() {
        return this.foo.get();
    }

    /*
     * Gets the state of the other solenoid
     */
    public boolean getOther() {
        boolean eggs = bar.get();
        return eggs;
    }
}