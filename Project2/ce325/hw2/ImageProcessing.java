package ce325.hw2;

import java.awt.image.BufferedImage;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.io.*;

//implements a graphical user interface for the main program which demonstrates all the other classes
public class ImageProcessing{
	BufferedImage bImage = null;
	File f = null;
	JMenuBar menubar;
	JMenu file, actions, open, save, stackingAlgorithm;
	JMenuItem grayscale, increaseSize, decreaseSize, rotateClockwise, equalizeHistogram, openPPM, openYUV, savePPM, saveYUV, multipleFiles, directory;
	JFrame frame;
	JLabel myLabel;
	ImageIcon myIcon;
	PPMImage ppmImage;

	public static void main(String args[]){
		new ImageProcessing();
	}

	public ImageProcessing(){
		Toolkit tk = Toolkit.getDefaultToolkit();

		Dimension dim = tk.getScreenSize();

		frame = new JFrame();

		frame.setSize(400, 400);

		int xPos = (dim.width / 2) - (frame.getWidth() / 2);
		int yPos = (dim.height / 2) - (frame.getHeight() / 2);

		frame.setLocation(xPos, yPos);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Define the title for the frame
		frame.setTitle("Image Processing");

		// creation of the menubar, the menus, submenus and menu items

		menubar = new JMenuBar();
		frame.setJMenuBar(menubar);

		file = new JMenu("File");
		menubar.add(file);

		open = new JMenu("Open");
		file.add(open);

		openPPM = new JMenuItem("PPM File");
		open.add(openPPM);

		openYUV = new JMenuItem("YUV File");
		open.add(openYUV);

		save = new JMenu("Save");
		file.add(save);

		savePPM = new JMenuItem("PPM File");
		save.add(savePPM);

		saveYUV = new JMenuItem("YUV File");
		save.add(saveYUV);

		actions = new JMenu("Actions");
		menubar.add(actions);

		grayscale = new JMenuItem("Grayscale");
		actions.add(grayscale);

		increaseSize = new JMenuItem("Increase Size");
		actions.add(increaseSize);

		decreaseSize = new JMenuItem("Decrease Size");
		actions.add(decreaseSize);

		rotateClockwise = new JMenuItem("Rotate Clockwise");
		actions.add(rotateClockwise);

		equalizeHistogram = new JMenuItem("Equalize Histogram");
		actions.add(equalizeHistogram);

		stackingAlgorithm = new JMenu("Stacking Algorithm");
		actions.add(stackingAlgorithm);

		multipleFiles = new JMenuItem("Select multiple files");
		stackingAlgorithm.add(multipleFiles);

		directory = new JMenuItem("Select directory");
		stackingAlgorithm.add(directory);

		//listen for menu item clicks

		MenuAction listener = new MenuAction();

		openPPM.addActionListener(listener);
		openYUV.addActionListener(listener);
		savePPM.addActionListener(listener);
		saveYUV.addActionListener(listener);
		grayscale.addActionListener(listener);
		increaseSize.addActionListener(listener);
		decreaseSize.addActionListener(listener);
		rotateClockwise.addActionListener(listener);
		equalizeHistogram.addActionListener(listener);
		multipleFiles.addActionListener(listener);
		directory.addActionListener(listener);

		frame.setJMenuBar(menubar);
		frame.setVisible(true);
	}

	private class MenuAction implements ActionListener{


		public void actionPerformed(ActionEvent e){

			if(e.getSource() == openPPM){
				JFileChooser fc = new JFileChooser();
				int result = fc.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					File f = fc.getSelectedFile();
					try{
						ppmImage = new PPMImage(f);

						bImage = new BufferedImage(ppmImage.getWidth(), ppmImage.getHeight(), BufferedImage.TYPE_INT_RGB);
						for(int i = 0; i < ppmImage.getHeight(); i++){
							for(int j = 0; j < ppmImage.getWidth(); j++){
								bImage.setRGB(j,i,ppmImage.pixelArray[i][j].getRGBValue());
							}
						}
						frame.getContentPane().removeAll();
						frame.getContentPane().setLayout(new FlowLayout());
						myIcon = new ImageIcon(bImage);
						myLabel = new JLabel(myIcon);
						frame.getContentPane().add(myLabel);
						frame.pack();
					}
					catch(UnsupportedFileFormatException ex){
						System.out.println("An UnsupportedFileFormatException occured");
						System.out.println(ex.getMessage());
					}
				}
			}

			if(e.getSource() == openYUV){
				JFileChooser fc = new JFileChooser();
				int result = fc.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					File f = fc.getSelectedFile();
					try{
						YUVImage yuvImage = new YUVImage(f);

						ppmImage = new PPMImage(new RGBImage(yuvImage));

						bImage = new BufferedImage(ppmImage.getWidth(), ppmImage.getHeight(), BufferedImage.TYPE_INT_RGB);
						for(int i = 0; i < ppmImage.getHeight(); i++){
							for(int j = 0; j < ppmImage.getWidth(); j++){
								bImage.setRGB(j,i,ppmImage.pixelArray[i][j].getRGBValue());
							}
						}
						frame.getContentPane().removeAll();
						frame.getContentPane().setLayout(new FlowLayout());
						myIcon = new ImageIcon(bImage);
						myLabel = new JLabel(myIcon);
						frame.getContentPane().add(myLabel);
						frame.pack();
					}
					catch(UnsupportedFileFormatException ex){
						System.out.println("An UnsupportedFileFormatException occured");
						System.out.println(ex.getMessage());
					}
				}
			}

			if(e.getSource() == savePPM){
				JFileChooser fc = new JFileChooser();
				int result = fc.showSaveDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					ppmImage.toFile(fc.getSelectedFile());
				}
			}

			if(e.getSource() == saveYUV){
				JFileChooser fc = new JFileChooser();
				int result = fc.showSaveDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					new YUVImage(ppmImage).toFile(fc.getSelectedFile());
				}
			}

			if(e.getSource() == grayscale){
				ppmImage.grayscale();

				bImage = new BufferedImage(ppmImage.getWidth(), ppmImage.getHeight(), BufferedImage.TYPE_INT_RGB);
				for(int i = 0; i < ppmImage.getHeight(); i++){
					for(int j = 0; j < ppmImage.getWidth(); j++){
						bImage.setRGB(j,i,ppmImage.pixelArray[i][j].getRGBValue());
					}
				}
				frame.getContentPane().removeAll();
				frame.getContentPane().setLayout(new FlowLayout());
				myIcon = new ImageIcon(bImage);
				myLabel = new JLabel(myIcon);
				frame.getContentPane().add(myLabel);
				frame.pack();
			}
			if(e.getSource() == increaseSize){
				ppmImage.doublesize();

				bImage = new BufferedImage(ppmImage.getWidth(), ppmImage.getHeight(), BufferedImage.TYPE_INT_RGB);
				for(int i = 0; i < ppmImage.getHeight(); i++){
					for(int j = 0; j < ppmImage.getWidth(); j++){
						bImage.setRGB(j,i,ppmImage.pixelArray[i][j].getRGBValue());
					}
				}
				frame.getContentPane().removeAll();
				frame.getContentPane().setLayout(new FlowLayout());
				myIcon = new ImageIcon(bImage);
				myLabel = new JLabel(myIcon);
				frame.getContentPane().add(myLabel);
				frame.pack();
			}
			if(e.getSource() == decreaseSize){
				ppmImage.halfsize();

				bImage = new BufferedImage(ppmImage.getWidth(), ppmImage.getHeight(), BufferedImage.TYPE_INT_RGB);
				for(int i = 0; i < ppmImage.getHeight(); i++){
					for(int j = 0; j < ppmImage.getWidth(); j++){
						bImage.setRGB(j,i,ppmImage.pixelArray[i][j].getRGBValue());
					}
				}
				frame.getContentPane().removeAll();
				frame.getContentPane().setLayout(new FlowLayout());
				myIcon = new ImageIcon(bImage);
				myLabel = new JLabel(myIcon);
				frame.getContentPane().add(myLabel);
				frame.pack();
			}
			if(e.getSource() == rotateClockwise){
				ppmImage.rotateClockwise();

				bImage = new BufferedImage(ppmImage.getWidth(), ppmImage.getHeight(), BufferedImage.TYPE_INT_RGB);
				for(int i = 0; i < ppmImage.getHeight(); i++){
					for(int j = 0; j < ppmImage.getWidth(); j++){
						bImage.setRGB(j,i,ppmImage.pixelArray[i][j].getRGBValue());
					}
				}
				frame.getContentPane().removeAll();
				frame.getContentPane().setLayout(new FlowLayout());
				myIcon = new ImageIcon(bImage);
				myLabel = new JLabel(myIcon);
				frame.getContentPane().add(myLabel);
				frame.pack();
			}
			if(e.getSource() == equalizeHistogram){
				YUVImage yuvImage = new YUVImage(ppmImage);
				yuvImage.equalize();

				ppmImage = new PPMImage(new RGBImage(yuvImage));

				bImage = new BufferedImage(ppmImage.getWidth(), ppmImage.getHeight(), BufferedImage.TYPE_INT_RGB);
				for(int i = 0; i < ppmImage.getHeight(); i++){
					for(int j = 0; j < ppmImage.getWidth(); j++){
						bImage.setRGB(j,i,ppmImage.pixelArray[i][j].getRGBValue());
					}
				}
				frame.getContentPane().removeAll();
				frame.getContentPane().setLayout(new FlowLayout());
				myIcon = new ImageIcon(bImage);
				myLabel = new JLabel(myIcon);
				frame.getContentPane().add(myLabel);
				frame.pack();
			}
			if(e.getSource() == multipleFiles){
				JFileChooser fc = new JFileChooser();

				fc.setDialogTitle("Choose Directory");
				fc.setMultiSelectionEnabled(true);

				int result = fc.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					File tempDir = new File("tempDir");
					tempDir.mkdirs();

					File[] files = fc.getSelectedFiles();

					for(int i = 0; i < files.length; i++){
						InputStream is = null;
						OutputStream os = null;
						File source = files[i];
						File dest = new File("tempDir/"+i);
						try {
							is = new FileInputStream(source);
							os = new FileOutputStream(dest);
							byte[] buffer = new byte[1024];
							int length;
							while ((length = is.read(buffer)) > 0) {
								os.write(buffer, 0, length);
							}
						}
						catch(FileNotFoundException ex1) {
							System.out.println("A FileNotFoundException occured");
							System.out.println(ex1.toString());
						}
						catch(IOException ex2){
							System.out.println("An IOException occured");
							System.out.println(ex2.toString());
						}
					}

					PPMImageStacker stacker = new PPMImageStacker(tempDir);
					stacker.stack();
					ppmImage = stacker.getStackedImage();

					bImage = new BufferedImage(ppmImage.getWidth(), ppmImage.getHeight(), BufferedImage.TYPE_INT_RGB);
					for(int i = 0; i < ppmImage.getHeight(); i++){
						for(int j = 0; j < ppmImage.getWidth(); j++){
							bImage.setRGB(j,i,ppmImage.pixelArray[i][j].getRGBValue());
						}
					}

					frame.getContentPane().removeAll();
					frame.getContentPane().setLayout(new FlowLayout());
					myIcon = new ImageIcon(bImage);
					myLabel = new JLabel(myIcon);
					frame.getContentPane().add(myLabel);
					frame.pack();

					if (tempDir.isDirectory()) {
						File[] children = tempDir.listFiles();
						for (int i = 0; i < children.length; i++) {
							children[i].delete();
						}
					}
					tempDir.delete();
				}
			}
			if(e.getSource() == directory){
				JFileChooser fc = new JFileChooser();

				fc.setDialogTitle("Choose Directory");
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

				int result = fc.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					File f = fc.getSelectedFile();
					PPMImageStacker stacker = new PPMImageStacker(f);
					stacker.stack();
					ppmImage = stacker.getStackedImage();

					bImage = new BufferedImage(ppmImage.getWidth(), ppmImage.getHeight(), BufferedImage.TYPE_INT_RGB);
					for(int i = 0; i < ppmImage.getHeight(); i++){
						for(int j = 0; j < ppmImage.getWidth(); j++){
							bImage.setRGB(j,i,ppmImage.pixelArray[i][j].getRGBValue());
						}
					}

					frame.getContentPane().removeAll();
					frame.getContentPane().setLayout(new FlowLayout());
					myIcon = new ImageIcon(bImage);
					myLabel = new JLabel(myIcon);
					frame.getContentPane().add(myLabel);
					frame.pack();
				}
			}
		}
	}
}
