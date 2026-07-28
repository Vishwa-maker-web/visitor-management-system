package com.example.visitor.service;
import com.example.visitor.dto.ReportDTO;
import com.example.visitor.entity.Blacklist;
import com.example.visitor.entity.Visitor;
import com.example.visitor.entity.OtpVerification;

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
import com.example.visitor.entity.ReceptionQueue;
import com.example.visitor.repository.ReceptionQueueRepository;

 

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
    private EmailService emailService;
    @Autowired
    private ReceptionQueueRepository receptionQueueRepository; 
   
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
        String otp = otpservice.generateOtp();
        OtpVerification otpData = new OtpVerification();

        otpData.setEmail(visitor.getEmail());
       otpData.setOtp(otp);
        visitor.setPassExpiry(LocalDateTime.now().plusHours(8));
//     otpRepository.deleteByEmail(visitor.getEmail()); 
       otpRepository.save(otpData);
   
     String phone = visitor.getPhone();
        if (!phone.startsWith("+91")){
            phone = "+91" + phone;
        }
        visitor.setPhone(phone);
        pendingVisitors.put(visitor.getEmail(),visitor);
        emailService.sendOtp(visitor.getEmail(),otp);

        return "Otp sent successfully";
    }


    public String verifyOtp(String email, String otp) {

     Visitor visitor = pendingVisitors.get(email);

     if (visitor == null) {

     Optional<Visitor> optionalVisitor = visitorRepository.findByEmail(email);

     if (optionalVisitor.isEmpty()) { 
        return "Visitor not found";
    }

    visitor = optionalVisitor.get();
    }

         Optional<OtpVerification> otpData = otpRepository.findByEmailAndOtp(email,otp);

         if (otpData.isEmpty()) {
        return "Invalid OTP";
     }
        if (otpData.isPresent() ) {

            visitor.setPassExpiry(
                    LocalDateTime.now().plusHours(8)
            );


            visitorRepository.save(visitor);
           
           
        ReceptionQueue queue = new ReceptionQueue();
      
     queue.setEmail(visitor.getEmail());
    queue.setName(visitor.getName());
    queue.setPurpose(visitor.getPurpose());
    queue.setStatus("WAITING");

receptionQueueRepository.save(queue);

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

    public String login(String email) {

    Optional<Visitor> optionalVisitor = visitorRepository.findByEmail(email);

    if (optionalVisitor.isEmpty()) {
        return "Visitor not found";
    }

    Visitor visitor = optionalVisitor.get();

            String otp = otpservice.generateOtp();

           OtpVerification otpData = new OtpVerification();
           otpData.setEmail(email);
          otpData. setOtp(otp);
//     otpRepository.deleteByEmail(email);
           otpRepository.save(otpData);

                emailService.sendOtp(email, otp);

           return "OTP sent successfully";
}

public String verifyLoginOtp(String email, String otp) {

    Optional<Visitor> optionalVisitor = visitorRepository.findByEmail(email);

    if (optionalVisitor.isEmpty()) {
        return "Visitor not found";
    }

    Visitor visitor = optionalVisitor.get();

     Optional<OtpVerification> otpData =
        otpRepository.findByEmailAndOtp(email, otp);

if (otpData.isEmpty()) {
    return "Invalid OTP";
}

    
        visitor.setPassExpiry(LocalDateTime.now().plusHours(8));
        visitorRepository.save(visitor);

        String qrData = "EMAIL=" + visitor.getEmail() + "|ID=" + visitor.getIdNumber();

        return qrService.generateQr(qrData, email);
   
}
}
