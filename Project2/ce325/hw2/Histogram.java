package ce325.hw2;

import java.io.File;
import java.io.PrintWriter;

//creates and equalizes the histogram of a YUVImage
public class Histogram{
	private YUVImage image;
	private int [] histogram_array = new int[236];
	private double [] probability = new double[236];
	private double [] cumulativeProbability = new double[236];
	private int [] probabilityAlteration = new int[236];

	public Histogram(YUVImage img){
		short yValue;
		for(int i = 0; i <= 235; i++){
			histogram_array[i] = 0;
			probability[i] = 0;
			cumulativeProbability[i] = 0;
			probabilityAlteration[i] = 0;
		}
		image = img;

		for(int i = 0; i < img.getHeight(); i++){
			for(int j = 0; j < img.getWidth(); j++){
				yValue = img.pixelArray[i][j].getY();
				histogram_array[yValue]++;
			}
		}
	}

	//print the histogram of the image
	public String toString(){
		StringBuffer str = new StringBuffer();
		int numberOfAsterisks = 0;
		int mostFrequentBrightness = 0;
		int maxNumberOfAsterisks = 80;

		for(int i = 0; i < histogram_array.length; i++){
			if(histogram_array[i] > mostFrequentBrightness){
				mostFrequentBrightness = i;
			}
		}

		for(int i = 0; i <= 235; i++){
			str.append(i + " ");
			if(histogram_array[mostFrequentBrightness] > 80){
				numberOfAsterisks = maxNumberOfAsterisks * histogram_array[i] / histogram_array[mostFrequentBrightness];
			}else{
				numberOfAsterisks = histogram_array[i];
			}

			for(int j = 0; j < numberOfAsterisks; j++){
				str.append("*");
			}
			str.append("\n");
		}
		return str.toString();
	}

	//save the YUVImage to a file
	public void toFile(File file){
		try{
			PrintWriter pfile = new PrintWriter(file);
			pfile.print(this.toString());
			pfile.close();
		}
		catch(java.io.FileNotFoundException ex) {
			System.out.println("A FileNotFoundException occured");
			System.out.println(ex.toString());
		}
	}

	//equalize the histogram of the YUVImage
	public void equalize(){
		int maxBrightness = 0;

		for(int i = 0; i <= 235; i++){
			probability[i] = (double)histogram_array[i] / (double)(image.getHeight() * image.getWidth());
		}

		for(int i = 0; i <= 235; i++){
			for(int j = 0; (j <= i); j++){
				cumulativeProbability[i] += probability[j];
			}
		}
		// find the max brightness
		for(int i = 235; i >= 0; i--){
			if(histogram_array[i] > 0){
				maxBrightness = i;
				break;
			}
		}

		for(int i = 0; i <= 235; i++){
			probabilityAlteration[i] = (int)((double)maxBrightness * cumulativeProbability[i]);
		}
	}

	public short getEqualizedLuminocity(int luminocity){
		return (short)probabilityAlteration[luminocity];
	}
}
