package ce325.hw2;

import java.io.File;
import java.util.ArrayList;

//creates a single noise-free image out of multiple images
public class PPMImageStacker{
	private ArrayList<PPMImage> PPMImageList = new ArrayList<PPMImage>();
	private PPMImage stackedImage;

	//reads the PPM images of a directory and saves them to an array list
	public PPMImageStacker(File dir){
		boolean exists = dir.exists();      // Check if the file exists
		boolean isDirectory = dir.isDirectory(); // Check if it's a directory

		if(!exists){
			System.out.println("[ERROR] Directory <" + dir.getName() + "> does not exist!");
		}
		else if(!isDirectory){
			System.out.println("[ERROR] <" + dir.getName() + "> is not a directory!");
		}

		int counter = 0;
		for (File fileEntry : dir.listFiles()) {
			if (!fileEntry.isDirectory()) {
				try{
					PPMImage image = new PPMImage(fileEntry);
					PPMImageList.add(image);
					if(counter == 0){
						stackedImage = new PPMImage(fileEntry);
						System.out.println("Applying Stacking Algorithm to files:");
					}
					System.out.println(fileEntry.getName());
				}
				catch(UnsupportedFileFormatException ex) {
					System.out.println("An UnsupportedFileFormatException occured");
					System.out.println(ex.getMessage());
				}
				counter++;
			}
		}
	}

	//uses the stacking algorithm to create a single noise-free image out of multiple images
	public void stack(){
		short redAverage = 0, greenAverage = 0, blueAverage = 0;

		for(int i = 0; i < stackedImage.getHeight(); i++){
			for(int j = 0; j < stackedImage.getWidth(); j++){
				for(int k = 0; k < PPMImageList.size(); k++){
					redAverage += PPMImageList.get(k).pixelArray[i][j].getRed();
					greenAverage += PPMImageList.get(k).pixelArray[i][j].getGreen();
					blueAverage += PPMImageList.get(k).pixelArray[i][j].getBlue();
				}
				redAverage = (short)(redAverage / PPMImageList.size());
				greenAverage = (short)(greenAverage / PPMImageList.size());
				blueAverage = (short)(blueAverage / PPMImageList.size());

				stackedImage.pixelArray[i][j] = new RGBPixel(redAverage, greenAverage, blueAverage);

				redAverage = 0;
				greenAverage = 0;
				blueAverage = 0;
			}
		}
	}

	public PPMImage getStackedImage(){
		return stackedImage;
	}
}
