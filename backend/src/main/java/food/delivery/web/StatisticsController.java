package food.delivery.web;

import food.delivery.exceptions.BadRequestAlertException;
import food.delivery.repositories.DailyStatistics;
import food.delivery.repositories.OrderRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/day/{date}")
    public ResponseEntity<List<DailyStatistics>> getUsedItemForDay(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        List<DailyStatistics> statistics = orderRepository.findAllByStartIsAfterAndEndIsBefore(
                startOfDay.toInstant(ZoneOffset.UTC),
                endOfDay.toInstant(ZoneOffset.UTC));
        return new ResponseEntity<>(statistics, HttpStatus.OK);
    }

    @GetMapping("/between/{startDate}/{endDate}")
    public void getIncome(@PathVariable String startDate, @PathVariable String endDate) {
        if ( startDate == null || endDate == null ) {
            throw new BadRequestAlertException("No path variables", "statistics", "datesnull");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-DD");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);
    }
}
