package com.github.Timmy8.controller;

import com.github.Timmy8.controller.payload.NewClientPayload;
import com.github.Timmy8.entity.Client;

import java.util.List;

public class EntityFactory {
    public static List<Client> getClientsList(){

        Client client1 = new Client(1, "John", "Doe", "+375123456789", "Some description", false);
        Client client2 = new Client(2, "Jane", "Doe", "+375000000000", "Another description", true);
        return List.of(client1, client2);
    }
    public static Client getClient(){
        return new Client(1, "John", "Doe", "+375123456789", "Some description", false);
    }
}
