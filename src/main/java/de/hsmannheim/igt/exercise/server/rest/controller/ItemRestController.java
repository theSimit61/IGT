package de.hsmannheim.igt.exercise.server.rest.controller;

import de.hsmannheim.igt.exercise.controller.ItemController;
import de.hsmannheim.igt.exercise.models.Item;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/item")
public class ItemRestController {

    @RequestMapping(value="/getItem",method = RequestMethod.GET)
    public @ResponseBody
    Item getItem(@RequestParam(value = "id", required = true) String id) {
        ItemController itemController = new ItemController();
        Item i = itemController.getItem(id);
        return i;
    }
    @RequestMapping(value="/getItemByName",method = RequestMethod.GET)
    public @ResponseBody
    Item getItemByName(@RequestParam(value = "name", required = true) String name) {
        ItemController itemController = new ItemController();
        Item i = itemController.getItemByName(name);
        return i;
    }

    @RequestMapping(value="/createItem",method = RequestMethod.GET)
    public @ResponseBody
    Item createItem(@RequestParam(value = "name", required = true) String name) {
        ItemController itemController = new ItemController();
        Item item = new Item();
        item.setI_NAME(name);
        Item i = itemController.createItem(item);
        return i;
    }

    @RequestMapping(value = "/getAllItems", method = RequestMethod.GET)
    public @ResponseBody
    String getAllItems() {
        StringBuilder output = new StringBuilder();
        ItemController itemController = new ItemController();
        for (Item item : itemController.getAllItems()) {
            output.append(item).append("\n");
        }
        if (output.toString().length() == 0) {
            return "No items present yet!";
        } else {
            return output.toString();
        }
    }

    @RequestMapping(value = "/deleteItem", method = RequestMethod.DELETE)
    public @ResponseBody
    String deleteItem(@RequestParam(value = "id") String id) {
        ItemController itemController = new ItemController();
        itemController.deleteItem(id);
        return String.format("The item '%s' was deleted successfully!", id);
    }

    @RequestMapping(value = "/updateItem", method = RequestMethod.PUT)
    public @ResponseBody
    String updateItem(@RequestParam(value = "id") String id, @RequestParam(value = "newName") String newName) {
        ItemController itemController = new ItemController();
        Item item = itemController.getItem(id);
        if (item == null) {
            return "The given customer id was NOT found!";
        }
        item.setI_NAME(newName);
        itemController.updateItem(id, item);
        return String.format("Customer with ID %s updated!\n", id);
    }
}
