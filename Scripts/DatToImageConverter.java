package passport;



import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import javax.imageio.ImageIO;
import org.jmrtd.lds.icao.DG2File;
import org.jmrtd.lds.iso19794.FaceImageInfo;
import org.jmrtd.lds.iso19794.FaceInfo;

public class DatToImageConverter {

    public static void main(String[] args) {
        try {
            // Replace with the path to your .dat file
            File datFile = new File(args[0]);

            // Read the DG2File from the .dat file
            InputStream is = new FileInputStream(datFile);
            DG2File dg2File = new DG2File(is);

            // Get the FaceInfo and FaceImageInfo objects
            List<FaceInfo> faceInfos = dg2File.getFaceInfos();
            FaceInfo faceInfo = faceInfos.get(0);  // Assuming only one face info
            List<FaceImageInfo> faceImageInfos = faceInfo.getFaceImageInfos();
            FaceImageInfo faceImageInfo = faceImageInfos.get(0);  // Assuming only one face image info

            // Get the image input stream
            InputStream imageStream = faceImageInfo.getImageInputStream();

            // Convert the input stream back to a BufferedImage
            BufferedImage restoredImage = ImageIO.read(imageStream);

            // Save the BufferedImage as a new image file (JPEG or PNG)
            File outputImageFile = new File("C:\\Users\\Haseeb Javed\\OneDrive\\Desktop\\passportwork\\restored_face_image.jpg");
            ImageIO.write(restoredImage, "JPEG", outputImageFile);

            System.out.println("Image successfully restored from .dat file: " + outputImageFile.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
