package com.ecommerce.shopping.customerproducts;

import com.ecommerce.shopping.customerproducts.datatransferobject.CustomerDTO;
import com.ecommerce.shopping.customerproducts.datatransferobject.ProductDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = CustomerProductsApplication.class)
@AutoConfigureMockMvc
class CustomerProductsApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	@Test
	void testCustomerFindAll() throws Exception {
		mockMvc.perform(get("/customers")
				.accept(APPLICATION_JSON)
				.contentType(APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	void testCustomerCreateCustomerKO() throws Exception {
		CustomerDTO customerDTO = new CustomerDTO();
		mockMvc.perform(post("/customers")
						.accept(APPLICATION_JSON)
						.contentType(APPLICATION_JSON)
						.content(mapper.writeValueAsString(customerDTO)))
				.andExpect(status().is4xxClientError());
	}

	@Test
	void testCustomerCreateCustomerOK() throws Exception {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setTitle("MOCK");
		mockMvc.perform(post("/customers")
						.accept(APPLICATION_JSON)
						.contentType(APPLICATION_JSON)
						.content(mapper.writeValueAsString(customerDTO)))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	void testCustomerUpdateCustomerOK() throws Exception {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setTitle("MOCK11");
		String customerId = "f9497d49-3fa2-449f-8f7c-3e4311b1cde7";
		mockMvc.perform(put("/customers/" + customerId)
						.accept(APPLICATION_JSON)
						.contentType(APPLICATION_JSON)
						.content(mapper.writeValueAsString(customerDTO)))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	void testCustomerUpdateCustomerKOIdNotFound() throws Exception {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setTitle("MOCK8");
		String customerId = "f9497d49-3fa2-449f-8f7c-3e4311b1cde8";
		mockMvc.perform(put("/customers/" + customerId)
						.accept(APPLICATION_JSON)
						.contentType(APPLICATION_JSON)
						.content(mapper.writeValueAsString(customerDTO)))
				.andExpect(status().is4xxClientError());
	}

	@Test
	void testCustomerUpdateCustomerKOEmptyTitle() throws Exception {
		CustomerDTO customerDTO = new CustomerDTO();
		String customerId = "f9497d49-3fa2-449f-8f7c-3e4311b1cde7";
		mockMvc.perform(put("/customers/" + customerId)
						.accept(APPLICATION_JSON)
						.contentType(APPLICATION_JSON)
						.content(mapper.writeValueAsString(customerDTO)))
				.andExpect(status().is4xxClientError());
	}

	@Test
	void testCustomerDeleteCustomerOK() throws Exception {
		String customerId = "0c83704f-bfc1-4ee0-8efe-1d8be1732fb8";
		mockMvc.perform(delete("/customers/" + customerId)
						.accept(APPLICATION_JSON)
						.contentType(APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	void testCustomerDeleteCustomerKOIdNotFound() throws Exception {
		String customerId = "0c83704f-bfc1-4ee0-8efe-1d8be1732fb9";
		mockMvc.perform(delete("/customers/" + customerId)
						.accept(APPLICATION_JSON)
						.contentType(APPLICATION_JSON))
				.andExpect(status().is4xxClientError());
	}

	@Test
	void testCustomerFindCustomerOK() throws Exception {
		String customerId = "a6021d18-e340-42c6-a866-dbd3854347f5";
		mockMvc.perform(get("/customers/" + customerId)
						.accept(APPLICATION_JSON)
						.contentType(APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	void testCustomerFindCustomerKOIdNotFound() throws Exception {
		String customerId = "a6021d18-e340-42c6-a866-dbd3854347f6";
		mockMvc.perform(get("/customers/" + customerId)
						.accept(APPLICATION_JSON)
						.contentType(APPLICATION_JSON))
				.andExpect(status().is4xxClientError());
	}

	@Test
	void testProductsGetProductsByCustomerOK() throws Exception {
		String customerId = "123e4567-e89b-12d3-a456-556642440000";
		mockMvc.perform(get("/customers/" + customerId + "/products")
						.accept(APPLICATION_JSON)
						.contentType(APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	void testProductsGetProductsByCustomerKOIdNotFound() throws Exception {
		String customerId = "a6021d18-e340-42c6-a866-dbd3854347f6";
		mockMvc.perform(get("/customers/" + customerId + "/products")
						.accept(APPLICATION_JSON)
						.contentType(APPLICATION_JSON))
				.andExpect(status().is4xxClientError());
	}

	@Test
	void testProductsCreateProductByCustomerOK() throws Exception {
		ProductDTO dto = new ProductDTO();
		dto.setTitle("Mock");
		dto.setDescription("MOCK");
		dto.setPrice(BigDecimal.TEN);
		String customerId = "123e4567-e89b-12d3-a456-556642440000";
		mockMvc.perform(post("/customers/" + customerId + "/products")
				.accept(APPLICATION_JSON)
				.contentType(APPLICATION_JSON)
				.content(mapper.writeValueAsString(dto)))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	void testProductsCreateProductByCustomerKO() throws Exception {
		ProductDTO dto = new ProductDTO();
		dto.setTitle("Mock");
		dto.setDescription("MOCK");
		dto.setPrice(BigDecimal.TEN);
		String customerId = "123e4567-e89b-12d3-a456-556642440001";
		mockMvc.perform(post("/customers/" + customerId + "/products")
						.accept(APPLICATION_JSON)
						.contentType(APPLICATION_JSON)
						.content(mapper.writeValueAsString(dto)))
				.andExpect(status().is4xxClientError());
	}

	@Test
	void testProductUpdateProductOK() throws Exception {
		ProductDTO dto = new ProductDTO();
		dto.setTitle("Mock");
		dto.setDescription("MOCK");
		dto.setPrice(BigDecimal.TEN);
		String productId = "97319968-6978-4135-8464-3b5ca5a2ae7e";
		mockMvc.perform(put("/products/" + productId)
						.accept(APPLICATION_JSON)
						.contentType(APPLICATION_JSON)
						.content(mapper.writeValueAsString(dto)))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	void testProductUpdateProductKO() throws Exception {
		ProductDTO dto = new ProductDTO();
		dto.setTitle("Mock");
		dto.setDescription("MOCK");
		dto.setPrice(BigDecimal.TEN);
		String productId = "97319968-6978-4135-8464-3b5ca5a2ae7f";
		mockMvc.perform(put("/products/" + productId)
						.accept(APPLICATION_JSON)
						.contentType(APPLICATION_JSON)
						.content(mapper.writeValueAsString(dto)))
				.andExpect(status().is4xxClientError());
	}

	@Test
	void testProductDeleteProductOK() throws Exception {
		String productId = "97319968-6978-4135-8464-3b5ca5a2ae7e";
		mockMvc.perform(delete("/products/" + productId)
						.accept(APPLICATION_JSON)
						.contentType(APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	void testProductDeleteProductKO() throws Exception {
		String productId = "97319968-6978-4135-8464-3b5ca5a2ae7f";
		mockMvc.perform(delete("/products/" + productId)
						.accept(APPLICATION_JSON)
						.contentType(APPLICATION_JSON))
				.andExpect(status().is4xxClientError());
	}

	@Test
	void testProductFindProductOK() throws Exception {
		String productId = "97319968-6978-4135-8464-3b5ca5a2ae7e";
		mockMvc.perform(get("/products/" + productId)
						.accept(APPLICATION_JSON)
						.contentType(APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	void testProductFindProductKO() throws Exception {
		String productId = "97319968-6978-4135-8464-3b5ca5a2ae7f";
		mockMvc.perform(get("/products/" + productId)
						.accept(APPLICATION_JSON)
						.contentType(APPLICATION_JSON))
				.andExpect(status().is4xxClientError());
	}
}
