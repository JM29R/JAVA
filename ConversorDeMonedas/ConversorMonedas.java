package com.example;

import java.net.URI;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ConversorMonedas {
    private String APIurl;
    private float ARS;
    private float COP;
    private float BRL;

    public ConversorMonedas(String url) {
        this.APIurl = url;
        cargarValores();

    }

    private void cargarValores() {
        try {
            HttpClient cliente = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(APIurl))
                    .GET()
                    .build();
            HttpResponse<String> response = cliente.send(request, HttpResponse.BodyHandlers.ofString());

            JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
            JsonObject rates = json.getAsJsonObject("conversion_rates");
            this.ARS = rates.get("ARS").getAsFloat();
            this.COP = rates.get("COP").getAsFloat();
            this.BRL = rates.get("BRL").getAsFloat();

        } catch (IOException | InterruptedException e) {
            System.out.println("Error al conectarse a la API" + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("Error: Una Moneda no fue encontrada" + e.getMessage());
        }
    }

    public float getARS() {
        return ARS;
    }

    public float getCOP() {
        return COP;
    }

    public float getBRL() {
        return BRL;
    }

    public float DLRtoARS(float cantidad) {
        return cantidad * ARS;
    }

    public float ARStoDLR(float cantidad) {
        return cantidad / ARS;
    }

    public float DLRtoBRL(float cantidad) {
        return cantidad * BRL;
    }

    public float BRLtoDLR(float cantidad) {
        return cantidad / BRL;
    }

    public float DLRtoCOP(float cantidad) {
        return cantidad * COP;
    }

    public float COPtoDLR(float cantidad) {
        return cantidad / COP;
    }

}
