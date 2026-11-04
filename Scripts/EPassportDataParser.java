package passport;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.imageio.ImageIO;

import org.jmrtd.lds.icao.DG2File;
import org.jmrtd.lds.iso19794.FaceImageInfo;
import org.jmrtd.lds.iso19794.FaceInfo;

public class EPassportDataParser {

    public static void main(String[] args) {
        try {
            // Replace with the path to your .dat file
//            File datFile = new File("C:\\\\Users\\\\Haseeb Javed\\\\OneDrive\\\\Desktop\\\\passportwork\\\\IRL-PK7030690-DG2 File.dat");
            File datFile = new File("C:\\\\Users\\\\Haseeb Javed\\\\OneDrive\\\\Desktop\\\\passportwork\\\\output_face_image.dat");
            
            

            // Read the .dat file using FileInputStream
            FileInputStream fis = new FileInputStream(datFile);

            // Parse the .dat file as a DG2 file (Face image data)
            DG2File dg2File = new DG2File(fis);

            // Get FaceInfo objects from DG2File
            List<FaceInfo> faceInfos = dg2File.getFaceInfos();

            // Loop through each FaceInfo and extract the FaceImageInfo
            for (FaceInfo faceInfo : faceInfos) {
                List<FaceImageInfo> faceImageInfos = faceInfo.getFaceImageInfos();

                // Loop through each FaceImageInfo
                for (FaceImageInfo faceImageInfo : faceImageInfos) {
                    System.out.println("Face Image Format: " + faceImageInfo.getFaceImageType());
                    System.out.println("Image Width: " + faceImageInfo.getWidth());
                    System.out.println("Image Height: " + faceImageInfo.getHeight());

                    // Get the face image as bytes
                    byte[] imageBytes = faceImageInfo.getEncoded();

                    // Convert the byte array to a BufferedImage
                    ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
                    BufferedImage faceImage = ImageIO.read(bais);

                    // Save the image as a JPEG file
                    File outputImage = new File("face_image.jpg");
                    ImageIO.write(faceImage, "JPEG", outputImage);

                    System.out.println("Image saved to: " + outputImage.getAbsolutePath());
                }
            }

            // Close the input stream
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

