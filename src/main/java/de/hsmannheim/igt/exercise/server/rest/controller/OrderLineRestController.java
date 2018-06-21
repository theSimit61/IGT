package de.hsmannheim.igt.exercise.server.rest.controller;

import de.hsmannheim.igt.exercise.models.Stock;
import de.hsmannheim.igt.exercise.util.DefaultHashMap;
import de.hsmannheim.igt.exercise.controller.CustomerController;
import de.hsmannheim.igt.exercise.controller.ItemController;
import de.hsmannheim.igt.exercise.controller.OrderLineController;
import de.hsmannheim.igt.exercise.models.Item;
import de.hsmannheim.igt.exercise.models.OrderLine;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/orderline")
public class OrderLineRestController {

    @RequestMapping(value="/createOrderline",method = RequestMethod.GET)
    public @ResponseBody
    OrderLine createOrderline(@RequestParam(value = "customer", required = true) String customerID,
                          @RequestParam(value = "items" ) String items) {
        OrderLineController orderLineController = new OrderLineController();
        OrderLine c = new OrderLine();

        c.setO_NEWORDER(true);
        c.setO_CUSTOMER(new CustomerController().getCustomer(customerID));
        List<Stock> itemsList = new ArrayList<>();
        String[] itemSplit = items.split(",");
        Map<Item,Integer > frequencies = new DefaultHashMap<>(0);
        for(String s : itemSplit){
            Item i = new ItemController().getItemByName(s);
            int frequency = frequencies.get(i);
            frequencies.put(i,frequency+1);
        }
        for(Item i : frequencies.keySet()){
           Stock stock = new Stock();
           stock.setST_ITEM_AMOUNT(frequencies.get(i));
           stock.setST_ITEM_ID(i);
           itemsList.add(stock);
        }
        c.setO_ITEMS(itemsList);
        c = orderLineController.createOrderLine(c);
        return c;
    }

    @RequestMapping(value = "/getAllOrderLines", method = RequestMethod.GET)
    public @ResponseBody
    Collection<OrderLine> getAllOrderLines() {
        OrderLineController orderLineController = new OrderLineController();
        return orderLineController.getAllOrderLines();
    }

    @RequestMapping(value = "/deleteOrderLine", method = RequestMethod.DELETE)
    public @ResponseBody
    String deleteOrderLine(@RequestParam(value = "id") String id) {
        OrderLineController orderlineController = new OrderLineController();
        orderlineController.deleteOrderLine(id);
        return String.format("The item '%s' was deleted successfully!", id);
    }

    @RequestMapping(value="/getOrderLine",method = RequestMethod.GET)
    public @ResponseBody
    OrderLine getOrderLine(@RequestParam(value = "id", required = true) String id) {
        OrderLineController orderlineController = new OrderLineController();
        OrderLine o = orderlineController.getOrderLine(id);
        return o;
    }

    // o
    @RequestMapping(value = "/updateOrderLine", method = RequestMethod.PUT)
    public @ResponseBody
    String updateOrderLine(@RequestParam(value = "id", required = true) String id,@RequestParam(value = "newOrder", required = true) boolean newOrder) {
        OrderLineController orderLineController = new OrderLineController();
        OrderLine ol = orderLineController.getOrderLine(id);
        if (id == null) {
            return "The given OrderLine id was NOT found!";
        }
        ol.setO_NEWORDER(newOrder);
        orderLineController.updateOrderLine(id, ol);
        return String.format("OrderLine with ID %s updated!\n", id);
    }

    }
