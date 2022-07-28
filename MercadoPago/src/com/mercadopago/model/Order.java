package com.mercadopago.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class Order {
	
	private String id;
	
	public static void createOrder(MercadoPago mercadoPago, Box box, Integer amount, 
									String esquema, String idNotificacion) throws Exception {
		JsonObject jsonOrder = new JsonObject();
		jsonOrder.addProperty("external_reference", "order-id-1234");
		jsonOrder.addProperty( "title", "Title");
		jsonOrder.addProperty( "description", "Mercado Pago");
		jsonOrder.addProperty( "notification_url", "https://clouderpgo.com:8443/CloudFactory/notificacionesml.jsp?esquema=" + esquema + "&id=" + idNotificacion);
		//jsonOrder.addProperty( "expiration_date", "2023-08-22T16:34:56.559-04:00");
		jsonOrder.addProperty( "total_amount", amount);
		JsonObject jsonItem = new JsonObject();
		jsonItem.addProperty("sku_number", "KS955RUR");
		jsonItem.addProperty("category", "FOOD");
		jsonItem.addProperty("title", "Item1");
		jsonItem.addProperty("description", "Item1 Mercado Pago");
		jsonItem.addProperty("unit_price", amount);
		jsonItem.addProperty("quantity", 1);
		jsonItem.addProperty("unit_measure", "unit");
		jsonItem.addProperty("total_amount", amount);
		JsonArray jsonItems = new JsonArray();
		jsonItems.add(jsonItem);
		jsonOrder.add("items", jsonItems);
//		JsonObject jsonSponsor = new JsonObject();
//		jsonSponsor.addProperty("id", Integer.valueOf(box.getId()));
//		jsonOrder.add("sponsor", jsonSponsor);
	
		MediaType JSON = MediaType.parse("application/json; charset=utf-8");
		RequestBody requestBody = RequestBody.create(JSON, jsonOrder.toString());
		Request request = new Request.Builder()
				  .url("https://api.mercadopago.com/instore/qr/seller/collectors/"+ box.getUser_id()
				  			+ "/stores/"+ box.getExternal_store_id()
				  			+ "/pos/" + box.getExternal_id() 
				  			+ "/orders")
				  .method("PUT", requestBody)
				  .addHeader("Authorization", "Bearer " + mercadoPago.getToken())
				  .build();
		
		MercadoPago.sendRequest(request);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
