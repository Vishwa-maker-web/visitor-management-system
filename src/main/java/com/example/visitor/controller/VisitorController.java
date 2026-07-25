package com.example.visitor.controller;
import com.example.visitor.dto.LoginRequest;
import com.example.visitor.dto.ReportDTO;
import com.example.visitor.dto.VerifyOtpRequest;
import com.example.visitor.entity.Visitor;
import com.example.visitor.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://13.206.110.245")
@RestController
@RequestMapping("/api/visitors")
public class VisitorController {
    @Autowired
    private VisitorService visitorService;

    @PostMapping("/register")
    public String register(@RequestBody Visitor visitor){
        return visitorService.register(visitor);
    }
    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestBody VerifyOtpRequest request){
      return visitorService.verifyOtp(
              request.getEmail(),
              request.getOtp()
      );
    }

 @GetMapping("/all")
    public List<Visitor> getAllVisitors() {
        return visitorService.getAllVisitors();
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteVisitor(@PathVariable Long id){
        visitorService.deleteVisitor(id);
        return ResponseEntity.ok("visitor deleted successfully");
    }


    @GetMapping("/reports")
    public ReportDTO getReport() {
        return visitorService.getReport();
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
    return visitorService.login(request.getEmail());
    }

     @PostMapping("/verify-login-otp")
   public String verifyLoginOtp(@RequestBody VerifyOtpRequest request) {
    return visitorService.verifyLoginOtp(
           request.getEmail(),
           request.getOtp()
    );
}

}
