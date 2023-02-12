package ch.pact.pactconsumer;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.model.RequestResponsePact;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "test_provider", port = "1234")
public class ATest {

	@Pact(provider="test_provider", consumer="test_consumer")
	public RequestResponsePact createPact(PactDslWithProvider builder) {

		/*
			Describing the API, which is expected from the Producer to be fulfilled.
			Alias : defining a consumer-driven-contract.

			from here - the MOCK will be generated for tests against it.

			*Mock: a dummy piece of code that you verify is called correctly as part of the test.
			Substitutes for real working code.
		 */

		return builder
				.given("test state")
				.uponReceiving("ExampleJavaConsumerPactTest test interaction")
				.path("/")
				.method("GET")
				.willRespondWith()
				.status(200)
				.body("{\"body_response_test_key\": true}")
				.toPact();
	}

	@Test
	void test(MockServer mockServer) throws Exception {
		HttpResponse httpResponse = Request.Get(mockServer.getUrl() + "/").execute().returnResponse();
		assertThat(httpResponse.getStatusLine().getStatusCode(), is(equalTo(200)));
	}
}
