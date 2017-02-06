package com.rundatop.core.utils;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.ImageIcon;

import org.springframework.web.context.ContextLoader;

import com.rundatop.core.mybatis.plug.UeConfig;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
  
public class ImageUtil {  
  
	/*
	 * flag:如果flag=true，考虑ue配置问题，否则忽略
	 */
	 public static File resize(File originalFile, File resizedFile,  
	            int newWidth, float quality,boolean flag) throws IOException {  
	  
	        if (quality > 1) {  
	            throw new IllegalArgumentException(  
	                    "Quality has to be between 0 and 1");  
	        }  
	  
	        ImageIcon ii = new ImageIcon(originalFile.getCanonicalPath());  
	        Image i = ii.getImage();  
	        Image resizedImage = null;  
	  
	        int iWidth = i.getWidth(null);
	        
	        //濡傛灉鍥剧墖鏈韩鐨勫ぇ灏忎笉澶т簬閰嶇疆鐨勫ぇ灏忥紝鍒欎笉鍘嬬缉
	        UeConfig ue=(UeConfig)ContextLoader.getCurrentWebApplicationContext().getBean("ueConfig");
	        if(flag && iWidth <= ue.getImageCompressBorder()){
	        	return originalFile;
	        }
	        	
	        
	        int iHeight = i.getHeight(null);  
	  
	        if (iWidth > iHeight) {  
	            resizedImage = i.getScaledInstance(newWidth, (newWidth * iHeight)  
	                    / iWidth, Image.SCALE_SMOOTH);  
	        } else {  
	            resizedImage = i.getScaledInstance((newWidth * iWidth) / iHeight,  
	                    newWidth, Image.SCALE_SMOOTH);  
	        }  
	  
	        // This code ensures that all the pixels in the image are loaded.  
	        Image temp = new ImageIcon(resizedImage).getImage();  
	  
	        // Create the buffered image.  
	        BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null),  
	                temp.getHeight(null), BufferedImage.TYPE_INT_RGB);  
	  
	        // Copy image to buffered image.  
	        Graphics g = bufferedImage.createGraphics();  
	  
	        // Clear background and paint the image.  
	        g.setColor(Color.white);  
	        g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));  
	        g.drawImage(temp, 0, 0, null);  
	        g.dispose();  
	  
	        // Soften.  
	        float softenFactor = 0.05f;  
	        float[] softenArray = { 0, softenFactor, 0, softenFactor,  
	                1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0 };  
	        Kernel kernel = new Kernel(3, 3, softenArray);  
	        ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);  
	        bufferedImage = cOp.filter(bufferedImage, null);  
	  
	        // Write the jpeg to a file.  
	        FileOutputStream out = new FileOutputStream(resizedFile);  
	  
	        // Encodes image as a JPEG data stream  
	        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);  
	  
	        JPEGEncodeParam param = encoder  
	                .getDefaultJPEGEncodeParam(bufferedImage);  
	  
	        param.setQuality(quality, true);  
	  
	        encoder.setJPEGEncodeParam(param);  
	        encoder.encode(bufferedImage);  
	        out.close();
	        return resizedFile;
	    }// Example usage  
  
    public static void main(String[] args) throws IOException {  
//       File originalImage = new File("C:\\11.jpg");  
//       resize(originalImage, new File("c:\\11-0.jpg"),150, 0.7f);  
//       resize(originalImage, new File("c:\\11-1.jpg"),150, 1f);  
    	File originalImage = new File("F:\\workspace2\\cc\\WebContent\\upload\\cover\\561DCEE16F98404185F752693A929AC3.png");  
        resize(originalImage, originalImage,200, 0.7f,false);  
         //resize(originalImage, new File("d:\\\psb1.jpg"),150, 1f);  
    }  
}  
