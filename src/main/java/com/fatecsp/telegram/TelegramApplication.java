package com.fatecsp.telegram;

import com.fatecsp.telegram.bot.TelegramBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mashape.unirest.http.exceptions.UnirestException;
import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
public class TelegramApplication {

	public static void main(String[] args) {
		SpringApplication.run(TelegramApplication.class, args);

		TelegramBot tb = new TelegramBot("962772047:AAHX0etYiVYAZcrd5DustAVfsAHysv2MB_8");
		try {
			tb.run();
		} catch (UnirestException ex) {
			Logger.getLogger(TelegramApplication.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
