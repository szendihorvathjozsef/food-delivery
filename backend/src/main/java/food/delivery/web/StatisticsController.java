package food.delivery.web;

import food.delivery.entities.Order;
import food.delivery.repositories.DailyStatistics;
import food.delivery.repositories.OrderRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author szendihorvathjozsef
 */
@RestController
@RequestMapping("/statistics")
public class StatisticsController extends BaseController {

    private final OrderRepository orderRepository;

    public StatisticsController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    // mennyi termék egy napra
    // kettő időpont között mennyi volt a bevétel

    @GetMapping("/day/{date}")
    public void getUsedItemForDay(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        List<DailyStatistics> statistics = orderRepository.findAllByStartIsAfterAndEndIsBefore(
                startOfDay.toInstant(ZoneOffset.UTC),
                endOfDay.toInstant(ZoneOffset.UTC));
        statistics.forEach(System.out::println);
    }

    @GetMapping("/between/{startDate}/{endDate}")
    public void getIncome(@PathVariable LocalDate startDate, @PathVariable LocalDate endDate) {

    }
}
