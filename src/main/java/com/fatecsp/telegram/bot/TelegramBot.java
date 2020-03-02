package com.fatecsp.telegram.bot;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpRequest;


public final class TelegramBot {

    private final String endpoint = "https://api.telegram.org/";
    private final String token;
    private HttpRequest httpRequest;

    public TelegramBot(String token) {
        this.token = token;
    }

    public HttpResponse sendMessage(Integer chatId, String text) throws UnirestException {
        return (HttpResponse) Unirest.post(endpoint + "bot" + token + "/sendMessage")
                .field("chat_id", chatId)
                .field("text", text)
                .asJson();
    }

    public HttpResponse getUpdates(Integer offset) throws UnirestException {
        return (HttpResponse) Unirest.post(endpoint + "bot" + token + "/getUpdates")
                .field("offset", offset)
                .asJson();
    }

    public void run() throws UnirestException {
        int last_update_id = 0; // controle das mensagens processadas
        HttpResponse response;
        while (true) {
            response = getUpdates(last_update_id++);

            if (response.getStatus() == 200) {

                // JSONArray responses = response.getBody().getObject().getJSONArray("result");
                System.out.println(response.getBody().toString());
                JSONObject responseObject = new JSONObject(response.getBody().toString());
                JSONArray responses = responseObject.getJSONArray("result");
                if (responses.isNull(0)) {
                    continue;
                } else {
                    last_update_id = responses
                            .getJSONObject(responses.length() - 1)
                            .getInt("update_id") + 1;
                }

                for (int i = 0; i < responses.length(); i++) {
                    JSONObject message = responses
                            .getJSONObject(i)
                            .getJSONObject("message");
                    int chat_id = message
                            .getJSONObject("chat")
                            .getInt("id");
                    String usuario = message
                            .getJSONObject("chat")
                            .getString("first_name");
                    String texto = message
                            .getString("text");



                    String textoInvertido = "";

                    for (int j = texto.length() - 1; j >= 0; j--) {
                        textoInvertido += texto.charAt(j);
                    }

                    sendMessage(chat_id, textoInvertido);
                }
            }
        }
    }
}
