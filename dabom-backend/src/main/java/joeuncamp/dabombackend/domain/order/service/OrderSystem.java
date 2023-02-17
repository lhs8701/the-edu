package joeuncamp.dabombackend.domain.order.service;

import joeuncamp.dabombackend.domain.order.dto.OrderDto;
import joeuncamp.dabombackend.domain.order.entity.Item;
import joeuncamp.dabombackend.domain.order.entity.Order;
import joeuncamp.dabombackend.domain.order.repository.ItemJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CBadRequestException;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderSystem {
    @Autowired
    List<OrderService> orderServices;
    @Autowired
    List<PostOrderManager> postOrderManagers;
    private final ItemJpaRepository itemJpaRepository;

    public PostOrderManager findPostOrderManager(Item item) {
        for (PostOrderManager postOrderManager : postOrderManagers) {
            if (postOrderManager.supports(item)) {
                return postOrderManager;
            }
        }
        throw new CBadRequestException("OrderManager 매핑 실패");
    }

    public OrderService findOrderService(Item item) {
        for (OrderService orderService : orderServices) {
            if (orderService.supports(item)) {
                return orderService;
            }
        }
        throw new CBadRequestException("OrderService 매핑 실패");
    }

    /**
     * 주문서 정보를 바탕으로 주문 내역을 생성한 후, 상품 별 알맞은 후속 조치를 취합니다.
     *
     * @param requestDto 주문서 정보
     */
    public void makeOrder(OrderDto.Request requestDto) {
        Item item = itemJpaRepository.findById(requestDto.getItemId()).orElseThrow(CResourceNotFoundException::new);
        OrderService orderService = findOrderService(item);
        PostOrderManager postOrderManager = findPostOrderManager(item);
        Order order = orderService.saveOrder(requestDto);
        postOrderManager.doAfterAction(order);
    }
}
