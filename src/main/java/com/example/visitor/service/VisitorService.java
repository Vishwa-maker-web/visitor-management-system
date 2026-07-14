package com.example.visitor.service;
import com.example.visitor.dto.ReportDTO;
import com.example.visitor.entity.Blacklist;
import com.example.visitor.entity.Visitor;
import com.example.visitor.repository.BlacklistRepository;
import com.example.visitor.repository.OtpRepository;
import com.example.visitor.repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class VisitorService {
    @Autowired
    private VisitorRepository visitorRepository;
    @Autowired
    private BlacklistRepository blacklistRepository;
    @Autowired
    private OtpService otpservice;
    @Autowired
    private OtpRepository otpRepository;
    @Autowired
    private QrService qrService;
    @Autowired
    private SmsService smsService;
    public final Map<String , Visitor> pendingVisitors = new ConcurrentHashMap<>();

    public String register(Visitor visitor) {

        Optional<Blacklist> blocked = blacklistRepository.findByEmailOrPhone(
                visitor.getEmail(), visitor.getPhone());
        if (blocked.isPresent()){
            return "Access Denied - Blacklisted Visited";
        }

        Optional<Visitor> existing = visitorRepository.findByEmailOrPhone(
                visitor.getEmail(),visitor.getPhone());
        if (existing.isPresent()){
            return "Visitor already exists";
        }
//        String otp = otpservice.generateOtp();
//        OtpVerification otpData = new OtpVerification();
//
//        otpData.setEmail(visitor.getEmail());
//        otpData.setOtp(otp);
        visitor.setPassExpiry(LocalDateTime.now().plusHours(8));

//        otpRepository.save(otpData);
        String phone = visitor.getPhone();
        if (!phone.startsWith("+91")){
            phone = "+91" + phone;
        }
        visitor.setPhone(phone);
        pendingVisitors.put(visitor.getEmail(),visitor);
        smsService.sendOtp(visitor.getPhone());

        return "Otp sent successfully";
    }


    public String verifyOtp(String email, String otp) {

        Visitor visitor = pendingVisitors.get(email);

        if (visitor == null) {
            return "Pending visitor not found";
        }

        boolean verified =
                smsService.verifyOtp(
                        visitor.getPhone(),
                        otp
                );

        if (verified) {

            visitor.setPassExpiry(
                    LocalDateTime.now().plusHours(8)
            );


            visitorRepository.save(visitor);

            pendingVisitors.remove(email);

            String qrData = "EMAIL=" + visitor.getEmail() + "|ID=" + visitor.getIdNumber();

            return qrService.generateQr(qrData, email);
        }

        return "Invalid OTP";
    }
    public List<Visitor> getAllVisitors() {
        return visitorRepository.findAll();
    }
    public void deleteVisitor(Long id){
        visitorRepository.deleteById(id);
    }
    public ReportDTO getReport() {

        long total = visitorRepository.count();

        long interviews = visitorRepository.countByPurpose("interviews");

        long today = visitorRepository.count();

        long restricted = 0;

        return new ReportDTO(
                total,
                interviews,
                today,
                restricted
        );
    }
}
