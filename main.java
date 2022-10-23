import javax.imageio.*;
import java.io.*;
import java.awt.image.*;
import java.util.Scanner;

public class main {
    //Returns the brightness (average of RGB values) for each pixel
    public static int[][] pixelBrightness(BufferedImage img) {
        final int width = img.getWidth();
        final int height = img.getHeight();

        int result[][] = new int[height][width];
        
        for(int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++)  {
                int red = (img.getRGB(col, row) & 0xff0000) >> 16; //Red
                int green = (img.getRGB(col, row) & 0xff00) >> 8; //Green
                int blue = img.getRGB(col, row) & 0xff; //Blue

                result[row][col] = (red + green + blue + 3) / 3; //Brightness
            }
        }

        return result;
    }


    public static void main(String[] args) {
        char density[] = " _.,-=+:;cba!?0123456789$W#@Ñ".toCharArray();
        final int dLength = density.length;
        final int dStep = (int)256 / dLength + 1;

        //Ask for image
        Scanner in = new Scanner(System.in);
        System.out.println("Please put an image inside of the images folder and provide its name:");
        String imgName = in.nextLine();

        in.close();

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("./images/" + imgName));
        } catch (IOException e) {
            System.out.println("Couldn't read file");
        }

        int[][] imgRGB = pixelBrightness(img); //Pixel Brightnessvalues

        System.out.println(256 / dStep);

        //Print the ASCII Art by mapping the density string to the brightness
        for(int row = 0; row < imgRGB.length; row++) {
            for(int col = 0; col < imgRGB[row].length; col++) {
                System.out.print(density[(int) imgRGB[row][col] / dStep]);
                for(int i = 0; i < dStep/4; i++) {
                    System.out.print(" ");
                }
            }
            System.out.print("\n");
        }
    }
}