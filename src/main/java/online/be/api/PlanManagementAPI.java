package online.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import online.be.model.request.PlanRequest;
import online.be.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/treatment-plan")
@SecurityRequirement(name = "api")
public class PlanManagementAPI {

    @Autowired
    PlanService planService;

    @GetMapping("/by-manager")
    public ResponseEntity getAllTreatmentPlanByManager() {
        return ResponseEntity.ok(planService.getAllTreatmentPlanByManager());
    }

    @GetMapping
    public ResponseEntity getAllTreatmentPlan() {
        return ResponseEntity.ok(planService.getAllTreatmentPlan());
    }

    @GetMapping("/{id}")
    public ResponseEntity getPlanById(@PathVariable long id) {
        return ResponseEntity.ok(planService.getPlanById(id));
    }

    @GetMapping("/{name}")
    public ResponseEntity getPlanByName(@PathVariable String name) {
        return ResponseEntity.ok(planService.getPlanByName(name));
    }

    @PostMapping
    public ResponseEntity createPlan(@RequestBody PlanRequest planRequest){
        return ResponseEntity.ok(planService.createPlan(planRequest));
    }

    @PutMapping
    public ResponseEntity updatePlan(@RequestBody PlanRequest planRequest) {
        return ResponseEntity.ok(planService.updatePlan(planRequest));
    }

    @DeleteMapping
    public ResponseEntity deletePlan(@RequestBody PlanRequest planRequest) {
        planService.deletePlan(planRequest);
        return ResponseEntity.ok("Delete " +planRequest.getId()+ " Treatment plan id sucessfully");
    }
}
