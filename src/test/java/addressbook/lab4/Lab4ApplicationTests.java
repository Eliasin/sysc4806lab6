package addressbook.lab4;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class Lab4ApplicationTests {

	private static String jsonStringify(Object o) throws JsonProcessingException {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		return ow.writeValueAsString(o);
	}

	@Autowired
	private MockMvc mockMvc;

	@Test
	void addressBookOperations() throws Exception {
		this.mockMvc.perform(post("/addressbooks")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		this.mockMvc.perform(post("/addressbooks/add/1").contentType(MediaType.APPLICATION_JSON)
				.content(jsonStringify(new BuddyInfo("tim", "2313"))))
				.andExpect(status().isOk());

		AddressBook addressBook = new AddressBook();
		addressBook.id = 1;
		addressBook.addBuddy(new BuddyInfo("tim", "2313"));
		// Assume first object created has id 1
		String responseString = this.mockMvc.perform(get("/addressbooks/1").accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse().getContentAsString();

		ObjectReader objectReader = new ObjectMapper().reader();
		AddressBook response = objectReader.readValue(responseString, AddressBook.class);
		assertThat(response.equals(addressBook));
	}
}
