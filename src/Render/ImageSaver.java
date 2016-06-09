package Render;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;

import javax.imageio.ImageIO;

public class ImageSaver {
	
	int fileCount = 0;
	String dir = "./images/";
	
	public ImageSaver() {
		
	}
	
	public BufferedImage getImage(int i) {
		
		BufferedImage newImage = null;
		
		try {
			newImage = ImageIO.read(new File(String.format("%simage_%03d.png", 
														   dir, 
														   i)));
		} catch (Exception e) {
			return newImage;
		}
		
		return newImage;
	}
	
	public void clearImages() throws FileNotFoundException {
		
		File dirFile = new File(dir);
		
		if(dirFile.exists()) {
		    for (File c : dirFile.listFiles()) {
		    	c.delete();
		    }
		}
	    
	    if (!dirFile.delete()) {
	    	throw new FileNotFoundException("Failed to delete directory: " + dirFile);
	    }
	}
	
	public void saveImage(BufferedImage image) {
		
		File file = new File(String.format("%simage_%03d.png", dir, fileCount));
		
		try {
			new File(dir).mkdir();
			ImageIO.write(image, "png", file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		++fileCount;
	}
}
