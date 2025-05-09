package com.example.demo;

import edu.gemini.app.ocs.model.DataProcRequirement;
import edu.gemini.app.ocs.model.SciencePlan;
import edu.gemini.app.ocs.model.StarSystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Response.ResponseWrapper;
import com.example.demo.Enum.Role;
import com.example.demo.Model.Staff;
import com.example.demo.Repository.StaffRepository;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/api")
public class DemoController {
     @Autowired
    private StaffRepository staffRepository;

    @CrossOrigin
    @GetMapping("/home")
    public @ResponseBody String Home() {
        return "Hello Gemini";
    }

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, Object> body) throws JsonProcessingException {
        String username = body.get("username").toString();
        String password = body.get("password").toString();
        String firstName = body.get("firstName").toString();
        String lastName = body.get("lastName").toString();
        String roleString = body.get("role").toString();
        Role role;
        try {
            role = Role.valueOf(roleString);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseWrapper.error("The provided role is not valid", HttpStatus.BAD_REQUEST));
        }

        if (staffRepository.findByUsername(username).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseWrapper.error("Username already exists", HttpStatus.BAD_REQUEST));
        }

        String staffId = Staff.generateStaffId(staffRepository, role);
        Staff staff = new Staff(username, password, firstName, lastName, role);
        staff.setStaffId(staffId);
        Staff savedStaff = staffRepository.save(staff);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ResponseWrapper.success(savedStaff, "Staff registered successfully", HttpStatus.CREATED));
    }

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, Object> body) throws JsonProcessingException {
        String username = body.get("username").toString();
        String password = body.get("password").toString();

        Optional<Staff> optionalStaff = staffRepository.findByUsername(username);

        if (optionalStaff.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseWrapper.error("Username does not exist", HttpStatus.BAD_REQUEST));
        }

        Staff staff = optionalStaff.get();

        if (staff.getPassword().equals(password)) {
            return ResponseEntity.ok(ResponseWrapper.success(staff, "Login successful", HttpStatus.OK));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseWrapper.error("Incorrect password", HttpStatus.BAD_REQUEST));
        }
    }

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @GetMapping("/staffs")
    public ResponseEntity<?> getAllStaff() {
        List<Staff> staffs = staffRepository.getAllStaff();
        if (staffs.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseWrapper.notFound("No staffs found", HttpStatus.NOT_FOUND));
        }
        return ResponseEntity.ok(ResponseWrapper.success(staffs, "Staffs retrieved successfully", HttpStatus.OK));
    }

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @GetMapping("/staffId/{id}")
    public ResponseEntity<?> getStaffById(@PathVariable("id") String id) {
        Staff staff = staffRepository.getStaffById(id).orElse(null);
        if (staff == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseWrapper.notFound("Staff not found", HttpStatus.NOT_FOUND));
        }
        return ResponseEntity.ok(ResponseWrapper.success(staff, "Staff retrieved successfully", HttpStatus.OK));
    }

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @GetMapping("/staffName/{name}")
    public ResponseEntity<?> getStaffByFullName(@PathVariable("name") String name) {
        String[] names = name.split(" ");
        Staff staff = staffRepository.getStaffByFullName(names[0], names[1]).orElse(null);
        if (staff != null) {
            return ResponseEntity.ok(ResponseWrapper.success(staff, "Staff retrieved successfully", HttpStatus.OK));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseWrapper.notFound("Staff not found", HttpStatus.NOT_FOUND));
    }

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @DeleteMapping("/deleteStaff/{id}")
    public ResponseEntity<?> deleteStaff(@PathVariable("id") String id) {
        Staff staff = staffRepository.getStaffById(id).orElse(null);
        if (staff == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseWrapper.notFound("Staff not found", HttpStatus.NOT_FOUND));
        }
        staffRepository.delete(staff);
        return ResponseEntity.ok(ResponseWrapper.success(staff, "Staff deleted successfully", HttpStatus.OK));
    }

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @DeleteMapping("/deleteStaffs")
    public ResponseEntity<?> deleteAllStaff() {
        staffRepository.deleteAll();
        return ResponseEntity.ok(ResponseWrapper.success(null, "All staffs deleted successfully", HttpStatus.OK));
    }


    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @PostMapping("/createSciencePlan")
    public ResponseEntity<?> createSciencePlan(@RequestBody Map<String, Object> body) {
        OCS o = new OCS();
        DataProcRequirement dataProcRequirement = new DataProcRequirement();
        String fileType = body.get("fileType").toString();
        String fileQuality = body.get("fileQuality").toString();
        String colorType = body.get("colorType").toString();
        double contrast = Double.parseDouble(body.get("contrast").toString());
        double brightness = Double.parseDouble(body.get("brightness").toString());
        double saturation = Double.parseDouble(body.get("saturation").toString());
        double highlights = Double.parseDouble(body.get("highlights").toString());
        double exposure = Double.parseDouble(body.get("exposure").toString());
        double shadows = Double.parseDouble(body.get("shadows").toString());
        double whites = Double.parseDouble(body.get("whites").toString());
        double blacks = Double.parseDouble(body.get("blacks").toString());
        double luminance = Double.parseDouble(body.get("luminance").toString());
        double hue = Double.parseDouble(body.get("hue").toString());

        dataProcRequirement.setFileType(fileType);
        dataProcRequirement.setFileQuality(fileQuality);
        dataProcRequirement.setColorType(colorType);
        dataProcRequirement.setContrast(contrast);
        dataProcRequirement.setBrightness(brightness);
        dataProcRequirement.setSaturation(saturation);
        dataProcRequirement.setHighlights(highlights);
        dataProcRequirement.setExposure(exposure);
        dataProcRequirement.setShadows(shadows);
        dataProcRequirement.setWhites(whites);
        dataProcRequirement.setBlacks(blacks);
        dataProcRequirement.setLuminance(luminance);
        dataProcRequirement.setHue(hue);

        String creator = body.get("creator").toString();
        String submitter = body.get("submitter").toString();
        double funding = Double.parseDouble(body.get("fundingInUSD").toString());
        String objectives = body.get("objectives").toString();
        String starSystem = body.get("starSystem").toString();
        String telescopeLocation = body.get("telescopeLocation").toString();
        String startDate = body.get("startDate").toString();
        String endDate = body.get("endDate").toString();

        Staff creatorStaff = staffRepository.getStaffByFullName(creator.split(" ")[0], creator.split(" ")[1]).orElse(null);
        if (creatorStaff == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseWrapper.error("The provided creator is not valid", HttpStatus.BAD_REQUEST));
        }

        Staff submitterStaff = staffRepository.getStaffByFullName(submitter.split(" ")[0], submitter.split(" ")[1]).orElse(null);
        if (submitterStaff == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseWrapper.error("The provided submitter is not valid", HttpStatus.BAD_REQUEST));
        }

        StarSystem.CONSTELLATIONS starSystemEnum = null;
        try {
            starSystemEnum = StarSystem.CONSTELLATIONS.valueOf(starSystem);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseWrapper.error("The provided star system is not valid", HttpStatus.BAD_REQUEST));
        }

        SciencePlan.TELESCOPELOC telescopeLocationEnum = null;
        try {
            telescopeLocationEnum = SciencePlan.TELESCOPELOC.valueOf(telescopeLocation);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseWrapper.error("The provided telescope location is not valid", HttpStatus.BAD_REQUEST));
        }
        
        SciencePlan sciencePlan = new SciencePlan();
        sciencePlan.setCreator(creator);
        sciencePlan.setSubmitter(submitter);
        sciencePlan.setFundingInUSD(funding);
        sciencePlan.setObjectives(objectives);
        sciencePlan.setStarSystem(starSystemEnum);
        sciencePlan.setTelescopeLocation(telescopeLocationEnum);
        sciencePlan.setStartDate(startDate);
        sciencePlan.setEndDate(endDate);
        sciencePlan.setDataProcRequirements(dataProcRequirement);
        
        String message = o.createSciencePlan(sciencePlan);

        if (!message.contains("images.txt")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseWrapper.error(message, HttpStatus.BAD_REQUEST));
        }
        
        ArrayList<SciencePlan> sciencePlans = o.getAllSciencePlans();
        SciencePlan createdSciencePlan = o.getSciencePlanByNo(sciencePlans.size());

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.success(createdSciencePlan, "Science plan created successfully", HttpStatus.CREATED));
    }

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @GetMapping("/sciencePlans")
    public ResponseEntity<?> getAllSciencePlans() {
        OCS o = new OCS();
        ArrayList<SciencePlan> sciencePlans = o.getAllSciencePlans();
        if (sciencePlans.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseWrapper.notFound("No science plans found", HttpStatus.NOT_FOUND));
        }
        
        return ResponseEntity.ok(ResponseWrapper.success(sciencePlans, "Science plans retrieved successfully", HttpStatus.OK));
    }

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @GetMapping("/sciencePlan/{id}")
    public ResponseEntity<?> getSciencePlanById(@PathVariable("id") int id) {
        OCS o = new OCS();
        SciencePlan sciencePlan = o.getSciencePlanByNo(id);
        if (sciencePlan == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseWrapper.notFound("Science plan not found", HttpStatus.NOT_FOUND));
        }
        return ResponseEntity.ok(ResponseWrapper.success(sciencePlan, "Science plan retrieved successfully", HttpStatus.OK));
    }

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @DeleteMapping("/deleteSciencePlan/{id}")
    public ResponseEntity<?> deleteSciencePlan(@PathVariable("id") int id) {
        OCS o = new OCS();
        SciencePlan sciencePlan = o.getSciencePlanByNo(id);
        if (sciencePlan == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseWrapper.notFound("Science plan not found", HttpStatus.NOT_FOUND));
        }
        o.deleteSciencePlanByNo(id);
        return ResponseEntity.ok(ResponseWrapper.success(sciencePlan, "Science plan deleted successfully", HttpStatus.OK));
    }

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @DeleteMapping("/deleteSciencePlans")
    public ResponseEntity<?> deleteAllSciencePlan() {
        OCS o = new OCS();
        o.deleteAllSciencePlans();
        return ResponseEntity.ok(ResponseWrapper.success(null, "All science plans deleted successfully", HttpStatus.OK));
    } 

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @GetMapping("/submitSciencePlan/{id}")
    public ResponseEntity<?> submitSciencePlan(@PathVariable("id") int id) {
        OCS o = new OCS();
        SciencePlan sciencePlan = o.getSciencePlanByNo(id);

        if (sciencePlan == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseWrapper.notFound("Science plan not found", HttpStatus.NOT_FOUND));
        }

        String message = o.submitSciencePlan(sciencePlan);
        sciencePlan = o.getSciencePlanByNo(id);

        if (message.contains("submitted")) {
            return ResponseEntity.ok(ResponseWrapper.success(sciencePlan, "Science plan submitted successfully", HttpStatus.OK));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseWrapper.error(message, HttpStatus.BAD_REQUEST));
    }

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @GetMapping("/validateSciencePlan/{id}")
    public ResponseEntity<?> validateSciencePlan(@PathVariable("id") int id){
        OCS o = new OCS();
        SciencePlan sciencePlan = o.getSciencePlanByNo(id);

        if (sciencePlan == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseWrapper.notFound("Science plan not found", HttpStatus.NOT_FOUND));
        }

        o.updateSciencePlanStatus(id, SciencePlan.STATUS.VALIDATED);
        sciencePlan = o.getSciencePlanByNo(id);
        return ResponseEntity.ok(ResponseWrapper.success(sciencePlan, "Science plan validated successfully", HttpStatus.OK));
    }

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @GetMapping("/invalidateSciencePlan/{id}")
    public ResponseEntity<?> invalidateSciencePlan(@PathVariable("id") int id){
        OCS o = new OCS();
        SciencePlan sciencePlan = o.getSciencePlanByNo(id);

        if (sciencePlan == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseWrapper.notFound("Science plan not found", HttpStatus.NOT_FOUND));
        }

        o.updateSciencePlanStatus(id, SciencePlan.STATUS.INVALIDATED);
        sciencePlan = o.getSciencePlanByNo(id);
        return ResponseEntity.ok(ResponseWrapper.success(sciencePlan, "Science plan invalidated successfully", HttpStatus.OK));
    }

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @PostMapping("/updateSciencePlanStatus")
    public ResponseEntity<?> updateSciencePlanStatus(@RequestBody Map<String, Object> body){
        OCS o = new OCS();
        int planId = Integer.parseInt(body.get("planID").toString());
        SciencePlan sciencePlan = o.getSciencePlanByNo(planId);
        
        if (sciencePlan == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseWrapper.notFound("Science plan not found", HttpStatus.NOT_FOUND));
        }

        SciencePlan.STATUS status = null;

        try {
            status = SciencePlan.STATUS.valueOf(body.get("status").toString());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseWrapper.error("The provided status is not valid", HttpStatus.BAD_REQUEST));
        }

        o.updateSciencePlanStatus(Integer.parseInt(body.get("planID").toString()), status);
        sciencePlan = o.getSciencePlanByNo(planId);
        return ResponseEntity.ok(ResponseWrapper.success(sciencePlan, "Update status successfully: " + status, HttpStatus.OK));
    }

}
