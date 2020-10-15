package ramo.klevis;

import com.mortennobel.imagescaling.ResampleFilters;
import com.mortennobel.imagescaling.ResampleOp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;


public class DrawingCanvasView extends JComponent {
    private Image image;
    private Graphics2D g2;
    private int currentX, currentY, oldX, oldY;

    /**
     * Constructor for the canvas.
     * Sets up a mouse listener so that the user can draw on the canvas properly.
     */
    public DrawingCanvasView() {
        setDoubleBuffered(false);
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                // save coord x,y when mouse is pressed
                oldX = e.getX();
                oldY = e.getY();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                // coord x,y when drag mouse
                currentX = e.getX();
                currentY = e.getY();

                if (g2 != null) {
                    g2.setStroke(new BasicStroke(10));
                    // draw line if g2 context not null
                    g2.drawLine(oldX, oldY, currentX, currentY);
                    // refresh draw area to repaint
                    repaint();
                    // store current coords x,y as olds x,y
                    oldX = currentX;
                    oldY = currentY;
                }
            }
        });
    }

    /**
     * Draws the users input on the canvas
     *
     * @param g Graphics object inherited from JComponent
     */
    protected void paintComponent(Graphics g) {
        if (image == null) {
            // image to draw null ==> we create
            image = createImage(getSize().width, getSize().height);
            g2 = (Graphics2D) image.getGraphics();
            // enable antialiasing
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // clear draw area
            clear();
        }

        g.drawImage(image, 0, 0, null);
    }

    /**
     * Deletes whatever was on the canvas
     */
    public void clear() {
        g2.setPaint(Color.white);
        // draw white on entire draw area to clear
        g2.fillRect(0, 0, getSize().width, getSize().height);
        g2.setPaint(Color.black);
        repaint();
    }

    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * Converts the canvas' input to a buffered image to be used in the prediction.
     *
     * @param image The input of the canvas.
     * @return The converted input of the canvas.
     */
    private BufferedImage toBufferedImage(Image image) {
        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(image, 0, 0, null);
        bGr.dispose();

        return bimage;
    }

    /**
     * Helper function to convert the buffered Image into a one dimensional vector
     * to fit the prediction method's input.
     *
     * @param img The buffered image to convert into an array.
     * @return The image converted into a 1D array.
     */
    private double[] transformImageToOneDimensionalVector(BufferedImage img) {
        double[] imageGray = new double[28 * 28];
        int w = img.getWidth();
        int h = img.getHeight();
        int index = 0;
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                Color color = new Color(img.getRGB(j, i), true);
                int red = (color.getRed());
                int green = (color.getGreen());
                int blue = (color.getBlue());
                double v = 255 - (red + green + blue) / 3d;
                imageGray[index] = v;
                index++;
            }
        }
        return imageGray;
    }

    /**
     * Scales the Buffered image to fit the model's size.
     *
     * @param imageToScale The image to scale.
     * @return The scaled image.
     */
    private BufferedImage scale(BufferedImage imageToScale) {
        ResampleOp resizeOp = new ResampleOp(28, 28);
        resizeOp.setFilter(ResampleFilters.getLanczos3Filter());
        return resizeOp.filter(imageToScale, null);
    }

    /**
     * Gets the input currently on the canvas, scales it, and then returns it as a one dimensional array.
     *
     * @return The image as a scaled one dimensional array.
     */
    public double[] getScaledPixels() {
        BufferedImage sbi = toBufferedImage(this.image);
        BufferedImage scaled = scale(sbi);
        return transformImageToOneDimensionalVector(scaled);
    }


}