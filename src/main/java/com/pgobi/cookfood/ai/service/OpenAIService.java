package com.pgobi.cookfood.ai.service;

import com.pgobi.cookfood.ai.model.ReceiptsResponse;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OpenAIService {

    private String promptTemplate;

    private final ChatClient chatClient;

    public OpenAIService(ChatClient chatClient) {
        this.chatClient = chatClient;

    }
    public ReceiptsResponse getReceiptsAI(String category, Integer people) {

        final String promptText = """
            Tell me a three recipes for {category} for {people} people."
            {format}
            """;

        BeanOutputParser<ReceiptsResponse> outputParser  = new BeanOutputParser<>(ReceiptsResponse.class);

        final PromptTemplate promptTemplate = new PromptTemplate(promptText);
        promptTemplate.add("category", category);
        promptTemplate.add("people", people);
        promptTemplate.add("format", outputParser.getFormat());
        promptTemplate.setOutputParser(outputParser);

        final Prompt prompt = promptTemplate.create();
        ChatResponse response = chatClient.call(prompt);;
        return outputParser.parse(response.getResult().getOutput().getContent());
    }

}
