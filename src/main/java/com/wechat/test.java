package com.wechat;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

public class test {
    public static void main(String[] args) throws Exception {
        Font font = new Font("\u5b8b\u4f53", Font.PLAIN, 20);
        BufferedImage image = createImage("张三", font, 80, 40);
        ImageIO.write(image, "jpg", new File("/Users/apple/Downloads/BM70ED3002001-01-02.jsp"));
    }

    private synchronized static BufferedImage createImage(String content, Font font, Integer width, Integer height) {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2 = (Graphics2D) bi.getGraphics();
        g2.setColor(Color.WHITE);// 背景色
        g2.fillRect(0, 0, width, height); // 画一个矩形

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // 去除锯齿(当设置的字体过大的时候,会出现锯齿)
        g2.setColor(Color.BLACK); // 字的颜色
        g2.setFont(font); // 字体字形字号

        FontRenderContext context = g2.getFontRenderContext();
        Rectangle2D bounds = font.getStringBounds(content, context);
        double x = (width - bounds.getWidth()) / 2;
        double y = (height - bounds.getHeight()) / 2;
        double ascent = -bounds.getY();
        double baseY = y + ascent;

        g2.drawString(content, (int) x, (int) baseY);

        return bi;
    }

}



