package de.hsmannheim.igt.exercise.server.rest.controller;

import de.hsmannheim.igt.exercise.controller.DistrictController;
import de.hsmannheim.igt.exercise.controller.StockController;
import de.hsmannheim.igt.exercise.controller.ItemController;
import de.hsmannheim.igt.exercise.controller.WarehouseController;
import de.hsmannheim.igt.exercise.models.District;
import de.hsmannheim.igt.exercise.models.Stock;
import de.hsmannheim.igt.exercise.models.Item;
import de.hsmannheim.igt.exercise.models.Warehouse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

@Controller
@RequestMapping("/warehouse")
public class WarehouseRestController {

    @RequestMapping(value="/createWarehouse",method = RequestMethod.GET)
    public @ResponseBody
    Warehouse createWarehouse (@RequestParam(value = "name", required = true) String name,
                               @RequestParam(value = "city", required = true) String city,
                               @RequestParam(value = "district", required = true) String district,
                               @RequestParam(value = "stock", required = true) String stock) {
        WarehouseController warehouseController = new WarehouseController();
        Warehouse warehouse = new Warehouse();
        warehouse.setW_NAME(name);
        warehouse.setW_CITY(city);
        District dis = new DistrictController().getDistrictByName(district);
        Stock st = new StockController().getStock(stock);
        warehouse.setW_District(dis);
        warehouse.addInventory(st);
        warehouse = warehouseController.createWarehouse(warehouse);
        return warehouse;
    }

    @RequestMapping(value = "/getWarehouse", method = RequestMethod.GET)
    public @ResponseBody
    Warehouse getWarehouse (@RequestParam(value = "id") String id) {
        WarehouseController warehouseController = new WarehouseController();
        Warehouse warehouse = warehouseController.getWarehouse(id);
        if (warehouse == null) {
            throw new IllegalArgumentException(String.format("Warehouse with ID %s could NOT be found!", id));
        } else {
            return warehouse;
        }
    }

    @RequestMapping(value = "/addStock", method = RequestMethod.GET)
    public @ResponseBody
    Warehouse addStock(@RequestParam(value = "warehouse") String warehouseID,
                        @RequestParam(value = "itemID") String itemID,
                        @RequestParam(value = "amount") String amount) {

        WarehouseController warehouseController = new WarehouseController();
        Warehouse warehouse = warehouseController.getWarehouse(warehouseID);
        if (warehouse == null) {
            throw new IllegalArgumentException(String.format("Warehouse with ID %s could NOT be found!", warehouseID));
        }

        StockController stController = new StockController();
        ItemController itemController = new ItemController();
        Item item = itemController.getItem(itemID);
        Stock stock = new Stock();

        stock.setST_ITEM_AMOUNT(Integer.parseInt(amount));
        stock.setST_ITEM_ID(item);
        stock = stController.createStock(stock);
        warehouseController.updateWarehouse(warehouse.getW_ID(), stock);

        return  warehouseController.getWarehouse(warehouseID);

    }
    @RequestMapping(value = "/getAllWarehouses", method = RequestMethod.GET)
    public @ResponseBody
    Collection<Warehouse> getAllWarehouses() {
        WarehouseController customerController = new WarehouseController();
        return customerController.getAllWareHouses();
    }

    @RequestMapping(value = "/deleteWarehouse", method = RequestMethod.DELETE)
    public @ResponseBody
    String deleteWarehouse(@RequestParam(value = "id") String id) {
        WarehouseController warehouseController = new WarehouseController();
        warehouseController.deleteWarehouse(id);
        return String.format("The Warehouse '%s' was deleted successfully!", id);
    }


    @RequestMapping(value = "/updateWarehouse", method = RequestMethod.PUT)
    public @ResponseBody
    String updateWarehouse(@RequestParam(value = "id", required = true) String id,@RequestParam(value = "name", required = true) String name, @RequestParam(value = "city", required = true) String city) {
        WarehouseController wareController = new WarehouseController();
        Warehouse w = wareController.getWarehouse(id);
        if (id == null) {
            return "The given Warehouse id was NOT found!";
        }
        w.setW_NAME(name);
        w.setW_CITY(city);
        wareController.updateWarehouse(id, w);
        return String.format("Warehouse with ID %s updated!\n", id);
    }

}
