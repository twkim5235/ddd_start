package com.example.ddd_start.order.presentation;

import com.example.ddd_start.common.domain.exception.ValidationErrorException;
import com.example.ddd_start.order.application.model.ChangeOrderShippingInfoCommand;
import com.example.ddd_start.order.application.model.PlaceOrderCommand;
import com.example.ddd_start.order.application.service.OrderService;
import com.example.ddd_start.order.presentation.model.PlaceOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @GetMapping("/orders")
  public void findAllOrders() {
    orderService.findOrders();
  }

  @PostMapping("/orders/place-order")
  public Long order(@RequestBody PlaceOrderRequest req, BindingResult bindingResult) {
    try {
      Long orderId = orderService.placeOrderV2(
          new PlaceOrderCommand(
              req.getOrderLines(),
              req.getShippingInfo(),
              req.getOrderer(),
              null));

      return orderId;
    } catch (ValidationErrorException e) {
      e.getErrors().forEach(err -> {
        if (err.hasName()) {
          bindingResult.rejectValue(err.getPropertyName(), err.getValue());
        } else {
          bindingResult.reject(err.getValue());
        }
      });

      throw new RuntimeException(e);
    }
  }

  @PostMapping("/orders/shipping-info")
  public void changeShippingInfo(ChangeOrderShippingInfoCommand command) {
    orderService.changeShippingInfo(command);
  }
}
