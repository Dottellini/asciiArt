import javax.imageio.*;
import java.io.*;
import java.awt.image.*;
import java.util.Scanner;
import java.awt.Image;

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

    //Resize image
    static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }

    public static void main(String[] args) {
        char density[] = " _.,-=+:;cba!?0123456789$W#@Ñ".toCharArray();
        final int dLength = density.length;
        final int dStep = (int)256 / dLength + 1;
        final String win_regex = "([a-zA-Z]:)?(\\\\[a-z  A-Z0-9_.-]+)+.(txt|gif|jpg|png|jpeg|pdf|doc|docx|xls|xlsx|DMS)\\\\?";
        final String linux_regex = "^(/[^/]*)+.(txt|gif|jpg|png|jpeg|pdf|doc|docx|xls|xlsx|DMS)/?$";

        //Ask for image
        Scanner in = new Scanner(System.in);
        System.out.println("Please put an image inside of the images folder and provide its name or provide the full image path:");
        String imgName = in.nextLine();

        System.out.println("Please choose the dimensions you want your image to be (e.g. 500 for a max of 500x500(pixels). The Scale of the image wont change) or enter nothing to keep the original size:");
        int size = in.nextLine() != "" ? Integer.parseInt(in.nextLine()) : 0;

        in.close();

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(imgName.matches(win_regex) || imgName.matches(linux_regex) ? imgName : "./images/" + imgName));
        } catch (IOException e) {
            System.out.println("Couldn't read file");
        }

        if(size != 0) {
            img = resizeImage(img, size, size);
        }

        int[][] imgRGB = pixelBrightness(img); //Pixel Brightnessvalues

        System.out.println(256 / dStep);

        //Print the ASCII Art by mapping the density string to the brightness
        for(int row = 0; row < imgRGB.length; row++) {
            for(int col = 0; col < imgRGB[row].length; col++) {
                System.out.print(density[(int) imgRGB[row][col] / dStep]);
                for(int i = 0; i < dStep/6; i++) {
                    System.out.print(" ");
                }
            }
            System.out.print("\n");
        }
    }
}