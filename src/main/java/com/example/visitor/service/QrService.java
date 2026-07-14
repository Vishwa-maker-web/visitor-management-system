package com.example.visitor.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;


import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class QrService {
    public String generateQr(String data , String email){
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE,300,300);
            String safeEmail = email.replace("@", "").replace(".", "");
            String filePath = "C:/qr/" + safeEmail + ".png";
            Path path = Paths.get(filePath);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
            return "http://localhost:4040/qr/"+safeEmail+".png" ;
        } catch (Exception e) {
            e.printStackTrace();
            return "QR Generation Failed";
        }
    }
}
