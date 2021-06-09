package example;

import org.springframework.web.bind.annotation.*;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class HelloController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    private final MeterRegistry registry;

    /**
     * We inject the MeterRegistry into this class
     */
    public HelloController(MeterRegistry registry) {
        this.registry = registry;
    }

    /**
     * The @Timed annotation adds timing support, so we can see how long
     * it takes to execute in Prometheus
     * percentiles
     */
    @GetMapping("/")
    @Timed(value = "greeting.time", description = "Time taken to return greeting",
            percentiles = {0.5, 0.90})
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
			counter.incrementAndGet();
      return String.format(template, name);
    }

}
