package ce325.hw2;

public interface Image{

	//convert image to the equivalent grayscale image
	public void grayscale();

	//doubles the size of the image
	public void doublesize();

	//halves the size of the image
	public void halfsize();

	//convert image to the equivalent image rotated by 90 degrees
	public void rotateClockwise();
}
