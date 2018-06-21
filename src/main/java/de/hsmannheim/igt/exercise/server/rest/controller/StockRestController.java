package de.hsmannheim.igt.exercise.server.rest.controller;

import de.hsmannheim.igt.exercise.controller.StockController;
import de.hsmannheim.igt.exercise.controller.ItemController;
import de.hsmannheim.igt.exercise.models.Stock;
import de.hsmannheim.igt.exercise.models.Item;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

@Controller
@RequestMapping("/stock")
public class StockRestController {

    @RequestMapping(value="/getStock",method = RequestMethod.GET)
    public @ResponseBody
    Stock getStock(@RequestParam(value = "id", required = true) String id) {
        StockController stockController = new StockController();
        Stock s = stockController.getStock(id);
        return s;
    }

    @RequestMapping(value="/createStock",method = RequestMethod.GET)
    public @ResponseBody
    Stock createStock(@RequestParam(value = "amount", required = true) String amount, @RequestParam(value = "item", required = true) String itemID) {
        StockController stController = new StockController();
        ItemController itemController = new ItemController();
        Item item = itemController.getItem(itemID);
        Stock stock = new Stock();

        stock.setST_ITEM_AMOUNT(Integer.parseInt(amount));
        stock.setST_ITEM_ID(item);
        stock = stController.createStock(stock);
        return stock;
    }

    @RequestMapping(value = "/getAllStocks", method = RequestMethod.GET)
    public @ResponseBody
    Collection<Stock> getAllStocks() {
        StockController stockController = new StockController();
        return stockController.getAllStocks();
    }

    @RequestMapping(value = "/deleteStock", method = RequestMethod.DELETE)
    public @ResponseBody
    String deleteStock(@RequestParam(value = "id", required = true) String id) {
        StockController stockController = new StockController();
        boolean wasDeleted = stockController.deleteStock(String.valueOf(id));
        if (wasDeleted) {
            return String.format("Customer with id %s was deleted successfully!\n", id);
        } else {
            return String.format("Customer with id %s could NOT be deleted!\n", id);
        }
    }


    @RequestMapping(value = "/updateStock", method = RequestMethod.PUT)
    public @ResponseBody
    String updateStock(@RequestParam(value = "id", required = true) String id,@RequestParam(value = "amount", required = true) String amount) {
        StockController stockController = new StockController();
        Stock st = stockController.getStock(id);
        if (id == null) {
            return "The given district id was NOT found!";
        }
        st.setST_ITEM_AMOUNT(Integer.parseInt(amount));
        stockController.updateStock(id, st);
        return String.format("Stock with ID %s updated!\n", id);
    }

}
