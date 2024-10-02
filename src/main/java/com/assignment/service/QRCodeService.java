package com.assignment.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

/**
 *
 * @author namnguyen
 */
public class QRCodeService {
    public String generateQRCode(String qrCodeData, int size) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeData, BarcodeFormat.QR_CODE, size, size);
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();

        ImageIO.write(MatrixToImageWriter.toBufferedImage(bitMatrix), "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();

        String base64Image = Base64.getEncoder().encodeToString(pngData);
        return "data:image/png;base64," + base64Image;
    }
}
