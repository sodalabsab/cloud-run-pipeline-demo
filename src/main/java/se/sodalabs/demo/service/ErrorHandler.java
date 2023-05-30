package se.sodalabs.demo.service;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

public class ErrorHandler implements ResponseErrorHandler {
  Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

  @Override
  public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
    return (httpResponse.getStatusCode().is4xxClientError()
        || httpResponse.getStatusCode().is5xxServerError());
  }

  @Override
  public void handleError(ClientHttpResponse httpResponse) throws IOException {
    if (httpResponse.getStatusCode().is5xxServerError()) {
      logger.error(httpResponse.getStatusText() + httpResponse.getStatusCode());
    } else if (httpResponse.getStatusCode().is4xxClientError()) {
      logger.error(httpResponse.getStatusText() + httpResponse.getStatusCode());
    }
  }
}
