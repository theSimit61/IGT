package de.hsmannheim.igt.exercise.server.rest.controller;

import de.hsmannheim.igt.exercise.controller.DistrictController;
import de.hsmannheim.igt.exercise.models.District;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;


@Controller
@RequestMapping("/district")
public class DistrictRestController {
    
    @RequestMapping(value="/createDistrict",method = RequestMethod.GET)
    public @ResponseBody
    District createDistrict(@RequestParam(value = "name", required = true) String name, @RequestParam(value = "city", required = true) String city) {
        DistrictController distController = new DistrictController();
        District d = new District();
        d.setD_NAME(name);
        d.setD_CITY(city);
        d = distController.createDistrict(d);
        return d;
    }
    @RequestMapping(value="/getDistrict",method = RequestMethod.GET)
    public @ResponseBody
    District getDistrict(@RequestParam(value = "id", required = true) String id) {
        DistrictController districtController = new DistrictController();
        District d = districtController.getDistrict(id);
        return d;
    }

    @RequestMapping(value = "/updateDistrict", method = RequestMethod.PUT)
    public @ResponseBody
    String updateDistrict(@RequestParam(value = "id", required = true) String id,@RequestParam(value = "name", required = true) String name, @RequestParam(value = "city", required = true) String city) {
        DistrictController distController = new DistrictController();
        District d = distController.getDistrict(id);
        if (id == null) {
            return "The given district id was NOT found!";
        }
        d.setD_NAME(name);
        d.setD_CITY(city);
        distController.updateDistrict(id, d);
        return String.format("District with ID %s updated!\n", id);
    }

    @RequestMapping(value = "/deleteDistrict", method = RequestMethod.DELETE)
    public @ResponseBody
    String deleteDistrict(@RequestParam(value = "id", required = true) String id) {
        DistrictController districtController = new DistrictController();
        boolean wasDeleted = districtController.deleteDistrict(String.valueOf(id));

        if (wasDeleted) {
            return String.format("Customer with id %s was deleted successfully!\n", id);
        } else {
            return String.format("Customer with id %s could NOT be deleted!\n", id);
        }
    }

    @RequestMapping(value = "/getAllDistricts", method = RequestMethod.GET)
    public @ResponseBody
    Collection<District> getAllDistricts() {
        DistrictController districtController = new DistrictController();
        return districtController.getAllDistricts();
    }

    @RequestMapping(value = "/getDistrictByName", method = RequestMethod.GET)
    public @ResponseBody
    String getDistrictByName(@RequestParam(value = "name", required = true) String name) {
        DistrictController districtController = new DistrictController();
        return String.format(districtController.getDistrictByName(name).toString());
    }

}
