package com.antigravity.utils;

public class AnimationUtils {

    private double value;
    private double target;
    private double speed;

    public AnimationUtils(double value, double speed) {
        this.value = value;
        this.target = value;
        this.speed = speed;
    }

    public void update() {
        double diff = target - value;
        value += diff * speed;
        if (Math.abs(diff) < 0.001) {
            value = target;
        }
    }

    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }
    public double getTarget() { return target; }
    public void setTarget(double target) { this.target = target; }
    public double getSpeed() { return speed; }
    public void setSpeed(double speed) { this.speed = speed; }
    public boolean isDone() { return value == target; }

    // Static easing helpers
    public static double easeInOutCubic(double t) {
        return t < 0.5 ? 4 * t * t * t : 1 - Math.pow(-2 * t + 2, 3) / 2;
    }

    public static double easeOutQuad(double t) {
        return 1 - (1 - t) * (1 - t);
    }

    public static double easeInOutQuad(double t) {
        return t < 0.5 ? 2 * t * t : 1 - Math.pow(-2 * t + 2, 2) / 2;
    }

    public static double lerp(double start, double end, double t) {
        return start + (end - start) * t;
    }

    public static float lerp(float start, float end, float t) {
        return start + (end - start) * t;
    }

    public static int lerpColor(int c1, int c2, float t) {
        int a1 = (c1 >> 24) & 0xFF, r1 = (c1 >> 16) & 0xFF, g1 = (c1 >> 8) & 0xFF, b1 = c1 & 0xFF;
        int a2 = (c2 >> 24) & 0xFF, r2 = (c2 >> 16) & 0xFF, g2 = (c2 >> 8) & 0xFF, b2 = c2 & 0xFF;
        int a = (int) lerp(a1, a2, t);
        int r = (int) lerp(r1, r2, t);
        int g = (int) lerp(g1, g2, t);
        int b = (int) lerp(b1, b2, t);
        return (a << 24) | (r << 16) | (g << 8) | b;
    }
}
