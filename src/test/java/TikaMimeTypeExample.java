
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.tika.Tika;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author namnguyen
 */
public class TikaMimeTypeExample {
 public static void main(String[] args) {
        // Đường dẫn đến file cần kiểm tra
        File file = new File("C:\\Users\\namnguyen\\Downloads\\unnamed.png");
        
        // Tạo đối tượng Tika
        Tika tika = new Tika();
        
        try (FileInputStream inputStream = new FileInputStream(file)) {
            // Phát hiện MIME type của file
            String mimeType = tika.detect(inputStream);
            
            // In ra kết quả MIME type
            System.out.println("MIME type của file là: " + mimeType);
            
            // Kiểm tra MIME type có phải là một định dạng hình ảnh hay không
            if (mimeType.startsWith("image/")) {
                System.out.println("Đây là một file ảnh.");
            } else {
                System.out.println("File này không phải là ảnh.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
