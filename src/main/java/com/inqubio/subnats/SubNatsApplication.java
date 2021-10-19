package com.inqubio.subnats;

import io.nats.client.*;
import io.nats.client.Connection;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

@SpringBootApplication
public class SubNatsApplication {

	List<Message> messages = new ArrayList<>();
	String SQL_SELECT = "INSERT into nats_db VALUES " + " (?,?)";
	int count = 0;

	public static void main(String[] args) {
		SpringApplication.run(SubNatsApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner() {
		return args -> {
			Connection connect = Nats.connect("nats://0.0.0.0:4222");
			Dispatcher dispatcher = connect.createDispatcher();
			dispatcher.subscribe("world", message -> {
				System.out.println(String.format("Received Message[%s] from [%s]",
						new String(message.getData(), StandardCharsets.UTF_8), message.getSubject()));
				Date date = new Date();
				Timestamp ts = new Timestamp(date.getTime());
				//System.out.println("Received time: " + ts.toString());

				// try(java.sql.Connection conn =
				// DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/natsDB",
				// "postgres", "utt@1234"); PreparedStatement preparedStatement =
				// conn.prepareStatement(SQL_SELECT)) {
				// preparedStatement.setInt(1, count);
				// preparedStatement.setString(2, new String(message.getData()));
				// preparedStatement.addBatch();
				// } catch (Exception e) {
				// //TODO: handle exception
				// }
				messages.add(message);
			});

		};
	}

}
