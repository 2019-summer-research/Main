import okhttp3.*;
import okio.BufferedSink;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import javax.xml.stream.FactoryConfigurationError;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;
public class FacialDetection {
    public static final MediaType JSON = MediaType.get("application/octet-stream; charset=utf-8");


    String FaceBody(byte[] FaceB)
    {
        return "{"+FaceB+"}";
    }



    OkHttpClient client = new OkHttpClient();

    String post(String url, byte[] bys) throws IOException{
        RequestBody body = RequestBody.create(bys,JSON);


        Request request = new Request.Builder()
                .url(url)

                .addHeader("Content-Type","application/octet-stream")
                .addHeader("Ocp-Apim-Subscription-Key", "7dbbaea80ecf46dd85dfed140bd025a4")
                .post(body)
                .build();
        //C:\Users\snowy\Desktop\Images\EthanAndGinger.JPG
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }



    public static void main(String[] args) throws IOException {
        Scanner SC = new Scanner(System.in);
        FacialDetection FD = new FacialDetection();

        String Path = SC.nextLine();
        File fi = new File(Path);
        byte[] fileContent = Files.readAllBytes(fi.toPath());

        String Response = FD.post("https://eastus.api.cognitive.microsoft.com/face/v1.0/detect?returnFaceId=true",fileContent);
        System.out.println(Response);

    }

}
