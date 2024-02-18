/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package G_Border;

/**
 *
 * @author mario
 */
public class FancyAnimation {
    private float start[];
    private float end[];
    
    public FancyAnimation(String start, String end) {
        this.start = new Border(start).toArray();
        this.end = new Border(end).toArray();
    }
    
    public FancyAnimation start(String start) {
        this.start = new Border(start).toArray();
        return this;
    }
    
    public FancyAnimation end(String end) {
        this.end = new Border(end).toArray();
        return this;
    }
    
    public Border getAnimate(float f) {
        float b[] = new float[start.length];
        for (int i = 0; i < start.length; i++) {
            float t = end[i] - start[i];
            t = start [i] + t * f;
            b[i] = t;
        }
        Border border = new Border();
        border.setBorder(b[0], b[1], b[2], b[3], b[4], b[5], b[6], b[7]);
        return border;
    }
}
