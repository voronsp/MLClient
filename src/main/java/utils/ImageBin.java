package utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 * @author sbt-voronova-id
 */
public class ImageBin {

    protected BufferedImage curImg;
    protected int white = (new Color(255, 255, 255)).getRGB();
    protected int black = (new Color(0, 0, 0)).getRGB();

    public ImageBin(BufferedImage im) {
        this.curImg = im;
    }

    public BufferedImage getGrayScale() throws IOException {
        BufferedImage img = new BufferedImage(curImg.getWidth(), curImg.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = img.getGraphics();
        g.drawImage(curImg, 0, 0, null);
        g.dispose();
//        ImageIO.write(img, "jpg", new File("images/imgGray!!!.jpg"));
        return img;
    }

    public BufferedImage getThersholdedGrayscale(BufferedImage source, int thresh, boolean inverted) throws IOException {
        int setPix;
        for (int i = 0; i < source.getWidth(); i++) {
            for (int j = 0; j < source.getHeight(); j++) {
                int pix = source.getRaster().getSample(i, j, 0);
                if (pix < thresh) {
                    setPix = inverted ? white : black;
                } else {
                    setPix = inverted ? black : white;
                }
                source.setRGB(i, j, setPix);
            }
        }
//        ImageIO.write(source, "jpg", new File("images/imgGray.jpg"));
        return source;
    }

    public double getMean(BufferedImage source) {
        double m = 0;
        for (int i = 0; i < source.getWidth(); i++) 
            for (int j = 0; j < source.getHeight(); j++) {
                m += source.getRaster().getSample(i, j, 0);
            }
        return m/(source.getHeight()*source.getWidth());
    }
    
    public double getSquare(BufferedImage source) {
        double s = 0;
        double m = getMean(source);
        for (int i = 0; i < source.getWidth(); i++) 
            for (int j = 0; j < source.getHeight(); j++) {
                s += Math.pow(source.getRaster().getSample(i, j, 0) - m, 2);
            }
        return Math.sqrt(s/(source.getHeight()*source.getWidth()));
    }
    
    public int getMin(BufferedImage source) {
        int min = source.getRaster().getSample(0, 0, 0);
        int cur;
        for (int i = 0; i < source.getWidth(); i++) 
            for (int j = 0; j < source.getHeight(); j++) {
                cur = source.getRaster().getSample(i, j, 0);
                if (min > cur) min = cur;
            }
        return min;
    }

    

    public BufferedImage adaptiveThresholdingChristian(BufferedImage source, int kernelX, int KernelY) throws IOException {
        BufferedImage res =  new BufferedImage(curImg.getWidth(), curImg.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = res.getGraphics();
        g.drawImage(source, 0, 0, null);
        BufferedImage subIm;
        int T, width, height, rightX = source.getWidth(), rightY = source.getHeight();
        double k = 0.5, s, m, mean;
        double R = 128;
        m = getMin(source);
        width = source.getWidth();
        height = source.getHeight();
        for (int i = 0; i < width; i+= rightX) 
            for (int j = 0; j < height; j+= rightY) {
                if ((i + kernelX) > width)  rightX = width - i;
                        else rightX = kernelX;
                if ((j + KernelY) > height)  rightY = height - j ;
                        else rightY = KernelY;
                subIm = source.getSubimage( i, j, rightX, rightY);
                s = getSquare(subIm);
                mean = getMean(subIm);                
                T = (int) (Math.round((1 - k)*mean + k*m + k*s/R*(mean - m)));
                g.drawImage(getThersholdedGrayscale(subIm, T, true), i, j, null);
            }
        g.dispose();
        ImageIO.write(res, "jpg", new File("images/imgGrayCh.jpg"));
        return res;
    }

    public BufferedImage addAll(List<BufferedImage> images) {
        if (images.isEmpty()) {
            throw new IllegalStateException("List of images is empty");
        }
        BufferedImage res = new BufferedImage(images.get(0).getWidth(), images.get(0).getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        res.setData(images.get(0).getData());
        int pixCurrent, pixFloat;
        for (BufferedImage im : images) {
            for (int i = 0; i < im.getWidth(); i++) {
                for (int j = 0; j < im.getHeight(); j++) {
                    pixCurrent = im.getRaster().getSample(i, j, 0);
                    pixFloat = res.getRaster().getSample(i, j, 0);
                    if (black == pixCurrent && white == pixFloat) {
                        res.setRGB(i, j, white);
                    }
                }
            }

        }
        return res;
    }
}
