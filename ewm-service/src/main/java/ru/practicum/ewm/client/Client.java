package ru.practicum.ewm.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.ewm.dto.stats.EndpointHit;
import ru.practicum.ewm.dto.stats.ViewStats;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class Client {

    private final WebClient webClient;

    @Autowired
    public Client(@Value("${STATS.URL}") String serverUrl) {
        webClient = WebClient.builder().baseUrl(serverUrl).build();
    }

    public void sendHit(EndpointHit endpointHit) {
        webClient.post()
                .uri("/hit")
                .header("Content-Type", "application/json")
                .body(BodyInserters.fromValue(endpointHit))
                .retrieve()
                .bodyToMono(EndpointHit.class)
                .block();
    }

    public List<ViewStats> get(String start, String end, List<String> uris, Boolean unique) {
        return webClient
                .get()
                .uri("/stats?start=" + start + "&end=" + end + "&uris=" + String.join(", ", uris)
                                .replace("{", "")
                                .replace("}", "")+ "&unique=" + unique)
                .retrieve()
                .bodyToFlux(ViewStats.class)
                .collect(Collectors.toList())
                .block();
    }
}