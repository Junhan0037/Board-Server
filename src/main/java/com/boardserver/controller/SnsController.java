package com.boardserver.controller;

import com.boardserver.config.AWSConfig;
import com.boardserver.service.SlackService;
import com.boardserver.service.SnsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.*;

import java.util.Map;

@RestController
@RequestMapping("/api/sns")
@RequiredArgsConstructor
@Log4j2
public class SnsController {

    private final AWSConfig awsConfig;
    private final SnsService snsService;
    private final SlackService slackService;

    @PostMapping("/create-topic")
    public ResponseEntity<String> createTopic(@RequestParam String topicName) {
        CreateTopicRequest createTopicRequest = CreateTopicRequest.builder().name(topicName).build();
        SnsClient snsClient = snsService.getSnsClient();
        CreateTopicResponse createTopicResponse = snsClient.createTopic(createTopicRequest);

        if (!createTopicResponse.sdkHttpResponse().isSuccessful()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, createTopicResponse.sdkHttpResponse().statusText().get());
        }

        log.info("topic name = " + createTopicResponse.topicArn());
        log.info("topic list = " + snsClient.listTopics());
        snsClient.close();

        return new ResponseEntity<>("TOPIC CREATING SUCCESS", HttpStatus.OK);
    }

    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribe(@RequestParam String endpoint, @RequestParam String topicArn) {
        SubscribeRequest subscribeRequest = SubscribeRequest.builder()
                .protocol("https")
                .topicArn(topicArn)
                .endpoint(endpoint)
                .build();
        SnsClient snsClient = snsService.getSnsClient();
        SubscribeResponse subscribeResponse = snsClient.subscribe(subscribeRequest);

        if (!subscribeResponse.sdkHttpResponse().isSuccessful()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, subscribeResponse.sdkHttpResponse().statusText().get());
        }

        log.info("topicARN to subscribe = " + subscribeResponse.subscriptionArn());
        log.info("subscription list = " + snsClient.listSubscriptions());
        snsClient.close();

        return new ResponseEntity<>("TOPIC SUBSCRIBE SUCCESS", HttpStatus.OK);
    }

    @PostMapping("/publish")
    public String publish(@RequestParam String topicArn, @RequestBody Map<String, Object> message) {
        SnsClient snsClient = snsService.getSnsClient();
        PublishRequest publishRequest = PublishRequest.builder()
                .topicArn(topicArn)
                .subject("HTTP ENDPOINT TEST MESSAGE")
                .message(message.toString())
                .build();
        PublishResponse publishResponse = snsClient.publish(publishRequest);

        log.info("message status: " + publishResponse.sdkHttpResponse().statusCode());
        snsClient.close();

        return "sent MSG ID = " + publishResponse.messageId();
    }

    @GetMapping("/slack/error")
    public void error() {
        log.info("슬랙 error 채널 리스트");
        slackService.sendSlackMessage("슬랙 에러 테스트", "error");
    }

}
